package com.example.demo.repositories;

import com.example.demo.Services.GoalService;
import com.example.demo.model.Game;
import com.example.demo.model.Goal;
import com.example.demo.model.Team;
import com.example.demo.series.serie3;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameRepository {

    @PersistenceContext
    private final EntityManager entityManager;
    private final GameRepositoryNew gameRepositoryNew;
    private final GoalService goalService;

    public GameRepository(EntityManager entityManager, GameRepositoryNew gameRepositoryNew, GoalService goalService) {
        this.entityManager = entityManager;
        this.gameRepositoryNew = gameRepositoryNew;
        this.goalService = goalService;
    }


    @Transactional
    public List<Team> roundFill() {
        List<Team> teamList = entityManager.createNativeQuery("SELECT * FROM team WHERE division_2018 IS NOT NULL", Team.class).getResultList();

        for (int k = 0; k<teamList.size();k++) {
            Long id = teamList.get(k).getId();
            List<Game> GameList = entityManager.createNativeQuery("SELECT * FROM game WHERE home="+id+" OR guest="+id+" AND season=2018",Game.class).getResultList();

            int i =1;
            for (Game g: GameList) {
                if (g.getHome() == Math.toIntExact(id)){
                    entityManager.detach(g);
                    g.setRound_home(i);
                    entityManager.merge(g);
                    i++;
                }else{
                    entityManager.detach(g);
                    g.setRound_guest(i);
                    entityManager.merge(g);
                    i++;
                }
            }
        }
        return teamList;
    }

    public Integer gameTeamID (Long gameId, String side){
        return (Integer) entityManager.createNativeQuery("SELECT " +side+ " FROM game WHERE id=" +gameId).getResultList().get(0);
    }

    public List<Goal> gameTeamGoalsPeriod(Long gameId, Integer teamId, Integer period){
        List<Goal> goalsForPeriod = goalService.goalsForPeriod(gameId,period);
        goalsForPeriod = goalsForPeriod.stream()
                                .filter(goal -> goal.getTeam() == teamId.longValue())
                                .collect(Collectors.toList());
        return goalsForPeriod;
    }

    public String periodResult (Long gameId, Integer period){
        Integer homeID = gameTeamID(gameId,"home");
        Integer guestID = gameTeamID(gameId,"guest");

        int home_goals = gameTeamGoalsPeriod(gameId,homeID,period).size();
        int guest_goals = gameTeamGoalsPeriod(gameId,guestID,period).size();

        return home_goals+":"+guest_goals;
    }

    public Integer periodBet (Long gameId, Integer period){
        Integer homeID = gameTeamID(gameId,"home");
        Integer guestID = gameTeamID(gameId,"guest");
        int home_goals = gameTeamGoalsPeriod(gameId,homeID,period).size();
        int guest_goals = gameTeamGoalsPeriod(gameId,guestID,period).size();
        int result = 0;
        if (home_goals>guest_goals) result=1;
        if (home_goals<guest_goals) result=2;
        return result;
    }

    public Integer gameBet(Long gameId, Integer samostatneNajezdy){
        Integer homeID = gameTeamID(gameId,"home");
        Integer guestID = gameTeamID(gameId,"guest");

        List<Goal> goalList = goalService.goalsFromGame(gameId);
        int home_goals=0, guest_goals=0;
        for (Goal g: goalList) {
            if(g.getTeam()==homeID.longValue()) home_goals++;
            if(g.getTeam()==guestID.longValue()) guest_goals++;
        }
        int result=0;
        if (goalService.goalsForPeriod(gameId, 4).isEmpty()){ //nebylo prodloužení
            if(home_goals>guest_goals) result=1;
            if(home_goals<guest_goals) result=2;
        }else{
            if(home_goals>guest_goals) result=10;
            if(home_goals<guest_goals) result=20;
        }
        // samostatne najezdy
        if (samostatneNajezdy ==1) result=10;
        if (samostatneNajezdy ==2) result=20;

        return result;
    }

    public String gameResult(Long gameId, Integer samostatneNajezdy){
        Integer homeID = gameTeamID(gameId,"home");
        Integer guestID = gameTeamID(gameId,"guest");

        List<Goal> goalsForGame = goalService.goalsFromGame(gameId);
        Long home_goals = goalsForGame.stream()
                .filter(goal -> goal.getTeam() == homeID.longValue())
                .count();
        Long guest_goals = goalsForGame.stream()
                .filter(goal -> goal.getTeam() == guestID.longValue())
                .count();

        Game game = gameRepositoryNew.findById(gameId).get();

        if (game.getProdlouzeni4sazka() != 0) return home_goals +":"+ guest_goals + "p";
        if (samostatneNajezdy ==1) return (home_goals+1) +":"+ guest_goals + "n";
        if (samostatneNajezdy ==2) return home_goals +":"+ (guest_goals+1) + "n";

        return home_goals+":"+guest_goals;
    }

    @Transactional
    public Game gameSave(Long gameId, Integer samostatneNajezdy) {
        Game game = gameRepositoryNew.findById(gameId).get();

        game.setTretina1sazka(periodBet(gameId,1));
        game.setTretina2sazka(periodBet(gameId,2));
        game.setTretina3sazka(periodBet(gameId,3));
        game.setProdlouzeni4sazka(periodBet(gameId,4));
        game.setTretina1vysledek(periodResult(gameId,1));
        game.setTretina2vysledek(periodResult(gameId,2));
        game.setTretina3vysledek(periodResult(gameId,3));
        game.setProdlouzeni4vysledek(periodResult(gameId,4));
        game.setVysledek_sazka(gameBet(gameId, samostatneNajezdy));
        game.setVysledek(gameResult(gameId, samostatneNajezdy));

        return game;
    }

    public List<Game> gamesForSeason(Long season){
        return entityManager.createNativeQuery("SELECT * FROM game WHERE season="+ season +" AND vysledek is null",Game.class).getResultList();
    }

    public List<Game> teamGames(Long teamId, Long season, String side){
        if (side.equals("home")) return entityManager.createNativeQuery("SELECT * FROM game WHERE home="+ teamId +" AND season="+ season,Game.class ).getResultList();

        return entityManager.createNativeQuery("SELECT * FROM game WHERE guest="+ teamId +" AND season="+ season,Game.class ).getResultList();
    }

    public String prubeznyVysledekZapasu (Long gameId){
        Game game = gameRepositoryNew.findById(gameId).get();
        int homeTeamGoals = entityManager.createNativeQuery("SELECT * FROM goal WHERE game="+gameId+" AND team="+game.getHome()).getResultList().size();
        int guestTeamGoals = entityManager.createNativeQuery("SELECT * FROM goal WHERE game="+gameId+" AND team="+game.getGuest()).getResultList().size();
        return homeTeamGoals+":"+guestTeamGoals;
    }

    //vyřeš si kdyžtak posunutí o víc než 1 opačný zápas
    public List<serie3> serie3List(List<Game> gameList, String strana){
        List<serie3> serie3s = new ArrayList<>();

        if (strana == "home"){
            System.out.println(gameList.get(0).getHome_team() +" - home");
            for (int i=0;i<gameList.size()-2;i++) {
                if (gameList.get(i).getRound_home() + 1 == gameList.get(i + 1).getRound_home() && //zjisti zda jsou zapasy za sebou
                        gameList.get(i).getRound_home() + 2 == gameList.get(i + 2).getRound_home()) {

                    serie3 serie = new serie3(gameList.get(i), gameList.get(i + 1), gameList.get(i + 2));
                    serie3s.add(serie);

                    System.out.println(gameList.get(i).getRound_home() + " " + gameList.get(i + 1).getRound_home() + " " + gameList.get(i + 2).getRound_home());

                    // hleda další serii
                    i = i + 2;
                    for (int j = i; j < gameList.size() - 2; j++) {
                        if (gameList.get(j).getRound_home() + 1 != gameList.get(j + 1).getRound_home()) {
                            i = j;
                            break;
                        }
                    }
                }
            }
        }

        if (strana == "guest"){
            System.out.println(gameList.get(0).getGuest_team() +" - guest");
            for (int i=0;i<gameList.size()-2;i++) {
                if (gameList.get(i).getRound_guest() + 1 == gameList.get(i + 1).getRound_guest() && //zjisti zda jsou zapasy za sebou
                        gameList.get(i).getRound_guest() + 2 == gameList.get(i + 2).getRound_guest()) {

                    serie3 serie = new serie3(gameList.get(i), gameList.get(i + 1), gameList.get(i + 2));
                    serie3s.add(serie);

                    System.out.println(gameList.get(i).getRound_guest() + " " + gameList.get(i + 1).getRound_guest() + " " + gameList.get(i + 2).getRound_guest());

                    // hleda další serii
                    i = i + 2;
                    for (int j = i; j < gameList.size() - 2; j++) {
                        if (gameList.get(j).getRound_guest() + 1 != gameList.get(j + 1).getRound_guest()) {
                            i = j;
                            break;
                        }
                    }
                }
            }
        }

        System.out.println("----------------------------------");
        return serie3s;
    }

}
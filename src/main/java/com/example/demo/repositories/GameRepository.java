package com.example.demo.repositories;

import com.example.demo.Services.GoalService;
import com.example.demo.model.Game;
import com.example.demo.model.Goal;
import com.example.demo.model.Team;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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


//    public Integer getRound(String side, String teamName){
//        Integer gameRound = (Integer) entityManager.createNativeQuery("SELECT max(round_"+side+") FROM game WHERE "+side+"_team = "+teamName).getSingleResult();
//        if (gameRound == null) gameRound = 0;
//        return gameRound;
//    }

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

    public Integer gameTeamID (Integer gameId, String side){
        return (Integer) entityManager.createNativeQuery("SELECT " +side+ " FROM game WHERE id=" +gameId).getResultList().get(0);
    }

    public List<Goal> gameTeamGoalsPeriod(Integer gameId, Integer teamId, Integer period){
        List<Goal> goalsForPeriod = goalService.goalsForPeriod(gameId,period);
        goalsForPeriod = goalsForPeriod.stream()
                                .filter(goal -> goal.getTeam() == teamId.longValue())
                                .collect(Collectors.toList());
        return goalsForPeriod;
    }

    public String periodResult (Integer gameId, Integer period){
        Integer homeID = gameTeamID(gameId,"home");
        Integer guestID = gameTeamID(gameId,"guest");

        int home_goals = gameTeamGoalsPeriod(gameId,homeID,period).size();
        int guest_goals = gameTeamGoalsPeriod(gameId,guestID,period).size();

        return home_goals+":"+guest_goals;
    }

    public Integer periodBet (Integer gameId, Integer period){
        Integer homeID = gameTeamID(gameId,"home");
        Integer guestID = gameTeamID(gameId,"guest");
        int home_goals = gameTeamGoalsPeriod(gameId,homeID,period).size();
        int guest_goals = gameTeamGoalsPeriod(gameId,guestID,period).size();
        int result = 0;
        if (home_goals>guest_goals) result=1;
        if (home_goals<guest_goals) result=2;
        return result;
    }

    public Integer gameBet(Integer gameId){
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
        return result;
    }

    public String gameResult(Integer gameId){
        Integer homeID = gameTeamID(gameId,"home");
        Integer guestID = gameTeamID(gameId,"guest");

        List<Goal> goalsForGame = goalService.goalsFromGame(gameId);
        Long home_goals = goalsForGame.stream()
                .filter(goal -> goal.getTeam() == homeID.longValue())
                .count();
        Long guest_goals = goalsForGame.stream()
                .filter(goal -> goal.getTeam() == guestID.longValue())
                .count();
        return home_goals+":"+guest_goals;
    }

    @Transactional
    public Game gameSave(Integer gameId) {
        Game game = gameRepositoryNew.findById(gameId.longValue()).get();

        game.setTretina1sazka(periodBet(gameId,1));
        game.setTretina2sazka(periodBet(gameId,2));
        game.setTretina3sazka(periodBet(gameId,3));
        game.setProdlouzeni4sazka(periodBet(gameId,4));
        game.setTretina1vysledek(periodResult(gameId,1));
        game.setTretina2vysledek(periodResult(gameId,2));
        game.setTretina3vysledek(periodResult(gameId,3));
        game.setProdlouzeni4vysledek(periodResult(gameId,4));
        game.setVysledek_sazka(gameBet(gameId));
        game.setVysledek(gameResult(gameId));

//        gameRepositoryNew.save(game);
        return game;
    }
}
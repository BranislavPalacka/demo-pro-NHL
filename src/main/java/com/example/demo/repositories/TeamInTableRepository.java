package com.example.demo.repositories;

import com.example.demo.Services.TeamService;
import com.example.demo.model.Game;
import com.example.demo.model.Season;
import com.example.demo.model.Team;
import com.example.demo.model.TeamInTable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.util.*;

@Component
public class TeamInTableRepository {

    @PersistenceContext
    private final EntityManager entityManager;
    private final TeamRepositoryNew teamRepositoryNew;
    private final TeamRepository teamRepository;
    private final SeasonRepository seasonRepository;
    private final TeamService teamService;


    public TeamInTableRepository(EntityManager entityManager, TeamRepositoryNew teamRepositoryNew, TeamRepository teamRepository, SeasonRepository seasonRepository, TeamService teamService) {
        this.entityManager = entityManager;
        this.teamRepositoryNew = teamRepositoryNew;
        this.teamRepository = teamRepository;
        this.seasonRepository = seasonRepository;
        this.teamService = teamService;
    }

    public TeamInTable getTeamUpToDate(Long teamId, String actualDate, Integer season) {
        EntityManager em = entityManager;
        Team team =  teamRepositoryNew.findById(teamId).get();
        TeamInTable teamInTable = new TeamInTable();

        teamInTable.setTeamId(teamId);
        teamInTable.setName(team.getName());

        List<Game> gameList = em.createNativeQuery("SELECT * FROM game WHERE season="+season+" AND (home="+teamId+" OR guest="+teamId+") AND date<'"+actualDate+"'",Game.class).getResultList();

        String vysledek = "";
        int goalsHomeTeam = 0;
        int goalsGuestTeam = 0;
        int rounds = 0;

        for (Game game: gameList ) {
            rounds = getRounds(teamId, teamInTable, rounds, game);
        }
        teamInTable.setRounds(rounds);

        return teamInTable;
    }

    private int getRounds(Long teamId, TeamInTable teamInTable, int rounds, Game game) {
        int goalsHomeTeam;
        int goalsGuestTeam;
        String vysledek;
        rounds++;
        vysledek = game.getVysledek();
        if (game.getVysledek_sazka()>3) {
            vysledek = game.getVysledek().substring(0, game.getVysledek().length()-1);
        }

        goalsHomeTeam = Integer.parseInt(vysledek.substring(0,vysledek.indexOf(':')));
        goalsGuestTeam = Integer.parseInt(vysledek.substring(vysledek.indexOf(':')+1));

        if (game.getHome() == teamId.intValue()){
            teamInTable.setRoundsHome(teamInTable.getRoundsHome()+1);
            if (game.getVysledek_sazka() == 10) {
                teamInTable.setGoalsPlus(teamInTable.getGoalsPlus()+goalsHomeTeam);
                teamInTable.setGoalsMinus(teamInTable.getGoalsMinus()+goalsGuestTeam);
                teamInTable.setGoalsPlusHome(teamInTable.getGoalsPlusHome()+goalsHomeTeam);
                teamInTable.setGoalsMinusHome(teamInTable.getGoalsMinusHome()+goalsGuestTeam);
                teamInTable.setWinsOrder(teamInTable.getWinsOrder()+1);
                teamInTable.setWinsOrderHome(teamInTable.getWinsOrderHome()+1);
                teamInTable.setDraws(teamInTable.getDraws()+1);
                teamInTable.setDrawsHome(teamInTable.getDrawsHome()+1);
                teamInTable.setPoints(teamInTable.getPoints()+2);
                teamInTable.setPointsHome(teamInTable.getPointsHome()+2);
            }else if (game.getVysledek_sazka() == 1){
                teamInTable.setGoalsPlus(teamInTable.getGoalsPlus()+goalsHomeTeam);
                teamInTable.setGoalsMinus(teamInTable.getGoalsMinus()+goalsGuestTeam);
                teamInTable.setGoalsPlusHome(teamInTable.getGoalsPlusHome()+goalsHomeTeam);
                teamInTable.setGoalsMinusHome(teamInTable.getGoalsMinusHome()+goalsGuestTeam);
                teamInTable.setWins(teamInTable.getWins()+1);
                teamInTable.setWinsHome(teamInTable.getWinsHome()+1);
                teamInTable.setWinsOrder(teamInTable.getWinsOrder()+1);
                teamInTable.setWinsOrderHome(teamInTable.getWinsOrderHome()+1);
                teamInTable.setPoints(teamInTable.getPoints()+3);
                teamInTable.setPointsHome(teamInTable.getPointsHome()+3);
            }else if (game.getVysledek_sazka() == 20){
                teamInTable.setGoalsPlus(teamInTable.getGoalsPlus()+goalsHomeTeam);
                teamInTable.setGoalsMinus(teamInTable.getGoalsMinus()+goalsGuestTeam);
                teamInTable.setGoalsPlusHome(teamInTable.getGoalsPlusHome()+goalsHomeTeam);
                teamInTable.setGoalsMinusHome(teamInTable.getGoalsMinusHome()+goalsGuestTeam);
                teamInTable.setDraws(teamInTable.getDraws()+1);
                teamInTable.setDrawsHome(teamInTable.getDrawsHome()+1);
                teamInTable.setPoints(teamInTable.getPoints()+1);
                teamInTable.setPointsHome(teamInTable.getPointsHome()+1);
            }else if (game.getVysledek_sazka() == 2){
                teamInTable.setGoalsPlus(teamInTable.getGoalsPlus()+goalsHomeTeam);
                teamInTable.setGoalsMinus(teamInTable.getGoalsMinus()+goalsGuestTeam);
                teamInTable.setGoalsPlusHome(teamInTable.getGoalsPlusHome()+goalsHomeTeam);
                teamInTable.setGoalsMinusHome(teamInTable.getGoalsMinusHome()+goalsGuestTeam);
                teamInTable.setLosses(teamInTable.getLosses()+1);
                teamInTable.setLossesHome(teamInTable.getLossesHome()+1);
            }
        }else{
            teamInTable.setRoundsGuest(teamInTable.getRoundsGuest()+1);
            if (game.getVysledek_sazka() == 10) {
                teamInTable.setGoalsPlus(teamInTable.getGoalsPlus()+goalsGuestTeam);
                teamInTable.setGoalsMinus(teamInTable.getGoalsMinus()+goalsHomeTeam);
                teamInTable.setGoalsPlusGuest(teamInTable.getGoalsPlusGuest()+goalsGuestTeam);
                teamInTable.setGoalsMinusGuest(teamInTable.getGoalsMinusGuest()+goalsHomeTeam);
                teamInTable.setDraws(teamInTable.getDraws()+1);
                teamInTable.setDrawsGuest(teamInTable.getDrawsGuest()+1);
                teamInTable.setPoints(teamInTable.getPoints()+1);
                teamInTable.setPointsGuest(teamInTable.getPointsGuest()+1);
            }else if (game.getVysledek_sazka() == 1){
                teamInTable.setGoalsPlus(teamInTable.getGoalsPlus()+goalsGuestTeam);
                teamInTable.setGoalsMinus(teamInTable.getGoalsMinus()+goalsHomeTeam);
                teamInTable.setGoalsPlusGuest(teamInTable.getGoalsPlusGuest()+goalsGuestTeam);
                teamInTable.setGoalsMinusGuest(teamInTable.getGoalsMinusGuest()+goalsHomeTeam);
                teamInTable.setLosses(teamInTable.getLosses()+1);
                teamInTable.setLossesGuest(teamInTable.getLossesGuest()+1);
            }else if (game.getVysledek_sazka() == 20){
                teamInTable.setGoalsPlus(teamInTable.getGoalsPlus()+goalsGuestTeam);
                teamInTable.setGoalsMinus(teamInTable.getGoalsMinus()+goalsHomeTeam);
                teamInTable.setGoalsPlusGuest(teamInTable.getGoalsPlusGuest()+goalsGuestTeam);
                teamInTable.setGoalsMinusGuest(teamInTable.getGoalsMinusGuest()+goalsHomeTeam);
                teamInTable.setWinsOrder(teamInTable.getWinsOrder()+1);
                teamInTable.setWinsOrderGuest(teamInTable.getWinsOrderGuest()+1);
                teamInTable.setDraws(teamInTable.getDraws()+1);
                teamInTable.setDrawsGuest(teamInTable.getDrawsGuest()+1);
                teamInTable.setPoints(teamInTable.getPoints()+2);
                teamInTable.setPointsGuest(teamInTable.getPointsGuest()+2);
            }else if (game.getVysledek_sazka() == 2){
                teamInTable.setGoalsPlus(teamInTable.getGoalsPlus()+goalsGuestTeam);
                teamInTable.setGoalsMinus(teamInTable.getGoalsMinus()+goalsHomeTeam);
                teamInTable.setGoalsPlusGuest(teamInTable.getGoalsPlusGuest()+goalsGuestTeam);
                teamInTable.setGoalsMinusGuest(teamInTable.getGoalsMinusGuest()+goalsHomeTeam);
                teamInTable.setWinsOrder(teamInTable.getWinsOrder()+1);
                teamInTable.setWinsOrderGuest(teamInTable.getWinsOrderGuest()+1);
                teamInTable.setWins(teamInTable.getWins()+1);
                teamInTable.setWinsGuest(teamInTable.getWinsGuest()+1);
                teamInTable.setPoints(teamInTable.getPoints()+3);
                teamInTable.setPointsGuest(teamInTable.getPointsGuest()+3);
            }
        }
        return rounds;
    }

    public List<TeamInTable> getDivisionTableUpToDate(String actualDate, Integer season, String division){
        List<TeamInTable> teamsInTable = new ArrayList<>();
        List<Team> teamList = teamRepository.teamListDivision(division,season);

        for (Team team : teamList) {
            teamsInTable.add(getTeamUpToDate(team.getId(),actualDate,season));
        }
        Collections.sort(teamsInTable,Comparator.comparing(TeamInTable::getPoints).reversed());
        return teamsInTable;
    }

    private Integer getSeasonFromDate (String actualDate){
        List<Season> seasonList = seasonRepository.getAllSeasons();
        Date dataActual = Date.valueOf(actualDate);
        Date dateStart = Date.valueOf("2000-01-01");
        Date dateEnd = Date.valueOf("2000-01-02");

        for (Season season: seasonList) {
            dateStart = season.getStart_date();
            dateEnd = season.getLast_date();
            if (dataActual.after(dateStart) && dataActual.before(dateEnd)) return season.getYear();
        }
     return null;
    }

    /**
     * Najde pořadí temu v divizy k určenému datu. (toto datum se nepočítá)
     */
    public Integer getTeamDivisionPositionUpToDate(Long teamId,String actualDate){
        Integer season = getSeasonFromDate(actualDate);
        String division = teamService.teamsDivision(teamId,season);
        List<TeamInTable> table = getDivisionTableUpToDate(actualDate,season,division);

        int counter = 0;
        for (TeamInTable team: table) {
            counter++;
            if (team.getTeamId().intValue() == teamId.intValue()) return counter;
        }
        return null;
    }

    /**
     * porovná vzájemné umístění v tabulce
     * @return +X první team je lepší o X pozic. -X druhý team je lepší o X pozic
     */
    public Integer compareTeamsUpToDate(Long teamId1, Long teamId2, String actualDate){
        int firstTeamPosition = getTeamDivisionPositionUpToDate(teamId1,actualDate);
        int secondTeamPosition = getTeamDivisionPositionUpToDate(teamId2,actualDate);
        return secondTeamPosition-firstTeamPosition;
    }

    public TeamInTable getTeamUpToDateAgainstTeam(Long teamId, Long teamId2, String actualDate, Integer season) {
        EntityManager em = entityManager;
        Team team =  teamRepositoryNew.findById(teamId).get();
        TeamInTable teamInTable = new TeamInTable();

        teamInTable.setTeamId(teamId);
        teamInTable.setName(team.getName());

        List<Game> gameList = em.createNativeQuery("SELECT * FROM game WHERE season="+season+" AND (home=" +teamId+
                " AND guest=" +teamId2+ ") OR (home=" +teamId2+ " AND guest="+teamId+") AND date<'"+actualDate+"'",Game.class).getResultList();

        String vysledek = "";
        int goalsHomeTeam = 0;
        int goalsGuestTeam = 0;
        int rounds = 0;

        for (Game game: gameList ) {
            rounds = getRounds(teamId, teamInTable, rounds, game);
        }
        teamInTable.setRounds(rounds);

        return teamInTable;
    }
}
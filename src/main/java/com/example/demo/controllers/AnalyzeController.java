package com.example.demo.controllers;

import com.example.demo.Services.*;
import com.example.demo.model.*;
import com.example.demo.repositories.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class AnalyzeController {

    private final TeamService teamService;
    private final GameService gameService;
    private final AllSeriesRepository allSeriesRepository;
    private final TeamInTableService teamInTableService;
    private final GoalService goalService;

    public AnalyzeController(TeamService teamService, GameService gameService, AllSeriesRepository allSeriesRepository, TeamInTableService teamInTableService, GoalService goalService) {
        this.teamService = teamService;
        this.gameService = gameService;
        this.allSeriesRepository = allSeriesRepository;
        this.teamInTableService = teamInTableService;
        this.goalService = goalService;
    }

    @GetMapping("/analytic_game_series")
    public String game(){
//        List<TeamInTable> teamInTableList = teamInTableService.getDivisionTableUpToDate("2018-10-04",2018,"Central");
//        System.out.println();
//        for (TeamInTable team: teamInTableList) {
//            System.out.println(team.getName()+" | points: "+team.getPoints()+" | score: "+team.getGoalsPlus()+":"+team.getGoalsMinus()+
//                    " | winsOrder: "+ team.getWinsOrder()+" | rounds: "+team.getRounds()+" | result: "+team.getWins()+":"+team.getLosses()+":"+team.getDraws());
//        }
//
//        System.out.println(teamInTableService.compareTeamsUpToDate(44,42,"2018-10-04"));
//        System.out.println(teamInTableService.getTeamUpToDateAgainstTeam(41,44,"2018-12-10"));

        // zapas guest, vitezstvi, rozdil v tabulce 2 a jina divize
        int stratingRound = 10;
        int comparedDifferance = 2;
        int vysledek = 2;
        int resultAll = 0;
        int gameCounterAll = 0;
        Long season = 2018L;
        String side = "guest";

        List<Team>teamList = teamService.getAllTeamsForSeason(season);
        System.out.println("\n"+side+" -- startingRound = "+stratingRound+" | comparedDifferance = "+comparedDifferance+" | vysledek = "+vysledek);

        for (Team team: teamList){
            List<Game> gameList = gameService.teamGames(team.getId(), season,side);
            int result = 0;
            int gameCounter = 0;

            for (Game game : gameList) {
                if (game.getRound_guest()>=stratingRound){
                    if (!teamService.teamsDivision(game.getHome().longValue(),season.intValue()).equals(teamService.teamsDivision(game.getGuest().longValue(),season.intValue()))){
                        LocalDate date = game.getDate().toLocalDate().minusDays(-1);
                        if(teamInTableService.compareTeamsUpToDate(game.getGuest(),game.getHome(),game.getDate().toString()) >= comparedDifferance &&
                                goalService.getTeamIdFromFirstGoalInGame(game).intValue() == game.getGuest()) {
                            gameCounter++;
                            if (game.getVysledek_sazka() == vysledek) result++;
                        }
                    }
                }
            }
            resultAll += result;
            gameCounterAll += gameCounter;
            if(gameCounter>0) System.out.print("\n"+gameList.get(0).getGuest_team() + " - celkem: "+gameList.size()+" | započítáno: "+gameCounter+" | "+result+"/"+gameCounter+" = "+result*100/gameCounter+"%");
        }

        System.out.println("\nCelkem: "+teamList.size()*41+" | započítáno: "+gameCounterAll+" | "+resultAll+"/"+gameCounterAll+" = "+resultAll*100/gameCounterAll+"%");


        return "analytic_game_series";
    }

    @PostMapping("/analytic_game_series")
    public String game_series(@Param(value="games") Integer games,
                              @Param(value="pause") Integer pause,
                              @Param(value="side") String side,
                              @Param(value="season") Long season,
                              @Param(value="gameNo") String gameNo,
                              @Param(value="goals") Double goals){


        System.out.println("\nDelaka serie = "+games +" -- "+"pauza = "+pause+"\n");

        List<Team> teamList = teamService.getAllTeamsForSeason(season);

//        List<Team> teamList = teamService.getAllConferenceTeamsForSeason(2018L,"Eastern");
        int count = 0;
        int goluVZapase = 0;

        for (Team t: teamList) {
//            List<Game> listOtherDivisionGames = gameRepository.teamGamesWithOtherDivision(t.getId(),season,side);

            List<Game> gameList = gameService.teamGames(t.getId(),season,side);
            List<AllSeries> allSeriesList = allSeriesRepository.AllSeriesList(gameList,games,pause,side);

            for (AllSeries allSeries : allSeriesList){
                if (gameNo.equals("first_game") && allSeriesRepository.goluVZapase(allSeries.getGame1())<goals) goluVZapase++;
                if (gameNo.equals("second_game") && allSeriesRepository.goluVZapase(allSeries.getGame2())<goals) goluVZapase++;
                if (gameNo.equals("third_game") && allSeriesRepository.goluVZapase(allSeries.getGame3())<goals) goluVZapase++;
                if (gameNo.equals("fourth_game") && allSeriesRepository.goluVZapase(allSeries.getGame4())<goals) goluVZapase++;
                if (gameNo.equals("fifth_game") && allSeriesRepository.goluVZapase(allSeries.getGame5())<goals) goluVZapase++;
                count++;
            }
        }
        System.out.println(gameNo);
        System.out.println("----------------------");
        System.out.println("celkem serií: "+count);
        System.out.println("golu v zapase méně než "+goals+": "+goluVZapase+" --> "+goluVZapase*100/count+"%");

        return "analytic_game_series";
    }

}
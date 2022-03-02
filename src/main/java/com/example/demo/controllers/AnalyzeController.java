package com.example.demo.controllers;

import com.example.demo.Services.GameService;
import com.example.demo.Services.GoalService;
import com.example.demo.Services.PlayerService;
import com.example.demo.Services.TeamService;
import com.example.demo.model.*;
import com.example.demo.repositories.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AnalyzeController {

    private final GameRepositoryNew gameRepositoryNew;
    private final GoalService goalService;
    private final TeamRepositoryNew teamRepositoryNew;
    private final TeamService teamService;
    private final GameService gameService;
    private final GoalRepositoryNew goalRepositoryNew;
    private final PlayerService playerService;
    private final GameRepository gameRepository;
    private final AllSeriesRepository allSeriesRepository;

    public AnalyzeController(GameRepositoryNew gameRepositoryNew, GoalService goalService, TeamRepositoryNew teamRepositoryNew, TeamService teamService, GameService gameService, GoalRepositoryNew goalRepositoryNew, PlayerService playerService, GameRepository gameRepository, AllSeriesRepository allSeriesRepository) {
        this.gameRepositoryNew = gameRepositoryNew;
        this.goalService = goalService;
        this.teamRepositoryNew = teamRepositoryNew;
        this.teamService = teamService;
        this.gameService = gameService;
        this.goalRepositoryNew = goalRepositoryNew;
        this.playerService = playerService;
        this.gameRepository = gameRepository;
        this.allSeriesRepository = allSeriesRepository;
    }

    @GetMapping("/analytic_game_series")
    public String game(){
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
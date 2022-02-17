package com.example.demo.Services;

import com.example.demo.model.Game;
import com.example.demo.model.Goal;
import com.example.demo.model.Team;
import com.example.demo.series.serie3;

import java.util.List;

public interface GameService {
    List<Team> roundFill();
    String periodResult (Long gameId, Integer period);
    Integer gameTeamID (Long gameId, String side);
    List<Goal> gameTeamGoalsPeriod(Long gameId, Integer teamId, Integer period);
    Integer gameBet (Long gameId,Integer samostatneNajezdy);
    Integer periodBet (Long gameId, Integer period);
    String gameResult(Long gameId, Integer samostatneNajezdy);
    Game gameSave(Long gameId, Integer samostatneNajezdy);
    List<Game> gamesForSeason(Long season);
    List<Game> teamGames(Long teamId, Long season, String side);
    String prubeznyVysledekZapasu (Long gameId);
    List<serie3> serie3List(List<Game> gameList, String strana);
}
package com.example.demo.Services;

import com.example.demo.model.Game;
import com.example.demo.model.Goal;
import com.example.demo.model.Team;

import java.util.List;

public interface GameService {
    List<Team> roundFill();
    String periodResult (Long gameId, Integer period);
    Integer gameTeamID (Long gameId, String side);
    List<Goal> gameTeamGoalsPeriod(Long gameId, Integer teamId, Integer period);
    Integer gameBet (Long gameId);
    Integer periodBet (Long gameId, Integer period);
    String gameResult(Long gameId);
    Game gameSave(Long gameId);
}
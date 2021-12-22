package com.example.demo.Services;

import com.example.demo.model.Game;
import com.example.demo.model.Goal;
import com.example.demo.model.Team;

import java.util.List;

public interface GameService {
    List<Team> roundFill();
    String periodResult (Integer gameId, Integer period);
    Integer gameTeamID (Integer gameId, String side);
    List<Goal> gameTeamGoalsPeriod(Integer gameId, Integer teamId, Integer period);
    Integer gameBet (Integer gameId);
    Integer periodBet (Integer gameId, Integer period);
    String gameResult(Integer gameId);
    Game gameSave(Integer gameId);
}
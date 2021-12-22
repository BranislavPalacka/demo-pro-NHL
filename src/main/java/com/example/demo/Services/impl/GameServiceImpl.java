package com.example.demo.Services.impl;

import com.example.demo.Services.GameService;
import com.example.demo.model.Game;
import com.example.demo.model.Goal;
import com.example.demo.model.Team;
import com.example.demo.repositories.GameRepository;
import com.example.demo.repositories.GameRepositoryNew;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GameRepositoryNew gameRepositoryNew;

    public GameServiceImpl(GameRepository gameRepository, GameRepositoryNew gameRepositoryNew) {
        this.gameRepository = gameRepository;
        this.gameRepositoryNew = gameRepositoryNew;
    }

    @Override
    public List<Team> roundFill() {
        return gameRepository.roundFill();
    }

    @Override
    public String periodResult(Integer gameId, Integer period) {
        return gameRepository.periodResult(gameId,period);
    }

    @Override
    public Integer gameTeamID(Integer gameId, String side) {
        return gameRepository.gameTeamID(gameId,side);
    }

    @Override
    public List<Goal> gameTeamGoalsPeriod(Integer gameId, Integer teamId, Integer period) {
        return gameRepository.gameTeamGoalsPeriod(gameId,teamId,period);
    }

    @Override
    public Integer gameBet(Integer gameId) {
        return gameRepository.gameBet(gameId);
    }

    @Override
    public Integer periodBet(Integer gameId, Integer period) {
        return gameRepository.periodBet(gameId,period);
    }

    @Override
    public String gameResult(Integer gameId) {
        return gameRepository.gameResult(gameId);
    }

    @Override
    public Game gameSave(Integer gameId) {
        return gameRepository.gameSave(gameId);
    }
}
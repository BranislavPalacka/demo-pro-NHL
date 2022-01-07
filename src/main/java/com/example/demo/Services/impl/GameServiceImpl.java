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
    public String periodResult(Long gameId, Integer period) {
        return gameRepository.periodResult(gameId,period);
    }

    @Override
    public Integer gameTeamID(Long gameId, String side) {
        return gameRepository.gameTeamID(gameId,side);
    }

    @Override
    public List<Goal> gameTeamGoalsPeriod(Long gameId, Integer teamId, Integer period) {
        return gameRepository.gameTeamGoalsPeriod(gameId,teamId,period);
    }

    @Override
    public Integer gameBet(Long gameId) {
        return gameRepository.gameBet(gameId);
    }

    @Override
    public Integer periodBet(Long gameId, Integer period) {
        return gameRepository.periodBet(gameId,period);
    }

    @Override
    public String gameResult(Long gameId) {
        return gameRepository.gameResult(gameId);
    }

    @Override
    public Game gameSave(Long gameId) {
        return gameRepository.gameSave(gameId);
    }
}
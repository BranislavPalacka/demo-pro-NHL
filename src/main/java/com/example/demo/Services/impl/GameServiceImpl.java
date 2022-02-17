package com.example.demo.Services.impl;

import com.example.demo.Services.GameService;
import com.example.demo.model.Game;
import com.example.demo.model.Goal;
import com.example.demo.model.Team;
import com.example.demo.repositories.GameRepository;
import com.example.demo.repositories.GameRepositoryNew;
import com.example.demo.series.serie3;
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
    public Integer gameBet(Long gameId, Integer samostatneNajezdy) {
        return gameRepository.gameBet(gameId,samostatneNajezdy);
    }

    @Override
    public Integer periodBet(Long gameId, Integer period) {
        return gameRepository.periodBet(gameId,period);
    }

    @Override
    public String gameResult(Long gameId, Integer samostatneNajezdy) {
        return gameRepository.gameResult(gameId,samostatneNajezdy);
    }

    @Override
    public Game gameSave(Long gameId, Integer samostatneNajezdy) {
        return gameRepository.gameSave(gameId,samostatneNajezdy);
    }

    @Override
    public List<Game> gamesForSeason(Long season) {
        return gameRepository.gamesForSeason(season);
    }

    @Override
    public List<Game> teamGames(Long teamId, Long season, String side) {
        return gameRepository.teamGames(teamId,season,side);
    }

    @Override
    public String prubeznyVysledekZapasu(Long gameId) {
        return gameRepository.prubeznyVysledekZapasu(gameId);
    }

    @Override
    public List<serie3> serie3List(List<Game> gameList, String strana) {
        return gameRepository.serie3List(gameList,strana);
    }
}
package com.example.demo.Services.impl;

import com.example.demo.Services.PlayerService;
import com.example.demo.model.Player;
import com.example.demo.repositories.PlayerRepository;
import com.example.demo.repositories.PlayerRepositoryNew;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    PlayerRepository playerRepository;
    PlayerRepositoryNew playerRepositoryNew;

    public PlayerServiceImpl(PlayerRepository playerRepository, PlayerRepositoryNew playerRepositoryNew) {
        this.playerRepository = playerRepository;
        this.playerRepositoryNew = playerRepositoryNew;
    }

    @Override
    public String playerNameById(Integer playerID) {
        return playerRepository.playerNameById(playerID);
    }

    @Override
    public Long playerTeamById(Integer playerID, String season) {
        return playerRepository.playerTeamById(playerID, season);
    }

    @Override
    public Player lastPayerAdded() {
        return playerRepository.lastPayerAdded();
    }

    @Override
    public List<String> playersTeamsBySeasons(Player player) {
        return playerRepository.playersTeamsBySeasons(player);
    }

    @Override
    public String[][] last5PlayersTeamsBySeasons() {
        return playerRepository.last5PlayersTeamsBySeasons();
    }

    @Override
    public Player testNewPlayerTeamsBeforeSaving(Player player) {
        return playerRepository.testNewPlayerTeamsBeforeSaving(player);
    }

    @Override
    public boolean anySeasonTeamFilled(Player player) {
        return playerRepository.anySeasonTeamFilled(player);
    }

    @Override
    public void mergePlayer(Player newPlayer) {
        playerRepository.mergePlayer(newPlayer);
    }

    @Override
    public Player findPlayerByName(String playerName) {
        return playerRepository.findPlayerByName(playerName);
    }
}
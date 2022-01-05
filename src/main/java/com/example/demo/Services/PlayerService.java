package com.example.demo.Services;

import com.example.demo.model.Player;

import java.util.List;

public interface PlayerService {
    String playerNameById(Integer playerID);
    Long playerTeamById(Integer playerID, String season);
    Player lastPayerAdded();
    List <String> playersTeamsBySeasons(Player player);
    String[][] last5PlayersTeamsBySeasons();
    Player testNewPlayerTeamsBeforeSaving(Player player);
    boolean anySeasonTeamFilled (Player player);
    void mergePlayer(Player newPlayer);
}
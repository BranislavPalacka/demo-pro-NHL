package com.example.demo.Services;

import com.example.demo.model.Player;

import java.util.List;

public interface PlayerService {
    String playerNameById(Long playerID);
    Long playerTeamById(Long playerID, String season);
    Player lastPayerAdded();
    List <String> playersTeamsBySeasons(Player player);
    String[][] last5PlayersTeamsBySeasons();
    Player testNewPlayerTeamsBeforeSaving(Player player);
    boolean anySeasonTeamFilled (Player player);
    void mergePlayer(Player newPlayer);
    Player findPlayerByName(String playerName);
    void removePlayerFromTeamSeason(Long playerId, Long season);
    void addPlayerForTeamSeason(Long playerId, Long season, Long teamId);
    List<Player> prijmeniAjmeno(List<Player> playersList);
}
package com.example.demo.Services;

import com.example.demo.model.Player;
import com.example.demo.model.Team;

import java.util.List;

public interface TeamService {
    List<Team> getAllTeams();
    List<String> teamList();
    List<String> teamListDivision(String division);
    Integer getTeamIdByName (String teamName);
    List<Player> getTeamAllPlayersForSeason(String teamName, Integer season);
}
package com.example.demo.Services;

import com.example.demo.model.Player;
import com.example.demo.model.Team;

import java.util.List;

public interface TeamService {
    List<Team> getAllTeams();
    List<String> teamList();
    List<Team> teamListDivision(String division, Integer season);
    Integer getTeamIdByName (String teamName);
    List<Player> getTeamAllPlayersForSeason(String teamName, Integer season);
    void importovani(String fileName);
    String teamNameById(Long id);
    List<Team> getAllTeamsForSeason (Long season);
    List<Team> getAllConferenceTeamsForSeason (Long season, String conference);
    List<Team> getAllDivisionTeamsForSeason_2018 (Long season, String division);
    String teamsDivision (Long teamId, Integer season);
    Integer teamGameResults (Long teamId, Integer season, int result, String side);
    Integer get1PeriodWinsCount (Long teamId, Long season, String side);
    Integer get2PeriodsWinsCount (Long teamId, Long season, String side);

}
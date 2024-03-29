package com.example.demo.Services.impl;

import com.example.demo.Services.TeamService;
import com.example.demo.model.Player;
import com.example.demo.model.Team;
import com.example.demo.repositories.TeamRepositoryNew;
import com.example.demo.repositories.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    TeamRepositoryNew teamRepositoryNew;
    TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepositoryNew teamRepositoryNew, TeamRepository teamRepository) {
        this.teamRepositoryNew = teamRepositoryNew;
        this.teamRepository = teamRepository;
    }

//    @Override
//    public Team createAndAddMovie() {
//        Team team = new Team();
//        team.setName("PrvniTeam");
//
//        return teamRepository.save(team);
//    }

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.queryForTeams();
    }

    @Override
    public List<String> teamList() {
        return teamRepository.teamNames();
    }

    @Override
    public List<Team> teamListDivision(String division, Integer season) {
        return teamRepository.teamListDivision(division,season);
    }

    @Override
    public Integer getTeamIdByName(String teamName) {
        return teamRepository.teamIdByName(teamName);
    }

    @Override
    public List<Player> getTeamAllPlayersForSeason(String teamName, Integer season) {
        return teamRepository.teamAllPlayersForSeason(teamName,season);
    }

    @Override
    public void importovani(String fileName) {
        teamRepository.importovaniCSV(fileName);
    }

    @Override
    public String teamNameById(Long id) {
        return teamRepository.teamNameById(id);
    }

    @Override
    public List<Team> getAllTeamsForSeason(Long season) {
        return teamRepository.getAllTeamsForSeason(season);
    }

    @Override
    public List<Team> getAllConferenceTeamsForSeason(Long season, String conference) {
        return teamRepository.getAllConferenceTeamsForSeason(season,conference);
    }

    @Override
    public List<Team> getAllDivisionTeamsForSeason_2018(Long season, String division) {
        return teamRepository.getAllDivisionTeamsForSeason_2018(season,division);
    }

    @Override
    public String teamsDivision(Long teamId, Integer season) {
        return teamRepository.teamsDivision(teamId,season);
    }

    @Override
    public Integer teamGameResults(Long teamId, Integer season, int result, String side) {
        return teamRepository.teamGameResults(teamId,season,result,side);
    }

    @Override
    public Integer get1PeriodWinsCount(Long teamId, Long season, String side) {
        return teamRepository.get1PeriodWinsCount(teamId,season,side);
    }

    @Override
    public Integer get2PeriodsWinsCount(Long teamId, Long season, String side) {
        return teamRepository.get2PeriodsWinsCount(teamId,season,side);
    }

    @Override
    public Integer getSumTeamGoalsInGamesByGameResult(Long teamId, Long season, String side, Integer gameResult) {
        return teamRepository.getSumTeamGoalsInGamesByGameResult(teamId,season,side,gameResult);
    }

    @Override
    public Integer getSumTeamFirstGoalsInGamesByGameResult(Long teamId, Long season, String side, Integer gameResult) {
        return teamRepository.getSumTeamFirstGoalsInGamesByGameResult(teamId,season,side,gameResult);
    }

    @Override
    public Integer getGoalDifference(Long teamId, Long season, String side, Integer gameResult) {
        return teamRepository.getGoalDifference(teamId,season,side,gameResult);
    }


}
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
}
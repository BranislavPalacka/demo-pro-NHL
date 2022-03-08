package com.example.demo.Services.impl;

import com.example.demo.Services.TeamInTableService;
import com.example.demo.model.TeamInTable;
import com.example.demo.repositories.TeamInTableRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamInTableServiceImpl implements TeamInTableService {

    TeamInTableRepository teamInTableRepository;

    public TeamInTableServiceImpl(TeamInTableRepository teamInTableRepository) {
        this.teamInTableRepository = teamInTableRepository;
    }

    @Override
    public TeamInTable getTeamUpToDate(Long teamId, String actualDate, Integer season) {
        return teamInTableRepository.getTeamUpToDate(teamId,actualDate,season);
    }

    @Override
    public List<TeamInTable> getDivisionTableUpToDate(String actualDate, Integer season, String division) {
        return teamInTableRepository.getDivisionTableUpToDate(actualDate,season,division);
    }

    @Override
    public Integer getTeamDivisionPositionUpToDate(Long teamId, String actualDate) {
        return teamInTableRepository.getTeamDivisionPositionUpToDate(teamId,actualDate);
    }

    @Override
    public Integer compareTeamsUpToDate(Long teamId1, Long teamId2, String actualDate) {
        return teamInTableRepository.compareTeamsUpToDate(teamId1,teamId2,actualDate);
    }

    @Override
    public TeamInTable getTeamUpToDateAgainstTeam(Long teamId, Long teamId2, String actualDate, Integer season) {
        return teamInTableRepository.getTeamUpToDateAgainstTeam(teamId,teamId2,actualDate,season);
    }
}
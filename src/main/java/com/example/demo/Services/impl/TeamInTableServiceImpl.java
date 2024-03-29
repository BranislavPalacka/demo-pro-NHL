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
    public TeamInTable getTeamUpToDate(Long teamId, String actualDate) {
        return teamInTableRepository.getTeamUpToDate(teamId,actualDate);
    }

    @Override
    public List<TeamInTable> getDivisionTableUpToDate(String actualDate, Integer season, String division) {
        return teamInTableRepository.getDivisionTableUpToDate(actualDate,season,division);
    }

    @Override
    public Integer getTeamDivisionPositionUpToDate(Integer teamId, String actualDate) {
        return teamInTableRepository.getTeamDivisionPositionUpToDate(teamId,actualDate);
    }

    @Override
    public Integer compareTeamsUpToDate(Integer teamId1, Integer teamId2, String actualDate) {
        return teamInTableRepository.compareTeamsUpToDate(teamId1,teamId2,actualDate);
    }

    @Override
    public TeamInTable getTeamUpToDateAgainstTeam(Integer teamId, Integer teamId2, String actualDate) {
        return teamInTableRepository.getTeamUpToDateAgainstTeam(teamId,teamId2,actualDate);
    }
}
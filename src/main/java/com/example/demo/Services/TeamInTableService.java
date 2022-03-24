package com.example.demo.Services;

import com.example.demo.model.AllSeries;
import com.example.demo.model.Game;
import com.example.demo.model.TeamInTable;

import java.util.List;

public interface TeamInTableService {
    TeamInTable getTeamUpToDate(Long teamId, String actualDate);
    List<TeamInTable> getDivisionTableUpToDate(String actualDate, Integer season, String division);
    Integer getTeamDivisionPositionUpToDate(Integer teamId,String actualDate);
    Integer compareTeamsUpToDate(Integer teamId1, Integer teamId2, String actualDate);
    TeamInTable getTeamUpToDateAgainstTeam(Integer teamId, Integer teamId2, String actualDate);
}
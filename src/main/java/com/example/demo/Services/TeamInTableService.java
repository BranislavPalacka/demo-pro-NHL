package com.example.demo.Services;

import com.example.demo.model.AllSeries;
import com.example.demo.model.Game;
import com.example.demo.model.TeamInTable;

import java.util.List;

public interface TeamInTableService {
    TeamInTable getTeamUpToDate(Long teamId, String actualDate, Integer season);
    List<TeamInTable> getDivisionTableUpToDate(String actualDate, Integer season, String division);
    Integer getTeamDivisionPositionUpToDate(Long teamId,String actualDate);
    Integer compareTeamsUpToDate(Long teamId1, Long teamId2, String actualDate);
}
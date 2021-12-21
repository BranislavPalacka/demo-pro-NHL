package com.example.demo.Services;

import com.example.demo.model.Player;

import java.util.List;

public interface PlayerService {
    String playerNameById(Integer playerID);
    Long playerTeamById(Integer playerID, String season);
}
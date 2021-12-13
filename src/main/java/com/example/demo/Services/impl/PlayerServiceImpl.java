package com.example.demo.Services.impl;

import com.example.demo.Services.PlayerService;
import com.example.demo.repositories.PlayerRepository;
import com.example.demo.repositories.PlayerRepositoryNew;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {

    PlayerRepository playerRepository;
    PlayerRepositoryNew playerRepositoryNew;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

}
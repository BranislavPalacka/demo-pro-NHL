package com.example.demo.Services.impl;

import com.example.demo.Services.GameService;
import com.example.demo.model.Team;
import com.example.demo.repositories.GameRepository;
import com.example.demo.repositories.GameRepositoryNew;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GameRepositoryNew gameRepositoryNew;

    public GameServiceImpl(GameRepository gameRepository, GameRepositoryNew gameRepositoryNew) {
        this.gameRepository = gameRepository;
        this.gameRepositoryNew = gameRepositoryNew;
    }

    @Override
    public List<Team> roundFill() {
        return gameRepository.roundFill();
    }
}
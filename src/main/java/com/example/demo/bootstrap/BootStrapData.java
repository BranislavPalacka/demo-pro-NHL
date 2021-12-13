package com.example.demo.bootstrap;

import com.example.demo.repositories.PlayerRepository;
import com.example.demo.repositories.TeamRepositoryNew;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

    private final TeamRepositoryNew teamRepositoryNew;
    private final PlayerRepository playerRepository;

    public BootStrapData(TeamRepositoryNew teamRepositoryNew, PlayerRepository playerRepository) {
        this.teamRepositoryNew = teamRepositoryNew;
        this.playerRepository = playerRepository;
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
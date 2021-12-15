package com.example.demo.controllers;

import com.example.demo.Services.GameService;
import com.example.demo.Services.TeamService;
import com.example.demo.model.Game;
import com.example.demo.model.Team;
import com.example.demo.repositories.GameRepositoryNew;
import com.example.demo.repositories.TeamRepositoryNew;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class GameController {

    private final GameRepositoryNew gameRepositoryNew;
//    private final GameService gameService;
    private final TeamRepositoryNew teamRepositoryNew;
    private final TeamService teamService;

    public GameController(GameRepositoryNew gameRepositoryNew, TeamRepositoryNew teamRepositoryNew, TeamService teamService) {
        this.gameRepositoryNew = gameRepositoryNew;
        this.teamRepositoryNew = teamRepositoryNew;
        this.teamService = teamService;
    }

    @GetMapping("/game_registration")
    public String formular(Model model){
        Game game = new Game();
        model.addAttribute("game", game);

        Team team = new Team();
        model.addAttribute("team", team);

        List<Team> homeList = teamService.getAllTeams();
        model.addAttribute("domaci",homeList);

        List<Team> guestList = teamService.getAllTeams();
        model.addAttribute("hoste",guestList);

        return "game_registration";
    }

    @PostMapping("/game_registration")
    public String odeslanyFormular(@ModelAttribute("game") Game game){
        gameRepositoryNew.save(game);
        return "game_registration_success";
    }
}
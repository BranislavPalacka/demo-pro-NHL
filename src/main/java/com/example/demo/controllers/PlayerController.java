package com.example.demo.controllers;

import com.example.demo.Services.PlayerService;
import com.example.demo.Services.TeamService;
import com.example.demo.model.Player;
import com.example.demo.repositories.PlayerRepositoryNew;
import com.example.demo.repositories.TeamRepositoryNew;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerRepositoryNew playerRepositoryNew;
    private final TeamRepositoryNew teamRepositoryNew;
    private final TeamService teamService;

    public PlayerController(PlayerService playerService, PlayerRepositoryNew playerRepositoryNew, TeamRepositoryNew teamRepositoryNew, TeamService teamService) {
        this.playerService = playerService;
        this.playerRepositoryNew = playerRepositoryNew;
        this.teamRepositoryNew = teamRepositoryNew;
        this.teamService = teamService;
    }

//    @RequestMapping("/teams")
//    public String getAllTeams(Model model){
//        model.addAttribute("teams",teamRepository.findAll());
//        return "teams";
//    }


    @GetMapping("/player_registration")
    public String formular(Model model){
        Player player = new Player();

        model.addAttribute("player", player);

        Integer teamId = teamService.getTeamIdByName("Winnipeg Jets");
        List<Integer> teamList = Arrays.asList(teamId);
        model.addAttribute("team",teamList);

        return "player_registration";
    }

    @PostMapping("/player_registration")
    public String odeslanyFormular(@ModelAttribute("player") Player player){
        playerRepositoryNew.save(player);
        return "player_registration_success";
    }
}
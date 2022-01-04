package com.example.demo.controllers;

import com.example.demo.Services.PlayerService;
import com.example.demo.Services.TeamService;
import com.example.demo.model.Game;
import com.example.demo.model.Player;
import com.example.demo.model.Team;
import com.example.demo.repositories.GameRepositoryNew;
import com.example.demo.repositories.PlayerRepositoryNew;
import com.example.demo.repositories.TeamRepositoryNew;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerRepositoryNew playerRepositoryNew;
    private final TeamRepositoryNew teamRepositoryNew;
    private final TeamService teamService;
    private final GameRepositoryNew gameRepositoryNew;

    public PlayerController(PlayerService playerService, PlayerRepositoryNew playerRepositoryNew, TeamRepositoryNew teamRepositoryNew, TeamService teamService, GameRepositoryNew gameRepositoryNew) {
        this.playerService = playerService;
        this.playerRepositoryNew = playerRepositoryNew;
        this.teamRepositoryNew = teamRepositoryNew;
        this.teamService = teamService;
        this.gameRepositoryNew = gameRepositoryNew;
    }

//    @RequestMapping("/teams")
//    public String getAllTeams(Model model){
//        model.addAttribute("teams",teamRepository.findAll());
//        return "teams";
//    }


//    @GetMapping("/player_registration")
//    public String formular(Model model){
//        Player player = new Player();
//
//        model.addAttribute("player", player);
//
//        Integer teamId = teamService.getTeamIdByName("New Jersey Devils");
//        List<Integer> teamList = Arrays.asList(teamId);
//        model.addAttribute("team",teamList);
//
//        return "player_registration";
//    }

    @PostMapping("/player_registration")
    public String odeslanyFormular(@ModelAttribute("player") Player player){
        playerRepositoryNew.save(player);
        return "player_registration_success";
    }
    @GetMapping("/player_registration/{id}")
    public String odeslanyFormularZeHry(@PathVariable String id, Model model){
        String teamSelected = id.substring(id.length()-1);
        String IDs = id.substring(0,(id.length()-1));
        Long ID = Long.valueOf(IDs);
        Team team1 = new Team();
        Team team2 = new Team();

        Game game = gameRepositoryNew.findById(ID).get();
        model.addAttribute("game",game);

        // zařídí správné pořadí teamů ve form/selectu
        if (teamSelected.contentEquals("h")){
            team1 = teamRepositoryNew.findById(game.getHome().longValue()).get();
            team2 = teamRepositoryNew.findById(game.getGuest().longValue()).get();
        }else if (teamSelected.contentEquals("g")){
            team2 = teamRepositoryNew.findById(game.getHome().longValue()).get();
            team1 = teamRepositoryNew.findById(game.getGuest().longValue()).get();
        }
        model.addAttribute("team1",team1);
        model.addAttribute("team2",team2);

        Player player = new Player();
        model.addAttribute("player", player);

        String season = "team_id_"+game.getSeason();
        model.addAttribute("season",season);


//        Team team = (teamRepositoryNew.findById(playerService.lastPayerAdded().getTeam_id_2019())).get();

        Player lastAddedPlayer = playerService.lastPayerAdded();
        model.addAttribute("lastAddedPlayer",lastAddedPlayer);

        List <String> teamsBySeasons = playerService.playersTeamsBySeasons(lastAddedPlayer);
        model.addAttribute("player_teams",teamsBySeasons);

        return "player_registration";
    }
}
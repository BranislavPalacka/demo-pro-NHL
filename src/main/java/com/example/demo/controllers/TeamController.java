package com.example.demo.controllers;

import com.example.demo.Services.TeamService;
import com.example.demo.model.Player;
import com.example.demo.model.Team;
import com.example.demo.repositories.TeamRepositoryNew;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class TeamController {

    private final TeamRepositoryNew teamRepositoryNew;
    private final TeamService teamService;

    public TeamController(TeamRepositoryNew teamRepositoryNew, TeamService teamService) {
        this.teamRepositoryNew = teamRepositoryNew;
        this.teamService = teamService;
    }

//    @RequestMapping("/teams")
//    public String getAllTeams(Model model){
//        model.addAttribute("teams",teamRepository.findAll());
//        return "teams";
//    }

    @RequestMapping("/teams")
    public String getAllTeams(Model model){
        model.addAttribute("Atlantic", teamService.teamListDivision("Atlantic"));
        model.addAttribute("Metropolitan", teamService.teamListDivision("Metropolitan"));
        model.addAttribute("Central", teamService.teamListDivision("Central"));
        model.addAttribute("Pacific", teamService.teamListDivision("Pacific"));
        return "teams";
    }

    @GetMapping("/team_registration")
    public String formular(Model model){
        Team team = new Team();
        model.addAttribute("team", team);

        List<String> conferenceList = Arrays.asList("Eastern","Western");
        model.addAttribute("conference",conferenceList);

        List<String> divisionList = Arrays.asList("Atlantic","Metropolitan","Central","Pacific");
        model.addAttribute("division",divisionList);

        return "team_registration";
    }

    @PostMapping("/team_registration")
    public String odeslanyFormular(@ModelAttribute("team") Team team){
        System.out.println(team);
        teamRepositoryNew.save(team);
        return "team_registration_success";
    }

    @GetMapping("/team_all_players_for_season")
    public String teamPlayers (Model model){
        Team team = new Team();
        team.setName("Boston Bruins");
        model.addAttribute("team",team);

        List<Player> playersList = teamService.getTeamAllPlayersForSeason("Boston Bruins",2019);
        model.addAttribute("listOfPlayers",playersList);
        return "team_all_players_for_season";
    }
}
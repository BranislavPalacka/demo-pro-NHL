package com.example.demo.controllers;

import com.example.demo.Services.GameService;
import com.example.demo.Services.PlayerService;
import com.example.demo.Services.TeamService;
import com.example.demo.model.Player;
import com.example.demo.model.Team;
import com.example.demo.repositories.PlayerRepositoryNew;
import com.example.demo.repositories.TeamRepositoryNew;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

@Controller
public class TeamController {

    private final EntityManager entityManager;
    private final TeamRepositoryNew teamRepositoryNew;
    private final TeamService teamService;
    private final GameService gameService;
    private final PlayerRepositoryNew playerRepositoryNew;
    private final PlayerService playerService;

    public TeamController(EntityManager entityManager, TeamRepositoryNew teamRepositoryNew, TeamService teamService, GameService gameService, PlayerRepositoryNew playerRepositoryNew, PlayerService playerService) {
        this.entityManager = entityManager;
        this.teamRepositoryNew = teamRepositoryNew;
        this.teamService = teamService;
        this.gameService = gameService;
        this.playerRepositoryNew = playerRepositoryNew;
        this.playerService = playerService;
    }


    @GetMapping("/teams/{season}")
    public String getAllTeamsForSeason(@PathVariable Integer season, Model model){
        model.addAttribute("Atlantic", teamService.teamListDivision("Atlantic",season));
        model.addAttribute("Metropolitan", teamService.teamListDivision("Metropolitan",season));
        model.addAttribute("Central", teamService.teamListDivision("Central",season));
        model.addAttribute("Pacific", teamService.teamListDivision("Pacific",season));
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

    @GetMapping("/team_all_players_for_season/{season}/{teamId}")
    public String teamPlayers (@PathVariable Integer season,@PathVariable Long teamId,Model model){
        Team team = teamRepositoryNew.findById(teamId).get();
        model.addAttribute("team",team);

        List<Player> playersList = teamService.getTeamAllPlayersForSeason(team.getName(),season);
        model.addAttribute("listOfPlayers",playersList);

        model.addAttribute("season",season);

        Player player = new Player();
        model.addAttribute("playerNew",player);

        String message ="";
        model.addAttribute("message",message);

        return "team_all_players_for_season";
    }

    @Transactional
    @PostMapping("/team_all_players_for_season/{season}/{teamId}")
    public String teamPlayersAdd (@PathVariable Integer season,@PathVariable Long teamId,@ModelAttribute("playerNew") Player playerToSave, Model model){
        String message ="";

        model.addAttribute("season",season);

        Team team = teamRepositoryNew.findById(teamId).get();
        model.addAttribute("team",team);

        if (!playerToSave.getName().isEmpty()){
            if (playerService.findPlayerByName(playerToSave.getName())==null) {
                playerRepositoryNew.save(playerToSave);
            }else{
                playerToSave = playerService.findPlayerByName(playerToSave.getName());
                System.out.println(playerToSave);
            }
            playerService.addPlayerForTeamSeason(playerToSave.getId(),Long.valueOf(season),teamId);
            message = "saved player - "+playerToSave.getName();
        }

        model.addAttribute("message",message);

        List<Player> playersList = teamService.getTeamAllPlayersForSeason(team.getName(),season);
        model.addAttribute("listOfPlayers",playersList);

        Player player = new Player();
        model.addAttribute("playerNew",player);

        return "team_all_players_for_season";
    }

    @Transactional
    @GetMapping("/team_all_players_for_season/{season}/{teamId}/{playerId}")
    public String teamPlayersChange (@PathVariable Integer season,@PathVariable Long teamId,@PathVariable String playerId,Model model){
        String actionSelected = playerId.substring(playerId.length()-1);
        Long playerID = Long.valueOf(playerId.substring(0,(playerId.length()-1)));
        String message= "";

        if (actionSelected.equals("d")){
        message ="deleted player " + playerService.playerNameById(Math.toIntExact(playerID));
        playerService.removePlayerFromTeamSeason(playerID,Long.valueOf(season));
        }

        model.addAttribute("message",message);

        Player player = new Player();
        model.addAttribute("playerNew",player);

        Team team = teamRepositoryNew.findById(teamId).get();
        model.addAttribute("team",team);

        List<Player> playersList = teamService.getTeamAllPlayersForSeason(team.getName(),season);
        model.addAttribute("listOfPlayers",playersList);

        model.addAttribute("season",season);


        return "team_all_players_for_season";
    }


    @GetMapping("/import")
    public String importovani(){
        teamService.importovani("nhl2018_2019_data.csv");
        gameService.roundFill();
        return "import";
    }
}
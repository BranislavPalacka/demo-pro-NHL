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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

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


    @PostMapping("/player_registration")
    public String odeslanyFormular(@ModelAttribute("player") Player player){
        playerRepositoryNew.save(player);
        return "player_registration_success";
    }
    @GetMapping("/player_registration/{id}")
    public String odeslanyFormularZeHry(@PathVariable String id, Model model){
        String teamSelected = id.substring(id.length()-1);
        String IDs = id.substring(0,(id.length()-1));
        model.addAttribute("gameID",IDs);
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


        Player lastAddedPlayer = playerService.lastPayerAdded();
        model.addAttribute("lastAddedPlayer",lastAddedPlayer);

        List <String> teamsBySeasons = playerService.playersTeamsBySeasons(lastAddedPlayer);
        model.addAttribute("player_teams",teamsBySeasons);

        return "player_registration";
    }

    @GetMapping("/player_registration_new")
    public String pripravenyFormularNovyHrac(Model model){
        String message = "";
        model.addAttribute("massage",message);

        Player playerNew = new Player();
        model.addAttribute("playerNew",playerNew);

        List<Team> teamList = (List<Team>) teamRepositoryNew.findAll();
        model.addAttribute("teamList",teamList);

        String [][] last5PlayersTeamsBySeasons = playerService.last5PlayersTeamsBySeasons();
        model.addAttribute("last5PlayersTeamsBySeasons",last5PlayersTeamsBySeasons);

        return "/player_registration_new";
    }

    @GetMapping("/player_registration_new/{id}")
    public String FormularNovyHracDEL(@PathVariable String id, Model model){
        String actionSelected = id.substring(id.length()-1);
        Long ID = Long.valueOf(id.substring(0,(id.length()-1)));

        if (actionSelected.equals("d")){
            String message = "deleted player: "+playerService.playerNameById(ID);
            model.addAttribute("message",message);

            playerRepositoryNew.deleteById(ID);
        }

        Player playerNew = new Player();
        model.addAttribute("playerNew",playerNew);

        List<Team> teamList = (List<Team>) teamRepositoryNew.findAll();
        model.addAttribute("teamList",teamList);

        String [][] last5PlayersTeamsBySeasons = playerService.last5PlayersTeamsBySeasons();
        model.addAttribute("last5PlayersTeamsBySeasons",last5PlayersTeamsBySeasons);

        return "/player_registration_new";
    }

    @PostMapping("/player_registration_new")
    public String odeslanyFormularNovyHrac(@ModelAttribute("playerNew") Player playerToSave, Model model){
        String message = "";

        if (!playerToSave.getName().isEmpty() && playerService.anySeasonTeamFilled(playerToSave)){
            playerToSave = playerService.testNewPlayerTeamsBeforeSaving(playerToSave);
            if (playerService.findPlayerByName(playerToSave.getName())==null) {
                playerRepositoryNew.save(playerToSave);
                message = "saved player - "+playerToSave.getName();
            }else{
                Player playerEdit = playerService.findPlayerByName(playerToSave.getName());
                return "redirect:/player_registration_edit/"+playerEdit.getId()+"p";
            }
        }

        model.addAttribute("message",message);

        Player playerNew = new Player();
        model.addAttribute("playerNew",playerNew);

        List<Team> teamList = (List<Team>) teamRepositoryNew.findAll();
        model.addAttribute("teamList",teamList);

        String [][] last5PlayersTeamsBySeasons = playerService.last5PlayersTeamsBySeasons();
        model.addAttribute("last5PlayersTeamsBySeasons",last5PlayersTeamsBySeasons);

        return "/player_registration_new";
    }

    @Transactional
    @PostMapping("/player_registration_new/{id}")
    public String odeslanyFormularNovyHracEDIT(@ModelAttribute("playerNew") Player playerToSave, Model model){

        if (!playerToSave.getName().isEmpty() && playerService.anySeasonTeamFilled(playerToSave)){
            playerToSave = playerService.testNewPlayerTeamsBeforeSaving(playerToSave);
            playerService.mergePlayer(playerToSave);
        }

        String message = "změněný hráč: "+playerToSave.getName();
        model.addAttribute("message",message);

        Player playerNew = new Player();
        model.addAttribute("playerNew",playerNew);

        List<Team> teamList = (List<Team>) teamRepositoryNew.findAll();
        model.addAttribute("teamList",teamList);

        String [][] last5PlayersTeamsBySeasons = playerService.last5PlayersTeamsBySeasons();
        model.addAttribute("last5PlayersTeamsBySeasons",last5PlayersTeamsBySeasons);

        return "/player_registration_new";
    }

    @GetMapping("/player_registration_edit/{id}")
    public String editaceFormularNovyHrac(@PathVariable String id, Model model){
        String actionSelected = id.substring(id.length()-1);
        Long ID = Long.valueOf(id.substring(0,(id.length()-1)));

        String nadpis = "";
        if (actionSelected.equals("e")) nadpis = "Editování Hráče";
        if (actionSelected.equals("p")) nadpis = "Zadaný hráč již existuje";
        model.addAttribute("nadpis",nadpis);

        Player playerNew = playerRepositoryNew.findById(ID).get();
        model.addAttribute("playerNew",playerNew);

        List<Team> teamList = (List<Team>) teamRepositoryNew.findAll();
        model.addAttribute("teamList",teamList);

        List <String> teamsBySeasons = playerService.playersTeamsBySeasons(playerNew);
        model.addAttribute("player_teams",teamsBySeasons);

        return "/player_registration_edit";
    }
}
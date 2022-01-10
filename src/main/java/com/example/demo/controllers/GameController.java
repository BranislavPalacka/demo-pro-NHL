package com.example.demo.controllers;

import com.example.demo.Services.GameService;
import com.example.demo.Services.GoalService;
import com.example.demo.Services.PlayerService;
import com.example.demo.Services.TeamService;
import com.example.demo.model.Game;
import com.example.demo.model.Goal;
import com.example.demo.model.Player;
import com.example.demo.model.Team;
import com.example.demo.repositories.GameRepositoryNew;
import com.example.demo.repositories.GoalRepositoryNew;
import com.example.demo.repositories.TeamRepositoryNew;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GameController {

    private final GameRepositoryNew gameRepositoryNew;
    private final GoalService goalService;
    private final TeamRepositoryNew teamRepositoryNew;
    private final TeamService teamService;
    private final GameService gameService;
    private final GoalRepositoryNew goalRepositoryNew;
    private final PlayerService playerService;

    public GameController(GameRepositoryNew gameRepositoryNew, GoalService goalService, TeamRepositoryNew teamRepositoryNew, TeamService teamService, GameService gameService, GoalRepositoryNew goalRepositoryNew, PlayerService playerService) {
        this.gameRepositoryNew = gameRepositoryNew;
        this.goalService = goalService;
        this.teamRepositoryNew = teamRepositoryNew;
        this.teamService = teamService;
        this.gameService = gameService;
        this.goalRepositoryNew = goalRepositoryNew;
        this.playerService = playerService;
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

        String homeTeam = teamRepositoryNew.findById(game.getHome().longValue()).get().getName();
        game.setHome_team(homeTeam);

        String guestTeam = teamRepositoryNew.findById(game.getGuest().longValue()).get().getName();
        game.setGuest_team(guestTeam);

        return "game_registration_success";
    }

    @GetMapping("/games/{season}")
    public String gamesList(@PathVariable Long season, Model model){

        List<Game> gameList = gameService.gamesForSeason(season);
        model.addAttribute("gameList",gameList);
        model.addAttribute("season",season);
        List<Team> teamList = (List<Team>) teamRepositoryNew.findAll();
        model.addAttribute("teamList",teamList);

        return "games";
    }

    @GetMapping("/games/{season}/{teamIdS}")
    public String gamesListTeam(@PathVariable Long season,@PathVariable String teamIdS, Model model){
        String sideSelected = teamIdS.substring(teamIdS.length()-1);
        if (sideSelected.equals("h")){
            sideSelected = "home";
        }else sideSelected = "guest";

        Long teamId = Long.valueOf(teamIdS.substring(0,(teamIdS.length()-1)));

        List<Game> gameList = gameService.teamGames(teamId,season,sideSelected);
        model.addAttribute("gameList",gameList);
        model.addAttribute("season",season);

        return "games";
    }

    @PostMapping("/games")
    public String odeslanyGamesFormular(@ModelAttribute("game") Game game){
        return "game_fill";
    }

    // bacha na sezonu

    @GetMapping("/game_fill/{gameId}")
    public String get(@PathVariable Long gameId, Model model) {
        Game game = gameRepositoryNew.findById(gameId).get();
        model.addAttribute("game",game);


        Goal goal = new Goal();
        model.addAttribute("goal",goal);;

        List<Player> home_players = teamService.getTeamAllPlayersForSeason(game.getHome_team(),game.getSeason());
        playerService.prijmeniAjmeno(home_players);
        model.addAttribute("home_players", home_players);

        List<Player> guest_players = teamService.getTeamAllPlayersForSeason(game.getGuest_team(),game.getSeason());
        playerService.prijmeniAjmeno(guest_players);
        model.addAttribute("guest_players", guest_players);

        List<Goal> goalList = goalService.goalsFromGame(gameId);
        model.addAttribute("goalList",goalList);

        return "game_fill";
    }

    @GetMapping("/game_fill/{gameId}/{goalId}")
    public String vyplnenaHraVymazaniGolu(@PathVariable Long gameId,@PathVariable Long goalId, Model model){
        goalRepositoryNew.deleteById(goalId);

        Game game = gameRepositoryNew.findById(gameId).get();
        model.addAttribute("game",game);

        Goal goal = new Goal();
        model.addAttribute("goal",goal);

        List<Player> home_players = teamService.getTeamAllPlayersForSeason(game.getHome_team(),game.getSeason());
        playerService.prijmeniAjmeno(home_players);
        model.addAttribute("home_players", home_players);

        List<Player> guest_players = teamService.getTeamAllPlayersForSeason(game.getGuest_team(),game.getSeason());
        playerService.prijmeniAjmeno(guest_players);
        model.addAttribute("guest_players", guest_players);

        List<Goal> goalList = goalService.goalsFromGame(gameId);
        model.addAttribute("goalList",goalList);

        return "game_fill";
    }

    @PostMapping("/game_fill/{gameId}")
    public String get1(@PathVariable Long gameId,@ModelAttribute("goal") Goal vstrelenygoal,Model model) {
        goalService.goalToSave(vstrelenygoal,gameId,gameRepositoryNew.findById(gameId).get().getSeason().toString());

        Game game = gameRepositoryNew.findById(gameId).get();
        model.addAttribute("game",game);

        Goal goal = new Goal();
        model.addAttribute("goal",goal);

        List<Player> home_players = teamService.getTeamAllPlayersForSeason(game.getHome_team(),game.getSeason());
        playerService.prijmeniAjmeno(home_players);
        model.addAttribute("home_players", home_players);

        List<Player> guest_players = teamService.getTeamAllPlayersForSeason(game.getGuest_team(),game.getSeason());
        playerService.prijmeniAjmeno(guest_players);
        model.addAttribute("guest_players", guest_players);


        List<Goal> goalList = goalService.goalsFromGame(gameId);
        model.addAttribute("goalList",goalList);

        return "game_fill";
    }



    @GetMapping("/game_filled/{gameId}")
    public String vyplnenaHra(@PathVariable Long gameId, Model model){

        List<Goal> goalListFirstPeriod = goalService.goalsForPeriod(gameId,1);
        model.addAttribute("goalListFirstPeriod",goalListFirstPeriod);
        List<Goal> goalListSecondPeriod = goalService.goalsForPeriod(gameId,2);
        model.addAttribute("goalListSecondPeriod",goalListSecondPeriod);
        List<Goal> goalListThirdPeriod = goalService.goalsForPeriod(gameId,3);
        model.addAttribute("goalListThirdPeriod",goalListThirdPeriod);
        List<Goal> goalListOvertime = goalService.goalsForPeriod(gameId,4);
        model.addAttribute("goalListOvertime",goalListOvertime);

        Game game = gameService.gameSave(gameId);
        model.addAttribute("game",game);

        return "game_filled";
    }

}
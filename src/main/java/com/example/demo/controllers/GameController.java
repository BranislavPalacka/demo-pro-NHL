package com.example.demo.controllers;

import com.example.demo.Services.GameService;
import com.example.demo.Services.GoalService;
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

    public GameController(GameRepositoryNew gameRepositoryNew, GoalService goalService, TeamRepositoryNew teamRepositoryNew, TeamService teamService, GameService gameService, GoalRepositoryNew goalRepositoryNew) {
        this.gameRepositoryNew = gameRepositoryNew;
        this.goalService = goalService;
        this.teamRepositoryNew = teamRepositoryNew;
        this.teamService = teamService;
        this.gameService = gameService;
        this.goalRepositoryNew = goalRepositoryNew;
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

    @GetMapping("/games")
    public String gamesList(Model model){

        List<Game> gameList = (List<Game>) gameRepositoryNew.findAll();
        model.addAttribute("gameList",gameList);

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
        model.addAttribute("goal",goal);

        List<Player> home_players = teamService.getTeamAllPlayersForSeason(game.getHome_team(),game.getSeason());
        model.addAttribute("home_players", home_players);

        List<Player> guest_players = teamService.getTeamAllPlayersForSeason(game.getGuest_team(),game.getSeason());
        model.addAttribute("guest_players", guest_players);

        List<Goal> goalList = goalService.goalsFromGame(gameId);
        model.addAttribute("goalList",goalList);

        String text = "pokus";
        model.addAttribute("zkouska",text);

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
        model.addAttribute("home_players", home_players);

        List<Player> guest_players = teamService.getTeamAllPlayersForSeason(game.getGuest_team(),game.getSeason());
        model.addAttribute("guest_players", guest_players);

        List<Goal> goalList = goalService.goalsFromGame(gameId);
        model.addAttribute("goalList",goalList);

        String text = "pokus";
        model.addAttribute("zkouska",text);

        return "game_fill";
    }

    @PostMapping("/game_fill/{gameId}")
    public String get1(@PathVariable Long gameId,@ModelAttribute("goal") Goal vstrelenygoal,Model model) {
        Game game = gameRepositoryNew.findById(gameId).get();
        model.addAttribute("game",game);

        Goal goal = new Goal();
        model.addAttribute("goal",goal);

        List<Player> home_players = teamService.getTeamAllPlayersForSeason(game.getHome_team(),game.getSeason());
        model.addAttribute("home_players", home_players);

        List<Player> guest_players = teamService.getTeamAllPlayersForSeason(game.getGuest_team(),game.getSeason());
        model.addAttribute("guest_players", guest_players);

        vstrelenygoal = goalService.goalToSave(vstrelenygoal,gameId,(game.getSeason()).toString());

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
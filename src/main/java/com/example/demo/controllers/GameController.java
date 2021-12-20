package com.example.demo.controllers;

import com.example.demo.Services.TeamService;
import com.example.demo.model.Game;
import com.example.demo.model.Goal;
import com.example.demo.model.Player;
import com.example.demo.model.Team;
import com.example.demo.repositories.GameRepositoryNew;
import com.example.demo.repositories.TeamRepository;
import com.example.demo.repositories.TeamRepositoryNew;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

        String homeTeam = teamRepositoryNew.findById(game.getHome().longValue()).get().getName();
        game.setHome_team(homeTeam);

        String guestTeam = teamRepositoryNew.findById(game.getGuest().longValue()).get().getName();
        game.setGuest_team(guestTeam);

        return "game_registration_success";
    }

    @GetMapping("/games")
    public String gamesList(Model model){
//        Game game = new Game();
//        model.addAttribute("game", game);

        List<Game> gameList = (List<Game>) gameRepositoryNew.findAll();
        model.addAttribute("gameList",gameList);

        return "games";
    }

    @PostMapping("/games")
    public String odeslanyGamesFormular(@ModelAttribute("game") Game game){

        return "game_fill";
    }

    // bacha na sezonu

    @GetMapping("/game_fill/{id}")
    public String get(@PathVariable String id, Model model) {
        Long IDcko = Long.valueOf(id);
        Game game = gameRepositoryNew.findById(IDcko).get();
        model.addAttribute("game",game);

        Goal goal = new Goal();
        model.addAttribute("goal",goal);

        List<Player> home_players = teamService.getTeamAllPlayersForSeason(game.getHome_team(),2019);
        model.addAttribute("home_players", home_players);

        List<Player> guest_players = teamService.getTeamAllPlayersForSeason(game.getGuest_team(),2019);
        model.addAttribute("guest_players", guest_players);

        return "game_fill";
    }

}
package com.example.demo.controllers;

import com.example.demo.Services.GameService;
import com.example.demo.Services.GoalService;
import com.example.demo.Services.PlayerService;
import com.example.demo.Services.TeamService;
import com.example.demo.model.*;
import com.example.demo.repositories.*;
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
    private final GameRepository gameRepository;
    private final AllSeriesRepository allSeriesRepository;

    public GameController(GameRepositoryNew gameRepositoryNew, GoalService goalService, TeamRepositoryNew teamRepositoryNew, TeamService teamService, GameService gameService, GoalRepositoryNew goalRepositoryNew, PlayerService playerService, GameRepository gameRepository, AllSeriesRepository allSeriesRepository) {
        this.gameRepositoryNew = gameRepositoryNew;
        this.goalService = goalService;
        this.teamRepositoryNew = teamRepositoryNew;
        this.teamService = teamService;
        this.gameService = gameService;
        this.goalRepositoryNew = goalRepositoryNew;
        this.playerService = playerService;
        this.gameRepository = gameRepository;
        this.allSeriesRepository = allSeriesRepository;
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
        model.addAttribute("home_players", home_players);

        List<Player> guest_players = teamService.getTeamAllPlayersForSeason(game.getGuest_team(),game.getSeason());
        model.addAttribute("guest_players", guest_players);

        model.addAttribute("goalListPeriod1",goalService.goalsForPeriod(gameId,1));
        model.addAttribute("goalListPeriod2",goalService.goalsForPeriod(gameId,2));
        model.addAttribute("goalListPeriod3",goalService.goalsForPeriod(gameId,3));
        model.addAttribute("goalListOvertime",goalService.goalsForPeriod(gameId,4));

        String prubeznyVysledek = gameService.prubeznyVysledekZapasu(gameId);
        model.addAttribute("prubeznyVysledek",prubeznyVysledek);

        Long lastGoalId = goalService.lastGoalFromGame(gameId).getId();
        model.addAttribute("lastGoalId",lastGoalId);

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

        model.addAttribute("goalListPeriod1",goalService.goalsForPeriod(gameId,1));
        model.addAttribute("goalListPeriod2",goalService.goalsForPeriod(gameId,2));
        model.addAttribute("goalListPeriod3",goalService.goalsForPeriod(gameId,3));
        model.addAttribute("goalListOvertime",goalService.goalsForPeriod(gameId,4));

        String prubeznyVysledek = gameService.prubeznyVysledekZapasu(gameId);
        model.addAttribute("prubeznyVysledek",prubeznyVysledek);

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

        model.addAttribute("goalListPeriod1",goalService.goalsForPeriod(gameId,1));
        model.addAttribute("goalListPeriod2",goalService.goalsForPeriod(gameId,2));
        model.addAttribute("goalListPeriod3",goalService.goalsForPeriod(gameId,3));
        model.addAttribute("goalListOvertime",goalService.goalsForPeriod(gameId,4));

        String prubeznyVysledek = gameService.prubeznyVysledekZapasu(gameId);
        model.addAttribute("prubeznyVysledek",prubeznyVysledek);

        return "game_fill";
    }



    @GetMapping("/game_filled/{gameId}/{samostatneNajezdy}")
    public String vyplnenaHra(@PathVariable Long gameId,@PathVariable Integer samostatneNajezdy, Model model){

        List<Goal> goalListFirstPeriod = goalService.goalsForPeriod(gameId,1);
        model.addAttribute("goalListFirstPeriod",goalListFirstPeriod);
        List<Goal> goalListSecondPeriod = goalService.goalsForPeriod(gameId,2);
        model.addAttribute("goalListSecondPeriod",goalListSecondPeriod);
        List<Goal> goalListThirdPeriod = goalService.goalsForPeriod(gameId,3);
        model.addAttribute("goalListThirdPeriod",goalListThirdPeriod);
        List<Goal> goalListOvertime = goalService.goalsForPeriod(gameId,4);
        model.addAttribute("goalListOvertime",goalListOvertime);

        Game game = gameService.gameSave(gameId, samostatneNajezdy);
        model.addAttribute("game",game);

        return "game_filled";
    }

    @GetMapping("/games_test")
    public String gamesListTest(Model model){
        int seriesLength = 4;
        int seriesPause = 2;

        System.out.println("\nDelaka serie = "+seriesLength +" -- "+"pauza = "+seriesPause+"\n");

        String strana ="home";
        List<Game> gameList = gameService.teamGames(48L,2018L,strana);

        List<AllSeries> allSeriesList = allSeriesRepository.AllSeriesList(gameList,seriesLength,seriesPause);
        for (AllSeries allSeries : allSeriesList){
            System.out.println(allSeries.getGame1().getRound_home()+" "+allSeries.getGame2().getRound_home()+" "+allSeries.getGame3().getRound_home());
        }

//        double pocet = 0;
//        double pocetTeamu = 0;
//        double jednicky = 0;
//        double neJednaPrvniZapas =0;
//
//        List <serie3> serie3List = new ArrayList<>();
//
//        List<Game> gameList = new ArrayList<>();
//        List<Team> teamList = teamService.getAllTeamsForSeason(2018L);
//        pocetTeamu = teamList.size();
//        for (Team team : teamList) {
//            gameList = gameService.teamGames(team.getId(),2018L,strana);
//            serie3List = gameService.serie3List(gameList, strana);
//            pocet += serie3List.size();
//
//            for (serie3 s: serie3List) {
//                //if (s.getGame1().getVysledek_sazka() != 1 && s.getGame2().getVysledek_sazka()!=1 && (s.getGame3().getVysledek_sazka()==1 || s.getGame3().getVysledek_sazka()==10)) jednicky++;
//                if (s.getGame1().getVysledek_sazka() == 1 && (s.getGame2().getVysledek_sazka() ==2 || s.getGame2().getVysledek_sazka() >9)) jednicky++;
//                if (s.getGame1().getVysledek_sazka() == 1) neJednaPrvniZapas++;
//            }
//
//        }
        model.addAttribute("gameList",gameList);



//        System.out.println("Celek teamu: "+pocetTeamu);
//        System.out.println("Celek sérií: "+pocet);
//        System.out.println("průměr: "+pocet/pocetTeamu);
//
//        System.out.println("počet prvních vyhraných zápasů " +strana+ ": " +jednicky+ "/"+neJednaPrvniZapas+ "  Statisticky: " + jednicky/neJednaPrvniZapas);

        return "games_test";
    }

}
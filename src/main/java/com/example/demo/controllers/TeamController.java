package com.example.demo.controllers;

import com.example.demo.Services.GameService;
import com.example.demo.Services.PlayerService;
import com.example.demo.Services.TeamService;
import com.example.demo.model.Player;
import com.example.demo.model.Team;
import com.example.demo.repositories.GameRepository;
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
    private final GameRepository gameRepository;

    public TeamController(EntityManager entityManager, TeamRepositoryNew teamRepositoryNew, TeamService teamService, GameService gameService, PlayerRepositoryNew playerRepositoryNew, PlayerService playerService, GameRepository gameRepository) {
        this.entityManager = entityManager;
        this.teamRepositoryNew = teamRepositoryNew;
        this.teamService = teamService;
        this.gameService = gameService;
        this.playerRepositoryNew = playerRepositoryNew;
        this.playerService = playerService;
        this.gameRepository = gameRepository;
    }


    @GetMapping("/teams/{season}")
    public String getAllTeamsForSeason(@PathVariable Integer season, Model model){
        model.addAttribute("Atlantic", teamService.teamListDivision("Atlantic",season));
        model.addAttribute("Metropolitan", teamService.teamListDivision("Metropolitan",season));
        model.addAttribute("Central", teamService.teamListDivision("Central",season));
        model.addAttribute("Pacific", teamService.teamListDivision("Pacific",season));
        model.addAttribute("season",season);
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

    @GetMapping("/team_all_players_for_season/{season}/{teamId}/{gameId}")
    public String teamPlayers (@PathVariable Integer season,@PathVariable Long teamId,@PathVariable Long gameId,Model model){
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
    @PostMapping("/team_all_players_for_season/{season}/{teamId}/{gameId}")
    public String teamPlayersAdd (@PathVariable Integer season,@PathVariable Long teamId,@PathVariable Long gameId,
                                  @ModelAttribute("playerNew")
            Player playerToSave, Model model){
        String message ="";
//        System.out.println(gameId);

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

        if (gameId != 0){
//            String returnAddress = "game_fill/"+gameId;
//            System.out.println(returnAddress);
            return "redirect:/game_fill/"+gameId;
        }

        model.addAttribute("message",message);

        List<Player> playersList = teamService.getTeamAllPlayersForSeason(team.getName(),season);
        model.addAttribute("listOfPlayers",playersList);

        Player player = new Player();
        model.addAttribute("playerNew",player);

        return "team_all_players_for_season";
    }

    @Transactional
    @GetMapping("/team_all_players_for_season/{season}/{teamId}/{gameId}/{playerId}")
    public String teamPlayersChange (@PathVariable Integer season,@PathVariable Long teamId,@PathVariable Long gameId,
                                     @PathVariable String playerId,Model model){
        String actionSelected = playerId.substring(playerId.length()-1);
        Long playerID = Long.valueOf(playerId.substring(0,(playerId.length()-1)));
        String message= "";

        if (actionSelected.equals("d")){
        message ="deleted player " + playerService.playerNameById(playerID);
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

    @GetMapping("/team_detail")
    public String getTeamDetail(Model model){
        Integer season = 2018;
        int gamesCount = 82;
        int gamesCountHalf = 41;
        List<Team> teamList = teamService.getAllTeamsForSeason(season.longValue());
        model.addAttribute("teamList",teamList);
        Long teamId = 38L;

        int winsNumberHome = teamService.teamGameResults(teamId,season,1,"home");
        int winsPercentHome = winsNumberHome*100/gamesCountHalf;
        int lostNumberHome = teamService.teamGameResults(teamId,season,2,"home");
        int lostPercentHome = lostNumberHome*100/gamesCountHalf;
        int tieWinsNumberHome = teamService.teamGameResults(teamId,season,10,"home");
        int tieWinsPercentHome = tieWinsNumberHome*100/gamesCountHalf;
        int tieLostNumberHome = teamService.teamGameResults(teamId,season,20,"home");
        int tieLostPercentHome = tieLostNumberHome*100/gamesCountHalf;
        int tieNumberHome = tieWinsNumberHome+tieLostNumberHome;
        int tiePercentHome = tieNumberHome*100/gamesCountHalf;

        model.addAttribute("winsNumberHome",winsNumberHome);
        model.addAttribute("winsPercentHome",winsPercentHome);
        model.addAttribute("lostNumberHome",lostNumberHome);
        model.addAttribute("lostPercentHome",lostPercentHome);
        model.addAttribute("tieNumberHome",tieNumberHome);
        model.addAttribute("tiePercentHome",tiePercentHome);
        model.addAttribute("tieWinsNumberHome",tieWinsNumberHome);
        model.addAttribute("tieWinsPercentHome",tieWinsPercentHome);
        model.addAttribute("tieLostNumberHome",tieLostNumberHome);
        model.addAttribute("tieLostPercentHome",tieLostPercentHome);

        int winsNumberGuest = teamService.teamGameResults(teamId,season,2,"guest");
        int winsPercentGuest = winsNumberGuest*100/gamesCountHalf;
        int lostNumberGuest = teamService.teamGameResults(teamId,season,1,"guest");
        int lostPercentGuest = lostNumberGuest*100/gamesCountHalf;
        int tieWinsNumberGuest = teamService.teamGameResults(teamId,season,20,"guest");
        int tieWinsPercentGuest = tieWinsNumberGuest*100/gamesCountHalf;
        int tieLostNumberGuest = teamService.teamGameResults(teamId,season,10,"guest");
        int tieLostPercentGuest = tieLostNumberGuest*100/gamesCountHalf;
        int tieNumberGuest = tieWinsNumberGuest+tieLostNumberGuest;
        int tiePercentGuest = tieNumberGuest*100/gamesCountHalf;

        model.addAttribute("winsNumberGuest",winsNumberGuest);
        model.addAttribute("winsPercentGuest",winsPercentGuest);
        model.addAttribute("lostNumberGuest",lostNumberGuest);
        model.addAttribute("lostPercentGuest",lostPercentGuest);
        model.addAttribute("tieNumberGuest",tieNumberGuest);
        model.addAttribute("tiePercentGuest",tiePercentGuest);
        model.addAttribute("tieWinsNumberGuest",tieWinsNumberGuest);
        model.addAttribute("tieWinsPercentGuest",tieWinsPercentGuest);
        model.addAttribute("tieLostNumberGuest",tieLostNumberGuest);
        model.addAttribute("tieLostPercentGuest",tieLostPercentGuest);

        int winsNumber = winsNumberHome + winsNumberGuest;
        int winsPercent = winsNumber*100/gamesCount;
        int lostNumber = lostNumberHome + lostNumberGuest;
        int lostPercent = lostNumber*100/gamesCount;
        int tieNumber = tieNumberHome + tieNumberGuest;
        int tiePercent = tieNumber*100/gamesCount;
        int tieWinsNumber = tieWinsNumberHome + tieWinsNumberGuest;
        int tieWinsPercent = tieWinsNumber*100/gamesCount;
        int tieLostNumber = tieLostNumberHome + tieLostNumberGuest;
        int tieLostPercent = tieLostNumber*100/gamesCount;

        model.addAttribute("winsNumber",winsNumber);
        model.addAttribute("winsPercent",winsPercent);
        model.addAttribute("lostNumber",lostNumber);
        model.addAttribute("lostPercent",lostPercent);
        model.addAttribute("tieNumber",tieNumber);
        model.addAttribute("tiePercent",tiePercent);
        model.addAttribute("tieWinsNumber",tieWinsNumber);
        model.addAttribute("tieWinsPercent",tieWinsPercent);
        model.addAttribute("tieLostNumber",tieLostNumber);
        model.addAttribute("tieLostPercent",tieLostPercent);

        // TAB Tretiny
        int winsNumber1Period = teamService.get1PeriodWinsCount(teamId,season.longValue(),"home")
                +teamService.get1PeriodWinsCount(teamId,season.longValue(),"guest");
        int winsPercent1Period = winsNumber1Period*100/gamesCount;
        int winsNumber2Periods = teamService.get2PeriodsWinsCount(teamId,season.longValue(),"home")
                +teamService.get2PeriodsWinsCount(teamId,season.longValue(),"guest");
        int winsPercent2Periods = winsNumber2Periods*100/gamesCount;

        model.addAttribute("winsNumber1Period",winsNumber1Period);
        model.addAttribute("winsPercent1Period",winsPercent1Period);
        model.addAttribute("winsNumber2Periods",winsNumber2Periods);
        model.addAttribute("winsPercent2Periods",winsPercent2Periods);

        int winsNumberHome1Period = teamService.get1PeriodWinsCount(teamId,season.longValue(),"home");
        int winsPercentHome1Period = winsNumberHome1Period*100/gamesCountHalf;
        int winsNumberHome2Periods = teamService.get2PeriodsWinsCount(teamId,season.longValue(),"home");
        int winsPercentHome2Periods = winsNumberHome2Periods*100/gamesCountHalf;

        model.addAttribute("winsNumberHome1Period",winsNumberHome1Period);
        model.addAttribute("winsPercentHome1Period",winsPercentHome1Period);
        model.addAttribute("winsNumberHome2Periods",winsNumberHome2Periods);
        model.addAttribute("winsPercentHome2Periods",winsPercentHome2Periods);

        int winsNumberGuest1Period = teamService.get1PeriodWinsCount(teamId,season.longValue(),"guest");
        int winsPercentGuest1Period = winsNumberGuest1Period*100/gamesCountHalf;
        int winsNumberGuest2Periods = teamService.get2PeriodsWinsCount(teamId,season.longValue(),"guest");
        int winsPercentGuest2Periods = winsNumberGuest2Periods*100/gamesCountHalf;

        model.addAttribute("winsNumberGuest1Period",winsNumberGuest1Period);
        model.addAttribute("winsPercentGuest1Period",winsPercentGuest1Period);
        model.addAttribute("winsNumberGuest2Periods",winsNumberGuest2Periods);
        model.addAttribute("winsPercentGuest2Periods",winsPercentGuest2Periods);

        // TAB GOLY
        int winsGoalHomeNumber = teamService.getSumTeamGoalsInGamesByGameResult(teamId,season.longValue(),"home",1);
        String winsGoalHomeAverage = String.format("%.2f",winsGoalHomeNumber/(double)winsNumberHome);
        int lostGoalHomeNumber = teamService.getSumTeamGoalsInGamesByGameResult(teamId,season.longValue(),"home",2);
        String lostGoalHomeAverage = String.format("%.2f",lostGoalHomeNumber/(double)lostNumberHome);
        int tieGoalHomeNumber = teamService.getSumTeamGoalsInGamesByGameResult(teamId,season.longValue(),"home",10)+
                teamService.getSumTeamGoalsInGamesByGameResult(teamId,season.longValue(),"home",20);
        String tieGoalHomeAverage = String.format("%.2f",tieGoalHomeNumber/(double)tieNumberHome);
        int sumGoalHomeNumber = winsGoalHomeNumber+lostGoalHomeNumber+tieGoalHomeNumber;
        String sumGoalHomeAverage = String.format("%.2f",sumGoalHomeNumber/(double)(gamesCountHalf));

        model.addAttribute("winsGoalHomeNumber",winsGoalHomeNumber);
        model.addAttribute("winsGoalHomeAverage",winsGoalHomeAverage);
        model.addAttribute("lostGoalHomeNumber",lostGoalHomeNumber);
        model.addAttribute("lostGoalHomeAverage",lostGoalHomeAverage);
        model.addAttribute("tieGoalHomeNumber",tieGoalHomeNumber);
        model.addAttribute("tieGoalHomeAverage",tieGoalHomeAverage);
        model.addAttribute("sumGoalHomeNumber",sumGoalHomeNumber);
        model.addAttribute("sumGoalHomeAverage",sumGoalHomeAverage);

        int winsGoalGuestNumber = teamService.getSumTeamGoalsInGamesByGameResult(teamId,season.longValue(),"guest",2);
        String winsGoalGuestAverage = String.format("%.2f",winsGoalGuestNumber/(double)winsNumberGuest);
        int lostGoalGuestNumber = teamService.getSumTeamGoalsInGamesByGameResult(teamId,season.longValue(),"guest",1);
        String lostGoalGuestAverage = String.format("%.2f",lostGoalGuestNumber/(double)lostNumberGuest);
        int tieGoalGuestNumber = teamService.getSumTeamGoalsInGamesByGameResult(teamId,season.longValue(),"guest",10)+
                teamService.getSumTeamGoalsInGamesByGameResult(teamId,season.longValue(),"guest",20);
        String tieGoalGuestAverage = String.format("%.2f",tieGoalGuestNumber/(double)tieNumberGuest);
        int sumGoalGuestNumber = winsGoalGuestNumber+lostGoalGuestNumber+tieGoalGuestNumber;
        String sumGoalGuestAverage = String.format("%.2f",sumGoalGuestNumber/(double)(gamesCountHalf));

        model.addAttribute("winsGoalGuestNumber",winsGoalGuestNumber);
        model.addAttribute("winsGoalGuestAverage",winsGoalGuestAverage);
        model.addAttribute("lostGoalGuestNumber",lostGoalGuestNumber);
        model.addAttribute("lostGoalGuestAverage",lostGoalGuestAverage);
        model.addAttribute("tieGoalGuestNumber",tieGoalGuestNumber);
        model.addAttribute("tieGoalGuestAverage",tieGoalGuestAverage);
        model.addAttribute("sumGoalGuestNumber",sumGoalGuestNumber);
        model.addAttribute("sumGoalGuestAverage",sumGoalGuestAverage);

        int winsGoalNumber = winsGoalHomeNumber+winsGoalGuestNumber;
        String winsGoalAverage = String.format("%.2f",winsGoalNumber/(double)winsNumber);
        int lostGoalNumber = lostGoalHomeNumber+lostGoalGuestNumber;
        String lostGoalAverage = String.format("%.2f",lostGoalNumber/(double)lostNumber);
        int tieGoalNumber = tieGoalHomeNumber+tieGoalGuestNumber;
        String tieGoalAverage = String.format("%.2f",tieGoalNumber/(double)tieNumber);
        int sumGoalNumber = winsGoalNumber+lostGoalNumber+tieGoalNumber;
        String sumGoalAverage = String.format("%.2f",sumGoalNumber/(double)(gamesCount));

        model.addAttribute("winsGoalNumber",winsGoalNumber);
        model.addAttribute("winsGoalAverage",winsGoalAverage);
        model.addAttribute("lostGoalNumber",lostGoalNumber);
        model.addAttribute("lostGoalAverage",lostGoalAverage);
        model.addAttribute("tieGoalNumber",tieGoalNumber);
        model.addAttribute("tieGoalAverage",tieGoalAverage);
        model.addAttribute("sumGoalNumber",sumGoalNumber);
        model.addAttribute("sumGoalAverage",sumGoalAverage);

        // TAB PRVNI GOL
        int winsFirstGoalNumberHome = teamService.getSumTeamFirstGoalsInGamesByGameResult(teamId,season.longValue(),"home",1);
        int winsPercentFirstGoalHome = winsFirstGoalNumberHome*100/winsNumberHome;
        int lostFirstGoalNumberHome = teamService.getSumTeamFirstGoalsInGamesByGameResult(teamId,season.longValue(),"home",2);
        int lostPercentFirstGoalHome = lostFirstGoalNumberHome*100/lostNumberHome;
        int tieFirstGoalNumberHome = teamService.getSumTeamFirstGoalsInGamesByGameResult(teamId,season.longValue(),"home",10)+
                teamService.getSumTeamFirstGoalsInGamesByGameResult(teamId,season.longValue(),"home",20);
        int tiePercentFirstGoalHome = tieFirstGoalNumberHome*100/tieNumberHome;
        int sumFirstGoalNumberHome = winsFirstGoalNumberHome+lostFirstGoalNumberHome+tieFirstGoalNumberHome;
        int sumPercentFirstGoalHome = sumFirstGoalNumberHome*100/gamesCountHalf;

        model.addAttribute("winsFirstGoalNumberHome",winsFirstGoalNumberHome);
        model.addAttribute("winsPercentFirstGoalHome",winsPercentFirstGoalHome);
        model.addAttribute("lostFirstGoalNumberHome",lostFirstGoalNumberHome);
        model.addAttribute("lostPercentFirstGoalHome",lostPercentFirstGoalHome);
        model.addAttribute("tieFirstGoalNumberHome",tieFirstGoalNumberHome);
        model.addAttribute("tiePercentFirstGoalHome",tiePercentFirstGoalHome);
        model.addAttribute("sumFirstGoalNumberHome",sumFirstGoalNumberHome);
        model.addAttribute("sumPercentFirstGoalHome",sumPercentFirstGoalHome);

        int winsFirstGoalNumberGuest = teamService.getSumTeamFirstGoalsInGamesByGameResult(teamId,season.longValue(),"guest",2);
        int winsPercentFirstGoalGuest = winsFirstGoalNumberGuest*100/winsNumberGuest;
        int lostFirstGoalNumberGuest = teamService.getSumTeamFirstGoalsInGamesByGameResult(teamId,season.longValue(),"guest",1);
        int lostPercentFirstGoalGuest = lostFirstGoalNumberGuest*100/lostNumberGuest;
        int tieFirstGoalNumberGuest = teamService.getSumTeamFirstGoalsInGamesByGameResult(teamId,season.longValue(),"guest",10)+
                teamService.getSumTeamFirstGoalsInGamesByGameResult(teamId,season.longValue(),"guest",20);
        int tiePercentFirstGoalGuest = tieFirstGoalNumberGuest*100/tieNumberGuest;
        int sumFirstGoalNumberGuest = winsFirstGoalNumberGuest+lostFirstGoalNumberGuest+tieFirstGoalNumberGuest;
        int sumPercentFirstGoalGuest = sumFirstGoalNumberGuest*100/gamesCountHalf;

        model.addAttribute("winsFirstGoalNumberGuest",winsFirstGoalNumberGuest);
        model.addAttribute("winsPercentFirstGoalGuest",winsPercentFirstGoalGuest);
        model.addAttribute("lostFirstGoalNumberGuest",lostFirstGoalNumberGuest);
        model.addAttribute("lostPercentFirstGoalGuest",lostPercentFirstGoalGuest);
        model.addAttribute("tieFirstGoalNumberGuest",tieFirstGoalNumberGuest);
        model.addAttribute("tiePercentFirstGoalGuest",tiePercentFirstGoalGuest);
        model.addAttribute("sumFirstGoalNumberGuest",sumFirstGoalNumberGuest);
        model.addAttribute("sumPercentFirstGoalGuest",sumPercentFirstGoalGuest);

        int winsFirstGoalNumber = winsFirstGoalNumberHome+winsFirstGoalNumberGuest;
        int winsPercentFirstGoal = winsFirstGoalNumber*100/winsNumber;
        int lostFirstGoalNumber = lostFirstGoalNumberHome+lostFirstGoalNumberGuest;
        int lostPercentFirstGoal = lostFirstGoalNumber*100/lostNumber;
        int tieFirstGoalNumber = tieFirstGoalNumberHome+tieFirstGoalNumberGuest;
        int tiePercentFirstGoal = tieFirstGoalNumber*100/tieNumber;
        int sumFirstGoalNumber = winsFirstGoalNumber + lostFirstGoalNumber + tieFirstGoalNumber;
        int sumPercentFirstGoal = sumFirstGoalNumber*100/gamesCount;

        model.addAttribute("winsFirstGoalNumber",winsFirstGoalNumber);
        model.addAttribute("winsPercentFirstGoal",winsPercentFirstGoal);
        model.addAttribute("lostFirstGoalNumber",lostFirstGoalNumber);
        model.addAttribute("lostPercentFirstGoal",lostPercentFirstGoal);
        model.addAttribute("tieFirstGoalNumber",tieFirstGoalNumber);
        model.addAttribute("tiePercentFirstGoal",tiePercentFirstGoal);
        model.addAttribute("sumFirstGoalNumber",sumFirstGoalNumber);
        model.addAttribute("sumPercentFirstGoal",sumPercentFirstGoal);

        // TAB Rozdíl gólů
        String winsGoalDifferenceAvgHome = String.format("%.2f",teamService.getGoalDifference(teamId,season.longValue(),"home",1)/(double)winsNumberHome);
        String lostGoalDifferenceAvgHome = String.format("%.2f",teamService.getGoalDifference(teamId,season.longValue(),"home",2)/(double)lostNumberHome);
        String tieGoalDifferenceAvgHome = "0";
        String sumGoalDifferenceAvgHome = String.format("%.2f",(teamService.getGoalDifference(teamId,season.longValue(),"home",1)+
                teamService.getGoalDifference(teamId,season.longValue(),"home",2))/(double)(winsNumberHome+lostNumberHome));

        model.addAttribute("winsGoalDifferenceAvgHome",winsGoalDifferenceAvgHome);
        model.addAttribute("lostGoalDifferenceAvgHome",lostGoalDifferenceAvgHome);
        model.addAttribute("tieGoalDifferenceAvgHome",tieGoalDifferenceAvgHome);
        model.addAttribute("sumGoalDifferenceAvgHome",sumGoalDifferenceAvgHome);

        String winsGoalDifferenceAvgGuest = String.format("%.2f",teamService.getGoalDifference(teamId,season.longValue(),"guest",2)/(double)winsNumberGuest);
        String lostGoalDifferenceAvgGuest = String.format("%.2f",teamService.getGoalDifference(teamId,season.longValue(),"guest",1)/(double)lostNumberGuest);
        String tieGoalDifferenceAvgGuest = "0";
        String sumGoalDifferenceAvgGuest = String.format("%.2f",(teamService.getGoalDifference(teamId,season.longValue(),"guest",2)+
                teamService.getGoalDifference(teamId,season.longValue(),"guest",1))/(double)(winsNumberGuest+lostNumberGuest));

        model.addAttribute("winsGoalDifferenceAvgGuest",winsGoalDifferenceAvgGuest);
        model.addAttribute("lostGoalDifferenceAvgGuest",lostGoalDifferenceAvgGuest);
        model.addAttribute("tieGoalDifferenceAvgGuest",tieGoalDifferenceAvgGuest);
        model.addAttribute("sumGoalDifferenceAvgGuest",sumGoalDifferenceAvgGuest);

        String winsGoalDifferenceAvg = String.format("%.2f",(teamService.getGoalDifference(teamId,season.longValue(),"guest",2)+
                teamService.getGoalDifference(teamId,season.longValue(),"home",1))/(double)(winsNumberGuest+winsNumberHome));
        String lostGoalDifferenceAvg = String.format("%.2f",(teamService.getGoalDifference(teamId,season.longValue(),"guest",1)+
                teamService.getGoalDifference(teamId,season.longValue(),"home",2))/(double)(lostNumberGuest+lostNumberHome));
        String tieGoalDifferenceAvg = "0";
        String sumGoalDifferenceAvg = String.format("%.2f",(teamService.getGoalDifference(teamId,season.longValue(),"guest",2)+
                        teamService.getGoalDifference(teamId,season.longValue(),"home",1)+teamService.getGoalDifference(teamId,season.longValue(),"guest",1)+
                        teamService.getGoalDifference(teamId,season.longValue(),"home",2))/(double)(lostNumberGuest+lostNumberHome+winsNumberGuest+winsNumberHome));

        model.addAttribute("winsGoalDifferenceAvg",winsGoalDifferenceAvg);
        model.addAttribute("lostGoalDifferenceAvg",lostGoalDifferenceAvg);
        model.addAttribute("tieGoalDifferenceAvg",tieGoalDifferenceAvg);
        model.addAttribute("sumGoalDifferenceAvg",sumGoalDifferenceAvg);

        return "team_detail";
    }
}
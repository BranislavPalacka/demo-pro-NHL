package com.example.demo.repositories;

import com.example.demo.Services.TeamService;
import com.example.demo.model.Player;
import com.example.demo.model.Team;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class PlayerRepository {
    @PersistenceContext
    private final EntityManager entityManager;
    private final PlayerRepositoryNew playerRepositoryNew;
    private final TeamService teamService;

    public PlayerRepository(EntityManager entityManager, PlayerRepositoryNew playerRepositoryNew, TeamService teamService) {
        this.entityManager = entityManager;
        this.playerRepositoryNew = playerRepositoryNew;
        this.teamService = teamService;
    }


    public List<String> PlayersNamesTeam(String teamName, Integer season){

        int teamId = entityManager.createNativeQuery("SELECT id FROM team WHERE name='"+teamName+"'").getFirstResult();
        List<String> teamNames = entityManager.createNativeQuery("SELECT name FROM player WHERE team_id_"+season+"="+teamId).getResultList();
        return teamNames;
    }

    public String playerNameById(Integer playerID){
        return(String) entityManager.createNativeQuery("SELECT name FROM player WHERE id="+playerID).getResultList().get(0);
    }
    // pozor na sezonu
    public Long playerTeamById(Integer playerID, String season){
        Player player = (Player) entityManager.createNativeQuery("SELECT * FROM player WHERE id=" + playerID, Player.class).getResultList().get(0);
        return player.getTeam_id_2019();
    }

    public Player lastPayerAdded(){
        Player player = (Player) entityManager.createNativeQuery("SELECT * FROM player ORDER BY id DESC LIMIT 1",Player.class).getResultList().get(0);
        return player;
    }
    public List <String> playersTeamsBySeasons(Player player) {
        List<String> teamsBySeasons = new ArrayList<>();
            teamsBySeasons.add(teamService.teamNameById(player.getTeam_id_2018()));
            teamsBySeasons.add(teamService.teamNameById(player.getTeam_id_2019()));
            teamsBySeasons.add(teamService.teamNameById(player.getTeam_id_2020()));
            teamsBySeasons.add(teamService.teamNameById(player.getTeam_id_2021()));
        return teamsBySeasons;
    }

    public String[][] last5PlayersTeamsBySeasons(){
        String [][] pole = new String[5][6];

        List<Player> players =  entityManager.createNativeQuery("SELECT * FROM player ORDER BY id DESC LIMIT 5",Player.class).getResultList();

        int i=0;
        for (Player p : players) {
            pole[i][0] = players.get(i).getId().toString();
            pole[i][1] = players.get(i).getName();
            if (players.get(i).getTeam_id_2018() != null){
                pole[i][2] = teamService.teamNameById(players.get(i).getTeam_id_2018());
            }else pole[i][2] = "";
            if (players.get(i).getTeam_id_2019() != null){
                pole[i][3] = teamService.teamNameById(players.get(i).getTeam_id_2019());
            }else pole[i][3] = "";
            if (players.get(i).getTeam_id_2020() != null){
                pole[i][4] = teamService.teamNameById(players.get(i).getTeam_id_2020());
            }else pole[i][4] = "";
            if (players.get(i).getTeam_id_2021() != null){
                pole[i][5] = teamService.teamNameById(players.get(i).getTeam_id_2021());
            }else pole[i][5] = "";
            i++;
        }
        return pole;
    }

    public Player testNewPlayerTeamsBeforeSaving(Player player){
        if (player.getTeam_id_2018()==-1) player.setTeam_id_2018(null);
        if (player.getTeam_id_2019()==-1) player.setTeam_id_2019(null);
        if (player.getTeam_id_2020()==-1) player.setTeam_id_2020(null);
        if (player.getTeam_id_2021()==-1) player.setTeam_id_2021(null);
        return player;
    }

    public boolean anySeasonTeamFilled (Player player){
        return (player.getTeam_id_2018() != -1 || player.getTeam_id_2019() != -1 || player.getTeam_id_2020() != -1 || player.getTeam_id_2021() != -1);
    }

    @Transactional  // pokud bude voláno jinou metodou, musí být také @Transactional
    public void mergePlayer(Player newPlayer) {
        Player oldPlayer = playerRepositoryNew.findById(newPlayer.getId()).get();
        entityManager.detach(oldPlayer);    // nejdřív musím odpojit objekt od databáze
        oldPlayer = newPlayer;
        entityManager.merge(oldPlayer); // zapíšu objekt do databáze
    }

    public Player findPlayerByName(String playerName){
        List<Player> players = entityManager.createNativeQuery("SELECT * FROM player WHERE name='"+playerName+"'",Player.class).getResultList();
        if (players.size()==0) return null;
        return players.get(0);
    }

}
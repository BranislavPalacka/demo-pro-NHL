package com.example.demo.repositories;

import com.example.demo.Services.TeamService;
import com.example.demo.model.Player;
import com.example.demo.model.Team;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Comparator;
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

    public String playerNameById(Long playerID){
        return(String) entityManager.createNativeQuery("SELECT name FROM player WHERE id="+playerID).getResultList().get(0);
    }

    public Long playerTeamById(Long playerID, String season){
        Player player = (Player) entityManager.createNativeQuery("SELECT * FROM player WHERE id="+ playerID +" AND team_id_"+ season, Player.class).getResultList().get(0);
        if (season.equals("2018")) return player.getTeam_id_2018();
        if (season.equals("2019")) return player.getTeam_id_2019();
        if (season.equals("2020")) return player.getTeam_id_2020();
        if (season.equals("2021")) return player.getTeam_id_2021();
        return null;
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

    @Transactional
    public void removePlayerFromTeamSeason(Long playerId, Long season){
        Player player = playerRepositoryNew.findById(playerId).get();

        entityManager.clear();
        entityManager.detach(player);
            if (season==2018) player.setTeam_id_2018(null);
            if (season==2019) player.setTeam_id_2019(null);
            if (season==2020) player.setTeam_id_2020(null);
            if (season==2021) player.setTeam_id_2021(null);
        entityManager.merge(player);
    }

    @Transactional
    public void addPlayerForTeamSeason(Long playerId, Long season, Long teamId){
        Player player = playerRepositoryNew.findById(playerId).get();

        entityManager.clear();
        entityManager.detach(player);
            if (season==2018) player.setTeam_id_2018(teamId);
            if (season==2019) player.setTeam_id_2019(teamId);
            if (season==2020) player.setTeam_id_2020(teamId);
            if (season==2021) player.setTeam_id_2021(teamId);
        entityManager.merge(player);
    }

    // zastaralá a nepoužívaná
    public List<Player> prijmeniAjmeno(List<Player> playersList){
        for (Player player : playersList) {
            int poloha =  (player.getName().indexOf(' ') + 1);
            String prijmeni = player.getName().substring(poloha);
            String jmeno = player.getName().substring(0,poloha-1);
            player.setName(prijmeni+" "+jmeno);
        }
        return playersList;
    }
}
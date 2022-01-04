package com.example.demo.repositories;

import com.example.demo.Services.TeamService;
import com.example.demo.model.Player;
import org.springframework.stereotype.Component;

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
}
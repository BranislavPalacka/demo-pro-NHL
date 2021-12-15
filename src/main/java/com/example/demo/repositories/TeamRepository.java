package com.example.demo.repositories;

import com.example.demo.model.Player;
import com.example.demo.model.Team;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class TeamRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public TeamRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Team> queryForTeams() {
        EntityManager em = entityManager;
        List<Team> teams = em.createNativeQuery("SELECT * FROM team",Team.class)
                .getResultList();
        return teams;
    }

    public List<String> teamNames(){
        List<String> teamNames = entityManager.createNativeQuery("SELECT name FROM team").getResultList();
        return teamNames;
    }

    public List<String> teamListDivision(String divisionName, Integer season){
        List<String> teamListDivision = entityManager.createNativeQuery("SELECT name FROM team WHERE division_"+season+"='"+divisionName+"'").getResultList();
        return teamListDivision;
    }

    public Integer teamIdByName(String teamName){
        Integer teamId = (Integer) entityManager.createNativeQuery("SELECT id FROM team WHERE name='"+teamName+"'").getSingleResult();
        return  teamId;
    }

    public List<Player> teamAllPlayersForSeason(String teamName, Integer season){
        List<Player> playersList = entityManager.createNativeQuery("SELECT * FROM player WHERE team_id_"+season+"="+teamIdByName(teamName),Player.class).getResultList();
        return playersList;
    }
}
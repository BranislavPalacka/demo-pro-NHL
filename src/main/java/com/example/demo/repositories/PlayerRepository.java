package com.example.demo.repositories;

import com.example.demo.model.Team;
import org.springframework.data.repository.CrudRepositoryExtensionsKt;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class PlayerRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public PlayerRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<String> PlayersNamesTeam(String teamName, Integer season){

        int teamId = entityManager.createNativeQuery("SELECT id FROM ream WHERE name='"+teamName+"'").getFirstResult();
        List<String> teamNames = entityManager.createNativeQuery("SELECT name FROM player WHERE team_id_"+season+"="+teamId).getResultList();
        return teamNames;
    }
}
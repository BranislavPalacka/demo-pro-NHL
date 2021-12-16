package com.example.demo;

import com.example.demo.Services.TeamService;
import com.example.demo.model.Game;
import com.example.demo.model.Team;
import com.example.demo.repositories.TeamRepositoryNew;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Component
public class AppRun {

    @Autowired
    TeamService teamService;

    @Autowired
    TeamRepositoryNew teamRepositoryNew;


//    public void run(){
//        teamService.createAndAddMovie();
//    }

//    public void run(){
//        Iterable<Team> teams = teamRepository.findAll();
//        System.out.println("teams - " +teams);
//    }

    @PersistenceContext
    private EntityManager entityManager;

    public Team getTeam(Long id) {
        EntityManager em = entityManager;
        Team klub = em.find(Team.class, id);
        //em.detach(klub);  jen kdyz chci objek odpojit od databaze
        return klub;
    }

    public List<Team> queryForTeams() {
        EntityManager em = entityManager;
        List<Team> teams = em.createNativeQuery("SELECT * FROM team",Team.class)
                .getResultList();
        return teams;
    }

    @Transactional
    public void addTeam(String name){
        Team team = new Team();
        team.setName(name);
        entityManager.persist(team);
    }

    @Transactional
    public void deleteTeam(Long id){
        Team teamDel = getTeam(id);
        System.out.println(teamDel);
        entityManager.remove(teamDel);
    }

    @Transactional  // pokud bude voláno jinou metodou, musí být také @Transactional
    public void mergeTeam() {
        EntityManager em = entityManager;
        Team team = getTeam(1L);
        em.detach(team);    // nejdřív musím odpojit objekt od databáze
        team.setName("FFFFFkouska"); // potom udělám změnu
        em.merge(team); // zapíšu objekt do databáze
    }

    @Transactional
    public void run(){
//        deleteTeam(5L);
//        System.out.println(queryForTeams());
    }




}
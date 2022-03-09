package com.example.demo.repositories;

import com.example.demo.model.Game;
import com.example.demo.model.Player;
import com.example.demo.model.Team;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeamRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    private final GameRepositoryNew gameRepositoryNew;

    public TeamRepository(EntityManager entityManager, GameRepositoryNew gameRepositoryNew) {
        this.entityManager = entityManager;
        this.gameRepositoryNew = gameRepositoryNew;
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

    public List<Team> teamListDivision(String divisionName, Integer season){
        return (List<Team>) entityManager.createNativeQuery("SELECT * FROM team WHERE division_"+season+"='"+divisionName+"'",Team.class).getResultList();
    }

    public Integer teamIdByName(String teamName){
        Integer teamId = (Integer) entityManager.createNativeQuery("SELECT id FROM team WHERE name='"+teamName+"'").getSingleResult();
        return  teamId;
    }

    public String teamNameById(Long id){
        if (id != null && id !=-1) {
            String s = String.valueOf(entityManager.createNativeQuery("SELECT name FROM team where id="+id).getSingleResult());
            return s;
        }
        return "";
    }

    public List<Player> teamAllPlayersForSeason(String teamName, Integer season) {
        List<Player> playersList = entityManager.createNativeQuery("SELECT * FROM player WHERE team_id_" + season + "=" + teamIdByName(teamName), Player.class).getResultList();

        for (Player player : playersList) {
            int poloha =  (player.getName().indexOf(' ') + 1);
            String prijmeni = player.getName().substring(poloha);
            String jmeno = player.getName().substring(0,poloha-1);
            player.setName(prijmeni+" "+jmeno);
        }

        playersList.sort(Comparator.comparing(Player::getName));
        return playersList;
    }

    @Transactional
    public void importovaniCSV(String fileName) {
//        File f = new File("Files/nhl2018_2019_data.csv");
//        if(f.exists() && !f.isDirectory()) {
//            System.out.println("\n \n ANO");
//        }

        try {
            CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

            CSVReader reader =
                    new CSVReaderBuilder(new FileReader("Files/nhl2018_2019_data.csv")).
                            withCSVParser(parser).
                            withSkipLines(1). // Skiping firstline as it is header
                            build();
            List<Game> csv_objectList = reader.readAll().stream().map(data -> {
                Game gameImportedCsv = new Game();
                gameImportedCsv.setDate(Date.valueOf((data[0])));
                gameImportedCsv.setHome_team(data[1]);
                gameImportedCsv.setGuest_team(data[2]);
                gameImportedCsv.setHome(teamIdByName(data[1]));
                gameImportedCsv.setGuest(teamIdByName(data[2]));
                gameImportedCsv.setSeason(2018);
                return gameImportedCsv;
            }).collect(Collectors.toList());

            csv_objectList.forEach(gameRepositoryNew::save);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    public List<Team> getAllTeamsForSeason (Long season){
        return entityManager.createNativeQuery("SELECT * FROM team where division_2018 IS NOT null",Team.class).getResultList();
    }
    public List<Team> getAllConferenceTeamsForSeason (Long season, String conference){
        return entityManager.createNativeQuery("SELECT * FROM team where division_"+season+" IS NOT null AND conference='"+conference+"'",Team.class).getResultList();
    }
    public List<Team> getAllDivisionTeamsForSeason_2018 (Long season, String division){
        return entityManager.createNativeQuery("SELECT * FROM team where division_2018 IS NOT null AND division_"+season+"='"+division+"'",Team.class).getResultList();
    }

    public String teamsDivision (Long teamId, Integer season){
        return (String) entityManager.createNativeQuery("SELECT division_"+season+" FROM team where id="+teamId).getSingleResult();
    }
}
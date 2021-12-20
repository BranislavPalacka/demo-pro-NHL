package com.example.demo;

import com.example.demo.Services.TeamService;
import com.example.demo.model.Game;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class StringToSQLDateExample {

    TeamService teamService;

    public StringToSQLDateExample(TeamService teamService) {
        this.teamService = teamService;
    }
    //    public static void main(String[] args) throws IOException, CsvException {
//        parse();
//    }

    public static void parse() throws IOException, CsvException {
        try{
            CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

            CSVReader reader=
                    new CSVReaderBuilder(new FileReader("Files/nhl2018_2019_data.csv")).
                            withCSVParser(parser).
                            withSkipLines(1). // Skiping firstline as it is header
                            build();
            List<Game> csv_objectList = reader.readAll().stream().map(data-> {
                Game gameImportedCsv = new Game();
                gameImportedCsv.setDate(Date.valueOf((data[0])));
                gameImportedCsv.setHome_team(data[1]);
                gameImportedCsv.setGuest_team(data[2]);
//                gameImportedCsv.setHome(teamService.getTeamIdByName(data[1]));
//                gameImportedCsv.setGuest(teamService.getTeamIdByName(data[2]));
                return gameImportedCsv;
            }).collect(Collectors.toList());
            csv_objectList.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
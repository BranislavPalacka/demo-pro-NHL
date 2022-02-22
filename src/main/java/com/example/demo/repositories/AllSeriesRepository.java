package com.example.demo.repositories;

import com.example.demo.Services.GameService;
import com.example.demo.Services.GoalService;
import com.example.demo.model.AllSeries;
import com.example.demo.model.Game;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class AllSeriesRepository {

    @PersistenceContext
    private final EntityManager entityManager;
    private final GameService gameRepositoryNew;
    private final GoalService goalService;

    public AllSeriesRepository(EntityManager entityManager, GameService gameRepositoryNew, GoalService goalService) {
        this.entityManager = entityManager;
        this.gameRepositoryNew = gameRepositoryNew;
        this.goalService = goalService;
    }


    /*
    funkce převezme List Her a najde v něm požadované série
     */
    public List<AllSeries> AllSeriesList(List<Game> gameList, int seriesLength, int seriesPause ){
        List <AllSeries> seriesList = new ArrayList<>();

        for (int i = 0; i<gameList.size()-seriesLength+1; i++){
            AllSeries allSeries = new AllSeries();
            boolean pauseOK = false;

            // pause control before series
            for (int y = 1; y <= seriesPause; y++) {
                if (i - y >= 0) {

                    if (gameList.get(i).getRound_home() - y == gameList.get(i - y).getRound_home()) {
                        break;
                    }
                    if (gameList.get(i-y).getRound_home() >= gameList.get(i).getRound_home()-seriesPause) {
                        break;
                    }
                }
                pauseOK = true;
            }

            // looking for series
            if (pauseOK){
                for (int j = 0; j < seriesLength - 1; j++) {
                    if (gameList.get(i + j).getRound_home() + 1 != gameList.get(i + j + 1).getRound_home()) {
                        break;
                    }
                    if (j == seriesLength - 2) {
                        allSeries.setGame1(gameList.get(i));
                        allSeries.setGame2(gameList.get(i + 1));
                        allSeries.setGame3(gameList.get(i + 2));
                        if (seriesLength >= 4) allSeries.setGame4(gameList.get(i + 3));
                        if (seriesLength >= 5) allSeries.setGame5(gameList.get(i + 4));
                        if (seriesLength >= 6) allSeries.setGame6(gameList.get(i + 5));
                        if (seriesLength >= 7) allSeries.setGame7(gameList.get(i + 6));
                        if (seriesLength >= 8) allSeries.setGame8(gameList.get(i + 7));
                        if (seriesLength >= 9) allSeries.setGame9(gameList.get(i + 8));
                        if (seriesLength >= 10) allSeries.setGame10(gameList.get(i + 9));
                        seriesList.add(allSeries);
                        i = i + seriesLength-1;
                    }
                }
            }
        }

        return seriesList;
    }
}
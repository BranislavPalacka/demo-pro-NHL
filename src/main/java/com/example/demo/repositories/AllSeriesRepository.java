package com.example.demo.repositories;

import com.example.demo.Services.GameService;
import com.example.demo.Services.GoalService;
import com.example.demo.model.AllSeries;
import com.example.demo.model.Game;
import com.example.demo.model.Goal;
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


    /**
     * funkce převezme List Her a najde v něm požadované série
     *
     * @param gameList     - seznam her k prohledání
     * @param seriesLength - kolik her za sebou má série obsahovat
     * @param seriesPause  - kolik her na stejné straně jí nesmí předcházet
     * @param side         - home/guest
     * @return list<' AllSeries>
     */
    public List<AllSeries> AllSeriesList(List<Game> gameList, int seriesLength, int seriesPause, String side) {
        List<AllSeries> seriesList = new ArrayList<>();

        if (side.equals("home")) {
            for (int i = 0; i < gameList.size() - seriesLength + 1; i++) {
                AllSeries allSeries = new AllSeries();
                boolean pauseOK = false;

                // pause control before series
                for (int y = 1; y <= seriesPause; y++) {
                    if (i - y >= 0) {
                        if (gameList.get(i).getRound_home() - y == gameList.get(i - y).getRound_home()) {
                            break;
                        }
                        if (gameList.get(i - y).getRound_home() >= gameList.get(i).getRound_home() - seriesPause) {
                            break;
                        }
                    }
                    pauseOK = true;
                }

                // looking for series
                if (pauseOK) {
                    for (int j = 0; j < seriesLength - 1; j++) {
                        if (gameList.get(i + j).getRound_home() + 1 != gameList.get(i + j + 1).getRound_home()) {
                            break;
                        }
                        if (j == seriesLength - 2) {
                            allSeries.setGame1(gameList.get(i));
                            allSeries.setGame2(gameList.get(i + 1));
                            if (seriesLength >= 3) allSeries.setGame3(gameList.get(i + 2));
                            if (seriesLength >= 4) allSeries.setGame4(gameList.get(i + 3));
                            if (seriesLength >= 5) allSeries.setGame5(gameList.get(i + 4));
                            if (seriesLength >= 6) allSeries.setGame6(gameList.get(i + 5));
                            if (seriesLength >= 7) allSeries.setGame7(gameList.get(i + 6));
                            if (seriesLength >= 8) allSeries.setGame8(gameList.get(i + 7));
                            if (seriesLength >= 9) allSeries.setGame9(gameList.get(i + 8));
                            if (seriesLength >= 10) allSeries.setGame10(gameList.get(i + 9));
                            seriesList.add(allSeries);
                            i = i + seriesLength - 1;
                        }
                    }
                }
            }
        }

        if (side.equals("guest")) {
            for (int i = 0; i < gameList.size() - seriesLength + 1; i++) {
                AllSeries allSeries = new AllSeries();
                boolean pauseOK = false;

                // pause control before series
                for (int y = 1; y <= seriesPause; y++) {
                    if (i - y >= 0) {
                        if (gameList.get(i).getRound_guest() - y == gameList.get(i - y).getRound_guest()) {
                            break;
                        }
                        if (gameList.get(i - y).getRound_guest() >= gameList.get(i).getRound_guest() - seriesPause) {
                            break;
                        }
                    }
                    pauseOK = true;
                }

                // looking for series
                if (pauseOK) {
                    for (int j = 0; j < seriesLength - 1; j++) {
                        if (gameList.get(i + j).getRound_guest() + 1 != gameList.get(i + j + 1).getRound_guest()) {
                            break;
                        }
                        if (j == seriesLength - 2) {
                            allSeries.setGame1(gameList.get(i));
                            allSeries.setGame2(gameList.get(i + 1));
                            if (seriesLength >= 3) allSeries.setGame3(gameList.get(i + 2));
                            if (seriesLength >= 4) allSeries.setGame4(gameList.get(i + 3));
                            if (seriesLength >= 5) allSeries.setGame5(gameList.get(i + 4));
                            if (seriesLength >= 6) allSeries.setGame6(gameList.get(i + 5));
                            if (seriesLength >= 7) allSeries.setGame7(gameList.get(i + 6));
                            if (seriesLength >= 8) allSeries.setGame8(gameList.get(i + 7));
                            if (seriesLength >= 9) allSeries.setGame9(gameList.get(i + 8));
                            if (seriesLength >= 10) allSeries.setGame10(gameList.get(i + 9));
                            seriesList.add(allSeries);
                            i = i + seriesLength - 1;
                        }
                    }
                }
            }
        }
        return seriesList;
    }

    /**
     * vytiskne do konzole cisla kol pro serie a pod ne vysledky zapasu
     */
    public void allSeriesPrint(AllSeries allSeries, String strana, int seriesLength) {
        if (strana.equals("guest")) {
            if (seriesLength > 1) {
                System.out.println();
                System.out.print("Rounds: " + allSeries.getGame1().getRound_guest() + " " + allSeries.getGame2().getRound_guest());
            }
            if (seriesLength > 2) System.out.print(" " + allSeries.getGame3().getRound_guest());
            if (seriesLength > 3) System.out.print(" " + allSeries.getGame4().getRound_guest());
            if (seriesLength > 4) System.out.print(" " + allSeries.getGame5().getRound_guest());
            if (seriesLength > 5) System.out.print(" " + allSeries.getGame6().getRound_guest());
            if (seriesLength > 6) System.out.print(" " + allSeries.getGame7().getRound_guest());
            if (seriesLength > 7) System.out.print(" " + allSeries.getGame8().getRound_guest());
            if (seriesLength > 8) System.out.print(" " + allSeries.getGame9().getRound_guest());
            if (seriesLength > 9) System.out.print(" " + allSeries.getGame10().getRound_guest());

            if (seriesLength > 1) {
                System.out.println();
                System.out.print("Results: " + allSeries.getGame1().getVysledek_sazka() + " " + allSeries.getGame2().getVysledek_sazka());
            }
            if (seriesLength > 2) System.out.print(" " + allSeries.getGame3().getVysledek_sazka());
            if (seriesLength > 3) System.out.print(" " + allSeries.getGame4().getVysledek_sazka());
            if (seriesLength > 4) System.out.print(" " + allSeries.getGame5().getVysledek_sazka());
            if (seriesLength > 5) System.out.print(" " + allSeries.getGame6().getVysledek_sazka());
            if (seriesLength > 6) System.out.print(" " + allSeries.getGame7().getVysledek_sazka());
            if (seriesLength > 7) System.out.print(" " + allSeries.getGame8().getVysledek_sazka());
            if (seriesLength > 8) System.out.print(" " + allSeries.getGame9().getVysledek_sazka());
            if (seriesLength > 9) System.out.print(" " + allSeries.getGame10().getVysledek_sazka());
        }
        if (strana.equals("home")) {
            if (seriesLength > 1) {
                System.out.println();
                System.out.print("Rounds: " + allSeries.getGame1().getRound_home() + " " + allSeries.getGame2().getRound_home());
            }
            if (seriesLength > 2) System.out.print(" " + allSeries.getGame3().getRound_home());
            if (seriesLength > 3) System.out.print(" " + allSeries.getGame4().getRound_home());
            if (seriesLength > 4) System.out.print(" " + allSeries.getGame5().getRound_home());
            if (seriesLength > 5) System.out.print(" " + allSeries.getGame6().getRound_home());
            if (seriesLength > 6) System.out.print(" " + allSeries.getGame7().getRound_home());
            if (seriesLength > 7) System.out.print(" " + allSeries.getGame8().getRound_home());
            if (seriesLength > 8) System.out.print(" " + allSeries.getGame9().getRound_home());
            if (seriesLength > 9) System.out.print(" " + allSeries.getGame10().getRound_home());

            if (seriesLength > 1) {
                System.out.println();
                System.out.print("Results: " + allSeries.getGame1().getVysledek_sazka() + " " + allSeries.getGame2().getVysledek_sazka());
            }
            if (seriesLength > 2) System.out.print(" " + allSeries.getGame3().getVysledek_sazka());
            if (seriesLength > 3) System.out.print(" " + allSeries.getGame4().getVysledek_sazka());
            if (seriesLength > 4) System.out.print(" " + allSeries.getGame5().getVysledek_sazka());
            if (seriesLength > 5) System.out.print(" " + allSeries.getGame6().getVysledek_sazka());
            if (seriesLength > 6) System.out.print(" " + allSeries.getGame7().getVysledek_sazka());
            if (seriesLength > 7) System.out.print(" " + allSeries.getGame8().getVysledek_sazka());
            if (seriesLength > 8) System.out.print(" " + allSeries.getGame9().getVysledek_sazka());
            if (seriesLength > 9) System.out.print(" " + allSeries.getGame10().getVysledek_sazka());
        }
    }

    /**
     * Zjsti, zda zadaná hra dopadla dle očekávání
     *
     * @return booleen
     */
    public boolean jeSazka(Game game, int result) {
        if (result == 1 || result == 2) {
            if (game.getVysledek_sazka() == result) return true;
        } else if (result == 0) {
            if (game.getVysledek_sazka() > 9) return true;
        }
        return false;
    }

    public boolean jeSazkaSerie(AllSeries allSeries, int bet1) {
        return jeSazka(allSeries.getGame1(), bet1);
    }
    public boolean jeSazkaSerie(AllSeries allSeries, int bet1, int bet2) {
        return jeSazkaSerie(allSeries, bet1) && jeSazka(allSeries.getGame2(), bet2);
    }
    public boolean jeSazkaSerie(AllSeries allSeries, int bet1, int bet2, int bet3) {
        return jeSazkaSerie(allSeries, bet1, bet2) && jeSazka(allSeries.getGame3(), bet3);
    }
    public boolean jeSazkaSerie(AllSeries allSeries, int bet1, int bet2, int bet3, int bet4) {
        return jeSazkaSerie(allSeries, bet1, bet2,bet3) && jeSazka(allSeries.getGame4(), bet4);
    }
    public boolean jeSazkaSerie(AllSeries allSeries, int bet1, int bet2, int bet3, int bet4, int bet5) {
        return jeSazkaSerie(allSeries, bet1, bet2,bet3,bet4) && jeSazka(allSeries.getGame5(), bet5);
    }
    public boolean jeSazkaSerie(AllSeries allSeries, int bet1, int bet2, int bet3, int bet4, int bet5, int bet6) {
        return jeSazkaSerie(allSeries, bet1, bet2,bet3,bet4,bet5) && jeSazka(allSeries.getGame6(), bet5);
    }

    /**
     * Pro hledání sázky X tého zápasu v serii
     * @param allSeries - serie
     * @param gameNumber - číslo zápasu v sérii
     * @param bet - sázka
     * @return boolean
     */
    public boolean jeSazkaZapasCislo(AllSeries allSeries, int gameNumber, int bet){
        if (gameNumber == 1) return jeSazka(allSeries.getGame1(),bet);
        if (gameNumber == 2) return jeSazka(allSeries.getGame2(),bet);
        if (gameNumber == 3) return jeSazka(allSeries.getGame3(),bet);
        if (gameNumber == 4) return jeSazka(allSeries.getGame4(),bet);
        if (gameNumber == 5) return jeSazka(allSeries.getGame5(),bet);
        return false;
    }

    public Integer goluVZapase (Game game){
        return entityManager.createNativeQuery("SELECT * FROM goal WHERE game="+ game.getId(), Goal.class).getResultList().size();
    }


    public Integer goluVeTretine (Game game,int tretina){
        return null;
    }
}
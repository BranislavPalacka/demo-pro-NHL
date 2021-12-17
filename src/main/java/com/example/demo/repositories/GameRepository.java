package com.example.demo.repositories;

import com.example.demo.Services.GameService;
import com.example.demo.model.Game;
import com.example.demo.model.Team;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class GameRepository {

    @PersistenceContext
    private final EntityManager entityManager;
    private final GameRepositoryNew gameRepositoryNew;

    public GameRepository(EntityManager entityManager, GameRepositoryNew gameRepositoryNew) {
        this.entityManager = entityManager;
        this.gameRepositoryNew = gameRepositoryNew;
    }


//    public Integer getRound(String side, String teamName){
//        Integer gameRound = (Integer) entityManager.createNativeQuery("SELECT max(round_"+side+") FROM game WHERE "+side+"_team = "+teamName).getSingleResult();
//        if (gameRound == null) gameRound = 0;
//        return gameRound;
//    }

    @Transactional
    public List<Team> roundFill() {
        List<Team> teamList = entityManager.createNativeQuery("SELECT * FROM team WHERE division_2018 IS NOT NULL", Team.class).getResultList();

        for (int k = 0; k<teamList.size();k++) {
            Long id = teamList.get(k).getId();
            List<Game> GameList = entityManager.createNativeQuery("SELECT * FROM game WHERE home="+id+" OR guest="+id+" AND season=2018",Game.class).getResultList();

            int i =1;
            for (Game g: GameList) {
                if (g.getHome() == Math.toIntExact(id)){
                    entityManager.detach(g);
                    g.setRound_home(i);
                    entityManager.merge(g);
                    i++;
                }else{
                    entityManager.detach(g);
                    g.setRound_guest(i);
                    entityManager.merge(g);
                    i++;
                }
            }
        }
        return teamList;
    }
}
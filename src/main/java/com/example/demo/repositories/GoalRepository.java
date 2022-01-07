package com.example.demo.repositories;

import com.example.demo.Services.PlayerService;
import com.example.demo.model.Goal;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class GoalRepository {
    @PersistenceContext
    private final EntityManager entityManager;
    private final PlayerService playerService;
    private final GoalRepositoryNew goalRepositoryNew;
    private final TeamRepositoryNew teamRepositoryNew;

    public GoalRepository(EntityManager entityManager, PlayerService playerService, GoalRepositoryNew goalRepositoryNew, TeamRepositoryNew teamRepositoryNew) {
        this.entityManager = entityManager;
        this.playerService = playerService;
        this.goalRepositoryNew = goalRepositoryNew;
        this.teamRepositoryNew = teamRepositoryNew;
    }


    public List<Goal> generatedListOfGoals(Integer numberOfGoals) {
        List<Goal> goalEmptyList = new ArrayList<>();

        for (int i = 0;i<numberOfGoals; i++){
            Goal goal = new Goal();
            goalEmptyList.add(goal);
        }
        return goalEmptyList;
    }

    public List<Goal> goalsFromGame(Long gameID){
        return entityManager.createNativeQuery("SELECT * FROM goal WHERE game="+gameID+" ORDER BY minute",Goal.class).getResultList();
    }

    // pozor na sezonu
    public Goal saveGoal(Goal goal, Long gameID, String season){
        if (goal.getMinute() != null && goal.getAuthor() !=-1 ){
                goal.setGame(gameID);
                goal.setTeam(playerService.playerTeamById(goal.getAuthor(), season));
                goal.setTeam_name(teamRepositoryNew.findById(goal.getTeam()).get().getName());
                goal.setAuthor_name(playerService.playerNameById(goal.getAuthor()));
                if (goal.getAssistance1() > 0) goal.setAssistance1_name(playerService.playerNameById(goal.getAssistance1()));
                if (goal.getAssistance2() > 0) goal.setAssistance2_name(playerService.playerNameById(goal.getAssistance2()));

            goalRepositoryNew.save(goal);
        }
        return goal;
    }

    public List<Goal> goalsForPeriod(Long gameID, Integer period){

        List<Goal> goalList = new ArrayList<>();
        if (period == 1) {
            goalList = entityManager.createNativeQuery("SELECT * FROM goal WHERE game=" + gameID + " AND minute>-1 AND minute<20 ORDER BY minute", Goal.class).getResultList();
        }else if(period == 2){
            goalList = entityManager.createNativeQuery("SELECT * FROM goal WHERE game=" + gameID + " AND minute>19 AND minute<40 ORDER BY minute", Goal.class).getResultList();
        }else if(period == 3) {
            goalList = entityManager.createNativeQuery("SELECT * FROM goal WHERE game=" + gameID + " AND minute>39 AND minute<60 ORDER BY minute", Goal.class).getResultList();
        }else if(period == 4) {
            goalList = entityManager.createNativeQuery("SELECT * FROM goal WHERE game=" + gameID + " AND minute>59 ORDER BY minute", Goal.class).getResultList();
        }
        return goalList;
    }
}
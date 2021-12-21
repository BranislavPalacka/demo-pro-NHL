package com.example.demo.repositories;

import com.example.demo.model.Goal;
import com.example.demo.model.Team;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class GoalRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public GoalRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<Goal> generatedListOfGoals(Integer numberOfGoals) {
        List<Goal> goalEmptyList = new ArrayList<>();

        for (int i = 0;i<numberOfGoals; i++){
            Goal goal = new Goal();
            goalEmptyList.add(goal);
        }
        return goalEmptyList;
    }

    public List<Goal> goalsFromGame(Integer gameID){
        return entityManager.createNativeQuery("SELECT * FROM goal WHERE game="+gameID,Goal.class).getResultList();
    }

}
package com.example.demo.Services;

import com.example.demo.model.Goal;

import java.util.List;

public interface GoalService {
    List<Goal> getAllGoals();
    List<Goal> generatedListOfGoals (Integer numberOfGoals);
    List<Goal> goalsFromGame(Integer gameID);
    Goal goalToSave (Goal goal,Long gameID,String season);

}
package com.example.demo.Services;

import com.example.demo.model.Game;
import com.example.demo.model.Goal;

import java.util.List;

public interface GoalService {
    List<Goal> getAllGoals();
    List<Goal> generatedListOfGoals (Integer numberOfGoals);
    List<Goal> goalsFromGame(Long gameID);
    Goal goalToSave (Goal goal,Long gameID,String season);
    List<Goal> goalsForPeriod(Long gameID, Integer period);
    Goal lastGoalFromGame(Long gameID);
    public Long getTeamIdFromFirstGoalInGame(Game game);
}
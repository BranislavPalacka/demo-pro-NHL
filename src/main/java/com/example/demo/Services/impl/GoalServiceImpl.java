package com.example.demo.Services.impl;

import com.example.demo.Services.GoalService;
import com.example.demo.model.Game;
import com.example.demo.model.Goal;
import com.example.demo.repositories.GoalRepository;
import com.example.demo.repositories.GoalRepositoryNew;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalServiceImpl implements GoalService {

    GoalRepositoryNew goalRepositoryNew;
    GoalRepository goalRepository;

    public GoalServiceImpl(GoalRepositoryNew goalRepositoryNew, GoalRepository goalRepository) {
        this.goalRepositoryNew = goalRepositoryNew;
        this.goalRepository = goalRepository;
    }

    @Override
    public List<Goal> getAllGoals() {
        return (List<Goal>) goalRepositoryNew.findAll();
    }

    @Override
    public List<Goal> generatedListOfGoals(Integer numberOfGoals) {
        return goalRepository.generatedListOfGoals(numberOfGoals);
    }

    @Override
    public List<Goal> goalsFromGame(Long gameID) {
        return goalRepository.goalsFromGame(gameID);
    }

    @Override
    public Goal goalToSave(Goal goal, Long gameID, String season) {
        return goalRepository.saveGoal(goal,gameID,season);
    }

    @Override
    public List<Goal> goalsForPeriod(Long gameID, Integer period) {
        return goalRepository.goalsForPeriod(gameID, period);
    }

    @Override
    public Goal lastGoalFromGame(Long gameID) {
        return goalRepository.lastGoalFromGame(gameID);
    }

    @Override
    public Long getTeamIdFromFirstGoalInGame(Game game) {
        return goalRepository.getTeamIdFromFirstGoalInGame(game);
    }
}
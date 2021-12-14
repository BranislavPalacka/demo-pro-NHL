package com.example.demo.controllers;

import com.example.demo.Services.GoalService;
import com.example.demo.repositories.GoalRepositoryNew;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GoalController {

    private final GoalRepositoryNew goalRepositoryNew;
    private final GoalService goalService;


    public GoalController(GoalRepositoryNew goalRepositoryNew, GoalService goalService) {
        this.goalRepositoryNew = goalRepositoryNew;
        this.goalService = goalService;
    }

    @RequestMapping("/goals")
    public String getAllGoals(Model model){
        model.addAttribute("goals", goalService.getAllGoals());
        return "goals";
    }
}
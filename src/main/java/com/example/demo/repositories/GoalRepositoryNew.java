package com.example.demo.repositories;

import com.example.demo.model.Goal;
import org.springframework.data.repository.CrudRepository;

public interface GoalRepositoryNew extends CrudRepository<Goal,Long> {

}
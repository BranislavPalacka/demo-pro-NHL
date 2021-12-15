package com.example.demo.repositories;

import com.example.demo.model.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepositoryNew extends CrudRepository<Game,Long> {

}
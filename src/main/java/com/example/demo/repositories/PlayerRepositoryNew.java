package com.example.demo.repositories;

import com.example.demo.model.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepositoryNew extends CrudRepository<Player,Long> {

}
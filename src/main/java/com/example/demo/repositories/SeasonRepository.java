package com.example.demo.repositories;

import com.example.demo.model.Season;
import com.example.demo.model.Team;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class SeasonRepository {

    @PersistenceContext
    private final EntityManager entityManager;


    public SeasonRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Season getSeason(Integer season) {
        EntityManager em = entityManager;
        Season returnSeason = (Season) em.createNativeQuery("SELECT * FROM season WHERE year="+season,Season.class).getSingleResult();
        return returnSeason;
    }

}
package com.example.demo.repositories;

import com.example.demo.model.Season;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
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

    public List<Season> getAllSeasons(){
        return entityManager.createNativeQuery("SELECT * FROM season",Season.class).getResultList();
    }

    public Integer getSeasonIntFromDate (String actualDate){
        List<Season> seasonList = getAllSeasons();
        LocalDate dataActual = LocalDate.parse(actualDate);
        LocalDate dateStart;
        LocalDate dateEnd;

        for (Season season: seasonList) {
            dateStart = season.getStart_date().toLocalDate().minusDays(1L);
            dateEnd = season.getLast_date().toLocalDate().plusDays(1L);
            if (dataActual.isAfter(dateStart) && dataActual.isBefore(dateEnd)) {
                return season.getYear();
            }
        }
        return null;
    }

}
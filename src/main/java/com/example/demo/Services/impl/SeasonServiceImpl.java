package com.example.demo.Services.impl;

import com.example.demo.Services.SeasonService;
import com.example.demo.model.Season;
import com.example.demo.repositories.SeasonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeasonServiceImpl implements SeasonService {

    SeasonRepository seasonRepository;

    public SeasonServiceImpl(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    @Override
    public List<Season> getAllSeasons() {
        return seasonRepository.getAllSeasons();
    }

    @Override
    public Season getSeason(Integer season) {
        return seasonRepository.getSeason(season);
    }

    @Override
    public Integer getSeasonIntFromDate(String actualDate) {
        return seasonRepository.getSeasonIntFromDate(actualDate);
    }
}
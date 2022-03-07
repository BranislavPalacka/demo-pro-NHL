package com.example.demo.Services.impl;

import com.example.demo.Services.AllSeriesService;
import com.example.demo.model.AllSeries;
import com.example.demo.model.Game;
import com.example.demo.repositories.AllSeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllSeriesServiceImpl implements AllSeriesService {

    private final AllSeriesRepository allSeriesRepository;

    public AllSeriesServiceImpl(AllSeriesRepository allSeriesRepository) {
        this.allSeriesRepository = allSeriesRepository;
    }

    @Override
    public List<Boolean[]> seriePrvniGolVZapaseJedenTeam(List<AllSeries> allSeriesList, Integer series) {
        return allSeriesRepository.seriePrvniGolVZapaseJedenTeam(allSeriesList,series);
    }

    @Override
    public List<AllSeries> AllSeriesList(List<Game> gameList, int seriesLength, int seriesPause, String side) {
        return allSeriesRepository.AllSeriesList(gameList, seriesLength, seriesPause, side);
    }
}
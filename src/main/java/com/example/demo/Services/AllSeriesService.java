package com.example.demo.Services;

import com.example.demo.model.AllSeries;
import com.example.demo.model.Game;

import java.util.List;

public interface AllSeriesService {
    List<Boolean[]> seriePrvniGolVZapaseJedenTeam(List <AllSeries> allSeriesList, Integer series);
    List<AllSeries> AllSeriesList(List<Game> gameList, int seriesLength, int seriesPause, String side);
}
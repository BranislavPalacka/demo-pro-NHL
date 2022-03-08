package com.example.demo.Services;

import com.example.demo.model.Season;

import java.util.List;

public interface SeasonService {
    List<Season> getAllSeasons();
    Season getSeason(Integer season);
}
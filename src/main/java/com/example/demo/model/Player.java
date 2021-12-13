package com.example.demo.model;

import javax.persistence.*;

@Entity
public class Player {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Long team_id_2019;

    private Long team_id_2020;

    private Long team_id_2021;

    public Player() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTeam_id_2019() {
        return team_id_2019;
    }

    public void setTeam_id_2019(Long team_id_2019) {
        this.team_id_2019 = team_id_2019;
    }

    public Long getTeam_id_2020() {
        return team_id_2020;
    }

    public void setTeam_id_2020(Long team_id_2020) {
        this.team_id_2020 = team_id_2020;
    }

    public Long getTeam_id_2021() {
        return team_id_2021;
    }

    public void setTeam_id_2021(Long team_id_2021) {
        this.team_id_2021 = team_id_2021;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", team_id_2019=" + team_id_2019 +
                ", team_id_2020=" + team_id_2020 +
                ", team_id_2021=" + team_id_2021 +
                '}';
    }
}
package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Team {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String conference;

    private String division_2018;

    private String division_2019;

    private String division_2021;

    private String logo;

    public Team() {
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

    public String getConference() {
        return conference;
    }

    public void setConference(String conference) {
        this.conference = conference;
    }

    public String getDivision_2019() {
        return division_2019;
    }

    public void setDivision_2019(String division_2019) {
        this.division_2019 = division_2019;
    }

    public String getDivision_2018() {
        return division_2018;
    }

    public void setDivision_2018(String division_2018) {
        this.division_2018 = division_2018;
    }

    public String getDivision_2021() {
        return division_2021;
    }

    public void setDivision_2021(String division_2021) {
        this.division_2021 = division_2021;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", conference='" + conference + '\'' +
                ", division_2018='" + division_2018 + '\'' +
                ", division_2019='" + division_2019 + '\'' +
                ", division_2021='" + division_2021 + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }
}
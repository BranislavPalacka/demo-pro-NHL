package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Goal {
    @Id
    @GeneratedValue
    private Long id;

    private Long game;

    private Integer minute;

    private Integer author;

    private Integer assistance1;

    private Integer assistance2;

    private Long team;

    private String author_name;

    private String assistance1_name;

    private String assistance2_name;

    private String team_name;

    public Goal() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGame() {
        return game;
    }

    public void setGame(Long game) {
        this.game = game;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public Integer getAssistance1() {
        return assistance1;
    }

    public void setAssistance1(Integer assistance1) {
        this.assistance1 = assistance1;
    }

    public Integer getAssistance2() {
        return assistance2;
    }

    public void setAssistance2(Integer assistance2) {
        this.assistance2 = assistance2;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAssistance1_name() {
        return assistance1_name;
    }

    public void setAssistance1_name(String assistance1_name) {
        this.assistance1_name = assistance1_name;
    }

    public String getAssistance2_name() {
        return assistance2_name;
    }

    public void setAssistance2_name(String assistance2_name) {
        this.assistance2_name = assistance2_name;
    }

    public Long getTeam() {
        return team;
    }

    public void setTeam(Long team) {
        this.team = team;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    @Override
    public String toString() {
        return "Goal{" +
                "id=" + id +
                ", game=" + game +
                ", minute=" + minute +
                ", author=" + author +
                ", assistance1=" + assistance1 +
                ", assistance2=" + assistance2 +
                ", team=" + team +
                ", author_name='" + author_name + '\'' +
                ", assistance1_name='" + assistance1_name + '\'' +
                ", assistance2_name='" + assistance2_name + '\'' +
                ", team_name='" + team_name + '\'' +
                '}';
    }
}
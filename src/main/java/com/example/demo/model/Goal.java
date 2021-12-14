package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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

    @Override
    public String toString() {
        return "Goal{" +
                "id=" + id +
                ", game=" + game +
                ", minute=" + minute +
                ", author=" + author +
                ", assistance1=" + assistance1 +
                ", assistance2=" + assistance2 +
                '}';
    }
}
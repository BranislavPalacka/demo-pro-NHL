package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;


@Entity
public class Game {

    @Id
    @GeneratedValue
    public Long id;

    private Integer round_home;

    private Integer round_guest;

    private Date date;

    private Integer home;

    private Integer guest;

    private Integer vysledek_sazka;

    private String vysledek;

    private Integer tretina1sazka;

    private String tretina1vysledek;

    private Integer tretina2sazka;

    private String tretina2vysledek;

    private Integer tretina3sazka;

    private String tretina3vysledek;

    private Integer prodlouzeni4sazka;

    private String prodlouzeni4vysledek;

    private String home_team;

    private String guest_team;

    private Integer season;

    public Game() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRound_home() {
        return round_home;
    }

    public void setRound_home(Integer round_home) {
        this.round_home = round_home;
    }

    public Integer getRound_guest() {
        return round_guest;
    }

    public void setRound_guest(Integer round_guest) {
        this.round_guest = round_guest;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getHome() {
        return home;
    }

    public void setHome(Integer home) {
        this.home = home;
    }

    public Integer getGuest() {
        return guest;
    }

    public void setGuest(Integer guest) {
        this.guest = guest;
    }

    public Integer getVysledek_sazka() {
        return vysledek_sazka;
    }

    public void setVysledek_sazka(Integer vysledek_sazka) {
        this.vysledek_sazka = vysledek_sazka;
    }

    public String getVysledek() {
        return vysledek;
    }

    public void setVysledek(String vysledek) {
        this.vysledek = vysledek;
    }

    public Integer getTretina1sazka() {
        return tretina1sazka;
    }

    public void setTretina1sazka(Integer tretina1sazka) {
        this.tretina1sazka = tretina1sazka;
    }

    public String getTretina1vysledek() {
        return tretina1vysledek;
    }

    public void setTretina1vysledek(String tretina1vysledek) {
        this.tretina1vysledek = tretina1vysledek;
    }

    public Integer getTretina2sazka() {
        return tretina2sazka;
    }

    public void setTretina2sazka(Integer tretina2sazka) {
        this.tretina2sazka = tretina2sazka;
    }

    public String getTretina2vysledek() {
        return tretina2vysledek;
    }

    public void setTretina2vysledek(String tretina2vysledek) {
        this.tretina2vysledek = tretina2vysledek;
    }

    public Integer getTretina3sazka() {
        return tretina3sazka;
    }

    public void setTretina3sazka(Integer tretina3sazka) {
        this.tretina3sazka = tretina3sazka;
    }

    public String getTretina3vysledek() {
        return tretina3vysledek;
    }

    public void setTretina3vysledek(String tretina3vysledek) {
        this.tretina3vysledek = tretina3vysledek;
    }

    public Integer getProdlouzeni4sazka() {
        return prodlouzeni4sazka;
    }

    public void setProdlouzeni4sazka(Integer prodlouzeni4sazka) {
        this.prodlouzeni4sazka = prodlouzeni4sazka;
    }

    public String getProdlouzeni4vysledek() {
        return prodlouzeni4vysledek;
    }

    public void setProdlouzeni4vysledek(String prodlouzeni4vysledek) {
        this.prodlouzeni4vysledek = prodlouzeni4vysledek;
    }

    public String getHome_team() {
        return home_team;
    }

    public void setHome_team(String home_team) {
        this.home_team = home_team;
    }

    public String getGuest_team() {
        return guest_team;
    }

    public void setGuest_team(String guest_team) {
        this.guest_team = guest_team;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", round_home=" + round_home +
                ", round_guest=" + round_guest +
                ", date=" + date +
                ", home=" + home +
                ", guest=" + guest +
                ", vysledek_sazka=" + vysledek_sazka +
                ", vysledek='" + vysledek + '\'' +
                ", tretina1sazka=" + tretina1sazka +
                ", tretina1vysledek='" + tretina1vysledek + '\'' +
                ", tretina2sazka=" + tretina2sazka +
                ", tretina2vysledek='" + tretina2vysledek + '\'' +
                ", tretina3sazka=" + tretina3sazka +
                ", tretina3vysledek='" + tretina3vysledek + '\'' +
                ", prodlouzeni4sazka=" + prodlouzeni4sazka +
                ", prodlouzeni4vysledek='" + prodlouzeni4vysledek + '\'' +
                ", homeTeam='" + home_team + '\'' +
                ", guestTeam='" + guest_team + '\'' +
                ", season=" + season +
                '}';
    }
}
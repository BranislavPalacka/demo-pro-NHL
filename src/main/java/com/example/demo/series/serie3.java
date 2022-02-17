package com.example.demo.series;

import com.example.demo.model.Game;

public class serie3 {
    private Game game1;
    private Game game2;
    private Game game3;

    public serie3() {
    }

    public serie3(Game game1, Game game2, Game game3) {
        this.game1 = game1;
        this.game2 = game2;
        this.game3 = game3;
    }

    public Game getGame1() {
        return game1;
    }

    public void setGame1(Game game1) {
        this.game1 = game1;
    }

    public Game getGame2() {
        return game2;
    }

    public void setGame2(Game game2) {
        this.game2 = game2;
    }

    public Game getGame3() {
        return game3;
    }

    public void setGame3(Game game3) {
        this.game3 = game3;
    }

    @Override
    public String toString() {
        return "serie3{" +
                "game1=" + game1 +
                ", game2=" + game2 +
                ", game3=" + game3 +
                '}';
    }
}
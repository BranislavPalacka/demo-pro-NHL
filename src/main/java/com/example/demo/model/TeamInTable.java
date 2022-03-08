package com.example.demo.model;

public class TeamInTable {
    private String name;
    private Long teamId;
    private Integer rounds =0;
    private Integer roundsHome =0;
    private Integer roundsGuest =0;
    private Integer Points = 0;
    private Integer PointsHome = 0;
    private Integer PointsGuest = 0;
    private Integer goalsPlus = 0;
    private Integer goalsMinus = 0;
    private Integer goalsPlusHome = 0;
    private Integer goalsMinusHome = 0;
    private Integer goalsPlusGuest = 0;
    private Integer goalsMinusGuest = 0;
    private Integer winsOrder = 0;  // započitatelné výhry pro určení pořadí v tabulce
    private Integer winsOrderHome = 0;
    private Integer winsOrderGuest = 0;
    private Integer wins = 0;
    private Integer winsHome = 0;
    private Integer winsGuest = 0;
    private Integer losses = 0;
    private Integer lossesHome = 0;
    private Integer lossesGuest = 0;
    private Integer draws = 0;
    private Integer drawsHome = 0;
    private Integer drawsGuest = 0;

    public TeamInTable() {
    }

    public Integer getRounds() {
        return rounds;
    }

    public void setRounds(Integer rounds) {
        this.rounds = rounds;
    }

    public Integer getRoundsHome() {
        return roundsHome;
    }

    public void setRoundsHome(Integer roundsHome) {
        this.roundsHome = roundsHome;
    }

    public Integer getRoundsGuest() {
        return roundsGuest;
    }

    public void setRoundsGuest(Integer roundsGuest) {
        this.roundsGuest = roundsGuest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Integer getPoints() {
        return Points;
    }

    public void setPoints(Integer points) {
        Points = points;
    }

    public Integer getPointsHome() {
        return PointsHome;
    }

    public void setPointsHome(Integer pointsHome) {
        PointsHome = pointsHome;
    }

    public Integer getPointsGuest() {
        return PointsGuest;
    }

    public void setPointsGuest(Integer pointsGuest) {
        PointsGuest = pointsGuest;
    }

    public Integer getGoalsPlus() {
        return goalsPlus;
    }

    public void setGoalsPlus(Integer goalsPlus) {
        this.goalsPlus = goalsPlus;
    }

    public Integer getGoalsMinus() {
        return goalsMinus;
    }

    public void setGoalsMinus(Integer goalsMinus) {
        this.goalsMinus = goalsMinus;
    }

    public Integer getGoalsPlusHome() {
        return goalsPlusHome;
    }

    public void setGoalsPlusHome(Integer goalsPlusHome) {
        this.goalsPlusHome = goalsPlusHome;
    }

    public Integer getGoalsMinusHome() {
        return goalsMinusHome;
    }

    public void setGoalsMinusHome(Integer goalsMinusHome) {
        this.goalsMinusHome = goalsMinusHome;
    }

    public Integer getGoalsPlusGuest() {
        return goalsPlusGuest;
    }

    public void setGoalsPlusGuest(Integer goalsPlusGuest) {
        this.goalsPlusGuest = goalsPlusGuest;
    }

    public Integer getGoalsMinusGuest() {
        return goalsMinusGuest;
    }

    public void setGoalsMinusGuest(Integer goalsMinusGuest) {
        this.goalsMinusGuest = goalsMinusGuest;
    }

    public Integer getWinsOrder() {
        return winsOrder;
    }

    public void setWinsOrder(Integer winsOrder) {
        this.winsOrder = winsOrder;
    }

    public Integer getWinsOrderHome() {
        return winsOrderHome;
    }

    public void setWinsOrderHome(Integer winsOrderHome) {
        this.winsOrderHome = winsOrderHome;
    }

    public Integer getWinsOrderGuest() {
        return winsOrderGuest;
    }

    public void setWinsOrderGuest(Integer winsOrderGuest) {
        this.winsOrderGuest = winsOrderGuest;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getWinsHome() {
        return winsHome;
    }

    public void setWinsHome(Integer winsHome) {
        this.winsHome = winsHome;
    }

    public Integer getWinsGuest() {
        return winsGuest;
    }

    public void setWinsGuest(Integer winsGuest) {
        this.winsGuest = winsGuest;
    }

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public Integer getLossesHome() {
        return lossesHome;
    }

    public void setLossesHome(Integer lossesHome) {
        this.lossesHome = lossesHome;
    }

    public Integer getLossesGuest() {
        return lossesGuest;
    }

    public void setLossesGuest(Integer lossesGuest) {
        this.lossesGuest = lossesGuest;
    }

    public Integer getDraws() {
        return draws;
    }

    public void setDraws(Integer draws) {
        this.draws = draws;
    }

    public Integer getDrawsHome() {
        return drawsHome;
    }

    public void setDrawsHome(Integer drawsHome) {
        this.drawsHome = drawsHome;
    }

    public Integer getDrawsGuest() {
        return drawsGuest;
    }

    public void setDrawsGuest(Integer drawsGuest) {
        this.drawsGuest = drawsGuest;
    }

    @Override
    public String toString() {
        return "TeamInTable{" +
                "name='" + name + '\'' +
                ", teamId=" + teamId +
                ", rounds=" + rounds +
                ", Points=" + Points +
                ", PointsHome=" + PointsHome +
                ", PointsGuest=" + PointsGuest +
                ", goalsPlus=" + goalsPlus +
                ", goalsMinus=" + goalsMinus +
                ", goalsPlusHome=" + goalsPlusHome +
                ", goalsMinusHome=" + goalsMinusHome +
                ", goalsPlusGuest=" + goalsPlusGuest +
                ", goalsMinusGuest=" + goalsMinusGuest +
                ", winsOrder=" + winsOrder +
                ", winsOrderHome=" + winsOrderHome +
                ", winsOrderGuest=" + winsOrderGuest +
                ", wins=" + wins +
                ", winsHome=" + winsHome +
                ", winsGuest=" + winsGuest +
                ", losses=" + losses +
                ", lossesHome=" + lossesHome +
                ", lossesGuest=" + lossesGuest +
                ", draws=" + draws +
                ", drawsHome=" + drawsHome +
                ", drawsGuest=" + drawsGuest +
                '}';
    }
}
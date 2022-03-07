package com.example.demo.model;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;


@Entity
public class Season {

    @Id
    public Integer year;

    private Date start_date;

    private Date last_date;

    public Season() {
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getLast_date() {
        return last_date;
    }

    public void setLast_date(Date last_date) {
        this.last_date = last_date;
    }

    @Override
    public String toString() {
        return "Season{" +
                "year=" + year +
                ", start_date=" + start_date +
                ", last_date=" + last_date +
                '}';
    }
}
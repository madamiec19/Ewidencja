package com.example.ewidencja;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.ewidencja.Database.Move;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "workday_table")
public class WorkDay {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String date;

    private double movesValue;

    private int movesNumber;

    private double hours;

    private double dayValue;


    public WorkDay(String date, double movesValue, int movesNumber, double hours){
        this.date = date;
        this.movesValue = movesValue;
        this.movesNumber = movesNumber;
        this.hours = hours;
        setDayValue();
    }

    private void setDayValue() {
        dayValue = 11 * hours + movesValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getMovesValue() {
        return movesValue;
    }

    public void setMovesValue(double movesValue) {
        this.movesValue = movesValue;
    }

    public int getMovesNumber() {
        return movesNumber;
    }

    public void setMovesNumber(int movesNumber) {
        this.movesNumber = movesNumber;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public double getDayValue() {
        return dayValue;
    }

    public void setDayValue(double dayValue) {
        this.dayValue = dayValue;
    }
}

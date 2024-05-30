package com.example.chessproject;

import java.util.LinkedList;
import java.util.List;

public class ChessPlayer {
    private long id;
    private String name;
    private String surname;
    private double rating;
    private long tournament_id;
    private double points = 0;
    private String PastOpponents = "";

    public ChessPlayer(long id, String name, String surname, double rating, long tournament_id, double points, String pastOpponents) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.rating = rating;
        this.tournament_id = tournament_id;
        this.points = points;
        PastOpponents = pastOpponents;
    }

    public ChessPlayer(long id, String name, String surname, double rating, long tournamentId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.rating = rating;
        this.tournament_id = tournamentId;
    }

    public ChessPlayer(long id, String name, String surname, double rating, long tournamentId, double points) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.rating = rating;
        this.tournament_id = tournamentId;
        this.points = points;
    }

    public ChessPlayer(String name, String surname, double rating, long tournament_id) {
        this.name = name;
        this.surname = surname;
        this.rating = rating;
        this.tournament_id = tournament_id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public double getRating() {
        return rating;
    }

    public long getTournament_id() {
        return tournament_id;
    }

    public double getPoints() {return points; }

    public String getPastOpponents() {return PastOpponents; }
}

package com.example.chessproject;

public class Tournament {
    private long id;
    private String name;
    private long date;


    public Tournament(String name, long date) {
        this.name = name;
        this.date = date;
    }

    public Tournament(long id, String name, long date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }


    public String getName() {
        return name;
    }

    public long getDate() {
        return date;
    }

    public long getId() {
        return id;
    }



}

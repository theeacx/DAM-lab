package com.example.lab6;

public class Movie {
    private final String title;
    private final String director;
    private final boolean boxOfficeSuccess;

    public Movie(String title, String director, boolean boxOfficeSuccess) {
        this.title = title;
        this.director = director;
        this.boxOfficeSuccess = boxOfficeSuccess;
    }

    public String getTitle() { return title; }
    public String getDirector() { return director; }
    public boolean isBoxOfficeSuccess() { return boxOfficeSuccess; }
}
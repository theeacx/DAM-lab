package com.example.lab4;

public class Book {
    private final String title;
    private final String author;
    private final String genre;
    private final String format;
    private final int pages;
    private final boolean read;
    private final int rating;
    private final int color;

    public Book(String title, String author, String genre, String format, int pages, boolean read, int rating, int color) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.format = format;
        this.pages = pages;
        this.read = read;
        this.rating = rating;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getFormat() {
        return format;
    }

    public int getPages() {
        return pages;
    }

    public boolean isRead() {
        return read;
    }

    public int getRating() {
        return rating;
    }

    public int getColor() {
        return color;
    }
}

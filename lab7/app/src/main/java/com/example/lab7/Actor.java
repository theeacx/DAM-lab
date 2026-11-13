package com.example.lab7;

import java.io.Serializable;

import java.time.LocalDate;



public class Actor implements Serializable {

    private final String name;

    private final String citizenship;

    private final LocalDate dateOfBirth;

    private final String imageUrl;

    private boolean hollywoodFamous;



    public Actor(String name, String citizenship, LocalDate dateOfBirth, String imageUrl,

                 boolean hollywoodFamous) {

        this.name = name;

        this.citizenship = citizenship;

        this.dateOfBirth = dateOfBirth;

        this.imageUrl = imageUrl;

        this.hollywoodFamous = hollywoodFamous;

    }



    public String getName() { return name; }

    public String getCitizenship() { return citizenship; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }

    public String getImageUrl() { return imageUrl; }

    public boolean isHollywoodFamous() { return hollywoodFamous; }

    public void setHollywoodFamous(boolean hollywoodFamous) { this.hollywoodFamous = hollywoodFamous; }

}
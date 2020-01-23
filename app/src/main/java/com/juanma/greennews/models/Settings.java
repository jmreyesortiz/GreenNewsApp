package com.juanma.greennews.models;

//Model class for the Settings

public class Settings {

    private String language;
    private String sortBy;
    private int date;

    private Settings() {
        language = "en";
        sortBy = "relevancy";
        date = 1;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }


    private static final Settings Instance = new Settings();

    public static Settings getInstance() {
        return Instance;
    }




}

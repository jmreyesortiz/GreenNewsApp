package com.juanma.greennews.models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Settings {


    public String language;
    public String sortBy;
    public int date;

    private static final Settings Instance = new Settings();

    public static Settings getInstance() {
        return Instance;
    }

    private Settings() {
        language = "en";
        sortBy = "publishedAt";
        date = 7;
    }


}

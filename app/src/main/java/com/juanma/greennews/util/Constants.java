package com.juanma.greennews.util;

public class Constants {


    public static final String BASE_URL = "https://newsapi.org";
    //first api key
    //public static final String API_KEY = "36ff0e226c7048fdab6d987813d163fb";
    //second api key
    public static final String API_KEY = "318797f5265a4ab69552508790391f6c";

    public static final int NETWORK_TIMEOUT = 3000;

    // Queries

    public static final String PAGE_SIZE = "20";




    //Sort by
    public static final String PUBLISHED_AT = "publishedAt";
    public static final String RELEVANCY = "relevancy";
    public static final String POPULARITY = "popularity";
    public static final String LANGUAGE = "en";

    //Date
    public static final String[] DEFAULT_SEARCH_CATEGORIES =
            {"Entertainment", "Finance", "Health", "Nutrition", "Enviroment", "Science", "Technology", "World"};

    public static final String[] DEFAULT_SEARCH_CATEGORY_IMAGES =
            {
                    "entertainment",
                    "finance",
                    "health",
                    "nutrition",
                    "enviroment",
                    "science",
                    "technology",
                    "world"
            };



}

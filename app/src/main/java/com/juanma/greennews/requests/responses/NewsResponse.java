package com.juanma.greennews.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.juanma.greennews.models.News;
import com.juanma.greennews.models.Sources;


import java.util.List;

public class NewsResponse {

    @SerializedName("status")
    @Expose()
    private String status;

    @SerializedName("totalResults")
    @Expose()
    private int totalResults;

    @SerializedName("articles")
    @Expose()
    private List <News> articles;


    public NewsResponse() {
    }

    public List<News> getNews() {
        return articles;
    }

    @Override
    public String toString() {
        return "NewsResponse{" +
                "status='" + status + '\'' +
                ", totalResults=" + totalResults +
                ", articles=" + articles +
                '}';
    }
}

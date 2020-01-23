package com.juanma.greennews.requests;

import com.juanma.greennews.requests.responses.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {


    @GET("v2/everything")
    Call<NewsResponse> searchArticle3(
            @Query("q") String query,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String key,
            @Query("page") String page
            );


    @GET("v2/everything")
    Call<NewsResponse> searchArticle(
            @Query("q") String query,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String key,
            @Query("page") String page,
            @Query("pageSize") String pageSize,
            @Query("language") String language,
            @Query("from") String from,
            @Query("to") String to
    );


}

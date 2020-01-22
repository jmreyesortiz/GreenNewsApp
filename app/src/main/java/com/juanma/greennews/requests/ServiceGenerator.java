package com.juanma.greennews.requests;

import com.juanma.greennews.util.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder().
            baseUrl(Constants.BASE_URL).
            addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();
    private static NewsApi newsApi = retrofit.create(NewsApi.class);    //Retrofit Instance instantiates the Articles API instance

    // Returns the instantiation of the Articles API
    public static NewsApi getNewsApi(){
        return newsApi;
    }
}

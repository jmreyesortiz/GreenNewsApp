package com.juanma.greennews.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.juanma.greennews.AppExecutors;
import com.juanma.greennews.models.News;
import com.juanma.greennews.requests.responses.NewsResponse;
import com.juanma.greennews.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class NewsApiClient {

    private static NewsApiClient instance;
    private MutableLiveData<List<News>> mNews;  // Main Live Data
    private static final String TAG = "NewsApiClient";
    private RetrieveNewsRunnable mRetrieveNewsRunnable;

    public static NewsApiClient getInstance(){
        if (instance == null){
            instance = new NewsApiClient();
        }
        return instance;
    }

    // Live-Data instantiation
    private NewsApiClient(){
        mNews = new MutableLiveData<>();

    }

    public LiveData<List<News>> getNews(){
        return mNews;
    }


    //Using the App Executors here
    public void searchRecipesApi(String query, String sorted, int pageNumber, int pageSize, String language, String from, String to){

        if(mRetrieveNewsRunnable != null){
            mRetrieveNewsRunnable = null;
        }
        mRetrieveNewsRunnable = new RetrieveNewsRunnable(query,sorted,pageNumber, pageSize, language, from, to);
        // handler gets the Runnable
        final Future handler = AppExecutors.getInstance().getNetworkIO().submit(mRetrieveNewsRunnable);
        //Setting a network-time-out
        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {

                // Let the user know its timed out.
                handler.cancel(true);
            }
        }
        ,Constants.NETWORK_TIMEOUT
                ,TimeUnit.MILLISECONDS);

    }

    private class RetrieveNewsRunnable implements Runnable{

       String query;
       String sorted;
       int pageNumber;
       int pageSize;
       String language;
       String from;
       String to;
       boolean cancelRequest;

        public RetrieveNewsRunnable(String query, String sorted, int pageNumber, int pageSize, String language, String from, String to) {
            this.query = query;
            this.sorted = sorted;
            this.pageNumber= pageNumber;
            this.pageSize = pageSize;
            this.language = language;
            this.from = from;
            this.to = to;
            cancelRequest = false;
        }

        @Override
        public void run() {

            //Executed on the background thread, asking information from the REST API
            try {
                Response response = getNews(query,sorted,pageNumber,language,from,to).execute();  //Gets the LiveData into the Response RetroFit Object
                if(cancelRequest){
                    return;
                }
                if(response.code() == 200){
                    assert response.body() != null;
                    List<News> list = new ArrayList<>(((NewsResponse)response.body()).getNews()); // ???

                    if (pageNumber == 1){
                        mNews.postValue(list);  //Post the value to the live data.
                    }
                    else{
                        List<News> currentNews = mNews.getValue();          //Gets the value from the Live Data
                        currentNews.addAll(list);
                        mNews.postValue(currentNews);                      //Adds everything from live Data
                    }


                }
                else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run " + error);
                    mNews.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }



        }


        private Call <NewsResponse> getNews(String query, String sorted, int pageNumber, String language,
        String from, String to){


            return ServiceGenerator.getNewsApi().searchArticle(
                    query,
                    sorted,
                    Constants.API_KEY,
                    String.valueOf(pageNumber),
                    Constants.PAGE_SIZE,
                    language,
                    from,
                    to
            );

        }


        private void cancelRequest(){
            Log.d(TAG, "cancelRequest: this is cancelling the request");
            cancelRequest = true;
        }
    }

    public void cancelRequest(){
        if(mRetrieveNewsRunnable != null){
            mRetrieveNewsRunnable.cancelRequest();
        }

    }

}

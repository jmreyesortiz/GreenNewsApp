package com.juanma.greennews.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.juanma.greennews.models.News;
import com.juanma.greennews.requests.NewsApiClient;

import java.util.List;

public class NewsRepository {

    private static NewsRepository instance;
    private NewsApiClient mNewsApiClient;
    private String mQuery;
    private String mSorted;
    private int mPageNumber;
    private int mPageSize;
    private String mLanguage;
    private String mFrom;
    private String mTo;
    private MediatorLiveData<List<News>> mNews = new MediatorLiveData<>();
    private MutableLiveData<Boolean> mIsTheQueryExhausted = new MutableLiveData<>();


    public static NewsRepository getInstance(){
        if (instance == null){
            instance = new NewsRepository();
        }
        return instance;
    }

    private NewsRepository(){
        mNewsApiClient = NewsApiClient.getInstance();
        initMed();

    }

    public LiveData<List<News>> getNews(){
        return mNews;
    }

    private void initMed(){
        LiveData<List<News>> newsListApiSource = mNewsApiClient.getNews();
        mNews.addSource(newsListApiSource, new Observer<List<News>>() {
            @Override
            public void onChanged(List<News> news) {
                if(news != null){
                    mNews.setValue(news);
                    doneQuery(news);
                }
                else{
                    //SEARCH CACHE
                    doneQuery(null);
                }
            }
        });
    }

    private void doneQuery(List<News> list){
        if(list!= null){
            if(list.size() % 20 != 0){
                mIsTheQueryExhausted.setValue(true);
            }
        }
        else
        {
            mIsTheQueryExhausted.setValue(true);
        }

    }

    public LiveData<Boolean> isQueryExhausted(){
        return mIsTheQueryExhausted;
    }

    public void searchNewsApi(String query, String sorted, int pageNumber, int pageSize, String language, String from, String to){
        if (pageNumber == 0){
            pageNumber = 1;
        }
        mQuery = query;
        mSorted = sorted;
        mPageNumber = pageNumber;
        mPageSize = pageSize;
        mLanguage = language;
        mFrom = from;
        mTo = to;
        mIsTheQueryExhausted.setValue(false);
        mNewsApiClient.searchRecipesApi(query, sorted, pageNumber, pageSize, language, from, to);
    }


    public void searchNextPage(){
        searchNewsApi(mQuery, mSorted,mPageNumber + 1,mPageSize,mLanguage,mFrom,mTo);
    }

    public void cancelRequest(){
        mNewsApiClient.cancelRequest();
    }


}

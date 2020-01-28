package com.juanma.greennews.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.juanma.greennews.models.News;
import com.juanma.greennews.repository.NewsRepository;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class NewsListViewModel extends ViewModel
{

    private NewsRepository mNewsRepository;
    private boolean mIsViewingNews;
    private boolean mIsPerformingQuery;

    public NewsListViewModel() {

        mNewsRepository = NewsRepository.getInstance();
        mIsPerformingQuery = false;

    }

    public LiveData<List<News>> getNews(){
        return mNewsRepository.getNews();
    }
    //ToDO: change this function too
    public void searchNewsApi(String query, String sorted, int pageNumber, int pageSize, String language, String from, String to){
        mIsViewingNews = true;
        mIsPerformingQuery = true;
        mNewsRepository.searchNewsApi(query, sorted, pageNumber,pageSize,language,from,to);
    }



    public LiveData<Boolean> isQueryExhausted(){
        return mNewsRepository.isQueryExhausted();
    }


    public boolean isViewingNews() {
        return mIsViewingNews;
    }

    public void setIsViewingNews(boolean isViewingRecipes){

        mIsViewingNews = isViewingRecipes;
    }

    public void setmIsPerformingQuery(boolean isPerformingQuery) {
        mIsPerformingQuery = isPerformingQuery;
    }

    public boolean ismIsPerformingQuery() {
        return mIsPerformingQuery;
    }


    public boolean onBackPressed(){
        if(mIsPerformingQuery){
            //cancel the  query
            Log.d(TAG, "onBackPressed: canceling the request");
            mNewsRepository.cancelRequest();
        }
        if (mIsViewingNews){
            mIsViewingNews = false;
            return false;
        }
        return true;
    }

    public void searchNextPage(){
        Log.d(TAG, "searchNextPage: called.");
        if(!mIsPerformingQuery
                && mIsViewingNews
                && !isQueryExhausted().getValue()) {
            mNewsRepository.searchNextPage();
        }
    }












}

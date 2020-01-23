package com.juanma.greennews;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juanma.greennews.adapters.NewsRecyclerAdapter;
import com.juanma.greennews.adapters.OnNewsListener;
import com.juanma.greennews.models.News;
import com.juanma.greennews.models.Settings;
import com.juanma.greennews.util.VerticalSpacingItemDecorator;
import com.juanma.greennews.viewmodels.NewsListViewModel;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewsListActivity extends BaseActivity implements OnNewsListener {

    private static final String TAG = "NewsListActivity";

    private NewsListViewModel mNewsListViewModel;
    private RecyclerView mRecyclerView;
    private NewsRecyclerAdapter mAdapter;
    private String language = "en";
    private String sortBy = "relevancy";
    private String currentDate;
    private String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        mRecyclerView = findViewById(R.id.news_list);


        // Instantiating View Model
        mNewsListViewModel = ViewModelProviders.of(this).get(NewsListViewModel.class);

        initRecyclerView();
        subscribeObservers();
        initSearchView();
        if(!mNewsListViewModel.isViewingNews()){
            displaySearchCategories();
        }

        // Toolbar
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));

    }

    private void initRecyclerView(){

        //Adapter manager
        mAdapter = new NewsRecyclerAdapter(this);

        //Vertical Decorator
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(30);
        mRecyclerView.addItemDecoration(itemDecorator);
        mRecyclerView.setAdapter(mAdapter);

        //Layout Manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Loading more pages and determination of what is on the bottom or not.
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(!mRecyclerView.canScrollVertically(1)){
                    // search for the next page
                    mNewsListViewModel.searchNextPage();
                }
            }
        });



    }
//Initialize the search
    private void initSearchView(){
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onQueryTextSubmit(String query) {
                SetUpSettings();
                Log.d(TAG, " dateClick: " + date + " sortBy " + sortBy + " language " + language);

                mNewsListViewModel.searchNewsApi(query, sortBy,1,20, language,
                        date,currentDate);
                searchView.clearFocus();
                return false;

            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }

    //Display Search Categories
    private void displaySearchCategories(){
        Log.d(TAG, "displaySearchCategories: called.");
        mNewsListViewModel.setIsViewingNews(false);
        mAdapter.displaySearchCategories();
    }

    //Observer Method
    private void subscribeObservers(){
        mNewsListViewModel.getNews().observe(this, new Observer<List<News>>() {
            @Override
            public void onChanged(List<News> news) {
             if (news!= null){
                 mNewsListViewModel.setmIsPerformingQuery(false);
             }
                mAdapter.setNews(news);

            }
        });

        mNewsListViewModel.isQueryExhausted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    mAdapter.setQueryExhausted();;
                }
            }
        });

    }


    //On click method with an intent to pass the url for the web view.
    @Override
    public void onNewsClick(int position) {
        Log.d(TAG, "onNewsClick: clicked" + position);
        Intent i = new Intent(NewsListActivity.this, NewsActivity.class);
        i.putExtra(NewsActivity.mLoadUrl,mAdapter.getSelectedNews(position).getUrl());
        startActivity(i);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCategoryClick(String category) {
        SetUpSettings();
        Log.d(TAG, " dateClick: " + date + " sortBy " + sortBy + " language " + language);
        mNewsListViewModel.searchNewsApi(category, sortBy, 1,20,language,date,currentDate);
    }

    @Override
    public void onBackPressed() {
      if(mNewsListViewModel.onBackPressed()){
          super.onBackPressed();
      }
      else
      {
          displaySearchCategories();
      }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.home_option1){
            displaySearchCategories();
        }
        else if(item.getItemId() == R.id.settings_option2){
            Log.d(TAG, "onOptionsItemSelected: " + language);
            displaySettings();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void displaySettings(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }


    //getting language

    public String getLanguage(){
        String language = Settings.getInstance().getLanguage();
        return language;
    }

    //getting Sort By
    public String getSortBy(){
        String sortBy = Settings.getInstance().getSortBy();
        return sortBy;
    }


    //Date Methods
    public static String CurrentTime(){
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        String formattedDate;
        formattedDate = simpleDateFormat.format(today);
        System.out.println("Today " + formattedDate);
        return formattedDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String  AnotherTime(int days){
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Instant today = Instant.now();
        Instant yesterday = today.minus(days, ChronoUnit.DAYS);
        Date myDate = Date.from(yesterday);
        String formatted = simpleDateFormat.format(myDate);
        System.out.println("Tomorrow " + formatted);
        return formatted;
    }

    // Method to Check the Date
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String checkDate(int days){
        String result;
        if(days == 7){
            result = AnotherTime(7);
            return result;
        }
        else if (days ==14){
            result = AnotherTime(14);
            return result;
        }
        else if (days ==20){
            result = AnotherTime(20);
            return result;
        }
        result = CurrentTime();
        return result;
    }

    // Method to Set Up the Settings
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void SetUpSettings(){
        mAdapter.displayLoading();
        language = getLanguage();
        sortBy = getSortBy();
        currentDate = checkDate(0);
        date = checkDate(Settings.getInstance().getDate());
        Log.d(TAG, " dateClick: " + date + " sortBy " + sortBy + " language " + language);
    }



}
package com.juanma.greennews.adapters;

import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.juanma.greennews.R;
import com.juanma.greennews.models.News;
import com.juanma.greennews.util.Constants;

import java.text.DateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{



    private static final int NEWS_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    private static final int CATEGORY_TYPE = 3;
    private static final int EXHAUSTED_TYPE= 4;

    private List<News> mNews;
    private OnNewsListener mOnNewsListener;

    public NewsRecyclerAdapter(OnNewsListener onNewsListener) {
        this.mOnNewsListener = onNewsListener;
        mNews = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = null;
        switch(i){
            case NEWS_TYPE:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_news_list_item, viewGroup, false);
                return new NewsViewHolder(view, mOnNewsListener);
            }
            case CATEGORY_TYPE:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_category_list_item, viewGroup, false);
                return new CategoryViewHolder(view, mOnNewsListener);
            }
            case LOADING_TYPE:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_loading_list_item, viewGroup, false);
                return new LoadViewHolder(view);
            }
            case EXHAUSTED_TYPE:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_search_exhausted, viewGroup, false);
                return new ExhaustionViewHolder(view);
            }

            default:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_news_list_item, viewGroup, false);
                return new NewsViewHolder(view, mOnNewsListener);
            }

        }

    }


    @Override
    public int getItemViewType(int position) {

        if (mNews.get(position).getNewsTrigger() == -1) {
            return CATEGORY_TYPE;
        } else if (mNews.get(position).getTitle().equals("LOADING...")) {
            return LOADING_TYPE;
        } else if (mNews.get(position).getTitle().equals("EXHAUSTED...")) {
            return EXHAUSTED_TYPE;
        }
        else if ((position == mNews.size() - 1) && position != 0 && !mNews.get(position).getTitle().equals("EXHAUSTED...")) {
            return LOADING_TYPE;
        } else {
            return NEWS_TYPE;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

            int itemViewType = getItemViewType(position);

            if(itemViewType == NEWS_TYPE){
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .error(R.drawable.ic_launcher_background);

                //Glide
                RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
                Glide.with(viewHolder.itemView.getContext())
                        .setDefaultRequestOptions(requestOptions)
                        .load(mNews.get(position).getUrlToImage())
                        .into(((NewsViewHolder)viewHolder).image);


                ((NewsViewHolder)viewHolder).title.setText(mNews.get(position).getTitle());
                ((NewsViewHolder)viewHolder).publisher.setText(mNews.get(position).getSource().getName());

                //Date
                String date = mNews.get(position).getPublishedAt();
                String new_date = parseDate(date);
                ((NewsViewHolder)viewHolder).date.setText(new_date);

            }

            else if (itemViewType == CATEGORY_TYPE){
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .error(R.drawable.ic_launcher_background);

                Uri path = Uri.parse("android.resource://com.juanma.greennews/drawable/"
                        + mNews.get(position).getUrlToImage());
                Glide.with(((CategoryViewHolder)viewHolder).itemView)
                        .setDefaultRequestOptions(options)
                        .load(path)
                        .into(((CategoryViewHolder)viewHolder).categoryImage);
                ((CategoryViewHolder)viewHolder).categoryTitle.setText(mNews.get(position).getTitle());

            }


        }



        /// Methods

    public void setQueryExhausted(){
        hideLoading();;
        News exhaustedNews = new News();
        exhaustedNews.setTitle("EXHAUSTED...");
        mNews.add(exhaustedNews);
        notifyDataSetChanged();

    }

    private void hideLoading(){
        if(isLoading()){
            for(News news: mNews){
                if(news.getTitle().equals("LOADING...")){
                    mNews.remove(news);
                }
                notifyDataSetChanged();
            }
        }
    }

    public void displayLoading(){

        if(!isLoading()){
            News news = new News();
            news.setTitle("LOADING...");
            List<News> loadingList = new ArrayList<>();
            loadingList.add(news);
            mNews = loadingList;
            notifyDataSetChanged();
        }

    }

    public void displaySearchCategories(){
        List<News> categories = new ArrayList<>();
        for(int i =0; i< Constants.DEFAULT_SEARCH_CATEGORIES.length; i++){
            News news = new News();
            news.setTitle(Constants.DEFAULT_SEARCH_CATEGORIES[i]);
            news.setUrlToImage(Constants.DEFAULT_SEARCH_CATEGORY_IMAGES[i]);
            news.setNewsTrigger(-1);
            categories.add(news);
        }
        mNews = categories;
        notifyDataSetChanged();

    }

    private boolean isLoading(){
        if(mNews!=null){
            if(mNews.size() > 0){
                if(mNews.get(mNews.size() -1).getTitle().equals("LOADING...")){
                    return true;
                }
            }
            return false;

        }


        return false;
    }

    @Override
    public int getItemCount() {
         return mNews.size();
    }

    public void setNews(List<News> news){
        mNews = news;
        notifyDataSetChanged();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String parseTime(String input){
        java.util.Date date = Date.from( Instant.parse(input));
        String dateOut;
        DateFormat timeFormatter = DateFormat.getTimeInstance(DateFormat.SHORT);
        dateOut = timeFormatter.format(date);
        return dateOut;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public  String parseDate(String input){
        java.util.Date date = Date.from( Instant.parse(input));
        String dateOut;
        DateFormat dateFormatter;
        dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT);
        dateOut = dateFormatter.format(date);

        return dateOut;
    }

    public News getSelectedNews(int position){

        if(mNews != null){
            if(mNews.size()>0){
                return mNews.get(position);
            }
        }
        return null;
    }


}

package com.juanma.greennews.util;

import android.util.Log;

import com.juanma.greennews.models.News;

import java.util.List;

public class Testing {

    public static void printNews(String tag, List<News> list)
    {
        for(News news: list ){
            Log.d(tag, "printNews: " + news.getTitle() + ", " + news.getAuthor());
        }

    }


}

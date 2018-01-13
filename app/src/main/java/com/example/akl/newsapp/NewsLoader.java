package com.example.akl.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Mohamed Akl on 1/11/2018.
 */

public class NewsLoader  extends AsyncTaskLoader<List<News>>{
    private String url;

    NewsLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if(url == null){
            return null;
        }
        return QueryUtils.fetchNewsData(url);
    }
}
package com.example.akl.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mohamed Akl on 1/11/2018.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    private Context context;

    NewsAdapter(Context context, List<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        News article = getItem(position);
        TextView section = v.findViewById(R.id.section);
        section.setText(article.getSection());
        TextView headline = v.findViewById(R.id.main_headline);
        headline.setText(article.getHeadline());
        return v;
    }
}

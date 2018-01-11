package com.example.akl.newsapp;

import android.widget.ImageView;

import java.net.URL;

/**
 * Created by Mohamed Akl on 1/11/2018.
 */

class News {
    private String headline;
    private URL url;
    private String section;

    News(String headline, URL url, String section) {
        this.headline = headline;
        this.url = url;
        this.section = section;
    }

    URL getUrl() {

        return url;
    }

    String getSection() {
        return section;
    }

    String getHeadline() {
        return headline;
    }

}

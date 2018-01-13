package com.example.akl.newsapp;

import java.net.URL;

/**
 * Created by Mohamed Akl on 1/11/2018.
 */

class News {
    private String headline;
    private URL url;
    private String section;
    private String date;
    private String author;


    News(String headline, URL url, String section,String date,String author) {
        this.headline = headline;
        this.url = url;
        this.section = section;
        this.date = date;
        this.author = author;
    }

    String getDate() {
        return date;
    }

    String getAuthor() {
        return author;
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

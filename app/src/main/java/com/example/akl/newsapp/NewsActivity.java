package com.example.akl.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    String GUARDIAN_DATA = "http://content.guardianapis.com/search?order-by=newest&show-tags=contributor&api-key=36ec69f0-a725-46fc-bff6-4943af00d300";
    private NewsAdapter adapter;
    private TextView mEmptyStateTextView;
    SwipeRefreshLayout srl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        srl = findViewById(R.id.swiperefresh);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkConnection();
            }
        });
        ListView lv = findViewById(R.id.list_view);
        mEmptyStateTextView = findViewById(R.id.empty_view);
        lv.setEmptyView(mEmptyStateTextView);
        adapter = new NewsAdapter(this,new ArrayList<News>());
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News currentArticle = adapter.getItem(position);
                Uri newsLink = null;
                if (currentArticle != null) {
                    newsLink = Uri.parse(String.valueOf(currentArticle.getUrl()));
                }
                Intent intent = new Intent(Intent.ACTION_VIEW,newsLink);
                startActivity(intent);
            }
        });
        checkConnection();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            Intent settingsIntent = new Intent(this,SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String section = sharedPreferences.getString(getString(R.string.settings_filter_key),getString(R.string.settings_filter_default));
        Uri baseUri = Uri.parse(GUARDIAN_DATA);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("q",section);
        return new NewsLoader(this,uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        ProgressBar pbar = findViewById(R.id.pbar);
        pbar.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.no_articles);
        adapter.clear();
        srl.setRefreshing(false);
        if(news != null && !news.isEmpty()){
            adapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        adapter.clear();
    }

    public void checkConnection(){
        ConnectivityManager manager1 = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = null;
        if (manager1 != null) {
            info = manager1.getActiveNetworkInfo();
        }
        if(info != null && info.isConnectedOrConnecting()){
            LoaderManager manager = getLoaderManager();
            manager.initLoader(0,null,this);
        }else{
            srl.setRefreshing(false);
            ProgressBar pbar = findViewById(R.id.pbar);
            pbar.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }
}
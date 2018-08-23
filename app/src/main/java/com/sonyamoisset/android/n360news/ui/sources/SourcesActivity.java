package com.sonyamoisset.android.n360news.ui.sources;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.sonyamoisset.android.n360news.R;
import com.sonyamoisset.android.n360news.data.model.Source;
import com.sonyamoisset.android.n360news.analytics.AnalyticsApplication;
import com.sonyamoisset.android.n360news.ui.BaseActivity;
import com.sonyamoisset.android.n360news.ui.articles.ArticlesActivity;

public class SourcesActivity extends BaseActivity {

    private SourcesAdapter sourceAdapter;
    private SourcesViewModel sourcesViewModel;
    private ProgressBar sourceProgressBar;
    private Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sources);

        AnalyticsApplication analyticsApplication = (AnalyticsApplication) getApplication();
        tracker = analyticsApplication.getDefaultTracker();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.sources_activity_action_bar_title);
        }

        sourceProgressBar = findViewById(R.id.activity_sources_progress_bar);
        sourceProgressBar.setMax(10);
        sourceProgressBar.setVisibility(View.VISIBLE);
        sourceProgressBar.setProgress(0);

        sourcesViewModel = ViewModelProviders.of(this).get(SourcesViewModel.class);

        populateBottomSpinnerWithCategories();
        populateUIForNewsSources();

        getSourcesFromWebService();
        getStatusCodeMutableLiveData();
    }

    private void populateUIForNewsSources() {
        sourceAdapter = new SourcesAdapter();
        sourceAdapter.setAdapterListener(this::filterArticlesBy);

        RecyclerView sourcesRecyclerView = findViewById(R.id.activity_sources_recyclerview);
        sourcesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sourcesRecyclerView.setHasFixedSize(true);
        sourcesRecyclerView.setAdapter(sourceAdapter);
    }

    private void sendEventsToGoogleAnalyticsFor(String category) {
        Log.i("SourcesActivity", "Setting category: " + category);
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction("ClickCategory")
                .build());
    }

    private void getSourcesFromWebService() {
        sourcesViewModel.getSourcesFromNewsWebService().observe(this, sources -> {
            ProgressTask task = new ProgressTask();
            task.execute(10);

            sourceProgressBar.setVisibility(View.GONE);
            sourceAdapter.submitList(sources);
        });
    }

    private void getSourcesByCategoryFromWebService(String category) {
        sourcesViewModel.filterNewsSourcesBy(category).observe(SourcesActivity.this,
                sources -> sourceAdapter.submitList(sources));
    }

    private void filterArticlesBy(Source source) {
        Intent articlesIntent = new Intent(this, ArticlesActivity.class);
        articlesIntent.putExtra(getString(R.string.sources_activity_extra_source_id), source.getId());
        articlesIntent.putExtra(getString(R.string.sources_activity_extra_source_name), source.getName());
        startActivity(articlesIntent);
    }

    private void populateBottomSpinnerWithCategories() {
        ArrayAdapter<String> newsCategoriesSpinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                SourcesCategories.filterByNewsSourcesCategories());

        Spinner newsCategoriesSpinner = findViewById(R.id.activity_sources_spinner);
        newsCategoriesSpinner.setAdapter(newsCategoriesSpinnerAdapter);
        newsCategoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String category = newsCategoriesSpinnerAdapter.getItem(i);
                sendEventsToGoogleAnalyticsFor(category);
                getSourcesByCategoryFromWebService(category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private class ProgressTask extends AsyncTask<Integer, Integer, Void> {

        protected void onPreExecute() {
            sourceProgressBar.setMax(10);
        }

        protected void onCancelled() {
            sourceProgressBar.setMax(0);
        }

        protected Void doInBackground(Integer... params) {
            int start = params[0];
            for (int i = start; i <= 10; i += 5) {
                try {
                    boolean cancelled = isCancelled();
                    if (!cancelled) {
                        publishProgress(i);
                        onProgressUpdate(i);
                        SystemClock.sleep(1000);
                    }
                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }
            }
            return null;
        }

        protected void onProgressUpdate(Integer... values) {
            setProgress(10);
        }

        protected void onPostExecute(Void result) {
            Log.v("Progress", "Finished");
        }
    }

    private void getStatusCodeMutableLiveData() {
        sourcesViewModel.getStatusCodeMutableLiveData().observe(this, errorCode -> {
            if (errorCode != null && errorCode == 0) {
                showNetworkState();

                ProgressTask task = new ProgressTask();
                task.execute(10);

                sourceProgressBar.setVisibility(View.GONE);
            }
        });
    }
}
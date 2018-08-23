package com.sonyamoisset.android.n360news.ui.articles;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.sonyamoisset.android.n360news.R;
import com.sonyamoisset.android.n360news.data.local.entity.Article;
import com.sonyamoisset.android.n360news.ui.BaseActivity;
import com.sonyamoisset.android.n360news.ui.webview.WebViewActivity;

public class ArticlesActivity extends BaseActivity {

    private static final String SOURCE_ID_KEY = "source_id";
    private static final String SOURCE_NAME_KEY = "source_name";

    private ArticlesViewModel articlesViewModel;
    private ArticlesAdapter articlesAdapter;
    private RecyclerView articlesRecyclerView;
    private ProgressBar articlesProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        articlesProgressBar = findViewById(R.id.activity_articles_progress_bar);
        articlesViewModel = ViewModelProviders.of(this).get(ArticlesViewModel.class);

        setActionBarTitle();
        populateUIForArticlesList();

        getArticlesFromNewsWebService(getIntent().getStringExtra(SOURCE_ID_KEY));
        getStatusCodeMutableLiveData();
    }

    private void setActionBarTitle() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getIntent().getStringExtra(SOURCE_NAME_KEY));
        }
    }

    private void populateUIForArticlesList() {
        articlesAdapter = new ArticlesAdapter();
        articlesAdapter.setOnItemClickListener(this::launchWebActivityFor);
        articlesAdapter.setOnItemInsertListener(article -> {
            articlesViewModel.insertIntoRoomDatabase(article);

            Snackbar.make(articlesRecyclerView,
                    R.string.articles_activity_favorite_article_message,
                    Snackbar.LENGTH_SHORT)
                    .setAction("Undo",
                            view -> articlesViewModel.deleteFromRoomDatabase(article))
                    .show();
        });

        articlesRecyclerView = findViewById(R.id.activity_articles_recycler_view);
        articlesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        articlesRecyclerView.setHasFixedSize(true);
        articlesRecyclerView.setAdapter(articlesAdapter);
    }

    private void launchWebActivityFor(Article article) {
        startActivity(WebViewActivity.launchWebViewIntentFor(this, article.getUrl()));
    }

    private void getArticlesFromNewsWebService(String sourceId) {
        articlesViewModel.getArticlesFromNewsWebService(sourceId).observe(this, articles -> {
            articlesAdapter.submitList(articles);
            articlesProgressBar.setVisibility(View.GONE);
        });
    }

    private void getStatusCodeMutableLiveData() {
        articlesViewModel.getStatusCodeMutableLiveData().observe(this, errorCode -> {
            if (errorCode != null && errorCode == 0) {
                showNetworkState();
                articlesProgressBar.setVisibility(View.GONE);
            }
        });
    }
}
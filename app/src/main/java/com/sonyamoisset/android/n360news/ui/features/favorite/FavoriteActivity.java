package com.sonyamoisset.android.n360news.ui.features.favorite;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.sonyamoisset.android.n360news.R;
import com.sonyamoisset.android.n360news.data.local.entity.Article;
import com.sonyamoisset.android.n360news.ui.BaseActivity;
import com.sonyamoisset.android.n360news.ui.article.ArticleAdapter;
import com.sonyamoisset.android.n360news.ui.articles.ArticlesAdapter;
import com.sonyamoisset.android.n360news.ui.webview.WebViewActivity;

public class FavoriteActivity extends BaseActivity {

    private FavoriteViewModel favoriteViewModel;
    private ArticlesAdapter articlesAdapter;
    private RecyclerView articlesRecyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        progressBar = findViewById(R.id.activity_articles_progress_bar);
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);

        populateUIForFavorites();

        getAllArticlesFromRoomDatabase();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.menu_clear_favorite:
                new AlertDialog.Builder(this)
                        .setTitle("Caution!")
                        .setMessage("Sure to delete")
                        .setPositiveButton("Yes",
                                (dialogInterface, i) -> favoriteViewModel.clearRoomDatabase())
                        .setNegativeButton("no", null)
                        .create()
                        .show();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite, menu);
        return true;
    }

    private void populateUIForFavorites() {
        articlesAdapter = new ArticleAdapter();
        articlesAdapter.setOnItemClickListener(this::launchWebActivityFor);
        articlesAdapter.setOnItemDeleteListener(article -> {
            favoriteViewModel.deleteFromRoomDatabase(article);

            Snackbar.make(articlesRecyclerView,
                    R.string.favorite_activity_delete_article_message,
                    Snackbar.LENGTH_SHORT)
                    .setAction("Undo",
                            view -> favoriteViewModel.insertIntoRoomDatabase(article))
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

    private void getAllArticlesFromRoomDatabase() {
        favoriteViewModel.getAllArticlesFromRoomDatabase().observe(this, articles -> {
            progressBar.setVisibility(View.GONE);
            articlesAdapter.submitList(articles);
        });
    }
}

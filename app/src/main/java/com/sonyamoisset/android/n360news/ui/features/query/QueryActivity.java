package com.sonyamoisset.android.n360news.ui.features.query;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sonyamoisset.android.n360news.R;
import com.sonyamoisset.android.n360news.data.local.entity.Article;
import com.sonyamoisset.android.n360news.ui.BaseActivity;
import com.sonyamoisset.android.n360news.ui.articles.ArticlesAdapter;
import com.sonyamoisset.android.n360news.ui.webview.WebViewActivity;

public class QueryActivity extends BaseActivity {

    private static final int QUERY_LENGTH = 2;

    private QueryViewModel queryViewModel;
    private ArticlesAdapter articlesAdapter;
    private RecyclerView queryRecyclerView;
    private ProgressBar queryProgressBar;
    private EditText queryInputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        queryProgressBar = findViewById(R.id.activity_query_progress_bar);
        queryInputText = findViewById(R.id.activity_query_input);
        queryViewModel = ViewModelProviders.of(this).get(QueryViewModel.class);

        launchQueryInput();
        populateUIForQueries();

        getArticlesMutableLiveData();
        getStatusCodeMutableLiveData();
    }

    private void populateUIForQueries() {
        articlesAdapter = new ArticlesAdapter();
        articlesAdapter.setOnItemClickListener(this::launchWebActivityFor);
        articlesAdapter.setOnItemInsertListener(article -> {
            queryViewModel.insertIntoRoomDatabase(article);

            Snackbar.make(queryRecyclerView,
                    R.string.articles_activity_favorite_article_message,
                    Snackbar.LENGTH_SHORT)
                    .setAction("Undo",
                            view -> queryViewModel.deleteFromRoomDatabase(article))
                    .show();
        });

        queryRecyclerView = findViewById(R.id.activity_query_recycler_view);
        queryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        queryRecyclerView.setHasFixedSize(true);
        queryRecyclerView.setAdapter(articlesAdapter);
    }

    private void launchWebActivityFor(Article article) {
        startActivity(WebViewActivity.launchWebViewIntentFor(this, article.getUrl()));
    }

    private void getArticlesMutableLiveData() {
        queryViewModel.getArticlesMutableLiveData().observe(this, articles -> {
            articlesAdapter.submitList(articles);
            queryProgressBar.setVisibility(View.GONE);
            queryInputText.setVisibility(View.VISIBLE);
        });
    }

    private void launchQueryInput() {
        queryInputText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                String queryInputText = this.queryInputText.getText().toString();

                if (queryInputText.length() > QUERY_LENGTH) {
                    queryViewModel.queryNewsWebServicesForArticles(queryInputText);
                    queryProgressBar.setVisibility(View.VISIBLE);
                    this.queryInputText.setVisibility(View.INVISIBLE);

                    InputMethodManager inputMethodManager =
                            (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);

                    if (inputMethodManager != null && getCurrentFocus() != null) {
                        inputMethodManager
                                .hideSoftInputFromWindow(getCurrentFocus()
                                        .getWindowToken(), 0);
                    }
                } else {
                    Toast.makeText(QueryActivity.this,
                            R.string.query_activity_input_text_toast_message,
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }

            return false;
        });
    }

    private void getStatusCodeMutableLiveData() {
        queryViewModel.getStatusCodeMutableLiveData().observe(this, errorCode -> {
            if (errorCode != null && errorCode == 0) {
                showNetworkState();
                queryProgressBar.setVisibility(View.GONE);
                queryInputText.setVisibility(View.VISIBLE);
            }
        });
    }
}

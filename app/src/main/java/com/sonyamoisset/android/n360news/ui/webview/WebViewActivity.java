package com.sonyamoisset.android.n360news.ui.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {

    public static final String ARTICLE_URL_KEY = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebView articleWebView = new WebView(this);
        setContentView(articleWebView);
        articleWebView.loadUrl(getIntent().getStringExtra(ARTICLE_URL_KEY));
    }

    public static Intent launchWebViewIntentFor(Context context, String url) {
        Intent webViewIntent = new Intent(context, WebViewActivity.class);
        webViewIntent.putExtra(ARTICLE_URL_KEY, url);
        return webViewIntent;
    }
}

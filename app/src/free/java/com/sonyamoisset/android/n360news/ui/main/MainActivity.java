package com.sonyamoisset.android.n360news.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.google.android.gms.ads.MobileAds;
import com.sonyamoisset.android.n360news.R;
import com.sonyamoisset.android.n360news.ui.features.favorite.FavoriteActivity;
import com.sonyamoisset.android.n360news.ui.features.query.QueryActivity;
import com.sonyamoisset.android.n360news.ui.sources.SourcesActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        findViewById(R.id.activity_main_cardview_sources).setOnClickListener(
                view -> startActivity(new Intent(this, SourcesActivity.class)));

        findViewById(R.id.activity_main_cardview_favorites).setOnClickListener(
                view -> startActivity(new Intent(this, FavoriteActivity.class)));

        findViewById(R.id.activity_main_cardview_queries).setOnClickListener(
                view -> startActivity(new Intent(this, QueryActivity.class)));
    }
}

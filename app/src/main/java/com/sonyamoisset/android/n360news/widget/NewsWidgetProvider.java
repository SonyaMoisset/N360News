package com.sonyamoisset.android.n360news.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.sonyamoisset.android.n360news.R;
import com.sonyamoisset.android.n360news.ui.features.favorite.FavoriteActivity;
import com.sonyamoisset.android.n360news.ui.features.query.QueryActivity;
import com.sonyamoisset.android.n360news.ui.sources.SourcesActivity;

public class NewsWidgetProvider extends AppWidgetProvider {

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_news);
            Intent archiveIntent = new Intent(context, FavoriteActivity.class);
            Intent newsIntent = new Intent(context, SourcesActivity.class);
            Intent searchIntent = new Intent(context, QueryActivity.class);

            PendingIntent pendingArchiveIntent =
                    PendingIntent.getActivity(context, 0, archiveIntent, 0);
            PendingIntent pendingNewsIntent =
                    PendingIntent.getActivity(context, 0, newsIntent, 0);
            PendingIntent pendingSearchIntent =
                    PendingIntent.getActivity(context, 0, searchIntent, 0);

            views.setOnClickPendingIntent(R.id.news_btn, pendingNewsIntent);
            views.setOnClickPendingIntent(R.id.archive_btn, pendingArchiveIntent);
            views.setOnClickPendingIntent(R.id.search_btn, pendingSearchIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}




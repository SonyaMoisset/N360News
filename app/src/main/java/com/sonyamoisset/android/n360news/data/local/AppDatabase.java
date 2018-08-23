package com.sonyamoisset.android.n360news.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.sonyamoisset.android.n360news.data.local.dao.ArticleDao;
import com.sonyamoisset.android.n360news.data.local.entity.Article;

@Database(entities = Article.class, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ArticleDao articleDao();

    private static AppDatabase instance;

    public static AppDatabase getDatabase(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "news_db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return instance;
    }
}

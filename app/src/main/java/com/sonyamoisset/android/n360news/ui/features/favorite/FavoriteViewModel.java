package com.sonyamoisset.android.n360news.ui.features.favorite;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.database.sqlite.SQLiteConstraintException;
import android.support.annotation.NonNull;

import com.sonyamoisset.android.n360news.data.local.entity.Article;
import com.sonyamoisset.android.n360news.repository.DataRepository;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {

    private DataRepository dataRepository;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);

        dataRepository =
                DataRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Article>> getAllArticlesFromRoomDatabase() {
        return dataRepository.getAllArticlesFromRoomDatabase();
    }

    public void insertIntoRoomDatabase(Article article) throws SQLiteConstraintException {
        dataRepository.insertIntoRoomDatabase(article);
    }

    public void deleteFromRoomDatabase(Article article) {
        dataRepository.deleteFromDatabase(article);
    }

    public void clearRoomDatabase() {
        dataRepository.deleteAllFromDatabase();
    }
}

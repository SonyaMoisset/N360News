package com.sonyamoisset.android.n360news.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import com.sonyamoisset.android.n360news.data.local.AppDatabase;
import com.sonyamoisset.android.n360news.data.local.entity.Article;
import com.sonyamoisset.android.n360news.data.model.ArticlesResponse;
import com.sonyamoisset.android.n360news.data.model.SourcesResponse;
import com.sonyamoisset.android.n360news.data.remote.WebServiceHelper;

import java.util.List;

import retrofit2.Call;

public class DataRepository {

    private WebServiceHelper webServiceHelper;
    private AppDatabase appDatabase;
    private static DataRepository dataRepository;

    private DataRepository(WebServiceHelper webServiceHelper, AppDatabase appDatabase) {
        this.webServiceHelper = webServiceHelper;
        this.appDatabase = appDatabase;
    }

    // Single instance repository
    public static DataRepository getInstance(Context context) {
        if (dataRepository == null) {
            dataRepository = new DataRepository(new WebServiceHelper(),
                    AppDatabase.getDatabase(context));
        }
        return dataRepository;
    }

    // WebService Calls
    public Call<SourcesResponse> getSourcesFromNewsWebService() {
        return webServiceHelper.getSourcesFromNewsWebService();
    }

    public Call<ArticlesResponse> getArticlesFromNewsWebServiceBy(String source) {
        return webServiceHelper.getArticlesFromNewsWebServiceBy(source);
    }

    public Call<ArticlesResponse> queryArticlesFromNewsWebServiceBy(String query) {
        return webServiceHelper.queryArticlesFromNewsWebServiceBy(query);
    }

    // Room Database CRUD Calls
    public LiveData<List<Article>> getAllArticlesFromRoomDatabase() {
        String ORDER_BY = "title";
        return appDatabase.articleDao().getAllArticles(ORDER_BY);
    }

    public void insertIntoRoomDatabase(Article article) throws SQLiteConstraintException {
        appDatabase.articleDao().insert(article);
    }

    public void deleteFromDatabase(Article article) {
        appDatabase.articleDao().delete(article);
    }

    public void deleteAllFromDatabase() {
        appDatabase.articleDao().deleteAllArticles();
    }
}

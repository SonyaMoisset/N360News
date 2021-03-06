package com.sonyamoisset.android.n360news.ui.features.query;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.database.sqlite.SQLiteConstraintException;
import android.support.annotation.NonNull;

import com.sonyamoisset.android.n360news.data.local.entity.Article;
import com.sonyamoisset.android.n360news.data.model.ArticlesResponse;
import com.sonyamoisset.android.n360news.repository.DataRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QueryViewModel extends AndroidViewModel {

    private DataRepository dataRepository;
    private MutableLiveData<List<Article>> articlesMutableLiveData;
    private MutableLiveData<Integer> statusCodeMutableLiveData;

    public QueryViewModel(@NonNull Application application) {
        super(application);

        dataRepository =
                DataRepository.getInstance(application.getApplicationContext());
        articlesMutableLiveData = new MutableLiveData<>();
        statusCodeMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Article>> getArticlesMutableLiveData() {
        return articlesMutableLiveData;
    }

    public void queryNewsWebServicesForArticles(String query) {
        dataRepository.queryArticlesFromNewsWebServiceBy(query).enqueue(new Callback<ArticlesResponse>() {
            @Override
            public void onResponse(@NonNull Call<ArticlesResponse> call,
                                   @NonNull Response<ArticlesResponse> response) {
                List<Article> articles = response.body().getArticles();
                articlesMutableLiveData.postValue(articles);
            }

            @Override
            public void onFailure(@NonNull Call<ArticlesResponse> call,
                                  @NonNull Throwable t) {
                statusCodeMutableLiveData.postValue(0);
            }
        });
    }

    public void insertIntoRoomDatabase(Article article) throws SQLiteConstraintException {
        dataRepository.insertIntoRoomDatabase(article);
    }

    public void deleteFromRoomDatabase(Article article) {
        dataRepository.deleteFromDatabase(article);
    }

    public MutableLiveData<Integer> getStatusCodeMutableLiveData() {
        return statusCodeMutableLiveData;
    }
}

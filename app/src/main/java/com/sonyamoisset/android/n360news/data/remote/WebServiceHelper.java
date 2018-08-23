package com.sonyamoisset.android.n360news.data.remote;

import com.sonyamoisset.android.n360news.BuildConfig;
import com.sonyamoisset.android.n360news.data.model.ArticlesResponse;
import com.sonyamoisset.android.n360news.data.model.SourcesResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceHelper {

    private static final String BASE_URL = "https://newsapi.org/v2/";
    private static final String NEWS_TOKEN = BuildConfig.NEWS_TOKEN;

    private static final String DEFAULT_CATEGORY = "relevancy";
    private static final String DEFAULT_LANGUAGE = "en";

    private NewsWebService newsWebService;

    public WebServiceHelper() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        newsWebService = retrofit.create(NewsWebService.class);
    }

    public Call<SourcesResponse> getSourcesFromNewsWebService() {
        return newsWebService.getSources(NEWS_TOKEN);
    }

    public Call<ArticlesResponse> getArticlesFromNewsWebServiceBy(String source) {
        return newsWebService.getArticles(source, NEWS_TOKEN);
    }

    public Call<ArticlesResponse> queryArticlesFromNewsWebServiceBy(String query) {
        return newsWebService.searchArticles(query, DEFAULT_CATEGORY, DEFAULT_LANGUAGE, NEWS_TOKEN);
    }
}

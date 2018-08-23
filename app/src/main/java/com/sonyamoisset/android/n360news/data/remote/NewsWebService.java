package com.sonyamoisset.android.n360news.data.remote;

import com.sonyamoisset.android.n360news.data.model.ArticlesResponse;
import com.sonyamoisset.android.n360news.data.model.SourcesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsWebService {

    @GET("sources")
    Call<SourcesResponse> getSources(@Query("apiKey") String apiKey);

    @GET("top-headlines")
    Call<ArticlesResponse> getArticles(@Query("sources") String sources,
                                       @Query("apiKey") String apiKey);

    @GET("everything")
    Call<ArticlesResponse> searchArticles(@Query("q") String query,
                                          @Query("sortBy") String sortBy,
                                          @Query("language") String language,
                                          @Query("apiKey") String apiKey);
}

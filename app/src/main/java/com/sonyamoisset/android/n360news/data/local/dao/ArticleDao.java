package com.sonyamoisset.android.n360news.data.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.sonyamoisset.android.n360news.data.local.entity.Article;

import java.util.List;

@Dao
public interface ArticleDao {

    @Query("SELECT * FROM article_table ORDER BY :orderBy ASC")
    LiveData<List<Article>> getAllArticles(String orderBy);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Article article);

    @Delete
    void delete(Article article);

    @Query("DELETE FROM article_table")
    void deleteAllArticles();
}

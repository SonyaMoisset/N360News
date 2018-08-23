package com.sonyamoisset.android.n360news.ui.article;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sonyamoisset.android.n360news.R;
import com.sonyamoisset.android.n360news.data.local.entity.Article;
import com.sonyamoisset.android.n360news.ui.articles.ArticlesAdapter;
import com.squareup.picasso.Picasso;

public class ArticleAdapter extends ArticlesAdapter {

    private Article article;
    private View itemView;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        itemView =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_article, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        article = getItem(position);
        itemView = holder.itemView;

        ImageView articleThumbnail = itemView.findViewById(R.id.list_item_articles_thumbnail);

        Picasso.get()
                .load(article.getUrlToImage())
                .error(R.drawable.ic_info_outline_black_24dp)
                .into(articleThumbnail);

        TextView articleTitle = itemView.findViewById(R.id.list_item_articles_title);
        articleTitle.setText(article.getTitle());
    }
}

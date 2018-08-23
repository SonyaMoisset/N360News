package com.sonyamoisset.android.n360news.ui.articles;

import android.support.annotation.AnimRes;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.sonyamoisset.android.n360news.R;
import com.sonyamoisset.android.n360news.data.local.entity.Article;
import com.squareup.picasso.Picasso;

public class ArticlesAdapter extends ListAdapter<Article, ArticlesAdapter.ViewHolder> {

    private Article article;
    private View itemView;
    private OnItemClickListener onItemClickListener;
    private OnItemInsertListener onItemInsertListener;
    private OnItemDeleteListener onItemDeleteListener;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View itemView;

        public ViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;
            setListeners(itemView);
        }

        public void setListeners(View itemView) {
            itemView.setOnClickListener(view -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(getItem(getAdapterPosition()));
                }
            });

            itemView.setOnLongClickListener(view -> {
                if (onItemDeleteListener != null) {
                    onItemDeleteListener.onLongClick(getItem(getAdapterPosition()));
                } else if (onItemInsertListener != null) {
                    onItemInsertListener.onLongClick(getItem(getAdapterPosition()));
                }

                return true;
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_articles, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        article = getItem(position);
        itemView = holder.itemView;

        ImageView articleThumbnail = itemView.findViewById(R.id.list_item_articles_thumbnail);

        Picasso.get()
                .load(article.getUrlToImage())
                .error(R.color.cardview_dark_background)
                .into(articleThumbnail);

        TextView articleTitle = itemView.findViewById(R.id.list_item_articles_title);
        TextView articleDescription = itemView.findViewById(R.id.list_item_articles_description);
        articleTitle.setText(article.getTitle());
        articleDescription.setText(article.getDescription());
    }

    public ArticlesAdapter() {
        super(Article.DIFF_CALLBACK);
    }

    public interface OnItemClickListener {
        void onClick(Article article);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemDeleteListener {
        void onLongClick(Article article);
    }

    public void setOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
        this.onItemDeleteListener = onItemDeleteListener;
    }

    public interface OnItemInsertListener {
        void onLongClick(Article article);
    }

    public void setOnItemInsertListener(OnItemInsertListener onItemInsertListener) {
        this.onItemInsertListener = onItemInsertListener;
    }
}

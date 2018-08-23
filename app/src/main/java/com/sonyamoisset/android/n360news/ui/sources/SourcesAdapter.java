package com.sonyamoisset.android.n360news.ui.sources;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sonyamoisset.android.n360news.R;
import com.sonyamoisset.android.n360news.data.model.Source;

public class SourcesAdapter extends ListAdapter<Source, SourcesAdapter.ViewHolder> {

    private AdapterListener adapterListener;

    class ViewHolder extends RecyclerView.ViewHolder {

        private View itemView;

        ViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;
            this.itemView.setOnClickListener(
                    view -> adapterListener.onClick(getItem(getAdapterPosition())));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_source, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TextView sourceName =
                holder.itemView.findViewById(R.id.list_item_source_name);
        TextView sourceDescription =
                holder.itemView.findViewById(R.id.list_item_source_description);
        TextView sourceCategory =
                holder.itemView.findViewById(R.id.list_item_source_category);

        sourceName.setText(getItem(position).getName());
        sourceDescription.setText(getItem(position).getDescription());
        sourceCategory.setText(getItem(position).getCategory());
    }

    public SourcesAdapter() {
        super(Source.DIFF_CALLBACK);
    }

    public void setAdapterListener(AdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

    public interface AdapterListener {
        void onClick(Source source);
    }
}

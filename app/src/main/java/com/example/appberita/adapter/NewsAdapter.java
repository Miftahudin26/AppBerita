package com.example.appberita.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appberita.R;
import com.example.appberita.retrofit.ArticleModel;
import com.example.appberita.utils.RecyclerViewItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private final RecyclerViewItemClickListener itemClickListener;
    private List<ArticleModel> items;
    private LayoutInflater layoutInflater;
    Context context;

    public NewsAdapter(RecyclerViewItemClickListener itemClickListener, List<ArticleModel> items, Context context) {
        this.itemClickListener = itemClickListener;
        this.context=context;
        this.items = items;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.item_layout,parent,false);
        return new NewsAdapter.NewsViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        ArticleModel item=items.get(position);
        String title=item.getTitle();
        //Toast.makeText(context, title, Toast.LENGTH_SHORT).show();
        String desc=item.getDescription();
        holder.title.setText(title);
        holder.description.setText(desc);
        Picasso.get().load(item.getUrlToImage()).into(holder.imageView);
        holder.itemView.setTag(item);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        public final TextView title,description;
        public final ImageView imageView;
        public NewsViewHolder(@NonNull View itemView, RecyclerViewItemClickListener itemClickListener) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.titleTextView);
            description = itemView.findViewById(R.id.descriptionTextView);
            itemView.setOnClickListener(v -> {

                if(itemClickListener!=null){
                    itemClickListener.onItemClick(getAdapterPosition(),itemView);

                }

            });

        }
    }
}

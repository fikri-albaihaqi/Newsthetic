package com.example.newsthetic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsthetic.model.NewsArticle;
import com.example.newsthetic.model.NewsResponse;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends ListAdapter<NewsResponse, NewsViewHolder> {
    Context context;
    List<NewsArticle> articles;

    public NewsAdapter(Context context, List<NewsArticle> articles,
                       @NonNull DiffUtil.ItemCallback<NewsResponse> diffCallback) {
        super(diffCallback);
        this.context = context;
        this.articles = articles;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return NewsViewHolder.create(parent);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NewsViewHolder holder, int position) {
        holder.bind(articles.get(position).getTitle(),
                articles.get(position).getPublishedAt(),
                articles.get(position).getSource().getName(),
                articles.get(position).getUrlToImage(),
                context);

        holder.judul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title",articles.get(position).getTitle());
                bundle.putString("time",articles.get(position).getPublishedAt());
                bundle.putString("image",articles.get(position).getUrlToImage());
                bundle.putString("publisher",articles.get(position).getSource().getName());
                bundle.putString("author",articles.get(position).getAuthor());
                bundle.putString("description",articles.get(position).getDescription());
                bundle.putString("url",articles.get(position).getUrl());

                Intent intent = new Intent(v.getContext(), DetailBeritaActivity.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
    }

    static class NewsDiff extends DiffUtil.ItemCallback<NewsResponse> {
        @Override
        public boolean areItemsTheSame(@NonNull NewsResponse oldItem, @NonNull NewsResponse newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull NewsResponse oldItem, @NonNull NewsResponse newItem) {
            return oldItem.getArticles().equals(newItem.getArticles());
        }
    }
}

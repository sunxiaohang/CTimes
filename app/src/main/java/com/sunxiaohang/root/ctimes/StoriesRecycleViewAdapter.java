package com.sunxiaohang.root.ctimes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunxiaohang.root.ctimes.pojo.News;
import com.sunxiaohang.root.ctimes.utils.ObtainImageDrawable;

import java.util.ArrayList;
import java.util.List;

public class StoriesRecycleViewAdapter extends RecyclerView.Adapter{
    private List<News> newsLists;

    public StoriesRecycleViewAdapter(ArrayList<News> newsLists) {
        this.newsLists = newsLists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item_content,viewGroup,false);
        RecyclerView.ViewHolder holder = new NewsViewHolder(rootView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        final NewsViewHolder holder = (NewsViewHolder) viewHolder;
        final Context context = holder.rootView.getContext();
        final News news = newsLists.get(i);
        holder.title.setText(news.getTitle());
        holder.newsAbstract.setText(news.getNewsAbstract());
        holder.author.setText(news.getAuthor());
        holder.published_date.setText(news.getPublished_date().substring(0,10));
        //加载图片
//        new ObtainImageDrawable(news.getImageOneUrl(),holder.imageViewOne).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,NewsDetailsActivity.class);
                intent.putExtra("newsAddress",news.getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsLists.size();
    }
    static class NewsViewHolder extends RecyclerView.ViewHolder{
        final View rootView;
        final TextView title;
        final TextView newsAbstract;
        final TextView author;
        final TextView published_date;
        final ImageView imageViewOne;
        public NewsViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            title = itemView.findViewById(R.id.news_title);
            newsAbstract = itemView.findViewById(R.id.news_abstract);
            author = itemView.findViewById(R.id.news_item_author);
            published_date = itemView.findViewById(R.id.news_item_time);
            imageViewOne = itemView.findViewById(R.id.news_item_image_one);
        }
    }
}

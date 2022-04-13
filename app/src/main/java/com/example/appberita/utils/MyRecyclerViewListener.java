package com.example.appberita.utils;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.appberita.activities.WebActivity;
import com.example.appberita.retrofit.ArticleModel;

public class MyRecyclerViewListener implements RecyclerViewItemClickListener {
    Context context;

    public MyRecyclerViewListener(Context context) {
        this.context = context;
    }

    @Override
    public void onItemClick(int position, View view) {
        ArticleModel articleModel=(ArticleModel)view.getTag();
        Intent intent=new Intent(context, WebActivity.class);
        intent.putExtra("url",articleModel.getUrl());
        context.startActivity(intent);
    }
}
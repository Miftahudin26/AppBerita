package com.example.appberita.utils;

import android.content.Context;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

public class MyOnQueryTextListener implements SearchView.OnQueryTextListener {
    private RecyclerView recyclerView;
    Context context;
    String category,searchFor;
    @Override
    public boolean onQueryTextSubmit(String query) {
        NewsLoader searchLoader=new NewsLoader(context,recyclerView,category,true,searchFor);
        searchLoader.loadNews();

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }
}

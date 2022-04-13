package com.example.appberita.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appberita.R;
import com.example.appberita.utils.NewsLoader;


public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private static String searchfor="";
    private static String category;
    private static NewsLoader newsLoader;
    private TextView textViewEmpty;



    public  void setCategory(String category) {
        SearchFragment.category = category;
    }

    public void setSearchfor(String searchfor) {
        this.searchfor = searchfor;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        textViewEmpty=(TextView)view.findViewById(R.id.nothing_to_showTV);
        newsLoader=new NewsLoader(getContext(),recyclerView,category,true,searchfor);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadNews();

    }
    public void loadNews(){
        newsLoader.setSearchFor(searchfor);
        newsLoader.loadNews();

    }


}
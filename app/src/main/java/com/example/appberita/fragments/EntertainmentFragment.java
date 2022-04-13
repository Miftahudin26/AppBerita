package com.example.appberita.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appberita.R;
import com.example.appberita.utils.NewsLoader;

public class EntertainmentFragment extends Fragment {

    private RecyclerView recyclerView;
    private static final String CATEGORY="entertainment";
    private static final String TITLE="Entertainment";

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_entertainment,container,false);
        recyclerView=view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(TITLE);
        NewsLoader newsLoader=new NewsLoader(getContext(),recyclerView,CATEGORY,false,null);
        newsLoader.loadNews();
    }
}
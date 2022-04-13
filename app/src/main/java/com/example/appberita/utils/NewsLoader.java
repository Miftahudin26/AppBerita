package com.example.appberita.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appberita.adapter.NewsAdapter;
import com.example.appberita.retrofit.APIInterface;
import com.example.appberita.retrofit.ApiClient;
import com.example.appberita.retrofit.ArticleModel;
import com.example.appberita.retrofit.ResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsLoader {
    public static final String MY_LOG="myLog";
    private Context context;
    private List<ArticleModel>list=new ArrayList<>();
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private String category;
    private boolean ifSearch;
    private String searchFor;


    public NewsLoader(Context context, RecyclerView recyclerView, String category, boolean ifSearch, String searchFor) {
        this.context = context;
        this.list = list;
        this.recyclerView = recyclerView;
        this.category=category;
        this.ifSearch=ifSearch;
        this.searchFor=searchFor;

    }

    public void setSearchFor(String searchFor) {
        this.searchFor = searchFor;
    }

    public  void loadNews(){

        Call<ResponseModel> call;
        final APIInterface apiInterface = ApiClient.getClient().create(APIInterface.class);
        if(ifSearch){
            call=apiInterface.getNews(category,searchFor);
        }else{
            call = apiInterface.getNews(category);
        }
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    list = response.body().getArticles();
                    if(list.size()>0){

                        adapter=new NewsAdapter(new MyRecyclerViewListener(context),list,context);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    }else{ Toast.makeText(context,"Nothing to show...",Toast.LENGTH_SHORT).show();}
                } else {

                    Toast.makeText(context, "response.body return null!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(context, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

    }

}


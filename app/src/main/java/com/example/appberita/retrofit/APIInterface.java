package com.example.appberita.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("v2/top-headlines?country=id&apiKey=" + MyConstants.NEWS_API_KEY)
    Call<ResponseModel> getNews(@Query("category") String category);

    @GET("v2/top-headlines?country=id&apiKey=" + MyConstants.NEWS_API_KEY)
    Call<ResponseModel> getNews();

    @GET("v2/top-headlines?country=id&apiKey=" + MyConstants.NEWS_API_KEY)
    Call<ResponseModel> getNews(@Query("category") String category, @Query("q") String searchFor);
}

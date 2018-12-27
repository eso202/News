package com.example.laptophome.news;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface app_interface {
    @GET("top-headlines")
    Call<news> getnews(


            @Query("country")String country,
            @Query("apiKey") String apikey
    );



    @GET("everything")
    Call<news>getsearchablenews
            (
                    @Query("q")String keyword,
                    @Query("from")String from,
                    @Query("sortBy")String sortby,
                    @Query("apiKey")String apikey
            );
}

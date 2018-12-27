package com.example.laptophome.news.api_key;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit_client {
    public static final String base_url="https://newsapi.org/v2/";
    public static Retrofit retrofit;

    public static Retrofit getRetrofit_client()
    {
        if(retrofit == null)
        {
            retrofit= new Retrofit.Builder().
                    baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }


        return retrofit;
    }


}

package com.vincent.githubusersearch.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class WebAgent {

    private static Retrofit retrofit;

    static Retrofit getRetrofit(String baseUrl) {
        if (retrofit == null) {
            retrofit = newRetrofit(baseUrl);
        }
        else if (!retrofit.baseUrl().toString().equals(baseUrl)) {
            retrofit = newRetrofit(baseUrl);
        }

        return retrofit;
    }

    private static Retrofit newRetrofit(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();

        return retrofit;
    }
}

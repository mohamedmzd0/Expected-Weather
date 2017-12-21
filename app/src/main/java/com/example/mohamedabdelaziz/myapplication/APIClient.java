package com.example.mohamedabdelaziz.myapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mohamed Abd Elaziz on 12/5/2017.
 */

public class APIClient {
    //
    final static String BASE_URL = "http://api.openweathermap.org";
    static Retrofit retrofit = null;

    static Retrofit getAPI() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}

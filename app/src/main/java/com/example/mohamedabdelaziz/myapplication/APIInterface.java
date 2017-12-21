package com.example.mohamedabdelaziz.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Mohamed Abd Elaziz on 12/5/2017.
 */

public interface APIInterface {
    @GET
    Call<Example> getdata(@Url String url);
}

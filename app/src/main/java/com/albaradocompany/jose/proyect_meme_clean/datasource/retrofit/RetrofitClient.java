package com.albaradocompany.jose.proyect_meme_clean.datasource.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jose on 22/04/2017.
 */

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String endpoint) {
        if (retrofit == null) {
            OkHttpClient.Builder httpclient = new OkHttpClient.Builder();
            httpclient.addInterceptor(new LogJsonInterceptor());
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(endpoint)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpclient.build())
                    .build();
        }
        return retrofit;
    }
}

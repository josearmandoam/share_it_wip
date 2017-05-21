package com.albaradocompany.jose.proyect_meme_clean.datasource.api;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.FeedApiResponse;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit.FeedService;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Feed;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetFeed;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jose on 20/05/2017.
 */

public class FeedApiImp implements GetFeed, Callback<FeedApiResponse> {

    String userId;
    GetFeed.Listener listener = new NullListener();

    public FeedApiImp(String userId) {
        this.userId = userId;
    }

    @Override
    public void getFeed(Listener listener) {
        if (listener != null) {
            this.listener = listener;
        }
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL_DEFAULT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        FeedService service = retrofit.create(FeedService.class);
        service.getFeed(userId).enqueue(this);
    }

    @Override
    public void onResponse(Call<FeedApiResponse> call, Response<FeedApiResponse> response) {
        if (response.isSuccessful()) {
            listener.onFeedReceived(response.body().parseFeeds());
        } else {
            listener.onError(new Exception("Body Failed"));
        }
    }

    @Override
    public void onFailure(Call<FeedApiResponse> call, Throwable t) {
        if (t instanceof IOException)
            listener.onNoInternetAvailable();
        else
            listener.onError(new Exception(t));
    }

    private class NullListener implements Listener {
        @Override
        public void onNoInternetAvailable() {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onFeedReceived(List<Feed> feeds) {

        }
    }
}

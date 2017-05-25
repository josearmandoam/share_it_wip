package com.albaradocompany.jose.proyect_meme_clean.datasource.api;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.UpdateFeedApiResponse;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit.UpdateFeedService;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdateFeed;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jose on 25/05/2017.
 */

public class UpdateFeedApiImp implements UpdateFeed, Callback<UpdateFeedApiResponse> {
    String userId;
    String xUserId;
    String xProfile;
    String feedId;
    String xUsername;
    String action;

    public UpdateFeedApiImp(String userId, String xUserId, String xProfile, String feedId, String xUsername, String action) {
        this.userId = userId;
        this.xUserId = xUserId;
        this.xProfile = xProfile;
        this.feedId = feedId;
        this.xUsername = xUsername;
        this.action = action;
    }

    UpdateFeed.Listener listener = new NullListener();

    @Override
    public void updateFedd(Listener listener) {
        if (listener != null)
            this.listener = listener;
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL_DEFAULT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        UpdateFeedService service = retrofit.create(UpdateFeedService.class);
        service.updateFeed(userId, xUserId, feedId, xProfile, xUsername, action).enqueue(this);
    }

    @Override
    public void onResponse(Call<UpdateFeedApiResponse> call, Response<UpdateFeedApiResponse> response) {
        if (response.isSuccessful()) {
            if (response.isSuccessful())
                listener.onSuccess();
            else
                listener.onFailure();
        } else {
            listener.onError(new Exception("Body failed"));
        }

    }

    @Override
    public void onFailure(Call<UpdateFeedApiResponse> call, Throwable t) {
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
        public void onSuccess() {

        }

        @Override
        public void onFailure() {

        }
    }
}

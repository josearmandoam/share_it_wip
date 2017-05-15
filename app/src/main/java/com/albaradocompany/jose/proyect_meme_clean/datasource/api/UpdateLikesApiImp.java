package com.albaradocompany.jose.proyect_meme_clean.datasource.api;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit.UpdateLikeService;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdateLike;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jose on 15/05/2017.
 */

public class UpdateLikesApiImp implements UpdateLike, Callback<GenericApiResponse> {

    String userId;
    String imageId;
    String name;
    String lastname;
    String profile;
    String action;

    UpdateLike.Listener listener = new NullListener();

    public UpdateLikesApiImp(String userId, String imageId, String name, String lastname, String profile, String action) {
        this.userId = userId;
        this.imageId = imageId;
        this.name = name;
        this.lastname = lastname;
        this.profile = profile;
        this.action = action;
    }

    @Override
    public void updateLike(Listener listener) {
        if (listener != null) {
            this.listener = listener;
        }
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_DEFAULT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        UpdateLikeService service = retrofit.create(UpdateLikeService.class);
        service.updateLike(userId, imageId, profile, name + " " + lastname, action).enqueue(this);
    }

    @Override
    public void onResponse(Call<GenericApiResponse> call, Response<GenericApiResponse> response) {
        if (response.isSuccessful()) {
            if (response.body().parseResponse().getCode().equals("1")) {
                listener.onSuccess();
            } else {
                listener.onFailure();
            }
        } else {
            listener.onError(new Exception("Body failed"));
        }
    }

    @Override
    public void onFailure(Call<GenericApiResponse> call, Throwable t) {
        listener.onNoInternetAvailable();
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

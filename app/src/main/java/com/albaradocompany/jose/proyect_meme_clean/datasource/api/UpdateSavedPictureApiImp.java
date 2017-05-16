package com.albaradocompany.jose.proyect_meme_clean.datasource.api;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.UpdateSavedPictureResponse;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit.UpdateSavedPictureService;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdateSavedPicture;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jose on 16/05/2017.
 */

public class UpdateSavedPictureApiImp implements UpdateSavedPicture, Callback<UpdateSavedPictureResponse> {

    UpdateSavedPicture.Listener listener = new NullListener();

    String userId;
    String imagePath;
    String description;
    String date;
    String imageId;
    String time;
    String action;

    public UpdateSavedPictureApiImp(String userId, String imagePath, String description, String date,
                                    String imageId, String time, String action) {
        this.userId = userId;
        this.imagePath = imagePath;
        this.description = description;
        this.date = date;
        this.imageId = imageId;
        this.time = time;
        this.action = action;
    }

    @Override
    public void updateSavedPicture(Listener listener) {
        if (listener != null) {
            this.listener = listener;
        }
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL_DEFAULT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        UpdateSavedPictureService service = retrofit.create(UpdateSavedPictureService.class);
        service.updateSavedPicture(userId, imagePath, description, date, imageId, time, action).enqueue(this);
    }

    @Override
    public void onResponse(Call<UpdateSavedPictureResponse> call, Response<UpdateSavedPictureResponse> response) {
        if (response.isSuccessful()) {
            if (response.body().isSuccessful()) {
                listener.onSuccess();
            } else {
                listener.onFailure();
            }
        } else {
            listener.onError(new Exception("Body Failed"));
        }
    }

    @Override
    public void onFailure(Call<UpdateSavedPictureResponse> call, Throwable t) {
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

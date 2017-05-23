package com.albaradocompany.jose.proyect_meme_clean.datasource.api;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.UploadPictureApiReponse;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit.UploadPictureService;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.usecase.UploadPicture;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.sql.Date;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jose on 23/05/2017.
 */

public class UploadPictureApiImp implements UploadPicture, Callback<UploadPictureApiReponse> {
    String userId;
    String imagePath;
    String description;
    String imageId;
    String time;
    String date;

    UploadPicture.Listener listener = new NullListener();

    public UploadPictureApiImp(String userId, String imagePath, String description, String imageId, String time, String date) {
        this.userId = userId;
        this.imagePath = imagePath;
        this.description = description;
        this.imageId = imageId;
        this.time = time;
        this.date = date;
    }

    @Override
    public void uploadPicture(Listener listener) {
        if (listener != null) {
            this.listener = listener;
        }
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_DEFAULT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        UploadPictureService service = retrofit.create(UploadPictureService.class);
        service.uploadPicture(userId, imagePath, description, imageId, time, Date.valueOf(date)).enqueue(this);
    }

    @Override
    public void onResponse(Call<UploadPictureApiReponse> call, Response<UploadPictureApiReponse> response) {
        if (response.isSuccessful()) {
            if (response.body().isSuccessful())
                listener.onSuccess();
            else
                listener.onFailure();
        } else {
            listener.onError(new Exception("Body Failed"));
        }
    }

    @Override
    public void onFailure(Call<UploadPictureApiReponse> call, Throwable t) {
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

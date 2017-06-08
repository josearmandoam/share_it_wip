package com.albaradocompany.jose.proyect_meme_clean.datasource.api;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.SendNotificationApiResponse;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit.SendNotificationService;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.usecase.SendNotification;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jose on 29/05/2017.
 */

public class SendNotificationApiImp implements SendNotification, Callback<SendNotificationApiResponse> {

    String to;
    String title;
    String message;
    String time;
    String sender;

    public SendNotificationApiImp(String to, String title, String message, String time, String sender) {
        this.to = to;
        this.title = title;
        this.message = message;
        this.time = time;
        this.sender = sender;
    }

    SendNotification.Listener listener = new NullListener();

    @Override
    public void sendNotification(Listener listener) {
        if (listener != null) {
            this.listener = listener;
        }
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_DEFAULT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        SendNotificationService service = retrofit.create(SendNotificationService.class);
        service.sendNotification(to, message, title, time, sender).enqueue(this);
    }

    @Override
    public void onResponse(Call<SendNotificationApiResponse> call, Response<SendNotificationApiResponse> response) {
        if (response.isSuccessful())
            if (response.body().isSuccessful())
                listener.onSuccess();
            else
                listener.onFailure();
        else
            listener.onError(new Exception("Body Failed"));
    }

    @Override
    public void onFailure(Call<SendNotificationApiResponse> call, Throwable t) {
        if (t instanceof IOException)
            listener.onNoInternetAvailable();
        else
            listener.onError(new Exception(t));
    }

    private class NullListener implements Listener {
        @Override
        public void onSuccess() {
        }

        @Override
        public void onFailure() {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onNoInternetAvailable() {

        }
    }
}

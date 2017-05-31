package com.albaradocompany.jose.proyect_meme_clean.datasource.api;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.RegisterTokenApiResponse;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit.RegisterTokenService;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.usecase.RegisterToken;
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

public class RegisterTokenApiImp implements RegisterToken, Callback<RegisterTokenApiResponse> {

    String userId;
    String token;
    RegisterToken.Listener listener = new NullListener();

    public RegisterTokenApiImp(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    @Override
    public void registerToken(Listener listener) {
        if (listener != null) {
            this.listener = listener;
        }
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_DEFAULT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        RegisterTokenService service = retrofit.create(RegisterTokenService.class);
        service.registerToken(userId, token).enqueue(this);
    }

    @Override
    public void onResponse(Call<RegisterTokenApiResponse> call, Response<RegisterTokenApiResponse> response) {
        if (response.isSuccessful())
            if (response.body().isSuccessful())
                listener.onSuccess();
            else
                listener.onFailure();
        else
            listener.onError(new Exception("Body failed"));
    }

    @Override
    public void onFailure(Call<RegisterTokenApiResponse> call, Throwable t) {
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
        public void onNoInternetAvailable() {

        }

        @Override
        public void onError(Exception e) {

        }
    }
}

package com.albaradocompany.jose.proyect_meme_clean.datasource.api;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.UserApiResponse;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit.UserByTokenService;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.User;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetUserByToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jose on 31/05/2017.
 */

public class UserByTokenImp implements GetUserByToken, Callback<UserApiResponse> {
    String token;

    public UserByTokenImp(String token) {
        this.token = token;
    }

    GetUserByToken.Listener listener = new NullListener();

    @Override
    public void getUserByTOken(Listener listener) {
        if (listener != null) {
            this.listener = listener;
        }
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_DEFAULT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        UserByTokenService service = retrofit.create(UserByTokenService.class);
        service.getUserByToken(token).enqueue(this);
    }

    @Override
    public void onResponse(Call<UserApiResponse> call, Response<UserApiResponse> response) {
        if (response.isSuccessful())
            if (response.body().parseUsers().size() > 0)
                listener.onSuccess(response.body().parseUsers().get(0));
            else
                listener.onFailure();
        else
            listener.onError(new Exception("Body failed"));
    }

    @Override
    public void onFailure(Call<UserApiResponse> call, Throwable t) {
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
        public void onSuccess(User user) {

        }

        @Override
        public void onFailure() {

        }
    }
}

package com.albaradocompany.jose.proyect_meme_clean.datasource.api;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.UserApiResponse;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit.UserByUsernameService;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.User;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetUserByUsername;
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
 * Created by jose on 25/05/2017.
 */

public class UserByUsernameApiImp implements GetUserByUsername, Callback<UserApiResponse> {
    String username;

    GetUserByUsername.Listener listener = new NullListener();

    public UserByUsernameApiImp(String username) {
        this.username = username;
    }

    @Override
    public void getUser(Listener listener) {
        if (listener != null) {
            this.listener = listener;
        }
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_DEFAULT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        UserByUsernameService service = retrofit.create(UserByUsernameService.class);
        service.getUser(username).enqueue(this);
    }

    @Override
    public void onResponse(Call<UserApiResponse> call, Response<UserApiResponse> response) {
        if (response.isSuccessful())
            listener.onSuccess(response.body().parseUsers());
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
        public void onSuccess(List<User> users) {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onNoInternetAvailable() {

        }
    }
}

package com.albaradocompany.jose.proyect_meme_clean.datasource.api;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.LoginApiEntry;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit.LoginService;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.usecase.GetLogin;
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
 * Created by jose on 17/04/2017.
 */

public class LoginApiImp implements GetLogin, Callback<LoginApiResponse> {

    private String username;
    Listener listener = new NullListener();

    public LoginApiImp(String username) {
        this.username = username;
    }

    @Override
    public LoginApiEntry get() {
        throw new IllegalStateException("Not implemented yet");
    }

    @Override
    public void getLogin(Listener listener) {
        if (listener != null) {
            this.listener = listener;
        }
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_DEFAULT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        LoginService response = retrofit.create(LoginService.class);
        response.getLoginResponsePOST(username).enqueue(this);
    }

    @Override
    public void onResponse(Call<LoginApiResponse> call, Response<LoginApiResponse> response) {

        if (response.isSuccessful()) {
            LoginApiResponse userdata = response.body();
            listener.onLoginReceived(userdata.parseLogins());
        } else {
            try {
                listener.onError(new Exception(response.errorBody().string()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onFailure(Call<LoginApiResponse> call, Throwable t) {
        listener.onNoInternetAvailable();
    }

    private class NullListener implements Listener {

        @Override
        public void onLoginReceived(List<Login> login) {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onNoInternetAvailable() {

        }

        @Override
        public void onUserNotFound() {

        }
    }
}

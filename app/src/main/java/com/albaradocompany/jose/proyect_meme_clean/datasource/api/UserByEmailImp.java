package com.albaradocompany.jose.proyect_meme_clean.datasource.api;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit.GetUserByEmailService;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.usecase.GetUserByEmail;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jose on 27/04/2017.
 */

public class UserByEmailImp implements GetUserByEmail, Callback<LoginApiResponse> {
    String email;
    GetUserByEmail.Listener listener = new NullListener();

    public UserByEmailImp(String email) {
        this.email = email;
    }

    @Override
    public void getUserByEmail(Listener listener) {
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
        GetUserByEmailService service = retrofit.create(GetUserByEmailService.class);
        service.getUserById(email).enqueue(this);
    }

    @Override
    public void onResponse(Call<LoginApiResponse> call, Response<LoginApiResponse> response) {
        if (response.isSuccessful()) {
            if (response.body().parseLogins().size() > 0) {
                listener.onUserReceived(response.body().parseLogins().get(0));
            } else {
                listener.onFailure();
            }
        } else {
            listener.onError(new Exception("Error body malformed"));
        }
    }

    @Override
    public void onFailure(Call<LoginApiResponse> call, Throwable t) {
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
        public void onUserReceived(Login user) {

        }

        @Override
        public void onFailure() {

        }
    }
}

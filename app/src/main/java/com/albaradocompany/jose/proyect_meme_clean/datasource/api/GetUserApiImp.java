package com.albaradocompany.jose.proyect_meme_clean.datasource.api;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.UserApiResponse;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit.GetUserService;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.User;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetUser;
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

public class GetUserApiImp implements GetUser, Callback<UserApiResponse> {
    String userId;

    public GetUserApiImp(String userId) {
        this.userId = userId;
    }

    GetUser.Listener listener = new NullListener();

    @Override
    public void getUser(Listener listener) {
        if (listener != null) {
            this.listener = listener;
        }
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL_DEFAULT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        GetUserService service = retrofit.create(GetUserService.class);
        service.getUser(userId).enqueue(this);
    }

    @Override
    public void onResponse(Call<UserApiResponse> call, Response<UserApiResponse> response) {
        if (response.isSuccessful()) {
            if (response.body().parseUsers().size() > 0)
                listener.onUserReceived(response.body().parseUsers().get(0));
        } else {
            listener.onError(new Exception("Body failed"));
        }
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
        public void onUserReceived(User user) {

        }
    }
}

package com.albaradocompany.jose.proyect_meme_clean.datasource.api;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit.UpdateUserService;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdateUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jose on 06/05/2017.
 */

public class UpdateUserImp implements UpdateUser, Callback<GenericApiResponse> {
    UpdateUser.Listener listener = new NullListener();
    String userId;
    String name;
    String lastName;
    String email;
    String username;
    String profile;
    String background;
    String description;

    public UpdateUserImp(String userId, String name, String lastName, String email, String username,
                         String profile, String background, String description) {
        this.userId = userId;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.profile = profile;
        this.background = background;
        this.description = description;
    }

    @Override
    public void updateUser(Listener listener) {
        if (listener != null) {
            this.listener = listener;
        }
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL_DEFAULT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        UpdateUserService service = retrofit.create(UpdateUserService.class);
        service.updateUser(userId, name, lastName, email, description, username, profile, background).enqueue(this);
    }

    @Override
    public void onResponse(Call<GenericApiResponse> call, Response<GenericApiResponse> response) {
        if (response.isSuccessful()) {
            if (response.body().parseResponse().getCode().equals("1")) {
                listener.onUpdateSuccess();
            } else {
                listener.onUpdateFailure();
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
        public void onUpdateSuccess() {

        }

        @Override
        public void onUpdateFailure() {

        }
    }
}

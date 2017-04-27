package com.albaradocompany.jose.proyect_meme_clean.datasource.api;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit.UpdatePasswordService;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.usecase.UpdatePassword;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jose on 28/04/2017.
 */

public class UpdatePasswordImp implements UpdatePassword, Callback<GenericApiResponse> {
    UpdatePassword.Listener listener = new NullListener();
    String newPassword;
    String idUser;

    public UpdatePasswordImp(String newPassword, String idUser) {
        this.newPassword = newPassword;
        this.idUser = idUser;
    }

    @Override
    public void updatePassword(Listener listener) {
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
        UpdatePasswordService service = retrofit.create(UpdatePasswordService.class);
        service.updatePassword(idUser, newPassword).enqueue(this);
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
            listener.onError(new Exception("Body malformed"));
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

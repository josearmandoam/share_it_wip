package com.albaradocompany.jose.proyect_meme_clean.datasource.api;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit.UpdatePermissionService;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdatePermissions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jose on 25/05/2017.
 */

public class UpdatePermissionsApiImp implements UpdatePermissions, Callback<String> {
    UpdatePermissions.Listener listener = new NullListener();

    @Override
    public void updatePermissions(Listener listener) {
        if (listener != null)
            this.listener = listener;
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_DEFAULT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        UpdatePermissionService service = retrofit.create(UpdatePermissionService.class);
        service.UpdatePermissions().enqueue(this);
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful())
            listener.onSuccess();
        else
            listener.onError(new Exception("Body Failed"));
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
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
    }
}

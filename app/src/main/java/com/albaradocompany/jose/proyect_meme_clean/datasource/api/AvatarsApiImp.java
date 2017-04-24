package com.albaradocompany.jose.proyect_meme_clean.datasource.api;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit.AvatarService;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit.LogJsonInterceptor;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Avatar;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.usecase.GetAvatars;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jose on 22/04/2017.
 */

public class AvatarsApiImp implements GetAvatars, Callback<AvatarApiResponse> {
    Listener listener = new NullListener();

    public AvatarsApiImp() {
    }

    @Override
    public void getAvatars(Listener listener) {
        if (listener != null) {
            this.listener = listener;
        }
        OkHttpClient.Builder httpclient = new OkHttpClient.Builder();
        httpclient.addInterceptor(new LogJsonInterceptor());
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_PHOTOS_AVATAR)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpclient.build())
                .build();
        AvatarService avatarService = retrofit.create(AvatarService.class);
        avatarService.getAvatars().enqueue(this);
    }

    @Override
    public void onResponse(Call<AvatarApiResponse> call, Response<AvatarApiResponse> response) {
        if (response.isSuccessful()) {
            listener.onAvatarsReceived(response.body().parseAvatars());
        } else {
            listener.onError(new Exception("An error ocurred"));
        }
    }

    @Override
    public void onFailure(Call<AvatarApiResponse> call, Throwable t) {
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
        public void onAvatarsReceived(List<Avatar> avatarList) {

        }
    }
}

package com.albaradocompany.jose.proyect_meme_clean.datasource.api;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.LikesApiResponse;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit.LikesService;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Like;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetLikes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jose on 14/05/2017.
 */

public class LikesApiImp implements GetLikes, Callback<LikesApiResponse> {
    String imageId;

    GetLikes.Listener listener = new NullListener();

    public LikesApiImp(String imageId) {
        this.imageId = imageId;
    }

    @Override
    public void getLikes(Listener listener) {
        if (listener != null) {
            this.listener = listener;
        }
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_DEFAULT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        LikesService service = retrofit.create(LikesService.class);
        service.getLikes(imageId).enqueue(this);
    }

    @Override
    public void onResponse(Call<LikesApiResponse> call, Response<LikesApiResponse> response) {
        if (response.isSuccessful()) {
            listener.onLikesReceived(response.body().parseLikes());
        } else {
            listener.onError(new Exception("Body Failed"));
        }
    }

    @Override
    public void onFailure(Call<LikesApiResponse> call, Throwable t) {
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
        public void onLikesReceived(List<Like> likes) {

        }
    }
}

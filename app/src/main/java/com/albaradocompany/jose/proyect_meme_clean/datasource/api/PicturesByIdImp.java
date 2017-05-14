package com.albaradocompany.jose.proyect_meme_clean.datasource.api;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.PictureResponse;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit.PicturesService;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetPicturesById;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jose on 05/05/2017.
 */

public class PicturesByIdImp implements GetPicturesById, Callback<PictureResponse> {
    String userId;
    GetPicturesById.Listener listener = new NullListener();

    public PicturesByIdImp(String userId) {
        this.userId = userId;
    }

    @Override
    public void getPictures(Listener listener) {
        if (listener!=null){
            this.listener = listener;
        }
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_DEFAULT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        PicturesService service = retrofit.create(PicturesService.class);
        service.getPictures(userId).enqueue(this);
    }

    @Override
    public void onResponse(Call<PictureResponse> call, Response<PictureResponse> response) {
        if (response.isSuccessful()){
            listener.onPicturesReceived(response.body().parsePictures());
        }else{
            listener.onError(new Exception("Body failed"));
        }
    }

    @Override
    public void onFailure(Call<PictureResponse> call, Throwable t) {
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
        public void onPicturesReceived(List<Picture> pictures) {

        }
    }
}

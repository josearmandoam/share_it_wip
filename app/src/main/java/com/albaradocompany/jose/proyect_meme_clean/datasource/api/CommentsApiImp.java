package com.albaradocompany.jose.proyect_meme_clean.datasource.api;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.CommentsApiResponse;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit.CommentsService;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetComments;
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

public class CommentsApiImp implements GetComments, Callback<CommentsApiResponse> {
    String imageId;
    GetComments.Listener listener = new NullListener();

    public CommentsApiImp(String imageId) {
        this.imageId = imageId;
    }

    @Override
    public void getComments(Listener listener) {
        if (listener != null) {
            this.listener = listener;
        }
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_DEFAULT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        CommentsService service = retrofit.create(CommentsService.class);
        service.getComments(imageId).enqueue(this);
    }

    @Override
    public void onResponse(Call<CommentsApiResponse> call, Response<CommentsApiResponse> response) {
        if (response.isSuccessful()) {
            listener.onCommentesReceived(response.body().parseComments());
        } else {
            listener.onError(new Exception("Body failed"));
        }
    }

    @Override
    public void onFailure(Call<CommentsApiResponse> call, Throwable t) {
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
        public void onCommentesReceived(List<Comment> comments) {

        }
    }
}

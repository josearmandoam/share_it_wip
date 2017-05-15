package com.albaradocompany.jose.proyect_meme_clean.datasource.api;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit.UpdateCommentService;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdateComment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jose on 15/05/2017.
 */

public class UpdateCommentApiImp implements UpdateComment, Callback<GenericApiResponse> {

    String userId;
    String imageId;
    String commentId;
    String comment;
    String name;
    String lastname;
    String profile;
    String time;
    String date;
    String action;

    public UpdateCommentApiImp(String userId, String imageId, String commentId, String comment,
                               String name, String lastname, String profile, String time, String date,
                               String action) {
        this.userId = userId;
        this.imageId = imageId;
        this.commentId = commentId;
        this.comment = comment;
        this.name = name;
        this.lastname = lastname;
        this.profile = profile;
        this.time = time;
        this.date = date;
        this.action = action;
    }

    UpdateComment.Listener listener = new NullListener();

    @Override
    public void updateComments(Listener listener) {
        if (listener != null) {
            this.listener = listener;
        }
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL_DEFAULT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        UpdateCommentService service = retrofit.create(UpdateCommentService.class);
        service.updateComment(userId, imageId, commentId, comment, name + " " + lastname, profile, time, date, action).enqueue(this);
    }

    @Override
    public void onResponse(Call<GenericApiResponse> call, Response<GenericApiResponse> response) {
        if (response.isSuccessful()) {
            if (response.body().parseResponse().getCode().equals("1")) {
                listener.onSuccess();
            } else {
                listener.onFailure();
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
        public void onSuccess() {

        }

        @Override
        public void onFailure() {

        }
    }
}

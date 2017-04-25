package com.albaradocompany.jose.proyect_meme_clean.datasource.api;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.QuestionsApiResponse;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit.QuestionsService;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Question;
import com.albaradocompany.jose.proyect_meme_clean.usecase.GetQuestions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jose on 25/04/2017.
 */

public class QuestionsApiImp implements GetQuestions, Callback<QuestionsApiResponse> {
    GetQuestions.Listener listener = new NullListener();

    public QuestionsApiImp() {
    }

    @Override
    public void getQuestions(Listener listener) {
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
        QuestionsService service = retrofit.create(QuestionsService.class);
        service.getQuestions().enqueue(this);
    }

    @Override
    public void onResponse(Call<QuestionsApiResponse> call, Response<QuestionsApiResponse> response) {
        if (response.isSuccessful()) {
            listener.onQuestionsReceived(response.body().parseQuestions());
        } else {
            listener.onError(new Exception("An error ocurred"));
        }
    }

    @Override
    public void onFailure(Call<QuestionsApiResponse> call, Throwable t) {
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
        public void onQuestionsReceived(List<Question> questions) {

        }
    }
}

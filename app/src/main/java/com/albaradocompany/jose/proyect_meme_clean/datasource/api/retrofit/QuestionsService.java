package com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.QuestionsApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by jose on 25/04/2017.
 */

public interface QuestionsService {
    @GET("preguntas.php")
    Call<QuestionsApiResponse> getQuestions();
}

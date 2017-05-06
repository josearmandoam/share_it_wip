package com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.PictureResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by jose on 05/05/2017.
 */

public interface PicturesSavedService {
    @FormUrlEncoded
    @POST("getPicturesSavedById.php")
    Call<PictureResponse> getPicturesSaved(@Field("userId") String userId);
}

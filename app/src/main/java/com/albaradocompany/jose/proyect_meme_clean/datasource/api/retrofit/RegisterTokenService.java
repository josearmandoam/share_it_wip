package com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.RegisterTokenApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by jose on 29/05/2017.
 */

public interface RegisterTokenService {
    @FormUrlEncoded
    @POST("registerToken.php")
    Call<RegisterTokenApiResponse> registerToken(@Field("idUser") String userId, @Field("token") String token);
}

package com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.UserApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by jose on 31/05/2017.
 */

public interface UserByTokenService {
    @FormUrlEncoded
    @POST("requestUserByToken.php")
    Call<UserApiResponse> getUserByToken(@Field("token") String token);
}

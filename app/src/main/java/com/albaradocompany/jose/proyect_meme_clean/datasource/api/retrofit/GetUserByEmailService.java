package com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.LoginApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by jose on 27/04/2017.
 */

public interface GetUserByEmailService {
    @FormUrlEncoded
    @POST("getUserByEmail.php")
    Call<LoginApiResponse> getUserById(@Field("email") String email);
}

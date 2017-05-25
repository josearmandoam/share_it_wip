package com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.UserApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by jose on 25/05/2017.
 */

public interface UserByUsernameService {
    @FormUrlEncoded
    @POST("requestUserByUsername.php")
    Call<UserApiResponse> getUser(@Field("username") String username);
}

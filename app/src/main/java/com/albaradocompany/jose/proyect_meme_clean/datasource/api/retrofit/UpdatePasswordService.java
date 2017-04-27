package com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.GenericApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by jose on 28/04/2017.
 */

public interface UpdatePasswordService {
    @FormUrlEncoded
    @POST("updatePassword.php")
    Call<GenericApiResponse> updatePassword(@Field("idUser") String idUser, @Field("password") String password);
}

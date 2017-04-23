package com.albaradocompany.jose.proyect_meme_clean.datasource.retrofit;

import com.albaradocompany.jose.proyect_meme_clean.datasource.RegistrationApiResponse;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by jose on 23/04/2017.
 */

public interface InsertUserService {
    @FormUrlEncoded
    @POST
    Call<RegistrationApiResponse> getRegistrationResult(@Body Login user);
}

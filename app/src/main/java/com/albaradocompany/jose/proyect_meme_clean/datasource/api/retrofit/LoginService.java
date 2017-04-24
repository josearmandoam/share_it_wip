package com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.LoginApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by jose on 16/04/2017.
 */

public interface LoginService {
    @GET("login.php")
    Call<LoginApiResponse> getLoginResponseGET(@Query("username") String username);

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginApiResponse> getLoginResponsePOST(@Field("username") String username);

}

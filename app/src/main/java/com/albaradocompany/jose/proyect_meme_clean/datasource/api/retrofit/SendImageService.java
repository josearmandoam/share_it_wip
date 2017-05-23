package com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by jose on 27/04/2017.
 */

public interface SendImageService {
    @FormUrlEncoded
    @POST("uploadPicture.php")
    Call<String> sendImage(@Field("idUser") String idUser, @Field("image") byte[] image);
}

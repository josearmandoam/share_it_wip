package com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.UploadPictureApiReponse;

import java.sql.Date;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by jose on 23/05/2017.
 */

public interface UploadPictureService {
    @FormUrlEncoded
    @POST("uploadPicture.php")
    Call<UploadPictureApiReponse> uploadPicture(@Field("userId") String userId, @Field("imagePath") String imagePath,
                                                @Field("description") String description, @Field("imageId") String imageId,
                                                @Field("time") String time, @Field("date") Date date);
}

package com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.UpdateSavedPictureResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by jose on 16/05/2017.
 */

public interface UpdateSavedPictureService {
    @FormUrlEncoded
    @POST("updateSavePicture.php")
    Call<UpdateSavedPictureResponse> updateSavedPicture(@Field("userId") String userId, @Field("imagePath") String imagePath,
                                                        @Field("description") String description, @Field("fechaSubida") String date,
                                                        @Field("imageId") String imageId, @Field("time") String time, @Field("action") String action);
}

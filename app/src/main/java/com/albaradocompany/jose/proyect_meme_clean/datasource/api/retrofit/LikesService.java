package com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.LikesApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by jose on 14/05/2017.
 */

public interface LikesService {
    @FormUrlEncoded
    @POST("getLikes.php")
    Call<LikesApiResponse> getLikes(@Field("imageId") String imageId);
}

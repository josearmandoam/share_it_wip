package com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.GenericApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by jose on 15/05/2017.
 */

public interface UpdateLikeService {
    @FormUrlEncoded
    @POST("updateUserLike.php")
    Call<GenericApiResponse> updateLike(@Field("userId") String userId, @Field("imageId") String imageId,
                                        @Field("profile") String profile, @Field("username") String username,
                                        @Field("likeId") String likeId, @Field("action") String action);
}

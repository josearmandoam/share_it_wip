package com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.GenericApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by jose on 15/05/2017.
 */

public interface UpdateCommentService {
    @FormUrlEncoded
    @POST("updateComment.php")
    Call<GenericApiResponse> updateComment(@Field("userId") String userId, @Field("imageId") String imageId,
                                           @Field("commentId") String commentId, @Field("comment") String comment,
                                           @Field("username") String username, @Field("profile") String profile, @Field("time") String time,
                                           @Field("date") String date, @Field("action") String action);
}

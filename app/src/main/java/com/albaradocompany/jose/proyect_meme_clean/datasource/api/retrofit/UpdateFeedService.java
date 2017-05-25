package com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.UpdateFeedApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by jose on 25/05/2017.
 */

public interface UpdateFeedService {
    @FormUrlEncoded
    @POST("updateFeed.php")
    Call<UpdateFeedApiResponse> updateFeed(@Field("userId") String userId, @Field("xUserId") String xUserId,
                                           @Field("feedId") String feedId, @Field("xProfile") String xProfile,
                                           @Field("xUsername") String xUsername, @Field("action") String action);
}

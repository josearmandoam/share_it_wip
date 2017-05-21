package com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.FeedApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by jose on 20/05/2017.
 */

public interface FeedService {
    @FormUrlEncoded
    @POST("requestFeed.php")
    Call<FeedApiResponse> getFeed(@Field("userId") String userId);
}

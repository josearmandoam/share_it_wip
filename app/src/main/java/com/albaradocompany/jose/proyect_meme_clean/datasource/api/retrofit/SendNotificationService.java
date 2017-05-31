package com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.SendNotificationApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by jose on 29/05/2017.
 */

public interface SendNotificationService {
    @FormUrlEncoded
    @POST("sendNotification.php")
    Call<SendNotificationApiResponse> sendNotification(@Field("userId") String userId, @Field("message") String message,
                                                       @Field("title") String title);
}

package com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.GenericApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by jose on 06/05/2017.
 */

public interface UpdateUserService {
    @FormUrlEncoded
    @POST("updateUser.php")
    Call<GenericApiResponse> updateUser(@Field("idUser") String userId, @Field("name") String name, @Field("lastName") String lastName,
                                        @Field("email") String email, @Field("description") String description, @Field("username") String username,
                                        @Field("profile") String profile, @Field("background") String background);
}

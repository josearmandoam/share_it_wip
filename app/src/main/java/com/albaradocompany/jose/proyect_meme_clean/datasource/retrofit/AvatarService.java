package com.albaradocompany.jose.proyect_meme_clean.datasource.retrofit;

import com.albaradocompany.jose.proyect_meme_clean.datasource.AvatarApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by jose on 22/04/2017.
 */

public interface AvatarService {
    @GET("avatar.php")
    Call<AvatarApiResponse> getAvatars();
}

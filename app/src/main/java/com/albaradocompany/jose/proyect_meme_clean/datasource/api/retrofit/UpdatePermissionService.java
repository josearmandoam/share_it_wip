package com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by jose on 25/05/2017.
 */

public interface UpdatePermissionService {
    @GET("armadolectura.php")
    Call<String> UpdatePermissions();
}

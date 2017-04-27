package com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.GenericApiResponse;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by jose on 23/04/2017.
 */

public interface RegistrationService {
    @FormUrlEncoded
    @POST("insertUser.php")
    Call<GenericApiResponse> getRegistrationResult(@Field("idUser") String idUser,
                                                   @Field("username") String username,
                                                   @Field("password") String password,
                                                   @Field("preguntaSeguridad") String preguntaSeguridad,
                                                   @Field("respuestaSeguridad") String respuestaSeguridad,
                                                   @Field("respuestaSeguridad2") String respuestaSeguridad2,
                                                   @Field("email") String email,
                                                   @Field("fechaNacimiento") String fechaNacimiento,
                                                   @Field("nombre") String nombre,
                                                   @Field("apellidos") String apellidos,
                                                   @Field("imagePath") String imagePath,
                                                   @Field("imageBlob") byte[] imageBlob);

    @POST("insertUser.php")
    Call<GenericApiResponse> getRegistrationResult(@Body Login login);
}

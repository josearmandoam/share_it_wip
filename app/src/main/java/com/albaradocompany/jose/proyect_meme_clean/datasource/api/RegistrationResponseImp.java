package com.albaradocompany.jose.proyect_meme_clean.datasource.api;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.retrofit.RegistrationService;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.GenericResponse;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetRegistrationResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jose on 24/04/2017.
 */

public class RegistrationResponseImp implements GetRegistrationResponse, Callback<GenericApiResponse> {
    Login user;
    Listener listener = new NullListener();

    public RegistrationResponseImp(Login user) {
        this.user = user;
    }

    @Override
    public void getRegistrationResponse(Listener listener) {
        if (listener != null) {
            this.listener = listener;
        }
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_DEFAULT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        RegistrationService service = retrofit.create(RegistrationService.class);
        service.getRegistrationResult(user.getIdUser(), user.getUsername(), user.getPassword(),
                user.getPreguntaSeguridad(), user.getRespuestaSeguridad(), user.getRespuestaSeguridad2(),
                user.getEmail(), user.getFechaNacimiento(), user.getNombre(), user.getApellidos(), user.getImagePath())
                .enqueue(this);
//        service.getRegistrationResult(user).enqueue(this);
    }

    @Override
    public void onResponse(Call<GenericApiResponse> call, Response<GenericApiResponse> response) {
        if (response.isSuccessful()) {
            GenericResponse registrationResponse = response.body().parseResponse();
            if (registrationResponse.getCode().equals("1")) {
                listener.onRegistrationSuccess(registrationResponse);
            } else {
                listener.onRegistrationFailed();
            }
        } else {
            listener.onError(new Exception("body failed"));
        }
    }

    @Override
    public void onFailure(Call<GenericApiResponse> call, Throwable t) {
        listener.onNoInternetAvailable();
    }

    private class NullListener implements Listener {
        @Override
        public void onNoInternetAvailable() {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onRegistrationSuccess(GenericResponse response) {

        }

        @Override
        public void onRegistrationFailed() {

        }
    }
}

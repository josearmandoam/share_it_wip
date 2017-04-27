package com.albaradocompany.jose.proyect_meme_clean.usecase;

import com.albaradocompany.jose.proyect_meme_clean.global.model.GenericResponse;

/**
 * Created by jose on 24/04/2017.
 */

public interface GetRegistrationResponse {
    void getRegistrationResponse(Listener listener);

    interface Listener {
        void onNoInternetAvailable();

        void onError(Exception e);

        void onRegistrationSuccess(GenericResponse response);

        void onRegistrationFailed();
    }
}

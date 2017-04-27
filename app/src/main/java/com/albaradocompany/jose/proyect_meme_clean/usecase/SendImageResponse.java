package com.albaradocompany.jose.proyect_meme_clean.usecase;

/**
 * Created by jose on 27/04/2017.
 */

public interface SendImageResponse {
    void getResponse(Listener listener);

    interface Listener {
        void onNoInternetAvailable();

        void onError(Exception e);

        void onSucces();

        void onFailure();
    }
}

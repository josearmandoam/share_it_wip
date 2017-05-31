package com.albaradocompany.jose.proyect_meme_clean.usecase;

/**
 * Created by jose on 29/05/2017.
 */

public interface SendNotification {
    void sendNotification(Listener listener);

    interface Listener {
        void onSuccess();

        void onFailure();

        void onError(Exception e);

        void onNoInternetAvailable();
    }
}

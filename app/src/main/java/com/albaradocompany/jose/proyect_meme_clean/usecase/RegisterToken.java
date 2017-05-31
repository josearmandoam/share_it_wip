package com.albaradocompany.jose.proyect_meme_clean.usecase;

/**
 * Created by jose on 29/05/2017.
 */

public interface RegisterToken {
    void registerToken(Listener listener);

    interface Listener {
        void onSuccess();

        void onFailure();

        void onNoInternetAvailable();

        void onError(Exception e);
    }
}

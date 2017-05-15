package com.albaradocompany.jose.proyect_meme_clean.usecase.update;

/**
 * Created by jose on 15/05/2017.
 */

public interface UpdateLike {
    void updateLike(Listener listener);

    interface Listener {
        void onNoInternetAvailable();

        void onError(Exception e);

        void onSuccess();

        void onFailure();
    }
}

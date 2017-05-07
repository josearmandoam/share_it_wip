package com.albaradocompany.jose.proyect_meme_clean.usecase.update;

/**
 * Created by jose on 28/04/2017.
 */

public interface UpdatePassword {
    void updatePassword(Listener listener);

    interface Listener {
        void onNoInternetAvailable();

        void onError(Exception e);

        void onUpdateSuccess();

        void onUpdateFailure();
    }
}

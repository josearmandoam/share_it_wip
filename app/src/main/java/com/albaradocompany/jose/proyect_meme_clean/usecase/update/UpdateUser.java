package com.albaradocompany.jose.proyect_meme_clean.usecase.update;

/**
 * Created by jose on 06/05/2017.
 */

public interface UpdateUser {
    void updateUser(Listener listener);

    interface Listener {
        void onNoInternetAvailable();

        void onError(Exception e);

        void onUpdateSuccess();

        void onUpdateFailure();
    }
}

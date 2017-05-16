package com.albaradocompany.jose.proyect_meme_clean.usecase.update;

/**
 * Created by jose on 16/05/2017.
 */

public interface UpdateSavedPicture {
    void updateSavedPicture(Listener listener);

    interface Listener {
        void onNoInternetAvailable();

        void onError(Exception e);

        void onSuccess();

        void onFailure();
    }
}

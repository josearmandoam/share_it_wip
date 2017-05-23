package com.albaradocompany.jose.proyect_meme_clean.usecase;

/**
 * Created by jose on 23/05/2017.
 */

public interface UploadPicture {
    void uploadPicture(Listener listener);

    interface Listener {
        void onNoInternetAvailable();

        void onError(Exception e);

        void onSuccess();

        void onFailure();
    }
}

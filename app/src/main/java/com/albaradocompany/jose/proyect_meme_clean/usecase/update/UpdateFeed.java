package com.albaradocompany.jose.proyect_meme_clean.usecase.update;

/**
 * Created by jose on 25/05/2017.
 */

public interface UpdateFeed {
    void updateFedd(Listener listener);

    interface Listener {
        void onNoInternetAvailable();

        void onError(Exception e);

        void onSuccess();

        void onFailure();
    }
}

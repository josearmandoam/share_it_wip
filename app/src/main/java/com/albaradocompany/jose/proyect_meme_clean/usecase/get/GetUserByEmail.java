package com.albaradocompany.jose.proyect_meme_clean.usecase.get;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;

/**
 * Created by jose on 27/04/2017.
 */

public interface GetUserByEmail {
    void getUserByEmail(Listener listener);

    interface Listener {
        void onNoInternetAvailable();

        void onError(Exception e);

        void onUserReceived(Login user);

        void onFailure();
    }
}

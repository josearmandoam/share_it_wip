package com.albaradocompany.jose.proyect_meme_clean.usecase.get;

import com.albaradocompany.jose.proyect_meme_clean.global.model.User;

/**
 * Created by jose on 16/05/2017.
 */

public interface GetUser {
    void getUser(Listener listener);

    interface Listener {
        void onNoInternetAvailable();

        void onError(Exception e);

        void onUserReceived(User user);
    }
}

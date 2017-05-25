package com.albaradocompany.jose.proyect_meme_clean.usecase.get;

import com.albaradocompany.jose.proyect_meme_clean.global.model.User;

import java.util.List;

/**
 * Created by jose on 25/05/2017.
 */

public interface GetUserByUsername {
    void getUser(final Listener listener);

    interface Listener {
        void onSuccess(final List<User> users);

        void onError(Exception e);

        void onNoInternetAvailable();
    }
}
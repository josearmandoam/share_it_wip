package com.albaradocompany.jose.proyect_meme_clean.usecase.get;

import com.albaradocompany.jose.proyect_meme_clean.global.model.User;

/**
 * Created by jose on 31/05/2017.
 */

public interface GetUserByToken {
    void getUserByTOken(Listener listener);

    interface Listener {
        void onNoInternetAvailable();

        void onError(Exception e);

        void onSuccess(User user);

        void onFailure();
    }
}

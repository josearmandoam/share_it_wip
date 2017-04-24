package com.albaradocompany.jose.proyect_meme_clean.usecase;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.LoginApiEntry;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;

import java.util.List;

/**
 * Created by jose on 17/04/2017.
 */

public interface GetLogin {
    LoginApiEntry get();

    void getLogin(final Listener listener);

    interface Listener {
        void onLoginReceived(List<Login> login);

        void onError(Exception e);

        void onNoInternetAvailable();

        void onUserNotFound();
    }
}

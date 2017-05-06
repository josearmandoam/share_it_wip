package com.albaradocompany.jose.proyect_meme_clean.usecase;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;

/**
 * Created by jose on 28/04/2017.
 */

public interface UserShared {
    boolean isLogged();

    void cleanUserLogged();

    void saveUserLogged();

    Login getUser();

    void saveUser(Login user);

    String getBackground();

    String getProfile();

    void saveBackground(String background);

    boolean isSelectedBackground();

    void deleteImageBackground();

    void saveUserID(String id);

    String getUserID();
}

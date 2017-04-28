package com.albaradocompany.jose.proyect_meme_clean.usecase;

import android.widget.ImageView;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;

/**
 * Created by jose on 28/04/2017.
 */

public interface UserShared {
    boolean isLogged();
    void cleanUserLogged();
    Login getUser(ImageView image);
    void saveUserLogged();
}

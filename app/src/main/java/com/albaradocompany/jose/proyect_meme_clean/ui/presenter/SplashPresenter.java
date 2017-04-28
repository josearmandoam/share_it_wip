package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;
import android.widget.Toast;

import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSplash;

/**
 * Created by jose on 18/04/2017.
 */

public class SplashPresenter extends AbsSplash {
    Context context;
    boolean userLogged;

    public SplashPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void initialize() {
        view.sleep();
        if (userLogged) {
            navigator.navigateToMainPage();
        } else {
            Toast.makeText(context, "LOGGED", Toast.LENGTH_SHORT).show();
            navigator.navigateToLoginActivity();
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void userLogged(boolean userLogged) {
        this.userLogged = userLogged;
    }
}

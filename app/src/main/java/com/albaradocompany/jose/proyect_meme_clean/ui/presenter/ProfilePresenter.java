package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsProfilePresenter;

/**
 * Created by jose on 04/05/2017.
 */

public class ProfilePresenter extends AbsProfilePresenter {

    public ProfilePresenter() {

    }

    @Override
    public void initialize() {
        view.showBackground();
        view.showName();
        view.showProfile();
        view.showUsername();
        view.showDescription();
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
    public void onLogOutClicked() {
        navigator.navigateToLogin();
    }

    @Override
    public void onBackClicked() {

    }
}

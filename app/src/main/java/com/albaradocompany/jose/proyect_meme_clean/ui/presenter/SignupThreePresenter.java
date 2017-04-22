package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSignupThree;

/**
 * Created by jose on 19/04/2017.
 */

public class SignupThreePresenter extends AbsSignupThree {
    Context context;

    public SignupThreePresenter(Context context) {
        this.context = context;
    }

    @Override
    public void initialize() {

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
    public void onBackPressed() {
        view.hideSignupThree();
    }

    @Override
    public void onMenuPressed() {
        view.showMenu();
    }

    @Override
    public void onConfirmClicked() {
        view.showLoading();
    }

}

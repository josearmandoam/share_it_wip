package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSignupTwo;

/**
 * Created by jose on 19/04/2017.
 */

public class SignupTwoPresenter extends AbsSignupTwo {
    Context context;

    public SignupTwoPresenter(Context context) {
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
        view.hideSignupTwo();
    }

    @Override
    public void onMenuPressed() {
        view.showMenu();
    }

    @Override
    public void onNextPagePressed() {
        navigator.navigatePageThree();
    }

}

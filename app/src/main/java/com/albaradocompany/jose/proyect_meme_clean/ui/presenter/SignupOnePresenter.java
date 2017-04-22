package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSignupOne;

/**
 * Created by jose on 19/04/2017.
 */

public class SignupOnePresenter extends AbsSignupOne {
    Context context;

    public SignupOnePresenter(Context context) {
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
        view.hideSignupOne();
    }

    @Override
    public void onMenuPressed() {
        view.showMenu();
    }

    @Override
    public void onNextPagePressed() {
        navigator.navigatePageTwo();
    }

    @Override
    public void onDatePickerClicked() {
        view.showDatePicker();
    }

    @Override
    public void onAddPhotoClicked() {
        navigator.navigateAddPhoto();
    }

}

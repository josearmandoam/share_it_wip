package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UserByEmailInteractor;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSignupOne;
import com.albaradocompany.jose.proyect_meme_clean.usecase.GetUserByEmail;

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
        view.loadUserImage();
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
    public void onCleanPressed() {
        view.cleanFields();
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
    public void onAddImageClicked() {
        navigator.navigateAddPhoto();
    }

    @Override
    public void onImageClicked() {
        view.showImage();
    }

    @Override
    public void chekImageImp(UserByEmailInteractor interactor) {
        view.showLoading();
        interactor.getUserByEmail(new GetUserByEmail.Listener() {
            @Override
            public void onNoInternetAvailable() {
                view.hideLoading();
                view.showNoInternetAvailable();
            }

            @Override
            public void onError(Exception e) {
                view.hideLoading();
                view.showError(e);
            }

            @Override
            public void onUserReceived(Login user) {
                view.hideLoading();
                view.showEmailRegistredAlready();
            }

            @Override
            public void onFailure() {
                view.hideLoading();
                navigator.navigatePageTwo();
            }
        });
    }


}

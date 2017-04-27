package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;
import android.widget.Toast;

import com.albaradocompany.jose.proyect_meme_clean.interactor.SendImageInteractor;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSignupOne;
import com.albaradocompany.jose.proyect_meme_clean.usecase.SendImageResponse;

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
        view.loadUserImage();
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
    public void onAddImageClicked() {
        navigator.navigateAddPhoto();
    }

    @Override
    public void onImageClicked() {
        view.showImage();
    }

    @Override
    public void onTestClicked(SendImageInteractor sendImageInteractor) {
        sendImageInteractor.getResponse(new SendImageResponse.Listener() {
            @Override
            public void onNoInternetAvailable() {
                Toast.makeText(context, "no internet", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(context, "error" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSucces() {
                Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure() {
                Toast.makeText(context, "failure", Toast.LENGTH_SHORT).show();

            }
        });

    }

}

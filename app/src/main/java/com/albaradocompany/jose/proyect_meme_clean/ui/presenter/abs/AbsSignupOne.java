package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import com.albaradocompany.jose.proyect_meme_clean.interactor.SendImageInteractor;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

/**
 * Created by jose on 19/04/2017.
 */

public abstract class AbsSignupOne extends Presenter<AbsSignupOne.View, AbsSignupOne.Navigator> {

    public abstract void onBackPressed();

    public abstract void onCleanPressed();

    public abstract void onNextPagePressed();

    public abstract void onDatePickerClicked();

    public abstract void onAddImageClicked();

    public abstract void onImageClicked();

    public interface View {

        void hideSignupOne();

        void cleanFields();

        void loadUserImage();

        void showDatePicker();

        void showImage();
    }

    public interface Navigator {

        void navigatePageTwo();

        void navigateAddPhoto();

    }
}

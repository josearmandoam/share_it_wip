package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

/**
 * Created by jose on 19/04/2017.
 */

public abstract class AbsSignupThree extends Presenter<AbsSignupThree.View, AbsSignupThree.Navigator> {

    public abstract void onBackPressed();

    public abstract void onMenuPressed();

    public abstract void onConfirmClicked();

    public abstract void onImageClicked();

    public interface View {

        void hideSignupThree();

        void showMenu();

        void showLoading();

        void hideLoading();

        void loadUserImage();

        void showImage();
    }

    public interface Navigator {


    }
}

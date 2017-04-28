package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

/**
 * Created by jose on 19/04/2017.
 */

public abstract class AbsSignupTwo extends Presenter<AbsSignupTwo.View, AbsSignupTwo.Navigator> {

    public abstract void onBackPressed();


    public abstract void onNextPagePressed();

    public abstract void onImageClicked();

    public abstract void onCleanPressed();

    public interface View {

        void hideSignupTwo();

        void loadUserImage();

        void showImage();

        void cleanFields();
    }

    public interface Navigator {

        void navigatePageThree();

    }
}

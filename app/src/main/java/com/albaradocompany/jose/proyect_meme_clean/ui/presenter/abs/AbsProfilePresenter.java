package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import android.graphics.Bitmap;

import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

/**
 * Created by jose on 04/05/2017.
 */

public abstract class AbsProfilePresenter extends Presenter<AbsProfilePresenter.View, AbsProfilePresenter.Navigator> {

    public abstract void onLogOutClicked();

    public abstract void onBackClicked();

    public abstract void onEditClicked();

    public abstract void saveProfileOnMemory(Bitmap bitmap);

    public abstract void saveBackgroundOnMemory(Bitmap bitmap);

    public interface View {
        void showProfile();

        void showBackground();

        void showName();

        void showUsername();

        void showDescription();

        void showPhotos();

        void checkProfile();

        void checkBackground();
    }

    public interface Navigator {
        void navigateToLogin();

        void navigateToEdit();
    }
}

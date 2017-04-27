package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import com.albaradocompany.jose.proyect_meme_clean.interactor.LoginInteractor;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

/**
 * Created by jose on 16/04/2017.
 */

public abstract class AbsUserLogin extends Presenter<AbsUserLogin.View, AbsUserLogin.Navigator> {

    public abstract void onSigninClicked(LoginInteractor getLogin, String username, String password);

    public abstract void onSignupClicked();

    public abstract void onPasswordClicked();

    public interface View {

        void showLoading();

        void hideLoading();

        void showErrorUserNotFound();

        void showErrorLoginPassword();

        void showNoInternetAvailable();

        void showError(Exception e);

        void hideButtonSignin();

        void showButtonSignin();

    }

    public interface Navigator {
        void navigateToHomePage();

        void navigateToSignupPage();

        void navigateToPassword();
    }
}

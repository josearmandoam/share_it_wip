package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.interactor.LoginInteractor;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsUserLogin;
import com.albaradocompany.jose.proyect_meme_clean.usecase.GetLogin;

import java.util.List;

/**
 * Created by jose on 16/04/2017.
 */

public class LoginPresenter extends AbsUserLogin {
    String password;
    String username;
    Context context;
    GetLogin getLogin;

    public LoginPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void initialize() {
        view.hideButtonSignin();
        view.showLoading();
        getLogin.getLogin(new GetLogin.Listener() {

            @Override
            public void onLoginReceived(List<Login> login) {
                if (login.size() > 0) {
                    if (login.get(0).getPassword().equals(password)) {
                        view.hideLoading();
                        navigator.navigateToHomePage();
                        view.showButtonSignin();
                    } else {
                        view.hideLoading();
                        view.showErrorLoginPassword();
                        view.showButtonSignin();
                    }
                } else {
                    view.hideLoading();
                    view.showErrorUserNotFound();
                    view.showButtonSignin();
                }
            }

            @Override
            public void onError(Exception e) {
                view.hideLoading();
                view.showError(e);
                view.showButtonSignin();
            }

            @Override
            public void onNoInternetAvailable() {
                view.hideLoading();
                view.showNoInternetAvailable();
                view.showButtonSignin();
            }

            @Override
            public void onUserNotFound() {
                view.hideLoading();
                view.showErrorUserNotFound();
                view.showButtonSignin();
            }
        });
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
    public void onSigninClicked(LoginInteractor getLogin, String username, String password) {
        this.getLogin = getLogin;
        this.username = username;
        this.password = password;
        initialize();
    }

    @Override
    public void onSignupClicked() {
        navigator.navigateToSignupPage();
    }

    @Override
    public void onPasswordClicked() {
        navigator.navigateToPassword();
    }
}

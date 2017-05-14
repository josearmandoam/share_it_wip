package com.albaradocompany.jose.proyect_meme_clean.interactor;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.LoginApiEntry;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetLogin;

import java.util.List;

/**
 * Created by jose on 17/04/2017.
 */

public class LoginInteractor implements Interactor, GetLogin, GetLogin.Listener {
    GetLogin.Listener listener = new NullListener();
    GetLogin getLogin;
    Executor executor;
    MainThread mainThread;

    public LoginInteractor(GetLogin getLogin, Executor executor, MainThread mainThread) {
        this.getLogin = getLogin;
        this.executor = executor;
        this.mainThread = mainThread;
    }

    @Override
    public void run() {
        getLogin.getLogin(listener);
    }

    @Override
    public LoginApiEntry get() {
        throw new IllegalArgumentException("Please use a sync version of this interactor");
    }

    @Override
    public void getLogin(Listener listener) {
        if (listener != null) {
            this.listener = listener;
        }
        this.executor.run(this);
    }

    @Override
    public void onLoginReceived(List<Login> login) {
        listener.onLoginReceived(login);
    }

    @Override
    public void onError(Exception e) {
        listener.onError(e);
    }

    @Override
    public void onNoInternetAvailable() {
        listener.onNoInternetAvailable();
    }

    @Override
    public void onUserNotFound() {
        listener.onUserNotFound();
    }

    private class NullListener implements Listener {
        @Override
        public void onLoginReceived(List<Login> login) {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onNoInternetAvailable() {

        }

        @Override
        public void onUserNotFound() {

        }
    }
}

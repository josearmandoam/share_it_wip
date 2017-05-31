package com.albaradocompany.jose.proyect_meme_clean.interactor;

import com.albaradocompany.jose.proyect_meme_clean.global.model.User;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetUserByToken;

/**
 * Created by jose on 31/05/2017.
 */

public class UserByTokenInteractor implements Interactor, GetUserByToken, GetUserByToken.Listener {
    GetUserByToken getUserByToken;
    MainThread mainThread;
    Executor executor;

    GetUserByToken.Listener listener = new NullListener();

    public UserByTokenInteractor(GetUserByToken getUserByToken, MainThread mainThread, Executor executor) {
        this.getUserByToken = getUserByToken;
        this.mainThread = mainThread;
        this.executor = executor;
    }

    @Override
    public void run() {
        getUserByToken.getUserByTOken(listener);
    }

    @Override
    public void getUserByTOken(Listener listener) {
        if (listener != null) {
            this.listener = listener;
        }
        this.executor.run(this);
    }

    @Override
    public void onNoInternetAvailable() {
        listener.onNoInternetAvailable();
    }

    @Override
    public void onError(Exception e) {
        listener.onError(e);
    }

    @Override
    public void onSuccess(User user) {
        listener.onSuccess(user);
    }

    @Override
    public void onFailure() {
        listener.onFailure();
    }

    private class NullListener implements Listener {
        @Override
        public void onNoInternetAvailable() {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess(User user) {

        }

        @Override
        public void onFailure() {

        }
    }
}

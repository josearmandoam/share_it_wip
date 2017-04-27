package com.albaradocompany.jose.proyect_meme_clean.interactor;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.usecase.GetUserByEmail;

import java.util.List;

/**
 * Created by jose on 27/04/2017.
 */

public class UserByEmailInteractor implements Interactor, GetUserByEmail, GetUserByEmail.Listener {
    GetUserByEmail getUserByEmail;
    MainThread mainThread;
    Executor executor;
    GetUserByEmail.Listener listener = new NullListener();

    public UserByEmailInteractor(GetUserByEmail getUserByEmail, MainThread mainThread, Executor executor) {
        this.getUserByEmail = getUserByEmail;
        this.mainThread = mainThread;
        this.executor = executor;
    }

    @Override
    public void run() {
        getUserByEmail.getUserByEmail(listener);
    }

    @Override
    public void getUserByEmail(Listener listener) {
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
    public void onUserReceived(List<Login> user) {
        listener.onUserReceived(user);
    }

    private class NullListener implements Listener {
        @Override
        public void onNoInternetAvailable() {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onUserReceived(List<Login> user) {

        }
    }
}

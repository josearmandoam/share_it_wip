package com.albaradocompany.jose.proyect_meme_clean.interactor;

import com.albaradocompany.jose.proyect_meme_clean.global.model.User;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetUserByUsername;

import java.util.List;

/**
 * Created by jose on 25/05/2017.
 */

public class UserByUsernameInteractor implements Interactor, GetUserByUsername, GetUserByUsername.Listener {
    GetUserByUsername getUserByUsername;
    MainThread mainThread;
    Executor executor;

    GetUserByUsername.Listener listener = new NullListener();

    public UserByUsernameInteractor(GetUserByUsername getUserByUsername, MainThread mainThread, Executor executor) {
        this.getUserByUsername = getUserByUsername;
        this.mainThread = mainThread;
        this.executor = executor;
    }

    @Override
    public void run() {
        getUserByUsername.getUser(listener);
    }

    @Override
    public void getUser(Listener listener) {
        if (listener != null) {
            this.listener = listener;
        }
        this.executor.run(this);
    }

    @Override
    public void onSuccess(List<User> users) {
        listener.onSuccess(users);
    }

    @Override
    public void onError(Exception e) {
        listener.onError(e);
    }

    @Override
    public void onNoInternetAvailable() {
        listener.onNoInternetAvailable();
    }

    private class NullListener implements Listener {
        @Override
        public void onSuccess(List<User> users) {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onNoInternetAvailable() {

        }
    }
}

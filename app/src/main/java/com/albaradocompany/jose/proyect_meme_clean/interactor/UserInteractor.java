package com.albaradocompany.jose.proyect_meme_clean.interactor;

import com.albaradocompany.jose.proyect_meme_clean.global.model.User;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetUser;

/**
 * Created by jose on 16/05/2017.
 */

public class UserInteractor implements Interactor, GetUser, GetUser.Listener {

    GetUser getUser;
    MainThread mainThread;
    Executor executor;

    GetUser.Listener listener = new NullListener();

    public UserInteractor(GetUser getUser, MainThread mainThread, Executor executor) {
        this.getUser = getUser;
        this.mainThread = mainThread;
        this.executor = executor;
    }

    @Override
    public void run() {
        getUser.getUser(listener);
    }

    @Override
    public void getUser(Listener listener) {
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
    public void onUserReceived(User user) {
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
        public void onUserReceived(User user) {

        }
    }
}

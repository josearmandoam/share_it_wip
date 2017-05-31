package com.albaradocompany.jose.proyect_meme_clean.interactor;

import com.albaradocompany.jose.proyect_meme_clean.usecase.RegisterToken;

/**
 * Created by jose on 29/05/2017.
 */

public class RegisterTokenInteractor implements Interactor, RegisterToken, RegisterToken.Listener {
    RegisterToken registerToken;
    MainThread mainThread;
    Executor executor;

    RegisterToken.Listener listener = new NUllListener();

    public RegisterTokenInteractor(RegisterToken registerToken, MainThread mainThread, Executor executor) {
        this.registerToken = registerToken;
        this.mainThread = mainThread;
        this.executor = executor;
    }

    @Override
    public void run() {
        registerToken.registerToken(listener);
    }

    @Override
    public void registerToken(Listener listener) {
        if (listener != null) {
            this.listener = listener;
        }
        this.executor.run(this);
    }

    @Override
    public void onSuccess() {
        listener.onSuccess();
    }

    @Override
    public void onFailure() {
        listener.onFailure();
    }

    @Override
    public void onNoInternetAvailable() {
        listener.onNoInternetAvailable();
    }

    @Override
    public void onError(Exception e) {
        listener.onError(e);
    }

    private class NUllListener implements Listener {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onFailure() {

        }

        @Override
        public void onNoInternetAvailable() {

        }

        @Override
        public void onError(Exception e) {

        }
    }
}

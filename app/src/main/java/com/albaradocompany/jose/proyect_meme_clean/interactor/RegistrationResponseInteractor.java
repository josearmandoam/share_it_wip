package com.albaradocompany.jose.proyect_meme_clean.interactor;

import com.albaradocompany.jose.proyect_meme_clean.global.model.RegistrationResponse;
import com.albaradocompany.jose.proyect_meme_clean.usecase.GetRegistrationResponse;

/**
 * Created by jose on 24/04/2017.
 */

public class RegistrationResponseInteractor implements Interactor, GetRegistrationResponse, GetRegistrationResponse.Listener {
    GetRegistrationResponse getRegistrationResponse;
    MainThread mainThread;
    Executor executor;

    GetRegistrationResponse.Listener listener = new NullListener();
    public RegistrationResponseInteractor(GetRegistrationResponse getRegistrationResponse, MainThread mainThread, Executor executor) {
        this.getRegistrationResponse = getRegistrationResponse;
        this.mainThread = mainThread;
        this.executor = executor;
    }

    @Override
    public void run() {
        getRegistrationResponse.getRegistrationResponse(listener);
    }

    @Override
    public void getRegistrationResponse(Listener listener) {
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
    public void onRegistrationSuccess(RegistrationResponse response) {
        listener.onRegistrationSuccess(response);
    }

    @Override
    public void onRegistrationFailed() {
        listener.onRegistrationFailed();
    }
    private class NullListener implements Listener {
        @Override
        public void onNoInternetAvailable() {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onRegistrationSuccess(RegistrationResponse response) {

        }

        @Override
        public void onRegistrationFailed() {

        }
    }
}

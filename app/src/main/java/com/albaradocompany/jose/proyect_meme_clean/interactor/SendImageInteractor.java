package com.albaradocompany.jose.proyect_meme_clean.interactor;

import com.albaradocompany.jose.proyect_meme_clean.usecase.SendImageResponse;

/**
 * Created by jose on 27/04/2017.
 */

public class SendImageInteractor implements Interactor, SendImageResponse, SendImageResponse.Listener {
    SendImageResponse sendImageResponse;
    MainThread mainThread;
    Executor executor;
    SendImageResponse.Listener listener = new NullListener();

    public SendImageInteractor(SendImageResponse sendImageResponse, MainThread mainThread, Executor executor) {
        this.sendImageResponse = sendImageResponse;
        this.mainThread = mainThread;
        this.executor = executor;
    }

    @Override
    public void run() {
        sendImageResponse.getResponse(listener);
    }

    @Override
    public void getResponse(Listener listener) {
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
    public void onSucces() {
        listener.onSucces();
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
        public void onSucces() {

        }

        @Override
        public void onFailure() {

        }
    }
}

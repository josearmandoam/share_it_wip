package com.albaradocompany.jose.proyect_meme_clean.interactor;

import com.albaradocompany.jose.proyect_meme_clean.usecase.SendNotification;

/**
 * Created by jose on 29/05/2017.
 */

public class SendNotificationInteractor implements Interactor, SendNotification, SendNotification.Listener {
    SendNotification sendNotification;
    MainThread mainThread;
    Executor executor;

    SendNotification.Listener listener = new NullListener();

    public SendNotificationInteractor(SendNotification sendNotification, MainThread mainThread, Executor executor) {
        this.sendNotification = sendNotification;
        this.mainThread = mainThread;
        this.executor = executor;
    }

    @Override
    public void run() {
        sendNotification.sendNotification(listener);
    }

    @Override
    public void sendNotification(Listener listener) {
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
    public void onError(Exception e) {
        listener.onError(e);
    }

    @Override
    public void onNoInternetAvailable() {
        listener.onNoInternetAvailable();
    }

    private class NullListener implements Listener {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onFailure() {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onNoInternetAvailable() {

        }
    }
}

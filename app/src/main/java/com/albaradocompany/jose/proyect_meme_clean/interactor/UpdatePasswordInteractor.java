package com.albaradocompany.jose.proyect_meme_clean.interactor;

import com.albaradocompany.jose.proyect_meme_clean.usecase.UpdatePassword;

/**
 * Created by jose on 28/04/2017.
 */

public class UpdatePasswordInteractor implements Interactor, UpdatePassword, UpdatePassword.Listener {
    UpdatePassword updatePassword;
    MainThread mainThread;
    Executor executor;

    UpdatePassword.Listener listener = new NullListener();

    public UpdatePasswordInteractor(UpdatePassword updatePassword, MainThread mainThread, Executor executor) {
        this.updatePassword = updatePassword;
        this.mainThread = mainThread;
        this.executor = executor;
    }

    @Override
    public void run() {
        updatePassword.updatePassword(listener);
    }

    @Override
    public void updatePassword(Listener listener) {
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
    public void onUpdateSuccess() {
        listener.onUpdateSuccess();
    }

    @Override
    public void onUpdateFailure() {
        listener.onUpdateFailure();
    }

    private class NullListener implements Listener {
        @Override
        public void onNoInternetAvailable() {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onUpdateSuccess() {

        }

        @Override
        public void onUpdateFailure() {

        }
    }
}

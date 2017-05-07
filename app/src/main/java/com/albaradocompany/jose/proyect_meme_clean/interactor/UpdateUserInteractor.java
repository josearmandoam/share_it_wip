package com.albaradocompany.jose.proyect_meme_clean.interactor;

import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdateUser;

/**
 * Created by jose on 06/05/2017.
 */

public class UpdateUserInteractor implements Interactor, UpdateUser, UpdateUser.Listener {
    UpdateUser updateUser;
    MainThread mainThread;
    Executor executor;

    UpdateUser.Listener listener = new NullListener();

    public UpdateUserInteractor(UpdateUser updateUser, MainThread mainThread, Executor executor) {
        this.updateUser = updateUser;
        this.mainThread = mainThread;
        this.executor = executor;
    }

    @Override
    public void run() {
        updateUser.updateUser(listener);
    }

    @Override
    public void updateUser(Listener listener) {
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

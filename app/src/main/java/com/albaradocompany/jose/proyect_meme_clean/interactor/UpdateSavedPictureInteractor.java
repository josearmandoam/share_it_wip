package com.albaradocompany.jose.proyect_meme_clean.interactor;

import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdateSavedPicture;

/**
 * Created by jose on 16/05/2017.
 */

public class UpdateSavedPictureInteractor implements Interactor, UpdateSavedPicture, UpdateSavedPicture.Listener {
    UpdateSavedPicture updateSavedPicture;
    MainThread mainThread;
    Executor executor;

    public UpdateSavedPictureInteractor(UpdateSavedPicture updateSavedPicture, MainThread mainThread, Executor executor) {
        this.updateSavedPicture = updateSavedPicture;
        this.mainThread = mainThread;
        this.executor = executor;
    }

    UpdateSavedPicture.Listener listener = new NullListener();

    @Override
    public void run() {
        updateSavedPicture.updateSavedPicture(listener);
    }

    @Override
    public void updateSavedPicture(Listener listener) {
        if (this.listener != null) {
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
    public void onSuccess() {
        listener.onSuccess();
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
        public void onSuccess() {

        }

        @Override
        public void onFailure() {

        }
    }
}

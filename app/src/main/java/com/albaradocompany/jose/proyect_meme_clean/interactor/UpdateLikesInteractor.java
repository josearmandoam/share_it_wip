package com.albaradocompany.jose.proyect_meme_clean.interactor;

import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdateLike;

/**
 * Created by jose on 15/05/2017.
 */

public class UpdateLikesInteractor implements Interactor, UpdateLike, UpdateLike.Listener {
    UpdateLike updateLike;
    MainThread mainThread;
    Executor executor;

    UpdateLike.Listener listener = new NullListener();

    public UpdateLikesInteractor(UpdateLike updateLike, MainThread mainThread, Executor executor) {
        this.updateLike = updateLike;
        this.mainThread = mainThread;
        this.executor = executor;
    }

    @Override
    public void run() {
        updateLike.updateLike(listener);
    }

    @Override
    public void updateLike(Listener listener) {
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

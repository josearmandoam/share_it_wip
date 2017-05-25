package com.albaradocompany.jose.proyect_meme_clean.interactor;

import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdateFeed;

/**
 * Created by jose on 25/05/2017.
 */

public class UpdateFeedInteractor implements Interactor, UpdateFeed, UpdateFeed.Listener {
    UpdateFeed updateFeed;
    MainThread mainThread;
    Executor executor;

    UpdateFeed.Listener listener = new NullListener();

    public UpdateFeedInteractor(UpdateFeed updateFeed, MainThread mainThread, Executor executor) {
        this.updateFeed = updateFeed;
        this.mainThread = mainThread;
        this.executor = executor;
    }

    @Override
    public void run() {
        updateFeed.updateFedd(listener);
    }

    @Override
    public void updateFedd(Listener listener) {
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

package com.albaradocompany.jose.proyect_meme_clean.interactor;

import com.albaradocompany.jose.proyect_meme_clean.usecase.UploadPicture;

/**
 * Created by jose on 23/05/2017.
 */

public class UploadPictureInteractor implements Interactor, UploadPicture, UploadPicture.Listener {

    UploadPicture uploadPicture;
    MainThread mainThread;
    Executor executor;
    UploadPicture.Listener listener = new NullListener();

    public UploadPictureInteractor(UploadPicture uploadPicture, MainThread mainThread, Executor executor) {
        this.uploadPicture = uploadPicture;
        this.mainThread = mainThread;
        this.executor = executor;
    }

    @Override

    public void run() {
         uploadPicture.uploadPicture(listener);
    }

    @Override
    public void uploadPicture(Listener listener) {
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

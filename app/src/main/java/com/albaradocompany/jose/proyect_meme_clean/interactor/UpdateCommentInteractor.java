package com.albaradocompany.jose.proyect_meme_clean.interactor;

import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdateComment;

/**
 * Created by jose on 15/05/2017.
 */

public class UpdateCommentInteractor implements Interactor, UpdateComment, UpdateComment.Listener {
    UpdateComment updateComment;
    MainThread mainThread;
    Executor executor;

    UpdateComment.Listener listener = new NullListener();

    public UpdateCommentInteractor(UpdateComment updateComment, MainThread mainThread, Executor executor) {
        this.updateComment = updateComment;
        this.mainThread = mainThread;
        this.executor = executor;
    }

    @Override
    public void run() {
        updateComment.updateComments(listener);
    }

    @Override
    public void updateComments(Listener listener) {
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

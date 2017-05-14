package com.albaradocompany.jose.proyect_meme_clean.interactor;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetComments;

import java.util.List;

/**
 * Created by jose on 14/05/2017.
 */

public class CommentsInteractor implements Interactor, GetComments, GetComments.Listener {
    GetComments getComments;
    MainThread mainThread;
    Executor executor;

    GetComments.Listener listener = new NullListener();

    public CommentsInteractor(GetComments getComments, MainThread mainThread, Executor executor) {
        this.getComments = getComments;
        this.mainThread = mainThread;
        this.executor = executor;
    }

    @Override
    public void run() {
        getComments.getComments(listener);
    }

    @Override
    public void getComments(Listener listener) {
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
    public void onCommentesReceived(List<Comment> comments) {
        listener.onCommentesReceived(comments);
    }

    private class NullListener implements Listener {
        @Override
        public void onNoInternetAvailable() {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onCommentesReceived(List<Comment> comments) {

        }
    }
}

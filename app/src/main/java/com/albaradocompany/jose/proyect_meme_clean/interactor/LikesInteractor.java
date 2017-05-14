package com.albaradocompany.jose.proyect_meme_clean.interactor;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Like;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetLikes;

import java.util.List;

/**
 * Created by jose on 14/05/2017.
 */

public class LikesInteractor implements Interactor, GetLikes, GetLikes.Listener {
    GetLikes getLikes;
    MainThread mainThread;
    Executor executor;

    GetLikes.Listener listener = new NullListener();

    public LikesInteractor(GetLikes getLikes, MainThread mainThread, Executor executor) {
        this.getLikes = getLikes;
        this.mainThread = mainThread;
        this.executor = executor;
    }

    @Override
    public void run() {
        getLikes.getLikes(listener);
    }

    @Override
    public void getLikes(Listener listener) {
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
    public void onLikesReceived(List<Like> likes) {
        listener.onLikesReceived(likes);
    }

    private class NullListener implements Listener {
        @Override
        public void onNoInternetAvailable() {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onLikesReceived(List<Like> likes) {

        }
    }
}

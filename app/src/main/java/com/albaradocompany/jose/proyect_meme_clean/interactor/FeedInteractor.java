package com.albaradocompany.jose.proyect_meme_clean.interactor;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Feed;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetFeed;

import java.util.List;

/**
 * Created by jose on 20/05/2017.
 */

public class FeedInteractor implements Interactor, GetFeed, GetFeed.Listener {
    GetFeed getFeed;
    MainThread mainThread;
    Executor executor;

    GetFeed.Listener listener = new NullListener();

    public FeedInteractor(GetFeed getFeed, MainThread mainThread, Executor executor) {
        this.getFeed = getFeed;
        this.mainThread = mainThread;
        this.executor = executor;
    }

    @Override
    public void run() {
        getFeed.getFeed(listener);
    }

    @Override
    public void getFeed(Listener listener) {
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
    public void onFeedReceived(List<Feed> feeds) {
        listener.onFeedReceived(feeds);
    }

    private class NullListener implements Listener {
        @Override
        public void onNoInternetAvailable() {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onFeedReceived(List<Feed> feeds) {

        }
    }
}

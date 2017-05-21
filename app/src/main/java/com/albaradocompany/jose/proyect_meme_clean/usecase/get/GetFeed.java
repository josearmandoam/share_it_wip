package com.albaradocompany.jose.proyect_meme_clean.usecase.get;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Feed;

import java.util.List;

/**
 * Created by jose on 20/05/2017.
 */

public interface GetFeed {
    void getFeed(Listener listener);

    public interface Listener {
        void onNoInternetAvailable();

        void onError(Exception e);

        void onFeedReceived(List<Feed> feeds);
    }
}

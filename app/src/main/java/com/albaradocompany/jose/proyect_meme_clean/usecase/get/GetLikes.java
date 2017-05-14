package com.albaradocompany.jose.proyect_meme_clean.usecase.get;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Like;

import java.util.List;

/**
 * Created by jose on 14/05/2017.
 */

public interface GetLikes {
    void getLikes(Listener listener);

    interface Listener {
        void onNoInternetAvailable();

        void onError(Exception e);

        void onLikesReceived(List<Like> likes);
    }
}

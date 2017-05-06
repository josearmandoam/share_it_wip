package com.albaradocompany.jose.proyect_meme_clean.usecase;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;

import java.util.List;

/**
 * Created by jose on 05/05/2017.
 */

public interface GetPicturesById {
    void getPictures(Listener listener);

    interface Listener {
        void onNoInternetAvailable();

        void onError(Exception e);

        void onPicturesReceived(List<Picture> pictures);
    }
}

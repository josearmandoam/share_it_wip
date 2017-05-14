package com.albaradocompany.jose.proyect_meme_clean.usecase.get;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Avatar;

import java.util.List;

/**
 * Created by jose on 22/04/2017.
 */

public interface GetAvatars {
    void getAvatars(Listener listener);

    interface Listener {
        void onNoInternetAvailable();

        void onError(Exception e);

        void onAvatarsReceived(List<Avatar> avatarList);
    }
}

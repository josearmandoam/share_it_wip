package com.albaradocompany.jose.proyect_meme_clean.interactor;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Avatar;
import com.albaradocompany.jose.proyect_meme_clean.usecase.GetAvatars;

import java.util.List;

/**
 * Created by jose on 22/04/2017.
 */

public class AvatarInteractor implements Interactor, GetAvatars, GetAvatars.Listener {
    GetAvatars getAvatars;
    Executor executor;
    MainThread mainThread;

    GetAvatars.Listener listener = new NullListener();

    public AvatarInteractor(GetAvatars getAvatars, Executor executor, MainThread mainThread) {
        this.getAvatars = getAvatars;
        this.executor = executor;
        this.mainThread = mainThread;
    }

    @Override
    public void run() {
        getAvatars.getAvatars(listener);
    }

    @Override
    public void getAvatars(Listener listener) {
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
    public void onAvatarsReceived(List<Avatar> avatarList) {
        listener.onAvatarsReceived(avatarList);
    }

    private class NullListener implements Listener {
        @Override
        public void onNoInternetAvailable() {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onAvatarsReceived(List<Avatar> avatarList) {

        }
    }
}

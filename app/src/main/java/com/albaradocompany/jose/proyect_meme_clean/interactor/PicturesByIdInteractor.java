package com.albaradocompany.jose.proyect_meme_clean.interactor;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.usecase.GetPicturesById;

import java.util.List;

/**
 * Created by jose on 05/05/2017.
 */

public class PicturesByIdInteractor implements Interactor, GetPicturesById, GetPicturesById.Listener {
    GetPicturesById getPicturesById;
    MainThread mainThread;
    Executor executor;

    GetPicturesById.Listener listener = new NullListener();

    public PicturesByIdInteractor(GetPicturesById getPicturesById, MainThread mainThread, Executor executor) {
        this.getPicturesById = getPicturesById;
        this.mainThread = mainThread;
        this.executor = executor;
    }

    @Override
    public void run() {
        getPicturesById.getPictures(listener);
    }

    @Override
    public void getPictures(Listener listener) {
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
    public void onPicturesReceived(List<Picture> pictures) {
        listener.onPicturesReceived(pictures);
    }

    private class NullListener implements Listener {
        @Override
        public void onNoInternetAvailable() {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onPicturesReceived(List<Picture> pictures) {

        }
    }
}

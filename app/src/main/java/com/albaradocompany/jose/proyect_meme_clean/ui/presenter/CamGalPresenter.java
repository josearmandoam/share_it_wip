package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsCamGalPresenter;

/**
 * Created by jose on 03/05/2017.
 */

public class CamGalPresenter extends AbsCamGalPresenter {
    @Override
    public void initialize() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onTakeFromCameraClicked() {
        navigator.navigateToCamera();
    }

    @Override
    public void onTakeFromGalleryClicked() {
        navigator.navigateToGallery();
    }
}

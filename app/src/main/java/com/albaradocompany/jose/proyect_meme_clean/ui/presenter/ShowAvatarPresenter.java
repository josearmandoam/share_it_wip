package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsShowAvatar;

/**
 * Created by jose on 23/04/2017.
 */

public class ShowAvatarPresenter extends AbsShowAvatar {
    Context context;

    public ShowAvatarPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void initialize() {
        view.loadImage();
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
    public void onEditClicked() {
        navigator.navigateToEdit();
    }

    @Override
    public void onDeleteClicked() {
        view.deleteImage();
    }
}

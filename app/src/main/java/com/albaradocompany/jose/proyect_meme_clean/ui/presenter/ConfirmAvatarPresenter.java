package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsConfirmAvatar;

/**
 * Created by jose on 23/04/2017.
 */

public class ConfirmAvatarPresenter extends AbsConfirmAvatar {
    Context context;

    public ConfirmAvatarPresenter(Context context) {
        this.context = context;
    }

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
    public void onAcceptClicked() {
        navigator.closeConfirmAvatar();
    }

}

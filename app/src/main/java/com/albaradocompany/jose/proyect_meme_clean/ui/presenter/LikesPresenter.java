package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Like;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsLikesPresenter;

/**
 * Created by jose on 15/05/2017.
 */

public class LikesPresenter extends AbsLikesPresenter {
    Context context;

    public LikesPresenter(Context context) {
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
    public void onBackClicked() {
        view.hideDialog();
    }

    @Override
    public void onPictureClicked(Like like) {
        navigator.navigateToUserDetail(like);
    }

    @Override
    public void onUsernameClicked(Like like) {
        navigator.navigateToUserDetail(like);
    }
}

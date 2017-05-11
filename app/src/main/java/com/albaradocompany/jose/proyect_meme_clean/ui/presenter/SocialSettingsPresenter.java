package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSocialSettingsPresenter;

/**
 * Created by jose on 11/05/2017.
 */

public class SocialSettingsPresenter extends AbsSocialSettingsPresenter {
    Context context;

    public SocialSettingsPresenter(Context context) {
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
    public void onConfirmClicked() {
        view.dimissDialog();
    }

    @Override
    public void updateSwitchs() {
        view.updateSwitchs();
    }
}

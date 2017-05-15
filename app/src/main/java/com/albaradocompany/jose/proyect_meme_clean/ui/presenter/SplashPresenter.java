package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SplashActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSplash;

import javax.inject.Inject;

/**
 * Created by jose on 18/04/2017.
 */

public class SplashPresenter extends AbsSplash {
    Context context;
    private UIComponent component;
    @Inject
    UserSharedImp userSharedImp;

    public SplashPresenter(Context context) {
        this.context = context;
        getComponent().inject(this);
    }

    @Override
    public void initialize() {
        view.sleep();
        if (userSharedImp.isLogged()) {
            navigator.navigateToMainPage();
        } else {
            navigator.navigateToLoginActivity();
        }
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

    protected UIComponent getComponent() {
        return ((SplashActivity) context).component();
    }
}

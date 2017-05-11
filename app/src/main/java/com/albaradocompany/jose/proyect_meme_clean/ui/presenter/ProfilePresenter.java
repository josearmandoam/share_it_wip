package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;

import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerUIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIModule;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsProfilePresenter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

/**
 * Created by jose on 04/05/2017.
 */

public class ProfilePresenter extends AbsProfilePresenter {

    private final Context context;
    private UIComponent component;
    @Inject
    UserSharedImp userSharedImp;

    public ProfilePresenter(Context context) {
        this.context = context;
        component().inject(this);
    }

    @Override
    public void initialize() {
        view.showBackground();
        view.showName();
        view.showProfile();
        view.showUsername();
        view.showDescription();
        view.showPhotos();
        view.checkSocialPrivacity();
    }

    @Override
    public void resume() {
        view.showBackground();
        view.showName();
        view.showProfile();
        view.showUsername();
        view.showDescription();
        view.showPhotos();
        view.checkSocialPrivacity();
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onLogOutClicked() {
        navigator.navigateToLogin();
    }

    @Override
    public void onBackClicked() {

    }

    @Override
    public void onEditClicked() {
        navigator.navigateToEdit();
    }


    public UIComponent component() {
        if (component == null) {
            component = DaggerUIComponent.builder()
                    .rootComponent(((App) context.getApplicationContext()).getComponent())
                    .uIModule(new UIModule(context.getApplicationContext()))
                    .mainModule(((App) context.getApplicationContext()).getMainModule())
                    .build();
        }
        return component;
    }
}

package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD.GetUserBDImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerUIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIModule;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSavedPicturesPresenter;

import javax.inject.Inject;

/**
 * Created by jose on 16/05/2017.
 */

public class SavedPicturesPresenter extends AbsSavedPicturesPresenter {
    Context context;
    private UIComponent component;
    @Inject
    GetUserBDImp getUserBDImp;
    @Inject
    UserSharedImp userSharedImp;

    public SavedPicturesPresenter(Context context) {
        this.context = context;
        component().inject(this);
    }

    @Override
    public void initialize() {
        view.showListPhotos(getUserBDImp.parseSavedPicturesBDList(getUserBDImp.getUserSavedPictures(userSharedImp.getUserID())));
    }

    @Override
    public void resume() {
        view.showListPhotos(getUserBDImp.parseSavedPicturesBDList(getUserBDImp.getUserSavedPictures(userSharedImp.getUserID())));
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

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

    @Override
    public void onImageClicked(Picture picture) {
        navigator.navigateToPictureDetail(picture);
    }

    @Override
    public void onBackClicked() {
        navigator.navigateToBack();
    }
}

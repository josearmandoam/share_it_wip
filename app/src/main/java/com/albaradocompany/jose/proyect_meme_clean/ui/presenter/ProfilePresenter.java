package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;
import android.graphics.Bitmap;

import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerUIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIModule;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsProfilePresenter;

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
    }

    @Override
    public void resume() {
        view.checkProfile();
        view.checkBackground();
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

    @Override
    public void saveProfileOnMemory(final Bitmap bitmap) {
        if (!userSharedImp.isSelectedProfile()) {
            new Thread(new Runnable() {
                public void run() {
                    String dirProf = userSharedImp.savePictureOnMemory(bitmap, userSharedImp.getUserID() + getCurrentDateAndTime());
                    userSharedImp.saveProfile(dirProf);
                }
            }).start();
        }
    }

    @Override
    public void saveBackgroundOnMemory(final Bitmap bitmap) {
        if (!userSharedImp.isSelectedBackground()) {
            new Thread(new Runnable() {
                public void run() {
                    String dirBack = userSharedImp.savePictureOnMemory(bitmap, userSharedImp.getUserID() + getCurrentDateAndTime());
                    userSharedImp.saveBackground(dirBack);
                }
            }).start();
        }
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
    public String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-­ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }
}

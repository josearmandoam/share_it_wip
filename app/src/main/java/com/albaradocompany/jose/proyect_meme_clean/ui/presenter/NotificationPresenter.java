package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD.GetUserBDImp;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.MainActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsNotificationPresenter;

import javax.inject.Inject;

/**
 * Created by jose on 31/05/2017.
 */

public class NotificationPresenter extends AbsNotificationPresenter {
    Context context;
    @Inject
    GetUserBDImp db;

    public NotificationPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void initialize() {
        getComponent().inject(this);
        view.showNotifications(db.parseNotificationLines(db.getNotificationLines()));
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
        return ((MainActivity) context).component();
    }
}

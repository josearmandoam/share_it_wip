package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD.GetUserBDImp;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.model.NotificationLine;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.MainActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsNotificationPresenter;

import java.util.List;

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
        view.showNotifications(db.parseNotificationLines(db.getNotificationLinesGRUOUPBY()),(db.getNotificationLines()));
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

    @Override
    public void onNotificationLineClicked(String name, List<NotificationLine> list, String userId) {
        view.showNotificationDialog(name, list, userId);
    }
}

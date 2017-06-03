package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;
import android.os.Bundle;

import com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD.GetUserBDImp;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.model.NotificationLine;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.MainActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsMainPresenter;

import javax.inject.Inject;

/**
 * Created by jose on 31/05/2017.
 */

public class MainPresenter extends AbsMainPresenter {
    private static final String TOKEN = "token";
    private static final java.lang.String LINE_ID = "lineId";
    private static final java.lang.String USER_ID = "userId";
    private static final java.lang.String PROFILE = "profile";
    private static final java.lang.String MESSAGE = "message";
    private static final java.lang.String TITLE = "title";
    private static final java.lang.String TIME = "time";
    private static final String NOT_SEEN = "not seen";
    Context context;
    @Inject
    GetUserBDImp db;

    public MainPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void initialize() {
        getComponent().inject(this);
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
    public void onNotificationReceived(final Bundle extras) {
        NotificationLine line = new NotificationLine(extras.getString(USER_ID), extras.getString(PROFILE), extras.getString(MESSAGE), extras.getString(TIME), extras.getString(TITLE), NOT_SEEN, extras.getString(LINE_ID), extras.getString(USER_ID));
        view.openNotificationFragment(line);

        db.insertNotificationLine(extras.getString(LINE_ID), extras.getString(USER_ID), extras.getString(PROFILE), extras.getString(MESSAGE), extras.getString(TITLE), extras.getString(TIME), NOT_SEEN, db.getUsers().get(0).userId);
    }

    protected UIComponent getComponent() {
        return ((MainActivity) context).component();
    }
}

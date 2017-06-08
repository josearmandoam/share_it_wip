package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;
import android.content.Intent;

import com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD.GetUserBDImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.SendNotificationApiImp;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.NotificationLine;
import com.albaradocompany.jose.proyect_meme_clean.global.util.DateUtil;
import com.albaradocompany.jose.proyect_meme_clean.interactor.SendNotificationInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.NotificationActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsNotificationPresenter;
import com.albaradocompany.jose.proyect_meme_clean.usecase.SendNotification;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by jose on 02/06/2017.
 */

public class NotificationsPresenter extends AbsNotificationPresenter {
    private static final String SEEN = "seen";
    private static final String TITLE = "title";
    private static final String MESSAGE = "message";
    private static final String TOKEN = "token";
    private static final String ACTION = "action";
    private static final String TIME = "time";
    private static final String USER_ID = "userId";
    private static final String LINEID = "lineId";
    private static final String PROFILE = "profile";
    private static final String NOT_SEEN = "not seen";
    Context context;
    @Inject
    GetUserBDImp db;

    public NotificationsPresenter(Context context) {
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
    public void initializeRecycler(List<NotificationLine> notifications, String userId) {
        view.showNotifications(notifications, userId);
    }

    @Override
    public void onSendMessageClicked(final String message, final String mUserId, final String mCompleteName, final String userId) {
        view.showLoading();
        SendNotificationInteractor interactor = getSendNotificationInteractor(message, mUserId, mCompleteName, userId);
        interactor.sendNotification(new SendNotification.Listener() {
            @Override
            public void onSuccess() {
                view.hideLoading();
                db.insertNotificationLine("line" + DateUtil.getCurrentDate() + DateUtil.getCurrentTime(), mUserId, BuildConfig.BASE_URL_DEFAULT + mUserId + "_profile", message, mCompleteName, DateUtil.getCurrentTimeFormated(), SEEN, userId);
                view.showNotifications(db.getAllNotifications(userId), userId);
                view.cleanMessage();
            }

            @Override
            public void onFailure() {
                view.hideLoading();
                view.showFailure();
            }

            @Override
            public void onError(Exception e) {
                view.hideLoading();
                view.showError(e);
            }

            @Override
            public void onNoInternetAvailable() {
                view.hideLoading();
                view.showNoInternetAvailable();
            }
        });
    }

    @Override
    public void onBackClicked() {
        navigator.navigateToBack();
    }

    @Override
    public void registerNewLine(final String message, final String mUserId, final String mCompleteName, final String userId, final String notifcationLineName) {
        view.showLoading();
        SendNotificationInteractor interactor = getSendNotificationInteractor(message, mUserId, mCompleteName, userId);
        interactor.sendNotification(new SendNotification.Listener() {
            @Override
            public void onSuccess() {
                view.hideLoading();
                db.insertNotificationLine("line" + DateUtil.getCurrentDate() + DateUtil.getCurrentTime(), userId, BuildConfig.BASE_URL_DEFAULT + userId + "_profile", message, notifcationLineName, DateUtil.getCurrentTimeFormated(), SEEN, userId);
                view.showNotifications(db.getAllNotifications(userId), userId);
                view.cleanMessage();
            }

            @Override
            public void onFailure() {
                view.hideLoading();
                view.showFailure();
            }

            @Override
            public void onError(Exception e) {
                view.hideLoading();
                view.showError(e);
            }

            @Override
            public void onNoInternetAvailable() {
                view.hideLoading();
                view.showNoInternetAvailable();
            }
        });
    }

    @Override
    public void onNotificationsReceived(Intent intent) {
        /* intent.putExtra(TITLE, title);
        intent.putExtra(LINEID, "line" + DateUtil.getCurrentDate() + DateUtil.getCurrentTime());
        intent.putExtra(USER_ID, userId);
        intent.putExtra(MESSAGE, body);
        intent.putExtra(ACTION, "notification");
        intent.putExtra(TIME, time);
        intent.putExtra(PROFILE, BuildConfig.BASE_URL_DEFAULT + userId + "_profile");*/
        db.insertNotificationLine(intent.getStringExtra(LINEID), intent.getStringExtra(USER_ID), intent.getStringExtra(PROFILE), intent.getStringExtra(MESSAGE), intent.getStringExtra(TITLE), intent.getStringExtra(TIME), NOT_SEEN, db.getUsers().get(0).userId);
        view.showNotifications(db.getAllNotifications(intent.getStringExtra(USER_ID)), intent.getStringExtra(USER_ID));

    }

    private SendNotificationInteractor getSendNotificationInteractor(String message, String mUserId, String mCompleteName, String userId) {
        return new SendNotificationInteractor(new SendNotificationApiImp(userId, mCompleteName, message, DateUtil.getCurrentTimeFormated(), mUserId), new MainThreadImp(), new ThreadExecutor());
    }

    protected UIComponent getComponent() {
        return ((NotificationActivity) context).component();
    }
}

package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD.GetUserBDImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.SendNotificationApiImp;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.NotificationLine;
import com.albaradocompany.jose.proyect_meme_clean.global.util.DateUtil;
import com.albaradocompany.jose.proyect_meme_clean.interactor.SendNotificationInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.MainActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsNotificationDialogPresenter;
import com.albaradocompany.jose.proyect_meme_clean.usecase.SendNotification;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by jose on 02/06/2017.
 */

public class NotificationsDialogPresenter extends AbsNotificationDialogPresenter {
    private String SEEN = "seen";
    Context context;
    @Inject
    GetUserBDImp db;

    public NotificationsDialogPresenter(Context context) {
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
                view.showNotifications(db.getNotificationLines(), userId);
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

    private SendNotificationInteractor getSendNotificationInteractor(String message, String mUserId, String mCompleteName, String userId) {
        return new SendNotificationInteractor(new SendNotificationApiImp(userId, mCompleteName + "-" + mUserId, message), new MainThreadImp(), new ThreadExecutor());
    }

    protected UIComponent getComponent() {
        return ((MainActivity) context).component();
    }
}

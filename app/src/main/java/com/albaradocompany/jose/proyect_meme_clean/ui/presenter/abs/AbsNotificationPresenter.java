package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import android.content.Intent;

import com.albaradocompany.jose.proyect_meme_clean.global.model.NotificationLine;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

import java.util.List;

/**
 * Created by jose on 02/06/2017.
 */

public abstract class AbsNotificationPresenter extends Presenter<AbsNotificationPresenter.View, AbsNotificationPresenter.Navigator> {
    public abstract void initializeRecycler(List<NotificationLine> notifications, String userId);

    public abstract void onSendMessageClicked(String message, String mUserId, String mCompleteName, String userId);

    public abstract void onBackClicked();

    public abstract void registerNewLine(String message, String mUserId, String mCompleteName, String userId, String notifcationLineName);

    public abstract void onNotificationsReceived(Intent intent);

    public interface View {
        void showNotifications(List<NotificationLine> notifications, String userId);

        void showLoading();

        void hideLoading();

        void showFailure();

        void showError(Exception e);

        void showNoInternetAvailable();

        void cleanMessage();
    }

    public interface Navigator {
        void navigateToBack();
    }
}

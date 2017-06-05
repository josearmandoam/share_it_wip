package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import android.content.Intent;
import android.os.Bundle;

import com.albaradocompany.jose.proyect_meme_clean.global.model.NotificationLine;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

/**
 * Created by jose on 31/05/2017.
 */

public abstract class AbsMainPresenter extends Presenter<AbsMainPresenter.View,AbsMainPresenter.Navigator> {
    public abstract void onNotificationReceived(Bundle extras);

    public abstract void onNotificationsReceived(Intent intent);

    public abstract void checkNewNotificationsReceived();

    public interface View {
        void openNotificationFragment(NotificationLine extras);

        void updateNotifications();

        void notifyNewNotificationReceived();

        void notifyNoNewNotificationReceived();
    }

    public interface Navigator {
    }
}

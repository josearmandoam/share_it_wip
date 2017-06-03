package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.NotificationLineBD;
import com.albaradocompany.jose.proyect_meme_clean.global.model.NotificationLine;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

import java.util.List;

/**
 * Created by jose on 31/05/2017.
 */

public abstract class AbsNotificationPresenter extends Presenter<AbsNotificationPresenter.View, AbsNotificationPresenter.Navigator>{
    public abstract void onNotificationLineClicked(String name, List<NotificationLine> list, String userId);

    public interface View {
        void showNotifications(List<NotificationLine> notificationLines, List<NotificationLine> lines);

        void showNotificationDialog(String name, List<NotificationLine> list, String userId);
    }

    public interface Navigator {
    }
}

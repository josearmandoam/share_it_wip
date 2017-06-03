package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Feed;
import com.albaradocompany.jose.proyect_meme_clean.global.model.NotificationLine;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

import java.util.List;

/**
 * Created by jose on 31/05/2017.
 */

public abstract class AbsNotificationFragmentPresenter extends Presenter<AbsNotificationFragmentPresenter.View, AbsNotificationFragmentPresenter.Navigator>{
    public abstract void onNotificationLineClicked(String name, List<NotificationLine> list, String userId);

    public abstract void onFloatingClicked();

    public abstract void onFloatingButtonHidden(String mUserId);

    public interface View {
        void showNotifications(List<NotificationLine> notificationLines, List<NotificationLine> lines);

        void openNotifications(String name, List<NotificationLine> list, String userId);

        void hideFloatingButton();

        void showFloatingButton();

        void showButtonSheet(List<Feed> list);
    }

    public interface Navigator {
    }
}

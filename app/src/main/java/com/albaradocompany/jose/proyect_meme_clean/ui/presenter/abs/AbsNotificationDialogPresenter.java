package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import com.albaradocompany.jose.proyect_meme_clean.global.model.NotificationLine;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

import java.util.List;

/**
 * Created by jose on 02/06/2017.
 */

public abstract class AbsNotificationDialogPresenter extends Presenter<AbsNotificationDialogPresenter.View, AbsNotificationDialogPresenter.Navigator> {
    public abstract void initializeRecycler(List<NotificationLine> notifications, String userId);

    public abstract void onSendMessageClicked(String message, String mUserId, String mCompleteName, String userId);

    public interface View {
        void showNotifications(List<NotificationLine> notifications, String userId);

        void showLoading();

        void hideLoading();

        void showFailure();

        void showError(Exception e);

        void showNoInternetAvailable();
    }

    public interface Navigator {
    }
}

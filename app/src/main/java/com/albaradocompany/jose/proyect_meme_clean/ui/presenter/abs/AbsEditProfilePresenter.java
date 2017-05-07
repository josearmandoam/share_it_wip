package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import com.albaradocompany.jose.proyect_meme_clean.interactor.UpdateUserInteractor;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

/**
 * Created by jose on 06/05/2017.
 */

public abstract class AbsEditProfilePresenter extends Presenter<AbsEditProfilePresenter.View, AbsEditProfilePresenter.Navigator> {
    public abstract void onCancelClicked();

    public abstract void onAcceptClicked(UpdateUserInteractor interactor);

    public abstract void onProfileClicked();

    public abstract void onBackgroundClicked();

    public interface View {

        void showProfilePicture();

        void showBackgroundPicture();

        void showName();

        void showLastName();

        void showEmail();

        void showDescription();

        void showUserName();

        void showLoading();

        void showNoInternetAvailable();

        void showError(Exception e);

        void showSuccess();

        void showFailure();

        void hideLoading();

        void showBackgroundDialog();

        void showProfileDialog();

        void checkProfile();

        void checkBaground();
    }

    public interface Navigator {
        void navigateToBack();
    }
}

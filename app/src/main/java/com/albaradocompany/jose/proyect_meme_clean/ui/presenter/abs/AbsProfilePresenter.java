package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

import java.util.List;

/**
 * Created by jose on 04/05/2017.
 */

public abstract class AbsProfilePresenter extends Presenter<AbsProfilePresenter.View, AbsProfilePresenter.Navigator> {

    public abstract void onLogOutClicked();

    public abstract void onBackClicked();

    public abstract void onEditClicked();

    public abstract void onFacebookClicked();

    public abstract void onWhastappClicked();

    public abstract void onInstagramClicked();

    public abstract void onWebsiteClicked();

    public abstract void onTwitterClicked();

    public abstract void onEmailClicked();

    public abstract void onFacebookDialogAccepted();

    public abstract void onWhatsappDialogAccepted();

    public abstract void onInstagramDialogAccepted();

    public abstract void onWebsiteDialogAccepted();

    public abstract void onTwitterDialogAccepted();

    public abstract void onEmailDialogAccepted();

    public abstract void onPictureClicked(Picture picture);

    public abstract void updatePictures();

    public abstract void updateSavedPictures();

    public abstract void onSaveClicked();


    public interface View {
        void showProfile();

        void showBackground();

        void showName();

        void showUsername();

        void showDescription();

        void showPhotos(List<Picture> pictures);

        void checkSocialPrivacity();

        void showFacebookDialog();

        void showWhastappDialog();

        void showInstagramDialog();

        void showWebsiteDialog();

        void showTwitterDialog();

        void showEmailDialog();

        void showNoInternetAvailable();

        void showError(Exception e);

        void updateRecycler();

        void showLoading();

        void hideLoading();
    }

    public interface Navigator {
        void navigateToLogin();

        void navigateToEdit();

        void openFacebookPage();

        void openWhatsapp();

        void openInstagramPage();

        void openWebsitePage();

        void openTwitterPage();

        void openEmailSelector();

        void navigateToPicture(Picture picture);

        void navigateToPicturesSaved();

        void navigateToBack();
    }
}

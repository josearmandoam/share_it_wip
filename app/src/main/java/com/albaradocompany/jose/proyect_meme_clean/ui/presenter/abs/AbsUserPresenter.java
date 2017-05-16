package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.global.model.User;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

import java.util.List;

/**
 * Created by jose on 16/05/2017.
 */

public abstract class AbsUserPresenter extends Presenter<AbsUserPresenter.View, AbsUserPresenter.Navigator> {
    public abstract void onPictureClicked(Picture picture);

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

    public abstract void onBackClicked();

    public interface View {
        void hideLoading();

        void showLoading();

        void showNoInternetAvailable();

        void showError(Exception e);

        void showPhotosLoading();

        void hidePhotosLoading();

        void showPictures(List<Picture> pictures);

        void showBackground(String background);

        void showProfile(String profile);

        void showUsername(String s);

        void showName(String s);

        void showDescription(String description);

        void showWhatsapp(String socialWhatsapp);

        void showInstagram(String socialInstagram);

        void showFacebook(String socialFacebook);

        void showWebsite(String socialWebsite);

        void showTwitter(String socialTwitter);

        void showEmail(String socialEmail);

        void showFacebookDialog();

        void showWhastappDialog();

        void showInstagramDialog();

        void showWebsiteDialog();

        void showTwitterDialog();

        void showEmailDialog();

        void showUser(User user);
    }

    public interface Navigator {
        void openFacebookPage();

        void openWhatsapp();

        void openInstagramPage();

        void openWebsitePage();

        void openTwitterPage();

        void openEmailSelector();

        void navigateToPicture(Picture picture);

        void navigateToBack();
    }
}

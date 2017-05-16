package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.PicturesByIdImp;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.global.model.User;
import com.albaradocompany.jose.proyect_meme_clean.interactor.PicturesByIdInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsUserPresenter;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetPicturesById;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetUser;

import java.util.List;

/**
 * Created by jose on 16/05/2017.
 */

public class UserPresenter extends AbsUserPresenter {
    Context context;
    String userId;
    GetUser getUser;

    public UserPresenter(Context context, String userId, GetUser getUser) {
        this.context = context;
        this.userId = userId;
        this.getUser = getUser;
    }

    @Override
    public void initialize() {
        view.showLoading();
        getUser.getUser(new GetUser.Listener() {
            @Override
            public void onNoInternetAvailable() {
                view.hideLoading();
                view.showNoInternetAvailable();
            }

            @Override
            public void onError(Exception e) {
                view.hideLoading();
                view.showError(e);
            }

            @Override
            public void onUserReceived(User user) {
                view.showBackground(user.getBackground());
                view.showProfile(user.getProfile());
                view.showName(user.getName() + " " + user.getLastname());
                view.showUsername("@" + user.getUsername());
                view.showDescription(user.getDescription());
                view.showWhatsapp(user.getSocialWhatsapp());
                view.showInstagram(user.getSocialInstagram());
                view.showFacebook(user.getSocialFacebook());
                view.showWebsite(user.getSocialWebsite());
                view.showTwitter(user.getSocialTwitter());
                view.showEmail(user.getSocialEmail());
                view.hideLoading();
                view.showPhotosLoading();
                view.showUser(user);
                getUserPhotos();
            }
        });
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
    private void getUserPhotos() {
        PicturesByIdInteractor interactor = new PicturesByIdInteractor(new PicturesByIdImp(userId), new MainThreadImp(), new ThreadExecutor());
        interactor.getPictures(new GetPicturesById.Listener() {
            @Override
            public void onNoInternetAvailable() {
                view.hidePhotosLoading();
                view.showNoInternetAvailable();
            }

            @Override
            public void onError(Exception e) {
                view.hidePhotosLoading();
                view.showError(e);
            }

            @Override
            public void onPicturesReceived(List<Picture> pictures) {
                view.hidePhotosLoading();
                view.showPictures(pictures);
            }
        });
    }

    @Override
    public void onPictureClicked(Picture picture) {
        navigator.navigateToPicture(picture);
    }

    @Override
    public void onFacebookClicked() {
        view.showFacebookDialog();
    }

    @Override
    public void onWhastappClicked() {
        view.showWhastappDialog();
    }

    @Override
    public void onInstagramClicked() {
        view.showInstagramDialog();
    }

    @Override
    public void onWebsiteClicked() {
        view.showWebsiteDialog();
    }

    @Override
    public void onTwitterClicked() {
        view.showTwitterDialog();
    }

    @Override
    public void onEmailClicked() {
        view.showEmailDialog();
    }

    @Override
    public void onFacebookDialogAccepted() {
        navigator.openFacebookPage();
    }

    @Override
    public void onWhatsappDialogAccepted() {
        navigator.openWhatsapp();
    }

    @Override
    public void onInstagramDialogAccepted() {
        navigator.openInstagramPage();
    }

    @Override
    public void onWebsiteDialogAccepted() {
        navigator.openWebsitePage();
    }

    @Override
    public void onTwitterDialogAccepted() {
        navigator.openTwitterPage();
    }

    @Override
    public void onEmailDialogAccepted() {
        navigator.openEmailSelector();
    }

    @Override
    public void onBackClicked() {
        navigator.navigateToBack();
    }
}

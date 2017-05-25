package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;
import android.os.Handler;

import com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD.GetUserBDImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.PicturesByIdApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.PicturesSavedApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerUIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIModule;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.interactor.PicturesByIdInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsProfilePresenter;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetPicturesById;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by jose on 04/05/2017.
 */

public class ProfilePresenter extends AbsProfilePresenter {

    private final Context context;
    private UIComponent component;
    @Inject
    UserSharedImp userSharedImp;
    @Inject
    GetUserBDImp getUserBDImp;

    public ProfilePresenter(Context context) {
        this.context = context;
        component().inject(this);
    }

    @Override
    public void initialize() {
        view.showLoading();
        view.showName();
        view.showBackground();
        view.showProfile();
        view.showUsername();
        view.showDescription();
        view.showPhotos();
        view.checkSocialPrivacity();
        view.hideLoading();
    }

    @Override
    public void resume() {
        view.showBackground();
        view.showName();
        view.showProfile();
        view.showUsername();
        view.showDescription();
        view.updateRecycler();
        view.checkSocialPrivacity();
        view.hideLoading();
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onLogOutClicked() {
        navigator.navigateToLogin();
    }

    @Override
    public void onBackClicked() {
        navigator.navigateToBack();
    }

    @Override
    public void onEditClicked() {
        navigator.navigateToEdit();
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
    public void onPictureClicked(Picture picture) {
        navigator.navigateToPicture(picture);
    }

    @Override
    public void updatePictures() {
        PicturesByIdInteractor getPicturesById = new PicturesByIdInteractor(new PicturesByIdApiImp(userSharedImp.getUserID()),
                new MainThreadImp(), new ThreadExecutor());
        getPicturesById.getPictures(new GetPicturesById.Listener() {
            @Override
            public void onNoInternetAvailable() {
                view.showNoInternetAvailable();

            }

            @Override
            public void onError(Exception e) {
                view.showError(e);
            }

            @Override
            public void onPicturesReceived(List<Picture> pictures) {
                getUserBDImp.deleteUserPictures(userSharedImp.getUserID());
                for (Picture picture : pictures) {
                    getUserBDImp.insertUserPicture(picture);
                }
            }
        });
    }

    @Override
    public void updateSavedPictures() {
        PicturesByIdInteractor getPicturesById = new PicturesByIdInteractor(new PicturesSavedApiImp(userSharedImp.getUserID()), new MainThreadImp(), new ThreadExecutor());
        getPicturesById.getPictures(new GetPicturesById.Listener() {
            @Override
            public void onNoInternetAvailable() {
                view.showNoInternetAvailable();
            }

            @Override
            public void onError(Exception e) {
                view.showError(e);
            }

            @Override
            public void onPicturesReceived(List<Picture> pictures) {
                getUserBDImp.deleteUserSavedPictures(userSharedImp.getUserID());
                for (Picture picture : pictures) {
                    getUserBDImp.insertUserSavedPicture(picture);
                }
            }
        });
    }

    @Override
    public void onSaveClicked() {
        navigator.navigateToPicturesSaved();
    }


    public UIComponent component() {
        if (component == null) {
            component = DaggerUIComponent.builder()
                    .rootComponent(((App) context.getApplicationContext()).getComponent())
                    .uIModule(new UIModule(context.getApplicationContext()))
                    .mainModule(((App) context.getApplicationContext()).getMainModule())
                    .build();
        }
        return component;
    }
}

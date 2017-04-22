package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Avatar;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsAddPhoto;
import com.albaradocompany.jose.proyect_meme_clean.usecase.GetAvatars;

import java.util.List;

/**
 * Created by jose on 20/04/2017.
 */

public class AddPhotoPresenter extends AbsAddPhoto {
    Context context;
    GetAvatars getAvatars;

    public AddPhotoPresenter(Context context, GetAvatars getAvatars) {
        this.context = context;
        this.getAvatars = getAvatars;
    }

    @Override
    public void initialize() {
        view.showLoading();
        getAvatars.getAvatars(new GetAvatars.Listener() {
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
            public void onAvatarsReceived(List<Avatar> avatarList) {
                view.hideLoading();
                view.showAvatarList(avatarList);
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

    @Override
    public void onTabCameraClicked() {
        view.showCameraLayout();
    }

    @Override
    public void onTabAvatarClicked() {
        view.showAvatarLayout();
    }

    @Override
    public void onTakeFromCameraClicked() {
        navigator.navigateToCamera();
    }

    @Override
    public void onTakeFromGalleryClicked() {
        navigator.navigateToGallery();
    }

    @Override
    public void onAvatarClicked() {
        navigator.navigateToSignup();
    }
}

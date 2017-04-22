package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Avatar;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

import java.util.List;

/**
 * Created by jose on 20/04/2017.
 */

public abstract class AbsAddPhoto extends Presenter<AbsAddPhoto.View, AbsAddPhoto.Navigator> {
    public abstract void onTabCameraClicked();

    public abstract void onTabAvatarClicked();

    public abstract void onTakeFromCameraClicked();

    public abstract void onTakeFromGalleryClicked();

    public abstract void onAvatarClicked();

    public interface View {
        void showCameraLayout();

        void showAvatarLayout();

        void showLoading();

        void hideLoading();

        void showAvatarList(List<Avatar> avatarList);

        void showNoInternetAvailable();

        void showError(Exception e);
    }

    public interface Navigator {
        void navigateToCamera();

        void navigateToGallery();

        void navigateToSignup(); //when user click any avatar back to signup
    }
}

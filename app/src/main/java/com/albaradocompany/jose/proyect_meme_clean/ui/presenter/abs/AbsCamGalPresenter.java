package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

/**
 * Created by jose on 03/05/2017.
 */

public abstract class AbsCamGalPresenter extends Presenter<AbsCamGalPresenter.View, AbsCamGalPresenter.Navigator> {
    public abstract void onTakeFromCameraClicked();

    public abstract void onTakeFromGalleryClicked();

    public interface View {
        void hideOptions();

        void showOptions();
    }

    public interface Navigator {
        void navigateToCamera();

        void navigateToGallery();
    }
}

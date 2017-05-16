package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.SavedPicturesBD;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

import java.util.List;

/**
 * Created by jose on 16/05/2017.
 */

public abstract class AbsSavedPicturesPresenter extends Presenter<AbsSavedPicturesPresenter.View, AbsSavedPicturesPresenter.Navigator> {
    public abstract void onImageClicked(Picture picture);

    public abstract void onBackClicked();

    public interface View {
        void showListPhotos(List<Picture> userSavedPictures);
    }

    public interface Navigator {
        void navigateToPictureDetail(Picture picture);

        void navigateToBack();
    }
}

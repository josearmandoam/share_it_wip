package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import android.graphics.Bitmap;
import android.net.Uri;

import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

/**
 * Created by jose on 23/05/2017.
 */

public abstract class AbsNewPicturePresenter extends Presenter<AbsNewPicturePresenter.View, AbsNewPicturePresenter.Navigator> {
    public abstract void onTakeFromCameraClicked();

    public abstract void onTakeFromGalleryClicked();

    public abstract void onBitmapReceivedFromCamera(Bitmap bitmap);

    public abstract void onUriReceivedeFromGallery(Uri data);

    public interface View {
    }

    public interface Navigator {
        void navigateToCamera();

        void navigateToGallery();

        void navigateToPictureDetail(Bitmap bitmap);

        void navigateToPictureDetail(Uri data);
    }
}

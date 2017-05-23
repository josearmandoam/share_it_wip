package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import android.graphics.Bitmap;
import android.net.Uri;

import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

/**
 * Created by jose on 23/05/2017.
 */

public abstract class AbsUploadPresenter extends Presenter<AbsUploadPresenter.View, AbsUploadPresenter.Navigator> {
    public abstract void onUriReceivedFromGallery(Uri uri);

    public abstract void onBitmapReceivedFromCamera(Bitmap bm);

    public abstract void onBackClicked();

    public abstract void onCheckClicked(Bitmap bitmap, String userID, String description);

    public interface View {
        void showPicture(Bitmap bm);

        void showPicture(Uri uri);

        void showNoInternetAvailable();

        void showError(Exception e);

        void showSuccess();

        void showFailure();

        void showLoading();

        void hideLoading();
    }

    public interface Navigator {
        void navigateToBack();

    }
}

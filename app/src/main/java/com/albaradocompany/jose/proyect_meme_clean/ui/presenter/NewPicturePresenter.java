package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsNewPicturePresenter;

/**
 * Created by jose on 23/05/2017.
 */

public class NewPicturePresenter extends AbsNewPicturePresenter {
    Context context;

    public NewPicturePresenter(Context context) {
        this.context = context;
    }

    @Override
    public void initialize() {

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
    public void onTakeFromCameraClicked() {
        navigator.navigateToCamera();
    }

    @Override
    public void onTakeFromGalleryClicked() {
        navigator.navigateToGallery();
    }

    @Override
    public void onBitmapReceivedFromCamera(Bitmap bitmap) {
        navigator.navigateToPictureDetail(bitmap);
    }

    @Override
    public void onUriReceivedeFromGallery(Uri data) {
        navigator.navigateToPictureDetail(data);
    }
}

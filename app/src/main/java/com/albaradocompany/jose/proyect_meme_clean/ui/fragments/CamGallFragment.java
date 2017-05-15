package com.albaradocompany.jose.proyect_meme_clean.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerUIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIModule;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.EditProfileActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupOneActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupThreeActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupTwoActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.CamGalPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsCamGalPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by jose on 03/05/2017.
 */

public class CamGallFragment extends Fragment implements AbsCamGalPresenter.View, AbsCamGalPresenter.Navigator {
    private static final int ACTION_SIGNUP = 0;
    private static final int ACTION_EDIT_PROFILE = 1;
    private static final int ACTION_EDIT_BACKGROUND = 2;
    int action;
    @BindView(R.id.cam_gall_layout_camera)
    RelativeLayout layout;
    @BindView(R.id.cam_gall_pbr)
    ProgressBar pbr;

    @Inject
    UserSharedImp userSharedImp;

    @OnClick(R.id.cam_gall_fromcamera)
    public void onTakeFromCameraClicked(View view) {
        presenter.onTakeFromCameraClicked();
    }

    @OnClick(R.id.cam_gall_fromgallery)
    public void onTakeFromGalleryClicked(View view) {
        presenter.onTakeFromGalleryClicked();
    }

    private UIComponent component;
    AbsCamGalPresenter presenter;
    Activity activity;

    public CamGallFragment(Activity activity, int action) {
        this.activity = activity;
        this.action = action;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    private void initialize() {
        component().inject(this);
        intializePresenter();
    }

    private void intializePresenter() {
        presenter = new CamGalPresenter();
        presenter.setView(this);
        presenter.setNavigator(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_cam, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void hideOptions() {
        layout.setVisibility(View.GONE);
        pbr.setVisibility(View.VISIBLE);
    }

    @Override
    public void showOptions() {
        layout.setVisibility(View.VISIBLE);
        pbr.setVisibility(View.GONE);
    }

    @Override
    public void navigateToCamera() {
        openCamera();
    }

    public void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, BuildConfig.ACTION_CAMERA);
    }

    @Override
    public void navigateToGallery() {
        openGallery();
    }

    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, BuildConfig.ACTION_GALERY);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BuildConfig.ACTION_GALERY) {
            if (resultCode == RESULT_OK) {
                updateProfileImageFromGallery(data);
            }
        } else {
            if (requestCode == BuildConfig.ACTION_CAMERA) {
                if (resultCode == RESULT_OK) {
                    updateProfileImageFromCamera(data);
                }
            }
        }
    }

    private void updateProfileImageFromGallery(Intent data) {
        cleanBitmapReceived();
        hideOptions();
        switch (action) {
            case ACTION_SIGNUP:
                updateProfileSignupFromGallery(data);
                break;
            case ACTION_EDIT_PROFILE:
                updateEditProfileFromGallery(data);
                break;
            case ACTION_EDIT_BACKGROUND:
                updateEditBackgroundFromGallery(data);
                break;
        }
    }

    private void updateEditBackgroundFromGallery(Intent data) {
        userSharedImp.deleteImageProfile();
        EditProfileActivity.backgroundUriReceived = data.getData();
        userSharedImp.photoStateTaken("true");
        userSharedImp.saveBackgroundChanges("true");
        activity.finish();
    }

    private void updateEditProfileFromGallery(Intent data) {
        userSharedImp.deleteImageProfile();
        EditProfileActivity.profileUriReceived = data.getData();
        userSharedImp.photoStateTaken("true");
        userSharedImp.saveProfileChanges("true");
        activity.finish();
    }

    private void updateProfileSignupFromGallery(Intent data) {
        userSharedImp.deleteImageProfile();
        SignupOneActivity.uriReceived = data.getData();
        SignupTwoActivity.uriReceived = data.getData();
        SignupThreeActivity.uriReceived = data.getData();
        userSharedImp.photoStateTaken("true");
        activity.finish();
    }

    private void cleanBitmapReceived() {
        SignupOneActivity.bitmapReceived = null;
        SignupTwoActivity.bitmapReceived = null;
        SignupThreeActivity.bitmapReceived = null;
        EditProfileActivity.backgroundBitmapReceived = null;
        EditProfileActivity.profileBitmapReceived = null;

        userSharedImp.saveProfileFTPSelected("false");
        userSharedImp.saveBackgroundFTPSelected("false");

        userSharedImp.saveProfileChanges("false");
        userSharedImp.saveBackgroundChanges("false");

    }

    private void updateProfileImageFromCamera(Intent data) {
        cleanUriReceived();
        hideOptions();
        switch (action) {
            case ACTION_SIGNUP:
                updateSignupFromCamera(data);
                break;
            case ACTION_EDIT_PROFILE:
                updateEditProfileFromCamera(data);
                break;
            case ACTION_EDIT_BACKGROUND:
                updateEditBackgroundFromCamera(data);
                break;
        }
    }

    private void updateEditBackgroundFromCamera(Intent data) {
        userSharedImp.deleteImageProfile();
        Bundle extras3 = data.getExtras();
        EditProfileActivity.backgroundBitmapReceived = (Bitmap) extras3.get("data");
        userSharedImp.photoStateTaken("true");
        userSharedImp.saveBackgroundFTPSelected("false");
        userSharedImp.saveBackgroundChanges("true");
        activity.finish();
    }

    private void updateEditProfileFromCamera(Intent data) {
        userSharedImp.deleteImageProfile();
        Bundle extras2 = data.getExtras();
        EditProfileActivity.profileBitmapReceived = (Bitmap) extras2.get("data");
        userSharedImp.photoStateTaken("true");
        userSharedImp.saveProfileFTPSelected("false");
        userSharedImp.saveProfileChanges("true");
        activity.finish();
    }

    private void updateSignupFromCamera(Intent data) {
        userSharedImp.deleteImageProfile();
        Bundle extras = data.getExtras();
        SignupOneActivity.bitmapReceived = (Bitmap) extras.get("data");
        SignupTwoActivity.bitmapReceived = (Bitmap) extras.get("data");
        SignupThreeActivity.bitmapReceived = (Bitmap) extras.get("data");
        userSharedImp.photoStateTaken("true");
        activity.finish();
    }

    private void cleanUriReceived() {
        SignupOneActivity.uriReceived = null;
        SignupTwoActivity.uriReceived = null;
        SignupThreeActivity.uriReceived = null;
        EditProfileActivity.backgroundUriReceived = null;
        EditProfileActivity.profileUriReceived = null;

        userSharedImp.saveProfileFTPSelected("false");
        userSharedImp.saveBackgroundFTPSelected("false");

        userSharedImp.saveProfileChanges("false");
        userSharedImp.saveBackgroundChanges("false");
    }

    public UIComponent component() {
        if (component == null) {
            component = DaggerUIComponent.builder()
                    .rootComponent(((App) activity.getApplication()).getComponent())
                    .uIModule(new UIModule(activity.getApplicationContext()))
                    .mainModule(((App) activity.getApplication()).getMainModule())
                    .build();
        }
        return component;
    }

}

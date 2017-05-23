package com.albaradocompany.jose.proyect_meme_clean.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.UploadActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.NewPicturePresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsNewPicturePresenter;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by jose on 20/05/2017.
 */

public class NewPictureFragment extends Fragment implements AbsNewPicturePresenter.View, AbsNewPicturePresenter.Navigator {
    @OnClick(R.id.new_picture_btn_from_camera)
    public void onFromCameraClicked(View view) {
        presenter.onTakeFromCameraClicked();
    }

    @OnClick(R.id.new_picture_btn_from_gallery)
    public void onFromGalleryClicked(View view) {
        presenter.onTakeFromGalleryClicked();
    }

    AbsNewPicturePresenter presenter;

    public NewPictureFragment() {
    }

    public static NewPictureFragment newInstance() {

        Bundle args = new Bundle();

        NewPictureFragment fragment = new NewPictureFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_picture, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();

    }

    private void initialize() {
        presenter = new NewPicturePresenter(getActivity().getApplicationContext());
        presenter.setView(this);
        presenter.setNavigator(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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

    @Override
    public void navigateToPictureDetail(Bitmap bitmap) {
        openPictureDetailWithBitmap(bitmap);
    }

    private void openPictureDetailWithBitmap(Bitmap bitmap) {
        Intent intent = new Intent(getActivity(), UploadActivity.class);
        intent.putExtra("bitmap", bitmap);
        startActivity(intent);
    }

    @Override
    public void navigateToPictureDetail(Uri data) {
        Intent intent = new Intent(getActivity(), UploadActivity.class);
        intent.putExtra("uri", data);
        startActivity(intent);
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
//                updateProfileImageFromGallery(data);
                presenter.onUriReceivedeFromGallery(data.getData());
            }
        } else {
            if (requestCode == BuildConfig.ACTION_CAMERA) {
                if (resultCode == RESULT_OK) {
//                    updateProfileImageFromCamera(data);
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    presenter.onBitmapReceivedFromCamera(bitmap);
                }
            }
        }
    }
}

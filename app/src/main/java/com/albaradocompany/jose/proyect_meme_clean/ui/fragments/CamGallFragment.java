package com.albaradocompany.jose.proyect_meme_clean.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupOneActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupThreeActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupTwoActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.CamGalPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsCamGalPresenter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by jose on 03/05/2017.
 */

public class CamGallFragment extends Fragment implements AbsCamGalPresenter.View, AbsCamGalPresenter.Navigator {
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
                hideOptions();
//                savePhotoFromGalery(data);
                switch (action) {
                    case 0:
                        userSharedImp.deleteImageProfile();
                        SignupOneActivity.uriReceived = data.getData();
                        SignupTwoActivity.uriReceived = data.getData();
                        SignupThreeActivity.uriReceived = data.getData();
                        userSharedImp.photoStateTaken("true");
                        activity.finish();
                        break;
                }

            }
        } else {
            if (requestCode == BuildConfig.ACTION_CAMERA) {
                if (resultCode == RESULT_OK) {
                    hideOptions();
//                    savePhotoFromCamera(data);
                    switch (action) {
                        case 0:
                            userSharedImp.deleteImageProfile();
                            Bundle extras = data.getExtras();
                            SignupOneActivity.bitmapReceived = (Bitmap) extras.get("data");
                            SignupTwoActivity.bitmapReceived = (Bitmap) extras.get("data");
                            SignupThreeActivity.bitmapReceived = (Bitmap) extras.get("data");
                            userSharedImp.photoStateTaken("true");
                            activity.finish();
                            break;
                    }

                }
            }
        }
    }

    private void savePhotoFromCamera(final Intent data) {
        new Thread(new Runnable() {
            public void run() {
                Bitmap bm = (Bitmap) data.getExtras().get("data");
                String photoName = "imagen" + getCurrentDateAndTime() + ".jpg";
                if (bm != null) {
                    String dirFotos = guardarImagen(activity, bm, photoName);
                    switch (action) {
                        case 0:
                            userSharedImp.savePhotoTaken(dirFotos);
                            userSharedImp.saveUserpPath(dirFotos);
                            break;
                        case 1: /*Profile Case*/
                            userSharedImp.saveProfile(dirFotos);
                            userSharedImp.saveProfileChanges("true");
                            break;
                        case 2: /*Background Case*/
                            userSharedImp.saveBackground(dirFotos);
                            userSharedImp.saveBackgroundChanges("true");
                            break;
                    }
                }
                activity.finish();
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        hideOptions();
                    }
                });
            }


        }).start();
    }

    private void savePhotoFromGalery(final Intent data) {
        new Thread(new Runnable() {
            public void run() {
                Uri fotoGaleria = data.getData();
                try {
                    InputStream is = activity.getContentResolver().openInputStream(fotoGaleria);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    Bitmap bm = BitmapFactory.decodeStream(bis);
                    String photoName = "imagen" + getCurrentDateAndTime() + ".jpg";
                    if (bm != null) {
                        String dirFotos = guardarImagen(activity, bm, photoName);
                        switch (action) {
                            case 0:
                                userSharedImp.savePhotoTaken(dirFotos);
                                userSharedImp.saveUserpPath(dirFotos);
                                break;
                            case 1:/*Profile case*/
                                userSharedImp.saveProfile(dirFotos);
                                userSharedImp.saveProfileChanges("true");
                                break;
                            case 2:/*Backgrund case*/
                                userSharedImp.saveBackground(dirFotos);
                                userSharedImp.saveBackgroundChanges("true");
                                break;
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                activity.finish();
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        layout.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
    }

    public String guardarImagen(Context context, Bitmap b, String name) {
        ContextWrapper cw = new ContextWrapper(activity.getApplicationContext());
        File directory = new File(context.getFilesDir() + "/user_imagenes");
        directory.mkdirs();
        File mypath = new File(directory, name);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            b.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.getAbsolutePath();
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

    public String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-­ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }
}

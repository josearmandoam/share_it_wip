package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.AvatarComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.AvatarModule;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerAvatarComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerSignupComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.SignupComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.SignupModule;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Avatar;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.interactor.AvatarInteractor;
import com.albaradocompany.jose.proyect_meme_clean.ui.adaptor.AvatarsRecyclerAdapter;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.ConfirmAvatarDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.AddPhotoPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsAddPhoto;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jose on 20/04/2017.
 */

public class AddPhotoActivty extends BaseActivty implements AbsAddPhoto.Navigator, AbsAddPhoto.View {
    @BindView(R.id.add_photo_camera)
    ImageButton camera;
    @BindView(R.id.add_photo_avatar)
    ImageButton avatar;
    @BindView(R.id.add_photo_separator2)
    ImageView cameraIndicator;
    @BindView(R.id.add_photo_separator1)
    ImageView avatarIndicator;
    @BindView(R.id.add_photo_layout_camera)
    RelativeLayout layoutCamera;
    @BindView(R.id.add_photo_layout_avatar)
    RelativeLayout layoutAvatar;
    @BindView(R.id.add_photo_listAvatar)
    RecyclerView recyclerView;
    @BindView(R.id.add_photo_fromcamera)
    TextView fromCamera;
    @BindView(R.id.add_photo_fromgallery)
    TextView fromGallery;
    @BindView(R.id.add_photo_pbr)
    ProgressBar pbr;
    @BindView(R.id.add_photo_camera_pbr)
    ProgressBar pbt_camera;

    @BindColor(R.color.color_login)
    int ppColor;
    @BindColor(R.color.light_gray)
    int ssColor;
    @BindDrawable(R.drawable.camera_dark)
    Drawable camera_dark;
    @BindDrawable(R.drawable.camera_light)
    Drawable camera_light;
    @BindDrawable(R.drawable.avatar_dark)
    Drawable avatar_dark;
    @BindDrawable(R.drawable.avatar_light)
    Drawable avatar_light;

    @OnClick(R.id.add_photo_fromcamera)
    public void onFromCamaraClicked(View view) {
        presenter.onTakeFromCameraClicked();
    }

    @OnClick(R.id.add_photo_fromgallery)
    public void onFromGalleryClicked(View view) {
        presenter.onTakeFromGalleryClicked();
    }

    @OnClick(R.id.add_photo_camera)
    public void onCamaraClicked(View view) {
        presenter.onTabCameraClicked();
    }

    @OnClick(R.id.add_photo_avatar)
    public void onAvatarClicked(View view) {
        if (layoutCamera.isShown()) {
            presenter.onTabAvatarClicked();
        }
    }

    @OnClick(R.id.add_photo_next)
    public void onNextClicked(View view) {
        presenter.onTabAvatarClicked();
    }

    @OnClick(R.id.add_photo_back)
    public void onBackClicked(View view) {
        presenter.onTabCameraClicked();
    }

    @Inject
    AvatarInteractor avatarInteractor;
    @Inject
    UserSharedImp userSharedImp;

    AbsAddPhoto presenter;
    SignupComponent component;
    AvatarsRecyclerAdapter adapter;

    AvatarsRecyclerAdapter.OnAvatarClicked onAvatarClicked = new AvatarsRecyclerAdapter.OnAvatarClicked() {
        @Override
        public void onAvatarClicked(Avatar avatar) {
            presenter.onAvatarClicked(avatar);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component().inject(this);
        initializePresenter();
    }

    private void initializeRecyclerList(List<Avatar> list) {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new AvatarsRecyclerAdapter(this, list, onAvatarClicked);
        recyclerView.setAdapter(adapter);
    }

    private void initializePresenter() {
        presenter = new AddPhotoPresenter(this, avatarInteractor);
        presenter.setView(this);
        presenter.setNavigator(this);
        presenter.initialize();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_photo;
    }

    @Override
    protected boolean hideToolbar() {
        return true;
    }

    @Override
    public void showCameraLayout() {
        cameraUp();
    }

    @Override
    public void showAvatarLayout() {
        avatarUp();
    }

    @Override
    public void showLoading() {
        pbr.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        pbr.setVisibility(View.GONE);
    }

    @Override
    public void showAvatarList(List<Avatar> avatarList) {
        initializeRecyclerList(avatarList);
    }

    @Override
    public void showNoInternetAvailable() {

    }

    @Override
    public void showError(Exception e) {

    }

    @Override
    public void showAvatarClicked(Avatar avatar) {
        openAvatarDialog(avatar);
    }

    private void openAvatarDialog(Avatar avatar) {
        ConfirmAvatarDialog c = new ConfirmAvatarDialog(this, avatar);
    }

    @Override
    public void navigateToCamera() {
        openCamera();
    }

    public void openCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        this.startActivityForResult(intent, BuildConfig.ACTION_CAMERA);
    }

    @Override
    public void navigateToGallery() {
        openGallery();
    }

    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        this.startActivityForResult(intent, BuildConfig.ACTION_GALERY);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BuildConfig.ACTION_GALERY) {
            if (resultCode == RESULT_OK) {
                savePhotoFromGalery(data);
            }
        } else {
            if (requestCode == BuildConfig.ACTION_CAMERA) {
                if (resultCode == RESULT_OK) {
                    savePhotoFromCamera(data);
                }
            }
        }
    }

    private void savePhotoFromCamera(final Intent data) {
        pbt_camera.setVisibility(View.VISIBLE);
        layoutCamera.setVisibility(View.GONE);
        new Thread(new Runnable() {
            public void run() {
                    Bitmap bm = (Bitmap) data.getExtras().get("data");
                    String photoName = "imagen" + AddPhotoActivty.this.getCurrentDateAndTime() + ".jpg";
                    if (bm != null) {
                        String dirFotos = guardarImagen(AddPhotoActivty.this, bm, photoName);
                        userSharedImp.savePhotoTaken(dirFotos);
                        userSharedImp.saveUserpPath(dirFotos);
                    }
                    AddPhotoActivty.this.finish();
                runOnUiThread(new Runnable() {
                    public void run() {
                        pbt_camera.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
    }

    private void savePhotoFromGalery(final Intent data) {
        pbt_camera.setVisibility(View.VISIBLE);
        layoutCamera.setVisibility(View.GONE);
        new Thread(new Runnable() {
            public void run() {
                Uri fotoGaleria = data.getData();
                try {
                    InputStream is = getContentResolver().openInputStream(fotoGaleria);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    Bitmap bm = BitmapFactory.decodeStream(bis);
                    String photoName = "imagen" + AddPhotoActivty.this.getCurrentDateAndTime() + ".jpg";
                    if (bm != null) {
                        String dirFotos = guardarImagen(AddPhotoActivty.this, bm, photoName);
                        userSharedImp.savePhotoTaken(dirFotos);
                        userSharedImp.saveUserpPath(dirFotos);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                AddPhotoActivty.this.finish();
                runOnUiThread(new Runnable() {
                    public void run() {
                        pbt_camera.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
    }

    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-­ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public String guardarImagen(Context context, Bitmap b, String name) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = new File(context.getFilesDir() + "/user_imagenes");
        directory.mkdirs();
        //File directory = cw.getDir("Directorio_imagen", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, name);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
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

    private void cameraUp() {
        layoutAvatar.setVisibility(View.GONE);
        layoutCamera.setVisibility(View.VISIBLE);
        camera.setImageDrawable(camera_dark);
        avatar.setImageDrawable(avatar_light);
        cameraIndicator.setBackgroundColor(ppColor);
        avatarIndicator.setBackgroundColor(ssColor);
    }

    private void avatarUp() {
        layoutCamera.setVisibility(View.GONE);
        layoutAvatar.setVisibility(View.VISIBLE);
        camera.setImageDrawable(camera_light);
        avatar.setImageDrawable(avatar_dark);
        cameraIndicator.setBackgroundColor(ssColor);
        avatarIndicator.setBackgroundColor(ppColor);
    }

    public SignupComponent component() {
        if (component == null) {
            component = DaggerSignupComponent.builder()
                    .rootComponent(((App) getApplication()).getComponent())
                    .signupModule(new SignupModule(getApplicationContext()))
                    .mainModule(((App) getApplication()).getMainModule())
                    .build();
        }
        return component;
    }
}

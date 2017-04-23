package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.AvatarComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.AvatarModule;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerAvatarComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Avatar;
import com.albaradocompany.jose.proyect_meme_clean.interactor.AvatarInteractor;
import com.albaradocompany.jose.proyect_meme_clean.ui.adaptor.AvatarsRecyclerAdapter;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.ConfirmAvatarDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.AddPhotoPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsAddPhoto;

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
    SwipeRefreshLayout layoutAvatar;
    @BindView(R.id.add_photo_listAvatar)
    RecyclerView recyclerView;
    @BindView(R.id.add_photo_fromcamera)
    TextView fromCamera;
    @BindView(R.id.add_photo_fromgallery)
    TextView fromGallery;
    @BindView(R.id.add_photo_pbr)
    ProgressBar pbr;

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
        presenter.onTabAvatarClicked();
    }

    @Inject
    AvatarInteractor avatarInteractor;

    AbsAddPhoto presenter;
    AvatarComponent component;
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

    }

    @Override
    public void navigateToGallery() {

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

    private AvatarComponent component() {
        if (component == null) {
            component = DaggerAvatarComponent.builder()
                    .rootComponent(((App) getApplication()).getComponent())
                    .avatarModule(new AvatarModule(getApplicationContext()))
                    .mainModule(((App) getApplication()).getMainModule())
                    .build();
        }
        return component;
    }
}

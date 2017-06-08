package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD.GetUserBDImp;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerUIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIModule;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.ui.adaptor.SavedPicturesRecyclerAdapter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.SavedPicturesPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSavedPicturesPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SavedPicturesActivity extends BaseActivty implements AbsSavedPicturesPresenter.View, AbsSavedPicturesPresenter.Navigator {

    @BindView(R.id.saved_pictures_recycler)
    RecyclerView recycler;
    @BindView(R.id.saved_pictures_lyt_empty_photos)
    RelativeLayout empty_saved_photos;

    AbsSavedPicturesPresenter presenter;
    private UIComponent component;
    SavedPicturesRecyclerAdapter adapter;

    @Inject
    GetUserBDImp getUserBD;

    SavedPicturesRecyclerAdapter.onClickListener onClickListener = new SavedPicturesRecyclerAdapter.onClickListener() {
        @Override
        public void onImageClicked(Picture picture) {
            presenter.onImageClicked(picture);
        }
    };

    @OnClick(R.id.saved_pictures_ibtn_back)
    public void onBackClicked(View view) {
        presenter.onBackClicked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.resume();
    }


    private void initialize() {
        component().inject(this);

        presenter = new SavedPicturesPresenter(this);
        presenter.setView(this);
        presenter.setNavigator(this);
        presenter.initialize();


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_saved_pictures;
    }

    @Override
    protected boolean hideToolbar() {
        return true;
    }

    public UIComponent component() {
        if (component == null) {
            component = DaggerUIComponent.builder()
                    .rootComponent(((App) getApplication()).getComponent())
                    .uIModule(new UIModule(getApplicationContext()))
                    .mainModule(((App) getApplication()).getMainModule())
                    .build();
        }
        return component;
    }

    @Override
    public void showListPhotos(List<Picture> userSavedPictures) {
        if (userSavedPictures.isEmpty()) {
            recycler.setVisibility(View.GONE);
            empty_saved_photos.setVisibility(View.VISIBLE);
        } else {
            if (adapter != null) {
                adapter.clear();
                adapter.setList(userSavedPictures);
                adapter.notifyDataSetChanged();
            } else {
                adapter = new SavedPicturesRecyclerAdapter(this, userSavedPictures, onClickListener);
                recycler.setAdapter(adapter);
                recycler.setLayoutManager(new GridLayoutManager(this, 3));
                recycler.setHasFixedSize(true);
            }
            recycler.setVisibility(View.VISIBLE);
            empty_saved_photos.setVisibility(View.GONE);
        }
    }

    @Override
    public void navigateToPictureDetail(Picture picture) {
        openPictureDetail(this, picture);
    }

    @Override
    public void navigateToBack() {
        onBackPressed();
    }

    public void openPictureDetail(Context ctx, Picture picture) {
        Intent intent = new Intent(ctx, PictureActivity.class);
        intent.putExtra("image", picture);
        ctx.startActivity(intent);
    }
}

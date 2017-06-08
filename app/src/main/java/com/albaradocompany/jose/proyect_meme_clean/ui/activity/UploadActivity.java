package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerUIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIModule;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.UploadPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsUploadPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.view.ShowSnackBarImp;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class UploadActivity extends BaseActivty implements AbsUploadPresenter.View, AbsUploadPresenter.Navigator {
    @BindView(R.id.upload_iv_image)
    ImageView image;
    @BindView(R.id.upload_lyt_container)
    RelativeLayout layout;
    @BindView(R.id.upload_pbr)
    ProgressBar progressBar;
    @BindView(R.id.upload_et_description)
    EditText description;
    @BindView(R.id.upload_ibtn_check)
    ImageButton check;
    @BindString(R.string.noInternetAvailable)
    String noInternet;
    @BindDrawable(R.drawable.roundedbutton_green)
    Drawable roundedGreen;
    @BindColor(android.R.color.white)
    int whiteColor;

    private UIComponent component;
    ShowSnackBarImp showSnackBarImp;

    @Inject
    UserSharedImp userSharedImp;

    @OnClick(R.id.upload_ibtn_back)
    public void onBackClicked(View view) {
        presenter.onBackClicked();
    }

    @OnClick(R.id.upload_ibtn_check)
    public void onCheckClicked(View view) {
        presenter.onCheckClicked(((BitmapDrawable) image.getDrawable()).getBitmap(), userSharedImp.getUserID(), description.getText().toString());
    }

    AbsUploadPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    private void initialize() {
        component().inject(this);

        presenter = new UploadPresenter(this);
        presenter.setView(this);
        presenter.setNavigator(this);
        presenter.initialize();

        layout.requestFocus();

        getExtras(getIntent());
        showSnackBarImp = new ShowSnackBarImp(layout);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_upload;
    }

    @Override
    protected boolean hideToolbar() {
        return true;
    }

    private void getExtras(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Uri uri = (Uri) bundle.get("uri");
            if (uri != null)
                presenter.onUriReceivedFromGallery(uri);
            Bitmap bm = (Bitmap) bundle.get("bitmap");
            if (bm != null)
                presenter.onBitmapReceivedFromCamera(bm);
        }
    }

    @Override
    public void showPicture(Bitmap bm) {
        image.setImageBitmap(bm);
    }

    @Override
    public void showPicture(Uri uri) {
        image.setImageURI(uri);
    }

    @Override
    public void showNoInternetAvailable() {
        showSnackBarImp.show(noInternet, BuildConfig.COLOR_RED);
    }

    @Override
    public void showError(Exception e) {
        showSnackBarImp.show(e.getMessage(), BuildConfig.COLOR_RED);
    }

    @Override
    public void showSuccess() {
        check.setBackground(roundedGreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                closeUploadActivity();
            }
        }, 500);
    }

    private void closeUploadActivity() {
        finish();
    }

    @Override
    public void showFailure() {
        showSnackBarImp.show(getString(R.string.upload_failure), BuildConfig.COLOR_RED);
    }

    @Override
    public void showLoading() {
        check.setBackgroundColor(whiteColor);
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void navigateToBack() {
        onBackPressed();
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

}

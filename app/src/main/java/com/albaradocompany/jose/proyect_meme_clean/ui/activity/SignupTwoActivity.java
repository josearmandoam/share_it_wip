package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerSignupComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.SignupComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.SignupModule;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.ShowAvatarDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.SignupTwoPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSignupTwo;
import com.albaradocompany.jose.proyect_meme_clean.ui.view.ShowSnackBarImp;
import com.albaradocompany.jose.proyect_meme_clean.usecase.ShowSnackBar;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class SignupTwoActivity extends BaseActivty implements AbsSignupTwo.View, AbsSignupTwo.Navigator {
    @BindView(R.id.signup_two_button_back)
    ImageButton bBack;
    @BindView(R.id.signup_two_button_menu)
    ImageButton bMenu;
    @BindView(R.id.signup_two_button_next)
    ImageButton bNext;
    @BindView(R.id.signup_two_button_pagetwo)
    ImageButton bPagetwo;
    @BindView(R.id.signup_two_button_pagethree)
    ImageButton bPagethree;
    @BindView(R.id.signup_two_et_username)
    EditText username;
    @BindView(R.id.signup_two_et_password)
    EditText password;
    @BindView(R.id.signup_two_et_password2)
    EditText password2;
    @BindView(R.id.signup_two_image)
    ImageView image;
    @BindDrawable(R.drawable.user_default_image)
    Drawable defaultUserImage;
    @BindString(R.string.default_font)
    String text_font;
    @BindString(R.string.error_username)
    String userError;
    @BindString(R.string.error_password)
    String passwordError;
    @BindString(R.string.error_password2)
    String passwordErrorS;
    @BindString(R.string.error_password_coincidence)
    String passwordMatchError;
    @BindColor(R.color.color_login)
    int color_login;


    AbsSignupTwo presenter;
    private SignupComponent component;
    @Inject
    UserSharedImp userSharedImp;
    private ShowSnackBarImp showSnackBar;

    @OnClick(R.id.signup_two_button_back)
    public void onBackpressed(View view) {
        presenter.onBackPressed();
    }

    @OnClick(R.id.signup_two_button_next)
    public void onNextPageClicked(View view) {
        presenter.onNextPagePressed();
    }

    @OnClick(R.id.signup_two_image)
    public void onImageClicked(View view) {
        presenter.onImageClicked();
    }

    @OnClick(R.id.signup_two_button_menu)
    public void onCleanClicked(View view) {
        presenter.onCleanPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component().inject(this);
        initializePresenter();
        intialize();
    }

    private void intialize() {
        showSnackBar = new ShowSnackBarImp(this);
    }

    private void checkDataSaved() {
        username.setText(userSharedImp.getUserUsernamedSaved());
        password.setText(userSharedImp.getUserPasswordSaved());
        password2.setText(userSharedImp.getUserPassword2Saved());
    }

    private void checkUserImage() {
        if (userSharedImp.isAvatarTaken()) {
            Picasso.with(this).load(userSharedImp.getUserAvatar()).into(image);
        } else {
            userSharedImp.showUserPhoto(image, userSharedImp.getUserPhoto());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_signup_two;
    }

    @Override
    protected boolean hideToolbar() {
        return true;
    }

    @Override
    public void hideSignupTwo() {
        onBackPressed();
    }

    @Override
    public void loadUserImage() {
        checkUserImage();
    }

    @Override
    public void showImage() {
        ShowAvatarDialog showAvatarDialog = new ShowAvatarDialog(this, 2);
    }

    @Override
    public void cleanFields() {
        clean();
    }

    @Override
    public void checkInfoSaved() {
        checkDataSaved();
    }

    private void clean() {
        username.setText("");
        password.setText("");
        password2.setText("");
        userSharedImp.deleteSigntwoData();
    }

    @Override
    public void navigatePageThree() {
        if (checkFields()) {
            userSharedImp.saveSigntwoData(username.getText().toString(), password.getText().toString());
            openSignupThreeActivity(this);
        }
    }

    private boolean checkFields() {
        if (username.getText().toString().isEmpty()) {
            showSnackBar.show(userError, Color.RED);
            return false;
        }
        if (password.getText().toString().isEmpty()) {
            showSnackBar.show(passwordError, Color.RED);
            return false;
        }
        if (password2.getText().toString().isEmpty()) {
            showSnackBar.show(passwordErrorS, Color.RED);
            return false;
        }
        if (!password.getText().toString().equals(password2.getText().toString())) {
            showSnackBar.show(passwordMatchError, Color.RED);
            return false;
        }
        return true;
    }

    private void initializePresenter() {
        presenter = new SignupTwoPresenter(this);
        presenter.setNavigator(this);
        presenter.setView(this);
        presenter.initialize();
    }

    public static void openSignupThreeActivity(Context ctx) {
        Intent intent = new Intent(ctx, SignupThreeActivity.class);
        ctx.startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.resume();
   }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private SignupComponent component() {
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

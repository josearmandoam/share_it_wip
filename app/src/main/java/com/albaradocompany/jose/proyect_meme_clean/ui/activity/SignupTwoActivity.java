package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.ConfirmAvatarDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.ShowAvatarDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.picasso.RoundedTransformation;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.SignupTwoPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSignupTwo;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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


    AbsSignupTwo presenter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializePresenter();
    }

    private void checkDataSaved() {
        sharedPreferences = this.getSharedPreferences(SignupTwoActivity.class.getName(), Context.MODE_PRIVATE);
        username.setText(sharedPreferences.getString(BuildConfig.USER_USERNAME, ""));
        password.setText(sharedPreferences.getString(BuildConfig.USER_PASSWORD, ""));
        password2.setText(sharedPreferences.getString(BuildConfig.USER_PASSWORD, ""));
    }

    private void checkUserImage() {
        sharedPreferences = this.getSharedPreferences(ConfirmAvatarDialog.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String isAvatarSelected = sharedPreferences.getString(BuildConfig.IS_SELECTED_AVATAR, "false");
        if (isAvatarSelected.equals("true")) {
            String avatarPath = sharedPreferences.getString(BuildConfig.AVATAR_IMAGE_PATH, "");
            String avatarId = sharedPreferences.getString(BuildConfig.AVATAR_IMAGE_ID, "");
            String avatarDescription = sharedPreferences.getString(BuildConfig.AVATAR_IMAGE_DESCRIPTION, "");
            Picasso.with(this)
                    .load(avatarPath)
//                    .transform(new RoundedTransformation())
                    .error(defaultUserImage)
                    .into(image);
        } else {
            sharedPreferences = this.getSharedPreferences(AddPhotoActivty.class.getName(), Context.MODE_PRIVATE);
            cargarImagenPerfil(sharedPreferences.getString(BuildConfig.USER_PHOTO, ""));
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
    public void showMenu() {

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
    public void navigatePageThree() {
        if (checkFields()) {
            saveSigntwoData();
            openSignupThreeActivity(this);
        }
    }

    private void saveSigntwoData() {
        sharedPreferences = this.getSharedPreferences(SignupTwoActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.USER_USERNAME, username.getText().toString());
        editor.putString(BuildConfig.USER_PASSWORD, password.getText().toString());
        editor.apply();
    }

    private boolean checkFields() {
        if (username.getText().toString().isEmpty()) {
            showSnackBar(userError, Color.RED);
            return false;
        }
        if (password.getText().toString().isEmpty()) {
            showSnackBar(passwordError, Color.RED);
            return false;
        }
        if (password2.getText().toString().isEmpty()) {
            showSnackBar(passwordErrorS, Color.RED);
            return false;
        }
        if (!password.getText().toString().equals(password2.getText().toString())) {
            showSnackBar(passwordMatchError, Color.RED);
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
        checkDataSaved();
    }

    private void showSnackBar(String message, int color) {
        final Snackbar snackbar = Snackbar.make(this.getCurrentFocus(), message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(color);
        View view = snackbar.getView();

        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        Typeface font = Typeface.create(text_font, Typeface.BOLD);
        tv.setTypeface(font);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        snackbar.show();
    }
    private void cargarImagenPerfil(String path) {
        try {
            File f = new File(path);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            image.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            image.setImageResource(R.drawable.user_default_image);
            //e.printStackTrace();
        }
    }
}

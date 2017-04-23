package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.ConfirmAvatarDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.ShowAvatarDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.picasso.RoundedTransformation;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.SignupOnePresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSignupOne;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class SignupOneActivity extends BaseActivty implements AbsSignupOne.Navigator, AbsSignupOne.View {

    @BindView(R.id.signup_button_back)
    ImageButton bBack;
    @BindView(R.id.signup_button_menu)
    ImageButton bMenu;
    @BindView(R.id.signup_button_next)
    ImageButton bNext;
    @BindView(R.id.signup_button_pagetwo)
    ImageButton bPagetwo;
    @BindView(R.id.signup_button_pagethree)
    ImageButton bPagethree;
    @BindView(R.id.signup_et_name)
    EditText name;
    @BindView(R.id.signup_et_email)
    EditText email;
    @BindView(R.id.signup_et_lastName)
    EditText lastName;
    @BindView(R.id.signup_image_user)
    ImageView image;
    @BindDrawable(R.drawable.user_default_image)
    Drawable defaultUserImage;
    @BindString(R.string.default_font)
    String text_font;
    @BindString(R.string.error_name)
    String nameErrorMessage;
    @BindString(R.string.error_email)
    String emailErrorMessage;
    @BindString(R.string.error_last_name)
    String lastNameErrorMessage;
    @BindString(R.string.error_date_not_take)
    String dateErrorMessage;
    @BindString(R.string.error_photo_not_taken)
    String photoErrorMessage;

    AbsSignupOne presenter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean avatar;


    @OnClick(R.id.signup_button_back)
    public void onBackpressed(View view) {
        presenter.onBackPressed();
    }

    @OnClick(R.id.signup_image_user)
    public void onImage(View view) {
        presenter.onImageClicked();
    }

    @OnClick(R.id.signup_button_next)
    public void onNextPageClicked(View view) {
        presenter.onNextPagePressed();
    }

    @OnClick(R.id.signup_button_birthday)
    public void onDatePickerClicked(View view) {
        presenter.onDatePickerClicked();
    }

    @OnClick(R.id.signup_button_addPhoto)
    public void onAddPhotoClicked(View view) {
        presenter.onAddImageClicked();
    }

    @OnClick(R.id.signup_button_menu)
    public void onMenuClicked(View view) {
        presenter.onMenuPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializePresenter();
    }

    private void initializePresenter() {
        presenter = new SignupOnePresenter(this);
        presenter.setNavigator(this);
        presenter.setView(this);
        presenter.initialize();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_signup;
    }

    @Override
    protected boolean hideToolbar() {
        return true;
    }

    @Override
    public void hideSignupOne() {
        finish();
    }

    @Override
    public void showMenu() {
        openOptionsMenu();
    }

    @Override
    public void loadUserImage() {
        checkUserImage();
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
                    .transform(new RoundedTransformation())
                    .error(defaultUserImage)
                    .into(image);
        } else {
            image.setImageDrawable(defaultUserImage);
        }
    }

    @Override
    public void showDatePicker() {
        showDialogDate();
    }

    @Override
    public void showImage() {
        ShowAvatarDialog showAvatarDialog = new ShowAvatarDialog(this, 1);
    }

    private void showDialogDate() {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(SignupOneActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                saveDate(i, i1, i2);
            }
        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private String formatDate(int i2, int i1, int i) {
        String year = "" + i;
        String month;
        String day;
        if (i1 < 10) {
            month = "0" + i1;
        } else {
            month = "" + i1;
        }
        if (i2 < 10) {
            day = "0" + i2;
        } else {
            day = "" + i2;
        }
        return day + "/" + month + "/" + year;
    }

    private void saveDate(int i, int i1, int i2) {
        sharedPreferences = SignupOneActivity.this.getSharedPreferences(SignupOneActivity.class.getName(),
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.IS_SELECTED_DATE, "true");
        editor.putString(BuildConfig.USER_DATE_BIRTHDAY, formatDate(i2, (i1 + 1), i));
        editor.apply();
    }

    public static void openAddPhotoActivity(Context ctx) {
        Intent intent = new Intent(ctx, AddPhotoActivty.class);
        ctx.startActivity(intent);
    }

    @Override
    public void navigatePageTwo() {
        if (checkFields()) {
            saveSignoneData();
            openPageTwo(this);
        }
    }

    private void saveSignoneData() {
        sharedPreferences = this.getSharedPreferences(SignupOneActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.USER_NAME, name.getText().toString());
        editor.putString(BuildConfig.USER_EMAIL, email.getText().toString());
        editor.putString(BuildConfig.USER_LAST_NAME, lastName.getText().toString());
        if (avatar) {
            editor.putString(BuildConfig.USER_AVATAR, sharedPreferences.getString(BuildConfig.USER_AVATAR, ""));
        }
        editor.putString(BuildConfig.USER_DATE_BIRTHDAY, sharedPreferences.getString(BuildConfig.USER_DATE_BIRTHDAY, ""));
        editor.apply();
    }

    private boolean checkFields() {
        if (name.getText().toString().isEmpty()) {
            showSnackBar(nameErrorMessage, Color.RED);
            return false;
        }
        if (lastName.getText().toString().isEmpty()) {
            showSnackBar(lastNameErrorMessage, Color.RED);
            return false;
        }
        if (email.getText().toString().isEmpty()) {
            showSnackBar(emailErrorMessage, Color.RED);
            return false;
        }
        if (!noPhotoTaken()) {
            showSnackBar(photoErrorMessage, Color.RED);
            return false;
        }
        if (!noDateBirdtdayTaken()) {
            showSnackBar(dateErrorMessage, Color.RED);
            return false;
        }
        return true;
    }

    private boolean noDateBirdtdayTaken() {
        sharedPreferences = this.getSharedPreferences(SignupOneActivity.class.getName(), Context.MODE_PRIVATE);
        if (sharedPreferences.getString(BuildConfig.IS_SELECTED_DATE, "false").equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean noPhotoTaken() {
        sharedPreferences = this.getSharedPreferences(SignupOneActivity.class.getName(), Context.MODE_PRIVATE);
        if (sharedPreferences.getString(BuildConfig.IS_SELECTED_PHOTO, "false").equals("true")) {
            SharedPreferences avatarShared = this.getSharedPreferences(ConfirmAvatarDialog.class.getName(), Context.MODE_PRIVATE);
            if (avatarShared.getString(BuildConfig.IS_SELECTED_AVATAR, "false").equals("true")) {
                editor = sharedPreferences.edit();
                editor.putString(BuildConfig.USER_AVATAR, avatarShared.getString(BuildConfig.AVATAR_IMAGE_PATH, ""));
                editor.apply();
                avatar = true;
            }
            return true;
        } else {
            return false;
        }
    }

    public static void openPageTwo(Context ctx) {
        Intent intent = new Intent(ctx, SignupTwoActivity.class);
        ctx.startActivity(intent);
    }

    @Override
    public void navigateAddPhoto() {
        openAddPhotoActivity(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        removeSignInformation();
    }

    private void removeSignInformation() {
        sharedPreferences = this.getSharedPreferences(ConfirmAvatarDialog.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.resume();
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
}

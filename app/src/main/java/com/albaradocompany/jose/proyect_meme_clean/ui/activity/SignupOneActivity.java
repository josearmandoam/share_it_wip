package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.UserByEmailApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerUIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIModule;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UserByEmailInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.ShowAvatarDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.SignupOnePresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSignupOne;
import com.albaradocompany.jose.proyect_meme_clean.ui.view.ShowSnackBarImp;
import com.albaradocompany.jose.proyect_meme_clean.usecase.ShowSnackBar;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class SignupOneActivity extends BaseActivty implements AbsSignupOne.Navigator, AbsSignupOne.View {

    private static final int SIGNUPONE = 1;
    @BindView(R.id.signup_button_back)
    ImageButton bBack;
    @BindView(R.id.signup_btn_clean)
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
    @BindView(R.id.signup_lyt_container)
    RelativeLayout layout;
    @BindView(R.id.signup_pbr)
    ProgressBar pbr;

    @BindDrawable(R.drawable.user_default_image)
    Drawable defaultUserImage;
    @BindString(R.string.default_font)
    String textFont;
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
    @BindString(R.string.email_registred)
    String emailRegistred;
    @BindColor(R.color.color_login)
    int colorLogin;
    @BindString(R.string.noInternetAvailable)
    String noInternet;
    @BindColor(android.R.color.white)
    int white;
    @BindDrawable(R.drawable.roundedbutton)
    Drawable round;

    AbsSignupOne presenter;
    public static boolean avatar;
    private UIComponent component;
    @Inject
    UserSharedImp userSharedImp;
    private ShowSnackBar showSnackBar;
    public static Uri uriReceived;
    public static Bitmap bitmapReceived;

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
        UserByEmailInteractor interactor = new UserByEmailInteractor(new UserByEmailApiImp(email.getText().toString()), new MainThreadImp(), new ThreadExecutor());
        if (checkFields()) {
            presenter.chekImageImp(interactor);
        }
    }

    @OnClick(R.id.signup_button_birthday)
    public void onDatePickerClicked(View view) {
        presenter.onDatePickerClicked();
    }

    @OnClick(R.id.signup_button_addPhoto)
    public void onAddPhotoClicked(View view) {
        presenter.onAddImageClicked();
    }

    @OnClick(R.id.signup_btn_clean)
    public void onCleanClicked(View view) {
        presenter.onCleanPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component().inject(this);
        initializePresenter();
        initialize();
    }

    private void initialize() {
        showSnackBar = new ShowSnackBarImp(this);
    }

    private void initializePresenter() {
        presenter = new SignupOnePresenter(this);
        presenter.setNavigator(this);
        presenter.setView(this);

        image.setImageDrawable(defaultUserImage);

        layout.requestFocus();
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
        depurateSignup();
    }

    private void depurateSignup() {
        removeSignInformation();
        uriReceived = null;
        bitmapReceived = null;
        finish();
    }

    @Override
    public void cleanFields() {
        clean();
    }

    private void clean() {
        name.setText("");
        lastName.setText("");
        email.setText("");
        userSharedImp.deleteSignoneData();
    }

    @Override
    public void loadUserImage() {
        if (userSharedImp.isAvatarTaken()) {
            Picasso.with(this).load(userSharedImp.getUserAvatar()).into(image);
            uriReceived = null;
            bitmapReceived = null;
        } else {
            if (uriReceived != null)
                image.setImageURI(uriReceived);
            if (bitmapReceived != null)
                image.setImageBitmap(bitmapReceived);

        }
        if (!userSharedImp.isAvatarTaken() && uriReceived == null && bitmapReceived == null)
            image.setImageDrawable(defaultUserImage);
    }

    @Override
    public void showDatePicker() {
        showDialogDate();
    }

    @Override
    public void showImage() {
        ShowAvatarDialog showAvatarDialog = new ShowAvatarDialog(this, 1);
    }

    @Override
    public void showLoading() {
        pbr.setVisibility(View.VISIBLE);
        bNext.setBackgroundColor(white);
    }

    @Override
    public void hideLoading() {
        pbr.setVisibility(View.GONE);
        bNext.setBackground(round);
    }

    @Override
    public void showNoInternetAvailable() {
        showSnackBar.show(noInternet, Color.RED);
    }

    @Override
    public void showError(Exception e) {
        showSnackBar.show(e.getMessage(), Color.RED);
    }

    @Override
    public void showEmailRegistredAlready() {
        showSnackBar.show(emailRegistred, Color.RED);
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

    private void saveDate(int i, int i1, int i2) {
        userSharedImp.saveDateBirthday(i, i1, i2);
    }

    public static void openAddPhotoActivity(Context ctx) {
        Intent intent = new Intent(ctx, AddPhotoActivty.class);
        ctx.startActivity(intent);
    }

    @Override
    public void navigatePageTwo() {
        if (checkFields()) {
            saveSignoneData();
            openPageTwo();
        }
    }

    private void saveSignoneData() {
        userSharedImp.saveSignoneInfo(name.getText().toString(), lastName.getText().toString(), email.getText().toString());
    }

    private boolean checkFields() {
        if (name.getText().toString().isEmpty()) {
            showSnackBar.show(nameErrorMessage, Color.RED);
            return false;
        }
        if (lastName.getText().toString().isEmpty()) {
            showSnackBar.show(lastNameErrorMessage, Color.RED);
            return false;
        }
        if (email.getText().toString().isEmpty()) {
            showSnackBar.show(emailErrorMessage, Color.RED);
            return false;
        }
        if (!userSharedImp.isPhotoTaken()) {
            showSnackBar.show(photoErrorMessage, Color.RED);
            return false;
        }
        if (!userSharedImp.isDateBirthDayTaken()) {
            showSnackBar.show(dateErrorMessage, Color.RED);
            return false;
        }
        return true;
    }

    public void openPageTwo() {
        Intent intent = new Intent(this, SignupTwoActivity.class);
        if (uriReceived != null) {
            intent.putExtra("uri", uriReceived);
        }
        if (bitmapReceived != null) {
            intent.putExtra("bitmap", bitmapReceived);
        }
        startActivityForResult(intent, SIGNUPONE);
    }

    @Override
    public void navigateAddPhoto() {
        openAddPhotoActivity(this);
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    private void removeSignInformation() {
        userSharedImp.removeSignInformation();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.resume();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SIGNUPONE) {
                getExtras(data);
            }
        }
    }

    private void getExtras(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Uri uri = (Uri) bundle.get("uri");
            if (uri != null)
                uriReceived = uri;
            Bitmap bm = (Bitmap) bundle.get("bitmap");
            if (bm != null)
                bitmapReceived = bm;
        }
    }
}

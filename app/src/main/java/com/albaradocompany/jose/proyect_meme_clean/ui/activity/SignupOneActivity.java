package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.SignupOnePresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSignupOne;

import java.util.Calendar;

import butterknife.InjectView;
import butterknife.OnClick;

public class SignupOneActivity extends BaseActivty implements AbsSignupOne.Navigator, AbsSignupOne.View {

    private static final String PREF_NAME = "AndroidHivePref";
    private static final String SIGN_USER_SEX = "SignUserSex";
    private static final String SIGN_PAGE = "SignPage";

    @InjectView(R.id.signup_button_back)
    ImageButton bBack;

    @InjectView(R.id.signup_button_menu)
    ImageButton bMenu;

    @InjectView(R.id.signup_button_next)
    ImageButton bNext;

    @InjectView(R.id.signup_button_pagetwo)
    ImageButton bPagetwo;

    @InjectView(R.id.signup_button_pagethree)
    ImageButton bPagethree;

    @InjectView(R.id.signup_et_name)
    EditText name;

    @InjectView(R.id.signup_et_email)
    EditText email;

    @InjectView(R.id.signup_et_lastName)
    EditText lastName;

    @InjectView(R.id.signup_image_user)
    ImageView image;

    AbsSignupOne presenter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @OnClick(R.id.signup_button_back)
    public void onBackpressed(View view) {
        presenter.onBackPressed();
    }

    @OnClick(R.id.signup_image_user)
    public void onImage(View view) {
//        presenter.onAddPhotoClicked();
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
        presenter.onAddPhotoClicked();
    }

    @OnClick(R.id.signup_button_menu)
    public void onMenuClicked(View view){
        presenter.onMenuPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeSharedPreferences();
        initializePresenter();
    }


    private void initializeSharedPreferences() {
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
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
    public void showDatePicker() {
        showDialogDate();
    }

    private void showDialogDate() {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(SignupOneActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                myCalendar.set(Calendar.YEAR, i);
                myCalendar.set(Calendar.MONTH, i1);
                myCalendar.set(Calendar.DAY_OF_MONTH, i2);
            }
        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    public static void openAddPhotoActivity(Context ctx) {
        Intent intent = new Intent(ctx, AddPhotoActivty.class);
        ctx.startActivity(intent);
    }

    @Override
    public void navigatePageTwo() {
        openPageTwo(this);
    }

    public static void openPageTwo(Context ctx) {
        Intent intent = new Intent(ctx, SignupTwoActivity.class);
        ctx.startActivity(intent);
    }

    @Override
    public void navigateAddPhoto() {
        openAddPhotoActivity(this);
    }

}

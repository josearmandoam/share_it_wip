package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.SignupTwoPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSignupTwo;

import butterknife.InjectView;
import butterknife.OnClick;

public class SignupTwoActivity extends BaseActivty implements AbsSignupTwo.View, AbsSignupTwo.Navigator {
    @InjectView(R.id.signup_two_button_back)
    ImageButton bBack;

    @InjectView(R.id.signup_two_button_menu)
    ImageButton bMenu;

    @InjectView(R.id.signup_two_button_next)
    ImageButton bNext;

    @InjectView(R.id.signup_two_button_pagetwo)
    ImageButton bPagetwo;

    @InjectView(R.id.signup_two_button_pagethree)
    ImageButton bPagethree;

    @InjectView(R.id.signup_two_et_name)
    EditText username;

    @InjectView(R.id.signup_two_et_email)
    EditText password;

    @InjectView(R.id.signup_two_et_lastName)
    EditText password2;

    @InjectView(R.id.signup_two_image)
    ImageView image;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializePresenter();
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
    public void navigatePageThree() {
        openSignupThreeActivity(this);
    }

    private void initializePresenter() {
        presenter = new SignupTwoPresenter(this);
        presenter.setNavigator(this);
        presenter.setView(this);
    }

    public static void openSignupThreeActivity(Context ctx) {
        Intent intent = new Intent(ctx, SignupThreeActivity.class);
        ctx.startActivity(intent);
    }

}

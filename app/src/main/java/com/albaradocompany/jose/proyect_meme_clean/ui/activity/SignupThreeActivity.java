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
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.SignupThreePresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSignupOne;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSignupThree;

import butterknife.InjectView;
import butterknife.OnClick;

public class SignupThreeActivity extends BaseActivty implements AbsSignupThree.View, AbsSignupThree.Navigator {
    @InjectView(R.id.signup_three_button_back)
    ImageButton bBack;

    @InjectView(R.id.signup_three_button_menu)
    ImageButton bMenu;

    @InjectView(R.id.signup_three_button_confirm)
    ImageButton bConfirm;

    @InjectView(R.id.signup_three_button_pageone)
    ImageButton bPageone;

    @InjectView(R.id.signup_three_button_pagetwo)
    ImageButton bPagetwo;

    @InjectView(R.id.signup_three_et_name)
    EditText question;

    @InjectView(R.id.signup_three_et_email)
    EditText answer1;

    @InjectView(R.id.signup_three_et_lastName)
    EditText answer2;

    @InjectView(R.id.signup_three_image)
    ImageView image;

    AbsSignupThree presenter;

    @OnClick(R.id.signup_three_button_back)
    public void onBackpressed(View view) {
        presenter.onBackPressed();
    }

    @OnClick(R.id.signup_three_button_confirm)
    public void onNextPageClicked(View view) {
        presenter.onConfirmClicked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializePrensenter();
    }

    private void initializePrensenter() {
        presenter=new SignupThreePresenter(this);
        presenter.setView(this);
        presenter.setNavigator(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_signup_three;
    }

    @Override
    protected boolean hideToolbar() {
        return true;
    }

    @Override
    public void hideSignupThree() {
        onBackPressed();
    }

    @Override
    public void showMenu() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

}

package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.LoginApiImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.LoginInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.LoginPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsUserLogin;

import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivty implements AbsUserLogin.View, AbsUserLogin.Navigator {

    private static final String PREF_NAME = "AndroidHivePref";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String KEY_NAME = "username";
    AbsUserLogin presenter;
    LoginInteractor loginInteractor;

    @InjectView(R.id.login_username)
    EditText username;
    @InjectView(R.id.login_password)
    EditText password;
    @InjectView(R.id.login_pbr)
    ProgressBar progressBar;
    @InjectView(R.id.login_b_signin)
    Button bSignin;
    @OnClick(R.id.login_b_signup)
        public void onSignupClicked(View view){
            presenter.onSignupClicked();
    }

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @OnClick(R.id.login_b_signin)
    public void onClickLogin(View view) {
        requestLogin();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializePresenter();
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

    }

    private void initializePresenter() {
        presenter = new LoginPresenter(this);
        presenter.setView(this);
        presenter.setNavigator(this);
    }

    private void requestLogin() {
        loginInteractor = new LoginInteractor(new LoginApiImp(username.getText().toString()),
                new ThreadExecutor(), new MainThreadImp());
        presenter.onSigninClicked(loginInteractor, username.getText().toString(),
                password.getText().toString());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showErrorUserNotFound() {
        showSnackBar(getString(R.string.errorUsername), Color.RED);
    }


    @Override
    public void showErrorLoginPassword() {
        showSnackBar(getString(R.string.errorPassword), Color.RED);
    }

    @Override
    public void showNoInternetAvailable() {
        showSnackBar(getString(R.string.noInternetAvailable), Color.RED);
    }

    @Override
    public void showError(Exception e) {
        showSnackBar(e.toString(), Color.RED);
    }

    @Override
    public boolean hideToolbar() {
        return true;
    }

    @Override
    public void hideButtonSignin() {
        bSignin.setVisibility(View.GONE);
    }

    @Override
    public void showButtonSignin() {
        bSignin.setVisibility(View.VISIBLE);
    }

    @Override
    public void navigateToHomePage() {
        userLoggedIn();
        showSnackBar(getString(R.string.loginCorrect), Color.GREEN);
    }

    @Override
    public void navigateToSignupPage() {
        openSignupActivity(this);
    }
    public static void openSignupActivity(Context ctx) {
        Intent intent = new Intent(ctx, SignupOneActivity.class);
        ctx.startActivity(intent);
    }
    private void userLoggedIn(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IS_LOGIN, "true");
        editor.commit();
    }
    private void showSnackBar(String message, int color) {
        final Snackbar snackbar = Snackbar.make(this.getCurrentFocus(), message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(color);
        View view = snackbar.getView();

        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        Typeface font = Typeface.create("casual", Typeface.BOLD);
        tv.setTypeface(font);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        snackbar.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}

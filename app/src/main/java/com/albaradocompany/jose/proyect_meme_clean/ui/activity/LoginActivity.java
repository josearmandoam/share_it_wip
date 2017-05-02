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
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.LoginApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerSignupComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.SignupComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.SignupModule;
import com.albaradocompany.jose.proyect_meme_clean.interactor.LoginInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.LoginPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsUserLogin;
import com.albaradocompany.jose.proyect_meme_clean.ui.view.ShowSnackBarImp;
import com.albaradocompany.jose.proyect_meme_clean.usecase.ShowSnackBar;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivty implements AbsUserLogin.View, AbsUserLogin.Navigator {

    AbsUserLogin presenter;
    LoginInteractor loginInteractor;

    @BindView(R.id.login_username)
    EditText username;
    @BindView(R.id.login_password)
    EditText password;
    @BindView(R.id.login_pbr)
    ProgressBar progressBar;
    @BindView(R.id.login_b_signin)
    Button bSignin;

    @BindString(R.string.default_font)
    String text_font;
    @BindString(R.string.error)
    String error;
    @BindString(R.string.empty_username)
    String empty_username;
    @BindString(R.string.empty_pasword)
    String empty_password;
    @BindString(R.string.empty_username_password)
    String empty_username_password;
    @BindColor(R.color.color_login)
    int color_login;
    @BindString(R.string.errorUsername)
    String errorUsername;
    @BindString(R.string.errorPassword)
    String errorPassword;
    @BindString(R.string.noInternetAvailable)
    String noInternet;
    @BindString(R.string.loginCorrect)
    String loginCorrect;


    SignupComponent component;
    @Inject
    UserSharedImp userSharedImp;
    ShowSnackBar showSnackBar;

    @OnClick(R.id.login_b_signup)
    public void onSignupClicked(View view) {
        presenter.onSignupClicked();
    }

    @OnClick(R.id.login_b_signin)
    public void onClickLogin(View view) {
        requestLogin();
    }

    @OnClick(R.id.login_fPassword)
    public void onPasswordClicled(View view) {
        presenter.onPasswordClicked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component().inject(this);
        initializePresenter();
        initialize();
    }

    private void initialize() {
        userSharedImp.removeSignInformation();
        showSnackBar = new ShowSnackBarImp(this);
    }

    private void initializePresenter() {
        presenter = new LoginPresenter(this);
        presenter.setView(this);
        presenter.setNavigator(this);
    }

    private void requestLogin() {
        loginInteractor = new LoginInteractor(new LoginApiImp(username.getText().toString()),
                new ThreadExecutor(), new MainThreadImp());
        if (checkFields()) {
            presenter.onSigninClicked(loginInteractor, username.getText().toString(),
                    password.getText().toString());
        }
    }

    private boolean checkFields() {
        if (username.getText().toString().isEmpty() && password.getText().toString().isEmpty()) {
            showSnackBar.show(empty_username_password, Color.RED);
            return false;
        }
        if (username.getText().toString().isEmpty()) {
            showSnackBar.show(empty_username, Color.RED);
            return false;
        }
        if (password.getText().toString().isEmpty()) {
            showSnackBar.show(empty_password, Color.RED);
            return false;
        }
        return true;
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
        showSnackBar.show(errorUsername, Color.RED);
    }


    @Override
    public void showErrorLoginPassword() {
        showSnackBar.show(errorPassword, Color.RED);
    }

    @Override
    public void showNoInternetAvailable() {
        showSnackBar.show(noInternet, Color.RED);
    }

    @Override
    public void showError(Exception e) {
        showSnackBar.show(error, Color.RED);
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
        userSharedImp.saveUserLogged();
        showSnackBar.show(loginCorrect, Color.RED);
    }

    @Override
    public void navigateToSignupPage() {
        openSignupActivity(this);
    }

    @Override
    public void navigateToPassword() {
        openPassword(this);
    }

    private void openPassword(Context context) {
        Intent i = new Intent(context, PasswordActivity.class);
        context.startActivity(i);
    }

    public static void openSignupActivity(Context ctx) {
        Intent intent = new Intent(ctx, SignupOneActivity.class);
        ctx.startActivity(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        userSharedImp.removeSignInformation();
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

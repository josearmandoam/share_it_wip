package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.SplashPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSplash;

public class SplashActivity extends AppCompatActivity implements AbsSplash.Navigator, AbsSplash.View {

    private static final String PREF_NAME = "AndroidHivePref";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String IS_FIRST_TIME = "IsFirstTime";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    AbsSplash presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initializePresenter();
        presenter.userLogged(userLogged());
        presenter.initialize();

    }

    private boolean userLogged() {
        SharedPreferences sharedPref = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String highScore = sharedPref.getString(IS_LOGIN, "false");
        if (highScore.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    private void initializePresenter() {
        presenter = new SplashPresenter(this);
        presenter.setView(this);
        presenter.setNavigator(this);
    }

    @Override
    public void sleep() {
        sleep(500);

    }

    private void sleep(final int time) {
        SystemClock.sleep(time);
    }

    @Override
    public void navigateToLoginActivity() {
        openLoginActivity(this);
    }

    public static void openLoginActivity(Context ctx) {
        Intent intent = new Intent(ctx, LoginActivity.class);
        ctx.startActivity(intent);
    }

    @Override
    public void navigateToMainPage() {
        openLoginActivity(this);
//        openSignUpActivity(this);
    }

    public static void openSignUpActivity(Context ctx) {
        Intent intent = new Intent(ctx, SignupOneActivity.class);
        ctx.startActivity(intent);
    }

}

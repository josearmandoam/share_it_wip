package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerSignupComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.SignupComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.SignupModule;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.SplashPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSplash;

import javax.inject.Inject;

public class SplashActivity extends AppCompatActivity implements AbsSplash.Navigator, AbsSplash.View {

    private static final String PREF_NAME = "AndroidHivePref";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String IS_FIRST_TIME = "IsFirstTime";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    AbsSplash presenter;
    private SignupComponent component;
    @Inject
    UserSharedImp userSharedImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component().inject(this);
        initializePresenter();
        presenter.userLogged(userSharedImp.isLogged());
        presenter.initialize();

    }

//    private boolean userLogged() {
//        SharedPreferences sharedPref = this.getSharedPreferences(BuildConfig.PREF_NAME, Context.MODE_PRIVATE);
//        String highScore = sharedPref.getString(BuildConfig.IS_LOGIN, "false");
//        if (highScore.equals("true")) {
//            return true;
//        } else {
//            return false;
//        }
//    }

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

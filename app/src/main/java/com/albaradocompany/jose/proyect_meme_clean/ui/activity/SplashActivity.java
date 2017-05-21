package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerUIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIModule;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.SplashPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSplash;

public class SplashActivity extends AppCompatActivity implements AbsSplash.Navigator, AbsSplash.View {

    AbsSplash presenter;
    private UIComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component().inject(this);
        initializePresenter();
    }

    private void initializePresenter() {
        presenter = new SplashPresenter(this);
        presenter.setView(this);
        presenter.setNavigator(this);
        presenter.initialize();
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

    public static void openProfileActivity(Context ctx) {
        Intent intent = new Intent(ctx, ProfileActivity.class);
        ctx.startActivity(intent);
    }

    @Override
    public void navigateToMainPage() {
        openMain(this);
    }

    public static void openLoginActivity(Context ctx) {
        Intent intent = new Intent(ctx, LoginActivity.class);
        ctx.startActivity(intent);
    }
    public static void openMain(Context ctx) {
        Intent intent = new Intent(ctx, MainActivity.class);
        ctx.startActivity(intent);
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
}

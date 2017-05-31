package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.NotificationLineBD;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.PicturesBD;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.SavedPicturesBD;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.UserBD;

import butterknife.ButterKnife;

/**
 * Created by jose on 14/04/2017.
 */

public abstract class BaseActivty extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        ButterKnife.bind(this);

        Configuration dbConfiguration = new Configuration.Builder(this)
                .setDatabaseName("MyDb.db")
                .addModelClass(UserBD.class)
                .addModelClass(PicturesBD.class)
                .addModelClass(SavedPicturesBD.class)
                .addModelClass(NotificationLineBD.class)
                .create();
        ActiveAndroid.initialize(dbConfiguration);

//        if (hideToolbar()) {
//            getSupportActionBar().hide();
//        }

    }

    protected abstract int getLayoutId();

    protected abstract boolean hideToolbar();
}

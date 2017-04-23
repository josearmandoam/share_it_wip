package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

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

        if (hideToolbar()) {
            getSupportActionBar().hide();
        }

    }

    protected abstract int getLayoutId();

    protected abstract boolean hideToolbar();
}

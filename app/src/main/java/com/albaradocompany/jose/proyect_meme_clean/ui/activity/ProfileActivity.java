package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.albaradocompany.jose.proyect_meme_clean.R;

public class ProfileActivity extends BaseActivty {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    protected boolean hideToolbar() {
        return true;
    }
}

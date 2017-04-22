package com.albaradocompany.jose.proyect_meme_clean.global.di;

import android.content.Context;

import dagger.Module;

/**
 * Created by jose on 22/04/2017.
 */
@Module
public class AvatarModule {
    Context context;

    public AvatarModule(Context context) {
        this.context = context;
    }
}

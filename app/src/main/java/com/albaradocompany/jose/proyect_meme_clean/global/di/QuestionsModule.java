package com.albaradocompany.jose.proyect_meme_clean.global.di;

import android.content.Context;

import dagger.Module;

/**
 * Created by jose on 25/04/2017.
 */
@Module
public class QuestionsModule {
    Context context;

    public QuestionsModule(Context context) {
        this.context = context;
    }
}

package com.albaradocompany.jose.proyect_meme_clean.global.di;

import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupThreeActivity;

import dagger.Component;

/**
 * Created by jose on 25/04/2017.
 */
@Component(dependencies = RootComponent.class, modules = {QuestionsModule.class, MainModule.class})
public interface QuestionsComponent {
    void inject(SignupThreeActivity signupThreeActivity);
}

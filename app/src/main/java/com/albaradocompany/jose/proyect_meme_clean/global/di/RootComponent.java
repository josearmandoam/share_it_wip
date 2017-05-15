package com.albaradocompany.jose.proyect_meme_clean.global.di;

import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.ProfileActivity;

import dagger.Component;

/**
 * Created by jose on 22/04/2017.
 */
@Component(modules = MainModule.class)
public interface RootComponent {
    void inject(App app);

    void inject(ProfileActivity profileActivity);
}

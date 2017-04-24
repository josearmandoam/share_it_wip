package com.albaradocompany.jose.proyect_meme_clean.global.di;

import com.albaradocompany.jose.proyect_meme_clean.ui.activity.AddPhotoActivty;

import dagger.Component;

/**
 * Created by jose on 22/04/2017.
 */
@Component(dependencies = RootComponent.class, modules = {AvatarModule.class, MainModule.class})
public interface AvatarComponent {
    void inject(AddPhotoActivty addPhotoActivty);
}
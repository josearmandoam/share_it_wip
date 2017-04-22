package com.albaradocompany.jose.proyect_meme_clean.global;

import android.app.Application;

import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerRootComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.MainModule;
import com.albaradocompany.jose.proyect_meme_clean.global.di.RootComponent;

/**
 * Created by jose on 22/04/2017.
 */

public class App extends Application {
    private MainModule mainModule;
    RootComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeDependencyInjector();
    }

    private void initializeDependencyInjector() {
        mainModule = new MainModule(this);
        component = DaggerRootComponent.builder()
                .mainModule(mainModule)
                .build();
        component.inject(this);
    }

    public MainModule getMainModule() {
        return mainModule;
    }

    public RootComponent getComponent() {
        return component;
    }
}

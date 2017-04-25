package com.albaradocompany.jose.proyect_meme_clean.global.di;

import android.app.Application;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.AvatarsApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.QuestionsApiImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.AvatarInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.QuestionsInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jose on 22/04/2017.
 */
@Module
public class MainModule {
    private Application application;
    AvatarInteractor avatarInteractor;
    QuestionsInteractor questionsInteractor;

    public MainModule(Application application) {
        this.application = application;

        avatarInteractor = new AvatarInteractor(new AvatarsApiImp(), new ThreadExecutor(), new MainThreadImp());
        questionsInteractor = new QuestionsInteractor(new QuestionsApiImp(), new MainThreadImp(), new ThreadExecutor());
    }

    @Provides
    public AvatarInteractor provideAvatarInteractor() {
        return avatarInteractor;
    }

    @Provides
    public QuestionsInteractor provideQuestionsInteractor() {
        return questionsInteractor;
    }
}

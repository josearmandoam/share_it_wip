package com.albaradocompany.jose.proyect_meme_clean.datasource.fcm;

import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerUIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIModule;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import javax.inject.Inject;

/**
 * Created by jose on 29/05/2017.
 */

public class FcmInstanceIdService extends FirebaseInstanceIdService {

    private UIComponent component;
    @Inject
    UserSharedImp userSharedImp;

    @Override
    public void onTokenRefresh() {
        component().inject(this);
        String recent_token = FirebaseInstanceId.getInstance().getToken();
        userSharedImp.saveUserToken(recent_token);

    }
    public UIComponent component() {
        if (component == null) {
            component = DaggerUIComponent.builder()
                    .rootComponent(((App) getApplication()).getComponent())
                    .uIModule(new UIModule(getApplicationContext()))
                    .mainModule(((App) getApplication()).getMainModule())
                    .build();
        }
        return component;
    }
}

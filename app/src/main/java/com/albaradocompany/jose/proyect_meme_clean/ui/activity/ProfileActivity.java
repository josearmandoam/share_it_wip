package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD.GetUserBDImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.PicturesBD;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.SavedPicturesBD;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.UserBD;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerUIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIModule;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.ProfilePresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsProfilePresenter;
import com.albaradocompany.jose.proyect_meme_clean.usecase.GetUserBD;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;

public class ProfileActivity extends BaseActivty implements AbsProfilePresenter.View, AbsProfilePresenter.Navigator {
    @BindView(R.id.profile_btn_menu)
    ImageButton menu;
    @BindView(R.id.profile_btn_edit)
    ImageButton edit;
    @BindView(R.id.profile_iv_photo)
    ImageView profile;
    @BindView(R.id.profile_iv_background)
    ImageView background;
    @BindView(R.id.profile_tv_user_name)
    TextView name;
    @BindView(R.id.profile_tv_user_username)
    TextView username;
    @BindView(R.id.profile_tv_user_description)
    TextView description;


    @BindDrawable(R.drawable.menu_exp)
    Drawable menu_exp;
    @BindDrawable(R.drawable.menu)
    Drawable menu_normal;

    private UIComponent component;
    AbsProfilePresenter presenter;
    UserBD userBD;

    @Inject
    UserSharedImp userSharedImp;
    @Inject
    GetUserBDImp getUserBD;


    @OnClick(R.id.profile_btn_menu)
    public void onLogOutClicked(View view) {
        presenter.onLogOutClicked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    private void initialize() {
        component().inject(this);

        presenter = new ProfilePresenter();
        presenter.setView(this);
        presenter.setNavigator(this);
        userBD =  getUserBD.getUserBD(userSharedImp.getUserID());
        List<PicturesBD> pc=getUserBD.getUserPictures(userSharedImp.getUserID());
        List<SavedPicturesBD> spc=getUserBD.getUserSavedPictures(userSharedImp.getUserID());
        presenter.initialize();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    protected boolean hideToolbar() {
        return true;
    }

    public static void openLogin(Context ctx) {
        Intent intent = new Intent(ctx, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ctx.startActivity(intent);
    }

    private void checkUserImage() {
        if (userSharedImp.isAvatarTaken()) {
            Picasso.with(this).load(userSharedImp.getProfile()).into(profile);

        } else {
            userSharedImp.showUserPhoto(profile, userSharedImp.getUserPhoto());
        }
        if (userSharedImp.isSelectedBackground()) {
            ImageView imageView = new ImageView(this);
            Picasso.with(this).load(userSharedImp.getBackground()).
                    into(imageView);
            background.setBackground(imageView.getDrawable());
        } else {
            ImageView imageView = new ImageView(this);
            userSharedImp.showUserPhoto(imageView, userSharedImp.getBackground());
            background.setBackground(imageView.getDrawable());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.resume();
    }


    @Override
    public void navigateToLogin() {
        userSharedImp.cleanUserLogged();
        openLogin(this);
        this.finish();
    }

    @Override
    public void showProfile() {
        Picasso.with(this)
                .load(userBD.user_profile)
                .into(profile, new Callback() {
                    @Override
                    public void onSuccess() {
                        /**/
                    }

                    @Override
                    public void onError() {
                        /**/
                    }
                });
    }

    @Override
    public void showBackground() {
        final ImageView imageView = new ImageView(this);
        Picasso.with(this)
                .load(userBD.user_background)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        background.setBackground(imageView.getDrawable());
                    }

                    @Override
                    public void onError() {
                        /**/
                    }
                });
    }

    @Override
    public void showName() {
        name.setText(userBD.user_name);
    }

    @Override
    public void showUsername() {
        username.setText("@"+userBD.user_username);
    }

    @Override
    public void showDescription() {
        description.setText(userBD.user_description);
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

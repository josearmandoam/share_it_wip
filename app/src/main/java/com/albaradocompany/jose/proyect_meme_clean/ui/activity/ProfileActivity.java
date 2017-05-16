package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.global.util.ActivityHelper;
import com.albaradocompany.jose.proyect_meme_clean.ui.adaptor.PhotosRecyclerAdapter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.ProfilePresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsProfilePresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class ProfileActivity extends BaseActivty implements AbsProfilePresenter.View, AbsProfilePresenter.Navigator {

    private final int LOW_VISIVILITY = 50;
    private final int HIGHT_VISIVILITY = 255;

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
    @BindView(R.id.profile_recyclerview_photos)
    RecyclerView recyclerPhotos;
    @BindView(R.id.profile_lyt_container)
    RelativeLayout layout;
    @BindView(R.id.profile_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.profile_ibtn_facebook)
    ImageButton facebook;
    @BindView(R.id.profile_ibtn_twitter)
    ImageButton twitter;
    @BindView(R.id.profile_ibtn_whatsapp)
    ImageButton whatsapp;
    @BindView(R.id.profile_ibtn_website)
    ImageButton website;
    @BindView(R.id.profile_ibtn_email)
    ImageButton email;
    @BindView(R.id.profile_ibtn_instagram)
    ImageButton instagram;
    @BindDrawable(R.drawable.menu_exp)
    Drawable menuExp;
    @BindDrawable(R.drawable.menu)
    Drawable menuNormal;
    @BindDrawable(R.mipmap.instagram)
    Drawable ic_instagram;
    @BindDrawable(R.mipmap.twitter)
    Drawable ic_twitter;
    @BindDrawable(R.mipmap.email)
    Drawable ic_email;
    @BindDrawable(R.mipmap.whatsapp)
    Drawable ic_whatsapp;
    @BindDrawable(R.mipmap.internet)
    Drawable ic_website;
    @BindDrawable(R.mipmap.facebook)
    Drawable ic_facebook;
    @BindString(R.string.open_facebook)
    String msg_facebook;
    @BindString(R.string.open_instagram)
    String msg_instagram;
    @BindString(R.string.open_website)
    String msg_website;
    @BindString(R.string.open_whatsapp)
    String msg_whatsapp;
    @BindString(R.string.open_twitter)
    String msg_twitter;
    @BindString(R.string.open_email)
    String msg_email;
    @BindString(R.string.go)
    String go;
    @BindString(R.string.send)
    String send;
    @BindString(R.string.facebook_so)
    String title_facebook;
    @BindString(R.string.instagram_so)
    String title_instagram;
    @BindString(R.string.website)
    String title_website;
    @BindString(R.string.email_so)
    String title_email_so;
    @BindString(R.string.whatsapp_so)
    String title_whatsapp_so;
    @BindString(R.string.twitter_so)
    String title_twitter_so;

    private UIComponent component;
    AbsProfilePresenter presenter;
    UserBD userBD;
    AlertDialog alertDialog;

    @Inject
    UserSharedImp userSharedImp;
    @Inject
    GetUserBDImp getUserBD;
    List<PicturesBD> userphotos;
    List<SavedPicturesBD> usersavedphotos;
    PhotosRecyclerAdapter adapter;

    @OnClick(R.id.profile_btn_menu)
    public void onLogOutClicked(View view) {
        presenter.onLogOutClicked();
    }

    @OnClick(R.id.profile_btn_edit)
    public void onEditClicked(View view) {
        presenter.onEditClicked();
    }

    @OnClick(R.id.profile_ibtn_facebook)
    public void onFacebookClicked(View view) {
        presenter.onFacebookClicked();
    }

    @OnClick(R.id.profile_ibtn_whatsapp)
    public void onWhastappClicked(View view) {
        presenter.onWhastappClicked();
    }

    @OnClick(R.id.profile_ibtn_instagram)
    public void onInstagramClicked(View view) {
        presenter.onInstagramClicked();
    }

    @OnClick(R.id.profile_ibtn_website)
    public void onWebsiteClicked(View view) {
        presenter.onWebsiteClicked();
    }

    @OnClick(R.id.profile_ibtn_twitter)
    public void onTwitterClicked(View view) {
        presenter.onTwitterClicked();
    }

    @OnClick(R.id.profile_ibtn_email)
    public void onEmailClicked(View view) {
        presenter.onEmailClicked();
    }

    @OnClick(R.id.profile_btn_saved)
    public void onSaveClicked(View view) {
        presenter.onSaveClicked();
    }

    PhotosRecyclerAdapter.Listener onPictureClicked = new PhotosRecyclerAdapter.Listener() {
        @Override
        public void onPictureClicked(Picture picture) {
            presenter.onPictureClicked(picture);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    private void initialize() {
        component().inject(this);

        intializeRepository();
        intializePresenter();
        layout.requestFocus();
        intializeSwipeRefresh();
    }

    private void intializeSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.resume();
                presenter.updatePictures();
                presenter.updateSavedPictures();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void intializePresenter() {
        presenter = new ProfilePresenter(this);
        presenter.setView(this);
        presenter.setNavigator(this);
        presenter.initialize();
    }

    private void intializeRepository() {
        List<UserBD> us = getUserBD.getUsers();
        userBD = getUserBD.getUserBD(us.get(0).userId);
        userSharedImp.saveUserID(us.get(0).userId);
        userphotos = getUserBD.getUserPictures(userSharedImp.getUserID());
        usersavedphotos = getUserBD.getUserSavedPictures(userSharedImp.getUserID());
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

    @Override
    public void onResume() {
        super.onResume();
        userBD = getUserBD.getUserBD(userSharedImp.getUserID());
        presenter.resume();
    }

    @Override
    public void navigateToLogin() {
        getUserBD.deleteUserBD(userSharedImp.getUserID());
        getUserBD.deleteUserPictures(userSharedImp.getUserID());
        getUserBD.deleteUserSavedPictures(userSharedImp.getUserID());
        userSharedImp.cleanUserLogged();
        openLogin(this);
        this.finish();
    }

    @Override
    public void navigateToEdit() {
        openEditActivity(this);
    }

    @Override
    public void openFacebookPage() {
        ActivityHelper.launchFacebook(this, userBD.social_facebook);
    }

    @Override
    public void openWhatsapp() {
        ActivityHelper.launchWhatsapp(this, userBD.social_whatsapp);
    }

    @Override
    public void openInstagramPage() {
        ActivityHelper.launchInstagram(this, userBD.social_instagram);
    }

    @Override
    public void openWebsitePage() {
        ActivityHelper.launchBrowser(this, userBD.social_website);
    }

    @Override
    public void openTwitterPage() {
        ActivityHelper.launchTwitter(this, userBD.social_twitter);
    }

    @Override
    public void openEmailSelector() {
        ActivityHelper.launchEmailSelector(this, userBD.social_email);
    }

    @Override
    public void navigateToPicture(Picture picture) {
        openPictureDetail(this, picture);
    }

    @Override
    public void navigateToPicturesSaved() {
        openSavedPictures(this);
    }

    public static void openSavedPictures(Context ctx) {
        Intent intent = new Intent(ctx, SavedPicturesActivity.class);
        ctx.startActivity(intent);
    }

    public void openPictureDetail(Context ctx, Picture picture) {
        Intent intent = new Intent(ctx, PictureActivity.class);
        intent.putExtra("imageId", picture.getImageId());
        UserBD userBD = getUserBD.getUserBD(picture.getUserId());
        intent.putExtra("image", picture);
        intent.putExtra("user", getUserBD.parseUserBD(userBD));
        ctx.startActivity(intent);
    }

    public static void openEditActivity(Context ctx) {
        Intent intent = new Intent(ctx, EditProfileActivity.class);
        ctx.startActivity(intent);
    }

    @Override
    public void showProfile() {
        userSharedImp.showUserPhoto(profile, userSharedImp.getPicturesDir() + "/" + userBD.userId + "_profile", userBD);
    }

    @Override
    public void showBackground() {
        userSharedImp.showUserBackground(background, userSharedImp.getPicturesDir() + "/" + userBD.userId + "_background", userBD);
    }

    @Override
    public void showName() {
        name.setText(userBD.user_name + " " + userBD.user_lastname);
    }

    @Override
    public void showUsername() {
        username.setText("@" + userBD.user_username);
    }

    @Override
    public void showDescription() {
        description.setText(userBD.user_description);
    }

    @Override
    public void showPhotos() {
        recyclerPhotos.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerPhotos.setHasFixedSize(true);
        adapter = new PhotosRecyclerAdapter(this, getUserBD.parsePicturesBDList(userphotos), onPictureClicked);
        recyclerPhotos.setAdapter(adapter);
    }

    @Override
    public void checkSocialPrivacity() {
        updatePrivacity();
    }

    @Override
    public void showFacebookDialog() {
        facebookDialog();
    }

    private void facebookDialog() {
        alertDialog = new AlertDialog.Builder(this)
                .setIcon(ic_facebook)
                .setTitle(title_facebook)
                .setMessage(msg_facebook)
                .setPositiveButton(go, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.onFacebookDialogAccepted();
                    }
                })
                .create();
        alertDialog.show();
    }

    @Override
    public void showWhastappDialog() {
        whatsappDialog();
    }

    private void whatsappDialog() {
        alertDialog = new AlertDialog.Builder(this)
                .setIcon(ic_whatsapp)
                .setTitle(title_whatsapp_so)
                .setMessage(msg_whatsapp)
                .setPositiveButton(send, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.onWhatsappDialogAccepted();
                    }
                })
                .create();
        alertDialog.show();
    }

    @Override
    public void showInstagramDialog() {
        instagramDialog();
    }

    private void instagramDialog() {
        alertDialog = new AlertDialog.Builder(this)
                .setIcon(ic_instagram)
                .setTitle(title_instagram)
                .setMessage(msg_instagram)
                .setPositiveButton(go, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.onInstagramDialogAccepted();
                    }
                })
                .create();
        alertDialog.show();
    }

    @Override
    public void showWebsiteDialog() {
        websiteDialog();
    }

    private void websiteDialog() {
        alertDialog = new AlertDialog.Builder(this)
                .setIcon(ic_website)
                .setTitle(title_website)
                .setMessage(msg_website)
                .setPositiveButton(go, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.onWebsiteDialogAccepted();
                    }
                })
                .create();
        alertDialog.show();
    }

    @Override
    public void showTwitterDialog() {
        twitterDialog();
    }

    private void twitterDialog() {
        alertDialog = new AlertDialog.Builder(this)
                .setIcon(ic_twitter)
                .setTitle(title_twitter_so)
                .setMessage(msg_twitter)
                .setPositiveButton(go, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.onTwitterDialogAccepted();
                    }
                })
                .create();
        alertDialog.show();
    }

    @Override
    public void showEmailDialog() {
        emailDialog();
    }

    @Override
    public void showNoInternetAvailable() {

    }

    @Override
    public void showError(Exception e) {

    }

    @Override
    public void updateRecycler() {
        userphotos = getUserBD.getUserPictures(userSharedImp.getUserID());
        usersavedphotos = getUserBD.getUserSavedPictures(userSharedImp.getUserID());
        adapter = new PhotosRecyclerAdapter(this, getUserBD.parsePicturesBDList(userphotos), onPictureClicked);
        recyclerPhotos.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void emailDialog() {
        alertDialog = new AlertDialog.Builder(this)
                .setIcon(ic_email)
                .setTitle(title_email_so)
                .setMessage(msg_email)
                .setPositiveButton(send, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.onEmailDialogAccepted();
                    }
                })
                .create();
        alertDialog.show();
    }

    private void updatePrivacity() {
        if (userBD.social_instagram.equals("private")) {
            instagram.setEnabled(false);
            instagram.setAlpha(LOW_VISIVILITY);
            instagram.setBackgroundColor(android.R.color.transparent);
        } else {
            instagram.setEnabled(true);
            instagram.setAlpha(HIGHT_VISIVILITY);
        }
        if (userBD.social_facebook.equals("private")) {
            facebook.setEnabled(false);
            facebook.setAlpha(LOW_VISIVILITY);
        } else {
            facebook.setEnabled(true);
            facebook.setAlpha(HIGHT_VISIVILITY);
        }
        if (userBD.social_twitter.equals("private")) {
            twitter.setEnabled(false);
            twitter.setAlpha(LOW_VISIVILITY);
        } else {
            twitter.setEnabled(true);
            twitter.setAlpha(HIGHT_VISIVILITY);
        }
        if (userBD.social_whatsapp.equals("private")) {
            whatsapp.setEnabled(false);
            whatsapp.setAlpha(LOW_VISIVILITY);
        } else {
            whatsapp.setEnabled(true);
            whatsapp.setAlpha(HIGHT_VISIVILITY);
        }
        if (userBD.social_website.equals("private")) {
            website.setEnabled(false);
            website.setAlpha(LOW_VISIVILITY);
        } else {
            website.setEnabled(true);
            website.setAlpha(HIGHT_VISIVILITY);
        }
        if (userBD.social_email.equals("private")) {
            email.setEnabled(false);
            email.setAlpha(LOW_VISIVILITY);
        } else {
            email.setEnabled(true);
            email.setAlpha(HIGHT_VISIVILITY);
        }
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

package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.GetUserApiImp;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.global.model.User;
import com.albaradocompany.jose.proyect_meme_clean.global.util.ActivityHelper;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UserInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;
import com.albaradocompany.jose.proyect_meme_clean.ui.adaptor.PhotosRecyclerAdapter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.UserPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsUserPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.view.ShowSnackBarImp;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class UserActivity extends BaseActivty implements AbsUserPresenter.View, AbsUserPresenter.Navigator {
    private final int LOW_VISIVILITY = 50;
    private final int HIGHT_VISIVILITY = 255;

    @BindView(R.id.user_btn_menu)
    ImageButton menu;
    @BindView(R.id.user_btn_edit)
    ImageButton edit;
    @BindView(R.id.user_iv_background)
    ImageView background;
    @BindView(R.id.user_iv_photo)
    ImageView profile;
    @BindView(R.id.user_tv_user_name)
    TextView name;
    @BindView(R.id.user_tv_user_username)
    TextView username;
    @BindView(R.id.user_tv_user_description)
    TextView description;
    @BindView(R.id.user_recyclerview_photos)
    RecyclerView recyclerPhotos;
    @BindView(R.id.user_lyt_container)
    RelativeLayout layout;
    @BindView(R.id.user_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.user_ibtn_facebook)
    ImageButton facebook;
    @BindView(R.id.user_ibtn_twitter)
    ImageButton twitter;
    @BindView(R.id.user_ibtn_whatsapp)
    ImageButton whatsapp;
    @BindView(R.id.user_ibtn_website)
    ImageButton website;
    @BindView(R.id.user_ibtn_email)
    ImageButton email;
    @BindView(R.id.user_ibtn_instagram)
    ImageButton instagram;
    @BindView(R.id.user_pbr_header)
    ProgressBar pbrHeader;
    @BindView(R.id.user_pbr_picture)
    ProgressBar pbrPictures;

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
    @BindString(R.string.noInternetAvailable)
    String noInternet;


    @OnClick(R.id.user_ibtn_facebook)
    public void onFacebookClicked(View view) {
        presenter.onFacebookClicked();
    }

    @OnClick(R.id.user_ibtn_whatsapp)
    public void onWhastappClicked(View view) {
        presenter.onWhastappClicked();
    }

    @OnClick(R.id.user_ibtn_instagram)
    public void onInstagramClicked(View view) {
        presenter.onInstagramClicked();
    }

    @OnClick(R.id.user_ibtn_website)
    public void onWebsiteClicked(View view) {
        presenter.onWebsiteClicked();
    }

    @OnClick(R.id.user_ibtn_twitter)
    public void onTwitterClicked(View view) {
        presenter.onTwitterClicked();
    }

    @OnClick(R.id.user_ibtn_email)
    public void onEmailClicked(View view) {
        presenter.onEmailClicked();
    }

    @OnClick(R.id.user_ibtn_back)
    public void onBackClicked(View view) {
        presenter.onBackClicked();
    }

    AbsUserPresenter presenter;
    ShowSnackBarImp showSnackBarImp;
    PhotosRecyclerAdapter adapter;
    private AlertDialog alertDialog;
    User user;

    PhotosRecyclerAdapter.Listener listener = new PhotosRecyclerAdapter.Listener() {
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

        showSnackBarImp = new ShowSnackBarImp(this);

        presenter = new UserPresenter(this, getUserId(), getUserInteractor());
        presenter.setView(this);
        presenter.setNavigator(this);
        presenter.initialize();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    protected boolean hideToolbar() {
        return true;
    }

    public String getUserId() {
        String userId = "";

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userId = (String) bundle.get("userId");
        }
        return userId;
    }

    public UserInteractor getUserInteractor() {
        return new UserInteractor(new GetUserApiImp(getUserId()), new MainThreadImp(), new ThreadExecutor());
    }

    @Override
    public void hideLoading() {
        layout.setVisibility(View.VISIBLE);
        pbrHeader.setVisibility(View.GONE);
        recyclerPhotos.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        layout.setVisibility(View.GONE);
        pbrHeader.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoInternetAvailable() {
        showSnackBarImp.show(noInternet, Color.RED);
    }

    @Override
    public void showError(Exception e) {
        showSnackBarImp.show(e.getMessage(), Color.RED);
    }

    @Override
    public void showPhotosLoading() {
        recyclerPhotos.setVisibility(View.GONE);
        pbrPictures.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePhotosLoading() {
        pbrPictures.setVisibility(View.GONE);
        recyclerPhotos.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPictures(List<Picture> pictures) {
        updateRecycler(pictures);
    }

    private void updateRecycler(List<Picture> pictures) {
        adapter = new PhotosRecyclerAdapter(this, pictures, listener);
        recyclerPhotos.setAdapter(adapter);
        recyclerPhotos.setHasFixedSize(true);
        recyclerPhotos.setLayoutManager(new GridLayoutManager(this, 3));
    }

    @Override
    public void showBackground(String background) {
        if (!background.isEmpty())
            Picasso.with(this).load(background).into(this.background);
    }

    @Override
    public void showProfile(String profile) {
        if (!profile.isEmpty())
            Picasso.with(this).load(profile).into(this.profile);
    }

    @Override
    public void showUsername(String s) {
        username.setText(s);
    }

    @Override
    public void showName(String s) {
        name.setText(s);
    }

    @Override
    public void showDescription(String description) {
        this.description.setText(description);
    }

    @Override
    public void showWhatsapp(String socialWhatsapp) {
        if (!socialWhatsapp.equals("private")) {
            whatsapp.setAlpha(HIGHT_VISIVILITY);
            whatsapp.setEnabled(true);
        } else {
            whatsapp.setEnabled(false);
            whatsapp.setAlpha(LOW_VISIVILITY);
        }
    }

    @Override
    public void showInstagram(String socialInstagram) {
        if (!socialInstagram.equals("private")) {
            instagram.setEnabled(true);
            instagram.setAlpha(HIGHT_VISIVILITY);
        } else {
            instagram.setEnabled(false);
            instagram.setAlpha(LOW_VISIVILITY);
        }
    }

    @Override
    public void showFacebook(String socialFacebook) {
        if (!socialFacebook.equals("private")) {
            facebook.setEnabled(true);
            facebook.setAlpha(HIGHT_VISIVILITY);
        } else {
            facebook.setEnabled(false);
            facebook.setAlpha(LOW_VISIVILITY);
        }
    }

    @Override
    public void showWebsite(String socialWebsite) {
        if (!socialWebsite.equals("private")) {
            website.setEnabled(true);
            website.setAlpha(HIGHT_VISIVILITY);
        } else {
            website.setEnabled(false);
            website.setAlpha(LOW_VISIVILITY);
        }
    }

    @Override
    public void showTwitter(String socialTwitter) {
        if (!socialTwitter.equals("private")) {
            twitter.setEnabled(true);
            twitter.setAlpha(HIGHT_VISIVILITY);
        } else {
            twitter.setEnabled(false);
            twitter.setAlpha(LOW_VISIVILITY);
        }
    }

    @Override
    public void showEmail(String socialEmail) {
        if (!socialEmail.equals("private")) {
            email.setEnabled(true);
            email.setAlpha(HIGHT_VISIVILITY);
        } else {
            email.setEnabled(false);
            email.setAlpha(LOW_VISIVILITY);
        }
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
    public void showUser(User user) {
        this.user = user;
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

    @Override
    public void openFacebookPage() {
        ActivityHelper.launchFacebook(this, user.getSocialFacebook());
    }

    @Override
    public void openWhatsapp() {
        ActivityHelper.launchWhatsapp(this, user.getSocialWhatsapp());
    }

    @Override
    public void openInstagramPage() {
        ActivityHelper.launchInstagram(this, user.getSocialInstagram());
    }

    @Override
    public void openWebsitePage() {
        ActivityHelper.launchBrowser(this, user.getSocialWebsite());
    }

    @Override
    public void openTwitterPage() {
        ActivityHelper.launchTwitter(this, user.getSocialTwitter());
    }

    @Override
    public void openEmailSelector() {
        ActivityHelper.launchEmailSelector(this, user.getSocialEmail());
    }

    @Override
    public void navigateToPicture(Picture picture) {
        openPictureDetail(this, picture);
    }

    @Override
    public void navigateToBack() {
        onBackPressed();
    }

    public void openPictureDetail(Context ctx, Picture picture) {
        Intent intent = new Intent(ctx, PictureActivity.class);
        intent.putExtra("imageId", picture.getImageId());
        intent.putExtra("image", picture);
        intent.putExtra("user", user);
        ctx.startActivity(intent);
    }
}

package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD.GetUserBDImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.UserBD;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.UpdateUserImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerUIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIModule;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UpdateUserInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.ShowAvatarDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.SocialSettingsDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.EditProfilePresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsEditProfilePresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.view.ShowSnackBarImp;
import com.albaradocompany.jose.proyect_meme_clean.usecase.ShowSnackBar;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class EditProfileActivity extends BaseActivty implements AbsEditProfilePresenter.View, AbsEditProfilePresenter.Navigator {

    private static final int ACTION_BACKGROUND = 2;
    private static final int ACTION_PROFILE = 1;
    private static final String PRIVATE = "private";

    @BindView(R.id.edit_profile_et_name)
    EditText name;
    @BindView(R.id.edit_profile_et_lastname)
    EditText lastName;
    @BindView(R.id.edit_profile_et_email)
    EditText email;
    @BindView(R.id.edit_profile_et_description)
    EditText description;
    @BindView(R.id.edit_profile_et_username)
    EditText userName;
    @BindView(R.id.edit_profile_iv_profile)
    ImageView profile;
    @BindView(R.id.edit_profile_iv_backgrund)
    ImageView background;
    @BindView(R.id.edit_profile_lyt_container)
    RelativeLayout layout;
    @BindView(R.id.edit_profile_pbr)
    ProgressBar progressBar;
    @BindView(R.id.edit_profile_ibtn_accept)
    ImageButton accept;
    @BindView(R.id.edit_profile_et_facebook)
    EditText socialFacebook;
    @BindView(R.id.edit_profile_et_email_social)
    EditText socialEmail;
    @BindView(R.id.edit_profile_et_twitter)
    EditText socialTwitter;
    @BindView(R.id.edit_profile_et_instagram)
    EditText socialInstagram;
    @BindView(R.id.edit_profile_et_whatsapp)
    EditText socialWhatsapp;
    @BindView(R.id.edit_profile_et_website)
    EditText socialWebsite;

    @BindString(R.string.noInternetAvailable)
    String noInternet;
    @BindString(R.string.error)
    String error;
    @BindString(R.string.facebook_empty)
    String facebook_empty;
    @BindString(R.string.whatsapp_empty)
    String whatsapp_empty;
    @BindString(R.string.instagram_empty)
    String instagram_empty;
    @BindString(R.string.website_empty)
    String website_empty;
    @BindString(R.string.email_empty)
    String email_empty;
    @BindString(R.string.twitter_empty)
    String twitter_empty;
    @BindString(R.string.name_empty)
    String name_empty;
    @BindString(R.string.username_empty)
    String username_empty;
    @BindString(R.string.lastname_empty)
    String lastname_empty;
    @BindString(R.string.demail_empty)
    String demail_empty;
    @BindString(R.string.description_empty)
    String description_empty;

    @Inject
    GetUserBDImp getUserBDImp;
    @Inject
    UserSharedImp userSharedImp;

    private UIComponent component;
    AbsEditProfilePresenter presenter;
    UserBD userBD;
    ShowSnackBar showSnackBar;
    public static Uri profileUriReceived;
    public static Bitmap profileBitmapReceived;
    public static Uri backgroundUriReceived;
    public static Bitmap backgroundBitmapReceived;
    int action;
    boolean fieldsStatus;
    @Inject
    GetUserBDImp getUserBD;

    @OnClick(R.id.edit_profile_ibtn_cancel)
    public void onCloseClicked(View view) {
        presenter.onCancelClicked();
    }

    @OnClick(R.id.edit_profile_ibtn_accept)
    public void onUpdateClicked(View view) {
        if (checkInfoValid()) {
            UpdateUserInteractor interactor = getInteractor();
            presenter.onAcceptClicked(interactor, ((BitmapDrawable) profile.getDrawable()).getBitmap(), ((BitmapDrawable) background.getDrawable()).getBitmap());
            updateUserBD();
        }
    }

    @OnClick(R.id.edit_profile_iv_profile)
    public void onProfileClicked(View view) {
        presenter.onProfileClicked();
    }

    @OnClick(R.id.edit_profile_iv_backgrund)
    public void onBackgroundClicked(View view) {
        presenter.onBackgroundClicked();
    }

    @OnClick(R.id.edit_profile_ibtn_settings)
    public void onSocialSettingsClicked(View view) {
        presenter.onSocialSettingsClicked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Override
    public void onResume() {
        super.onResume();
//        List<UserBD> us = getUserBD.getUsers();
//        userBD = getUserBD.getUserBD(us.get(0).userId);
        presenter.resume();
    }

    private void initialize() {
        component().inject(this);
        userBD = getUserBDImp.getUserBD(userSharedImp.getUserID());
        presenter = new EditProfilePresenter(this);
        presenter.setView(this);
        presenter.setNavigator(this);
        presenter.initialize();
        layout.requestFocus();
        showSnackBar = new ShowSnackBarImp(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_profile;
    }

    @Override
    protected boolean hideToolbar() {
        return true;
    }

    @Override
    public void showProfilePicture() {
        userSharedImp.showUserPhoto(profile, userSharedImp.getPicturesDir() + "/" + userBD.userId + "_profile", userBD);
    }

    @Override
    public void showBackgroundPicture() {
        userSharedImp.showUserPhoto(background, userSharedImp.getPicturesDir() + "/" + userBD.userId + "_background", userBD);
    }

    @Override
    public void showName() {
        name.setText(userBD.user_name);
    }

    @Override
    public void showLastName() {
        lastName.setText(userBD.user_lastname);
    }

    @Override
    public void showEmail() {
        email.setText(userBD.user_email);
    }

    @Override
    public void showDescription() {
        description.setText(userBD.user_description);
    }

    @Override
    public void showUserName() {
        userName.setText(userBD.user_username);
    }

    @Override
    public void showLoading() {
        accept.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoInternetAvailable() {
        showSnackBar.show(noInternet, Color.RED);
    }

    @Override
    public void showError(Exception e) {
        showSnackBar.show(e.getMessage(), Color.RED);
    }

    @Override
    public void showSuccess() {
        accept.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFailure() {
        showSnackBar.show(error, Color.RED);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showBackgroundDialog() {
        new ShowAvatarDialog(this, 4, ACTION_BACKGROUND);
        action = ACTION_BACKGROUND;
    }

    @Override
    public void showProfileDialog() {
        new ShowAvatarDialog(this, 4, ACTION_PROFILE);
        action = ACTION_PROFILE;
    }

    @Override
    public void checkProfile() {
        if (userSharedImp.isProfileFTPSelected()) {
            Picasso.with(this).load(userSharedImp.getProfileAvatar()).into(profile);
            profileUriReceived = null;
            profileBitmapReceived = null;
        } else {
            if (profileUriReceived != null) {
                profile.setImageURI(profileUriReceived);
            }
            if (profileBitmapReceived != null) {
                profile.setImageBitmap(profileBitmapReceived);
            }
        }
        if (!userSharedImp.isProfileFTPSelected() && profileUriReceived == null && profileBitmapReceived == null)
            showProfilePicture();

    }

    @Override
    public void checkBaground() {
        if (userSharedImp.isBackgroundFTPSelected()) {
            Picasso.with(this).load(userSharedImp.getBackgroundAvatar()).into(background);
            backgroundUriReceived = null;
            backgroundBitmapReceived = null;
        } else {
            if (backgroundUriReceived != null) {
                background.setImageURI(backgroundUriReceived);
            }
            if (backgroundBitmapReceived != null) {
                background.setImageBitmap(backgroundBitmapReceived);
            }
        }
        if (!userSharedImp.isBackgroundFTPSelected() && backgroundUriReceived == null && backgroundBitmapReceived == null)
            showBackgroundPicture();
    }

    @Override
    public void showSocialSettingsDialog() {
        new SocialSettingsDialog(this);
    }

    @Override
    public void showSocialFacebook() {
        if (userBD.social_facebook.equals("private")) {
            socialFacebook.setHint("private information");
            userSharedImp.saveFacebookPrivacity("false");
        } else {
            socialFacebook.setText(userBD.social_facebook);
            userSharedImp.saveFacebookPrivacity("true");
        }
    }

    @Override
    public void showSocialInstagram() {
        if (userBD.social_instagram.equals("private")) {
            socialInstagram.setHint("private information");
            userSharedImp.saveInstagramPrivacity("false");
        } else {
            socialInstagram.setText(userBD.social_instagram);
            userSharedImp.saveInstagramPrivacity("true");
        }
    }

    @Override
    public void showSocialWhatsapp() {
        if (userBD.social_whatsapp.equals("private")) {
            socialWhatsapp.setHint("private information");
            userSharedImp.saveWhatsappPrivacity("false");
        } else {
            socialWhatsapp.setText(userBD.social_whatsapp);
            userSharedImp.saveWhatsappPrivacity("true");

        }
    }

    @Override
    public void showSocialWebsite() {
        if (userBD.social_website.equals("private")) {
            socialWebsite.setHint("private information");
            userSharedImp.saveWebsitePrivacity("false");

        } else {
            socialWebsite.setText(userBD.social_website);
            userSharedImp.saveWebsitePrivacity("true");

        }
    }

    @Override
    public void showSocialTwitter() {
        if (userBD.social_twitter.equals("private")) {
            socialTwitter.setHint("private information");
            userSharedImp.saveTwitterPrivacity("false");
        } else {
            socialTwitter.setText(userBD.social_twitter);
            userSharedImp.saveTwitterPrivacity("true");
        }
    }

    @Override
    public void showSocialEmail() {
        if (userBD.social_email.equals("private")) {
            socialEmail.setHint("private information");
            userSharedImp.saveEmailPrivacity("false");
        } else {
            socialEmail.setText(userBD.social_email);
            userSharedImp.saveEmailPrivacity("true");
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

    @Override
    public void navigateToBack() {
        this.finish();
    }

    private UpdateUserInteractor getInteractor() {
        UpdateUserInteractor interactor = null;
        userBD = getUserBDImp.getUserBD(userSharedImp.getUserID());
        if (userSharedImp.isProfileChanged() && userSharedImp.isBackgroundChanged()) {
            interactor = new UpdateUserInteractor(new UpdateUserImp(userSharedImp.getUserID(),
                    name.getText().toString(), lastName.getText().toString(), email.getText().toString(),
                    userName.getText().toString(), BuildConfig.BASE_URL_DEFAULT + userSharedImp.getUserID() + "_profile", BuildConfig.BASE_URL_DEFAULT + userSharedImp.getUserID() + "_background",
                    description.getText().toString(), getSocialWebsite(), getSocialWhatsapp(), getSocialEmail(), getSocialInstagram(), getSocialFacebook(), getSocialTwitter()),
                    new MainThreadImp(), new ThreadExecutor());
        } else {
            if (userSharedImp.isProfileChanged()) {
                if (userSharedImp.isProfileFTPSelected()) {
                    interactor = new UpdateUserInteractor(new UpdateUserImp(userSharedImp.getUserID(),
                            name.getText().toString(), lastName.getText().toString(), email.getText().toString(),
                            userName.getText().toString(), userSharedImp.getProfile(), userBD.user_background,
                            description.getText().toString(), getSocialWebsite(), getSocialWhatsapp(), getSocialEmail(), getSocialInstagram(), getSocialFacebook(), getSocialTwitter()),
                            new MainThreadImp(), new ThreadExecutor());

                } else {
                    interactor = new UpdateUserInteractor(new UpdateUserImp(userSharedImp.getUserID(),
                            name.getText().toString(), lastName.getText().toString(), email.getText().toString(),
                            userName.getText().toString(), BuildConfig.BASE_URL_DEFAULT + userSharedImp.getUserID() + "_profile", userBD.user_background,
                            description.getText().toString(), getSocialWebsite(), getSocialWhatsapp(), getSocialEmail(), getSocialInstagram(), getSocialFacebook(), getSocialTwitter()),
                            new MainThreadImp(), new ThreadExecutor());
                }
            } else {
                if (userSharedImp.isBackgroundChanged()) {
                    if (userSharedImp.isBackgroundFTPSelected()) {
                        interactor = new UpdateUserInteractor(new UpdateUserImp(userSharedImp.getUserID(),
                                name.getText().toString(), lastName.getText().toString(), email.getText().toString(),
                                userName.getText().toString(), userBD.user_profile, userSharedImp.getBackground(),
                                description.getText().toString(), getSocialWebsite(), getSocialWhatsapp(), getSocialEmail(), getSocialInstagram(), getSocialFacebook(), getSocialTwitter()),
                                new MainThreadImp(), new ThreadExecutor());

                    } else {
                        interactor = new UpdateUserInteractor(new UpdateUserImp(userSharedImp.getUserID(),
                                name.getText().toString(), lastName.getText().toString(), email.getText().toString(),
                                userName.getText().toString(), userBD.user_profile, BuildConfig.BASE_URL_DEFAULT + userSharedImp.getUserID() + "_background",
                                description.getText().toString(), getSocialWebsite(), getSocialWhatsapp(), getSocialEmail(), getSocialInstagram(), getSocialFacebook(), getSocialTwitter()),
                                new MainThreadImp(), new ThreadExecutor());
                    }
                } else {
                    interactor = new UpdateUserInteractor(new UpdateUserImp(userSharedImp.getUserID(),
                            name.getText().toString(), lastName.getText().toString(), email.getText().toString(),
                            userName.getText().toString(), userBD.user_profile, userBD.user_background,
                            description.getText().toString(), getSocialWebsite(), getSocialWhatsapp(), getSocialEmail(), getSocialInstagram(), getSocialFacebook(), getSocialTwitter()),
                            new MainThreadImp(), new ThreadExecutor());
                }
            }
        }
        return interactor;
    }

    private void updateUserBD() {
        Login c = new Login();
        c.setIdUser(userSharedImp.getUserID());
        c.setEmail(email.getText().toString());
        c.setNombre(name.getText().toString());
        c.setApellidos(lastName.getText().toString());
        c.setDescription(description.getText().toString());
        c.setUsername(userName.getText().toString());
        if (userSharedImp.isProfileChanged()) {
            c.setImagePath(BuildConfig.BASE_URL_DEFAULT + userSharedImp.getUserID() + "_profile");
        } else {
            c.setImagePath(getUserBDImp.getUserBD(userSharedImp.getUserID()).user_profile);
        }

        if (userSharedImp.isBackgroundChanged()) {
            c.setBackgrundPath(BuildConfig.BASE_URL_DEFAULT + userSharedImp.getUserID() + "_background");
        } else {
            c.setBackgrundPath(getUserBDImp.getUserBD(userSharedImp.getUserID()).user_background);
        }
        c.setSocialTwitter(getSocialTwitter());
        c.setSocialFacebook(getSocialFacebook());
        c.setSocialWhatsapp(getSocialWhatsapp());
        c.setSocialEmail(getSocialEmail());
        c.setSocialInstagram(getSocialInstagram());
        c.setSocialWebsite(getSocialWebsite());
//        userSharedImp.updateSocialMedia(getSocialTwitter(), getSocialFacebook(), getSocialWhatsapp(),
//                getSocialEmail(), getSocialInstagram(), getSocialWebsite());
        getUserBDImp.updateUserBD(c);
    }

    private String getSocialWebsite() {
        String websitePrivacity = null;
        if (userSharedImp.getWebsitePrivacity())
            if (socialWebsite.getText().toString().isEmpty()) {
                showSnackBar.show(website_empty, Color.RED);
                fieldsStatus = false;
            } else {
                websitePrivacity = socialWebsite.getText().toString();
            }
        else
            websitePrivacity = PRIVATE;
        return websitePrivacity;
    }

    private String getSocialInstagram() {
        String instagramPrivacity = null;
        if (userSharedImp.getInstagramPrivacity())
            if (socialInstagram.getText().toString().isEmpty()) {
                showSnackBar.show(instagram_empty, Color.RED);
                fieldsStatus = false;
            } else {
                instagramPrivacity = socialInstagram.getText().toString();
            }
        else
            instagramPrivacity = PRIVATE;
        return instagramPrivacity;
    }

    private String getSocialFacebook() {
        String facebookPrivacity = null;
        if (userSharedImp.getFacebookPrivacity())
            if (socialFacebook.getText().toString().isEmpty()) {
                showSnackBar.show(facebook_empty, Color.RED);
                fieldsStatus = false;
            } else {
                facebookPrivacity = socialFacebook.getText().toString();
            }
        else
            facebookPrivacity = PRIVATE;
        return facebookPrivacity;
    }

    private String getSocialEmail() {
        String emailPrivacity = null;
        if (userSharedImp.getEmailPrivacity())
            if (socialEmail.getText().toString().isEmpty()) {
                showSnackBar.show(email_empty, Color.RED);
                fieldsStatus = false;
            } else {
                emailPrivacity = socialEmail.getText().toString();
            }
        else
            emailPrivacity = PRIVATE;
        return emailPrivacity;
    }

    private String getSocialTwitter() {
        String twitterPrivacity = null;
        if (userSharedImp.getTwitterPrivacity())
            if (socialTwitter.getText().toString().isEmpty()) {
                showSnackBar.show(twitter_empty, Color.RED);
                fieldsStatus = false;
            } else {
                twitterPrivacity = socialTwitter.getText().toString();
            }
        else
            twitterPrivacity = PRIVATE;
        return twitterPrivacity;
    }

    private String getSocialWhatsapp() {
        String whatsappPrivacity = null;
        if (userSharedImp.getWhatsappPrivacity())
            if (socialWhatsapp.getText().toString().isEmpty()) {
                showSnackBar.show(whatsapp_empty, Color.RED);
                fieldsStatus = false;
            } else {
                whatsappPrivacity = socialWhatsapp.getText().toString();
            }
        else
            whatsappPrivacity = PRIVATE;
        return whatsappPrivacity;
    }

    private void checkPersonalInformationFields() {
        if (name.getText().toString().isEmpty()) {
            showSnackBar.show(name_empty, Color.RED);
            fieldsStatus = false;
        }
        if (email.getText().toString().isEmpty()) {
            showSnackBar.show(demail_empty, Color.RED);
            fieldsStatus = false;
        }
        if (lastName.getText().toString().isEmpty()) {
            showSnackBar.show(lastname_empty, Color.RED);
            fieldsStatus = false;
        }
        if (userName.getText().toString().isEmpty()) {
            showSnackBar.show(username_empty, Color.RED);
            fieldsStatus = false;
        }
        if (description.getText().toString().isEmpty()) {
            showSnackBar.show(description_empty, Color.RED);
            fieldsStatus = false;
        }
    }

    private boolean checkInfoValid() {
        fieldsStatus = true;
        checkPersonalInformationFields();
        getSocialEmail();
        getSocialFacebook();
        getSocialInstagram();
        getSocialTwitter();
        getSocialWebsite();
        getSocialWhatsapp();
        return fieldsStatus;
    }
}

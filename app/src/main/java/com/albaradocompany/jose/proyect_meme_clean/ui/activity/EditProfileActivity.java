package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.graphics.Color;
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
import com.albaradocompany.jose.proyect_meme_clean.interactor.UpdateUserInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.ShowAvatarDialog;
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

    @BindString(R.string.noInternetAvailable)
    String noInternet;
    @BindString(R.string.error)
    String error;

    @Inject
    GetUserBDImp getUserBDImp;
    @Inject
    UserSharedImp userSharedImp;

    private UIComponent component;
    AbsEditProfilePresenter presenter;
    UserBD userBD;
    ShowSnackBar showSnackBar;

    @OnClick(R.id.edit_profile_ibtn_cancel)
    public void onCloseClicked(View view) {
        presenter.onCancelClicked();
    }

    @OnClick(R.id.edit_profile_ibtn_accept)
    public void onUpdateClicked(View view) {
        UpdateUserInteractor interactor = getInteractor();
        presenter.onAcceptClicked(interactor);
    }

    @OnClick(R.id.edit_profile_iv_profile)
    public void onProfileClicked(View view) {
        presenter.onProfileClicked();
    }

    @OnClick(R.id.edit_profile_iv_backgrund)
    public void onBackgroundClicked(View view) {
        presenter.onBackgroundClicked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Override
    public void onResume() {
        super.onResume();
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
        Picasso.with(this)
                .load(userBD.user_profile)
                .into(profile);
    }

    @Override
    public void showBackgroundPicture() {
        Picasso.with(this)
                .load(userBD.user_background)
                .into(background);
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
        ShowAvatarDialog showAvatarDialog = new ShowAvatarDialog(this, 4, BuildConfig.ACTION_BACKGROUND);
    }

    @Override
    public void showProfileDialog() {
        ShowAvatarDialog showAvatarDialog = new ShowAvatarDialog(this, 4, BuildConfig.ACTION_PROFILE);
    }

    @Override
    public void checkProfile() {
        if (userSharedImp.isProfileChanged()) {
            if (userSharedImp.isProfileFTPSelected()) {
                Picasso.with(this)
                        .load(userSharedImp.getProfile())
                        .into(profile);
            } else {
                userSharedImp.showUserPhoto(profile, userSharedImp.getProfile());
            }
        } else {
            Picasso.with(this)
                    .load(getUserBDImp.getUserBD(userSharedImp.getUserID()).user_profile)
                    .into(profile);
        }
    }

    @Override
    public void checkBaground() {
        if (userSharedImp.isBackgroundChanged()) {
            if (userSharedImp.isBackgroundFTPSelected()) {
                Picasso.with(this)
                        .load(userSharedImp.getBackground())
                        .into(background);
            } else {
                userSharedImp.showUserPhoto(background, userSharedImp.getBackground());
            }
        } else {
            Picasso.with(this)
                    .load(getUserBDImp.getUserBD(userSharedImp.getUserID()).user_background)
                    .into(background);
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
        userSharedImp.saveBackgroundChanges("false");
        userSharedImp.saveProfileChanges("false");
        super.onBackPressed();
    }

    private UpdateUserInteractor getInteractor() {
        UpdateUserInteractor interactor;
        if (userSharedImp.isProfileChanged() && userSharedImp.isBackgroundChanged()) {
            interactor = new UpdateUserInteractor(new UpdateUserImp(userSharedImp.getUserID(),
                    name.getText().toString(), lastName.getText().toString(), email.getText().toString(),
                    userName.getText().toString(), BuildConfig.BASE_URL_DEFAULT + userSharedImp.getUserID() + "_profile", BuildConfig.BASE_URL_DEFAULT + userSharedImp.getUserID() + "_background",
                    description.getText().toString()),
                    new MainThreadImp(), new ThreadExecutor());
        } else {
            if (userSharedImp.isProfileChanged()) {
                interactor = new UpdateUserInteractor(new UpdateUserImp(userSharedImp.getUserID(),
                    name.getText().toString(), lastName.getText().toString(), email.getText().toString(),
                    userName.getText().toString(), BuildConfig.BASE_URL_DEFAULT + userSharedImp.getUserID() + "_profile", userBD.user_background,
                    description.getText().toString()),
                    new MainThreadImp(), new ThreadExecutor());
            } else {
                if (userSharedImp.isBackgroundChanged()) {
                    interactor = new UpdateUserInteractor(new UpdateUserImp(userSharedImp.getUserID(),
                    name.getText().toString(), lastName.getText().toString(), email.getText().toString(),
                    userName.getText().toString(), userBD.user_profile, BuildConfig.BASE_URL_DEFAULT + userSharedImp.getUserID() + "_background",
                    description.getText().toString()),
                    new MainThreadImp(), new ThreadExecutor());
                } else {
                    if (userSharedImp.isBackgroundFTPSelected()) {
                        interactor = new UpdateUserInteractor(new UpdateUserImp(userSharedImp.getUserID(),
                        name.getText().toString(), lastName.getText().toString(), email.getText().toString(),
                        userName.getText().toString(), userSharedImp.getProfile(), userSharedImp.getBackground(),
                        description.getText().toString()),
                        new MainThreadImp(), new ThreadExecutor());
                        userSharedImp.saveBackgroundChanges("false");
                    } else {
                        if (userSharedImp.isProfileFTPSelected()) {
                            interactor = new UpdateUserInteractor(new UpdateUserImp(userSharedImp.getUserID(),
                            name.getText().toString(), lastName.getText().toString(), email.getText().toString(),
                            userName.getText().toString(), userSharedImp.getProfile(), userBD.user_background,
                            description.getText().toString()),
                            new MainThreadImp(), new ThreadExecutor());
                            userSharedImp.saveProfileChanges("false");
                        } else {
                            interactor = new UpdateUserInteractor(new UpdateUserImp(userSharedImp.getUserID(),
                            name.getText().toString(), lastName.getText().toString(), email.getText().toString(),
                            userName.getText().toString(), userBD.user_profile, userBD.user_background,
                            description.getText().toString()),
                            new MainThreadImp(), new ThreadExecutor());
                        }
                    }

                }
            }
        }
        return interactor;
    }

}

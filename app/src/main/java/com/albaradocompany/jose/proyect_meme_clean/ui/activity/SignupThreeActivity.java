package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.ConfirmAvatarDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.ShowAvatarDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.picasso.RoundedTransformation;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.SignupThreePresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSignupThree;
import com.squareup.picasso.Picasso;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class SignupThreeActivity extends BaseActivty implements AbsSignupThree.View, AbsSignupThree.Navigator {
    @BindView(R.id.signup_three_button_back)
    ImageButton bBack;
    @BindView(R.id.signup_three_button_menu)
    ImageButton bMenu;
    @BindView(R.id.signup_three_button_confirm)
    ImageButton bConfirm;
    @BindView(R.id.signup_three_button_pageone)
    ImageButton bPageone;
    @BindView(R.id.signup_three_button_pagetwo)
    ImageButton bPagetwo;
    @BindView(R.id.signup_three_et_name)
    EditText question;
    @BindView(R.id.signup_three_et_email)
    EditText answer1;
    @BindView(R.id.signup_three_et_lastName)
    EditText answer2;
    @BindView(R.id.signup_three_image)
    ImageView image;
    @BindView(R.id.signup_three_pbr)
    ProgressBar pbr;
    @BindDrawable(R.drawable.user_default_image)
    Drawable defaultUserImage;
    @BindString(R.string.default_font)
    String text_font;
    @BindString(R.string.error_answer)
    String answerErrorMessage;

    AbsSignupThree presenter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @OnClick(R.id.signup_three_button_back)
    public void onBackpressed(View view) {
        presenter.onBackPressed();
    }

    @OnClick(R.id.signup_three_button_confirm)
    public void onConfirmClicked(View view) {
        if (checkFields()) {
            saveSignthreeData();
            presenter.onConfirmClicked();
        }
    }

    @OnClick(R.id.signup_three_image)
    public void onImageClicked(View view) {
        presenter.onImageClicked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializePrensenter();
    }

    private void checkUserImage() {
        sharedPreferences = this.getSharedPreferences(ConfirmAvatarDialog.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String isAvatarSelected = sharedPreferences.getString(BuildConfig.IS_SELECTED_AVATAR, "false");
        if (isAvatarSelected.equals("true")) {
            String avatarPath = sharedPreferences.getString(BuildConfig.AVATAR_IMAGE_PATH, "");
            String avatarId = sharedPreferences.getString(BuildConfig.AVATAR_IMAGE_ID, "");
            String avatarDescription = sharedPreferences.getString(BuildConfig.AVATAR_IMAGE_DESCRIPTION, "");
            Picasso.with(this)
                    .load(avatarPath)
                    .transform(new RoundedTransformation())
                    .error(defaultUserImage)
                    .into(image);
        } else {
            image.setImageDrawable(defaultUserImage);
        }
    }

    private void initializePrensenter() {
        presenter = new SignupThreePresenter(this);
        presenter.setView(this);
        presenter.setNavigator(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_signup_three;
    }

    @Override
    protected boolean hideToolbar() {
        return true;
    }

    @Override
    public void hideSignupThree() {
        onBackPressed();
    }

    @Override
    public void showMenu() {

    }

    @Override
    public void showLoading() {
        pbr.setVisibility(View.VISIBLE);
        bConfirm.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        pbr.setVisibility(View.VISIBLE);
        bConfirm.setVisibility(View.GONE);
    }

    @Override
    public void loadUserImage() {
        checkUserImage();
    }

    @Override
    public void showImage() {
        ShowAvatarDialog showAvatarDialog = new ShowAvatarDialog(this, 3);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.resume();
    }

    private boolean checkFields() {
        if (answer1.getText().toString().isEmpty()) {
            showSnackBar(answerErrorMessage, Color.RED);
            return false;
        }
        return true;
    }

    private void showSnackBar(String message, int color) {
        final Snackbar snackbar = Snackbar.make(this.getCurrentFocus(), message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(color);
        View view = snackbar.getView();

        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        Typeface font = Typeface.create(text_font, Typeface.BOLD);
        tv.setTypeface(font);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        snackbar.show();
    }

    private void saveSignthreeData() {
        sharedPreferences = this.getSharedPreferences(SignupThreeActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.USER_QUESTION, question.getText().toString());
        editor.putString(BuildConfig.USER_ANSWER1, answer1.getText().toString());
        if (answer2.getText().toString().isEmpty()) {
            editor.putString(BuildConfig.USER_ANSWER2, "");
        } else {
            editor.putString(BuildConfig.USER_ANSWER2, answer2.getText().toString());
        }
        editor.apply();
    }
}

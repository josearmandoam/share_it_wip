package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
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
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.RegistrationResponseImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerSignupComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.SignupComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.SignupModule;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Question;
import com.albaradocompany.jose.proyect_meme_clean.interactor.QuestionsInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.RegistrationResponseInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.ConfirmAvatarDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.ShowAvatarDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.SignupThreePresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSignupThree;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindColor;
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
    @BindView(R.id.signup_three_et_question)
    TextView question;
    @BindView(R.id.signup_three_et_answer)
    EditText answer1;
    @BindView(R.id.signup_three_et_answer2)
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
    @BindString(R.string.noInternetAvailable)
    String noInternet;
    @BindString(R.string.error)
    String error;
    @BindString(R.string.account_created)
    String acountCreated;
    @BindString(R.string.error_registration)
    String error_registration;
    @BindColor(R.color.color_login)
    int color_login;

    private AbsSignupThree presenter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private RegistrationResponseInteractor interactor;
    private int cnt;
    @Inject
    QuestionsInteractor questionsInteractor;
    @Inject
    UserSharedImp userSharedImp;
    private List<Question> listQuestions;
    private SignupComponent component;


    @OnClick(R.id.signup_three_button_back)
    public void onBackpressed(View view) {
        presenter.onBackPressed();
    }

    @OnClick(R.id.signup_three_button_confirm)
    public void onConfirmClicked(View view) {
        if (checkFields()) {
            userSharedImp.saveSignThreeData(question.getText().toString(),answer1.getText().toString(), answer2.getText().toString());
            Login user = userSharedImp.getUser(image);
            interactor = new RegistrationResponseInteractor(new RegistrationResponseImp(user), new MainThreadImp(), new ThreadExecutor());
            presenter.onConfirmClicked(interactor, user);
        }
    }

    @OnClick(R.id.signup_three_button_generate_question)
    public void onRefreshClicked(View view) {
        presenter.onRefreshQuestionClicked();
    }

    @OnClick(R.id.signup_three_image)
    public void onImageClicked(View view) {
        presenter.onImageClicked();
    }
    @OnClick(R.id.signup_three_button_menu)
    public void onCleanClicked(View view){
        presenter.onCleanClicked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component().inject(this);
        initializePrensenter();

    }

    private void checkUserImage() {
        if (userSharedImp.isAvatarTaken()) {
            Picasso.with(this).load(userSharedImp.getUserAvatar()).into(image);
        } else {
            userSharedImp.showUserPhoto(image,userSharedImp.getUserPhoto());
        }
    }

    private void initializePrensenter() {
        presenter = new SignupThreePresenter(this, questionsInteractor);
        presenter.setView(this);
        presenter.setNavigator(this);
        presenter.initialize();
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
    public void showLoading() {
        pbr.setVisibility(View.VISIBLE);
        bConfirm.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        pbr.setVisibility(View.GONE);
        bConfirm.setVisibility(View.VISIBLE);
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
    public void showNoInternetAvailable() {
        showSnackBar(noInternet, Color.RED);
    }

    @Override
    public void showError(Exception e) {
        showSnackBar(e.getMessage(), Color.RED);
    }

    @Override
    public void showSuccess() {
        showSnackBar(acountCreated, Color.GREEN);
    }

    @Override
    public void showErrorRegistration() {
        pbr.setVisibility(View.GONE);
        bConfirm.setVisibility(View.VISIBLE);
        showSnackBar(error_registration, Color.RED);
    }

    @Override
    public void showQuestions(List<Question> questions) {
        saveQuestions(questions);
        checkQuestion();
    }

    private void saveQuestions(List<Question> questions) {
        this.listQuestions = questions;
    }

    private void checkQuestion() {
        question.setText(getQuestion());
    }

    @Override
    public void refreshQuestions() {
        question.setText(getQuestion());
    }

    @Override
    public void cleanFields() {
        clean();
    }

    private void clean() {
        answer1.setText("");
        answer2.setText("");
        userSharedImp.deleteSignThreeData();
    }

    private String getQuestion() {
        String qtn = listQuestions.get(cnt++).getQuestion();
        if (cnt == listQuestions.size()) {
            cnt = 0;
        }
        return qtn;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.resume();
        checkDataSaved();
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
        tv.setTextColor(color_login);
        snackbar.show();
    }

    @Override
    public void navigateToLogin() {
        this.finish();
        openLogin(this);
    }

    public static void openLogin(Context ctx) {
        Intent intent = new Intent(ctx, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ctx.startActivity(intent);
    }

    private void checkDataSaved() {
        answer1.setText(userSharedImp.getUserAnswer1Saved());
        answer2.setText(userSharedImp.getUserAnswer2Saved());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private SignupComponent component() {
        if (component == null) {
            component = DaggerSignupComponent.builder()
                    .rootComponent(((App) getApplication()).getComponent())
                    .signupModule(new SignupModule(getApplicationContext()))
                    .mainModule(((App) getApplication()).getMainModule())
                    .build();
        }
        return component;
    }
}

package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.RegistrationResponseImp;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerQuestionsComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.QuestionsComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.QuestionsModule;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Question;
import com.albaradocompany.jose.proyect_meme_clean.interactor.QuestionsInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.RegistrationResponseInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.ConfirmAvatarDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.ShowAvatarDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.picasso.RoundedTransformation;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.SignupThreePresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSignupThree;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

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

    private AbsSignupThree presenter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private RegistrationResponseInteractor interactor;
    private int cnt;
    @Inject
    QuestionsInteractor questionsInteractor;
    private List<Question> listQuestions;
    private QuestionsComponent component;


    @OnClick(R.id.signup_three_button_back)
    public void onBackpressed(View view) {
        presenter.onBackPressed();
    }

    @OnClick(R.id.signup_three_button_confirm)
    public void onConfirmClicked(View view) {
        if (checkFields()) {
            saveSignthreeData();
            Login user = getUser();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component().inject(this);
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
//                    .transform(new RoundedTransformation())
                    .error(defaultUserImage)
                    .into(image);
        } else {
            sharedPreferences = this.getSharedPreferences(AddPhotoActivty.class.getName(), Context.MODE_PRIVATE);
            cargarImagenPerfil(sharedPreferences.getString(BuildConfig.USER_PHOTO, ""));
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
    public void showMenu() {

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

    private String getQuestion() {
        String qtn = listQuestions.get(cnt++).getQuestion();
        if (cnt == listQuestions.size()) {
            cnt = 0;
        }
        return qtn;
    }

    private int generateRandomNumber() {
        Random r = new Random();
        return r.nextInt(listQuestions.size());

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

    public Login getUser() {
        Login c = new Login();
        sharedPreferences = this.getSharedPreferences(SignupOneActivity.class.getName(), Context.MODE_PRIVATE);
        c.setNombre(sharedPreferences.getString(BuildConfig.USER_NAME, ""));
        c.setApellidos(sharedPreferences.getString(BuildConfig.USER_LAST_NAME, ""));
        c.setEmail(sharedPreferences.getString(BuildConfig.USER_EMAIL, ""));
        c.setFechaNacimiento(sharedPreferences.getString(BuildConfig.USER_DATE_BIRTHDAY, ""));
        c.setIdUser(sharedPreferences.getString(BuildConfig.USER_ID,""));
        if (sharedPreferences.getString(BuildConfig.IS_SELECTED_PHOTO, "false").equals("true")) {
            sharedPreferences = this.getSharedPreferences(ConfirmAvatarDialog.class.getName(), Context.MODE_PRIVATE);
            if (sharedPreferences.getString(BuildConfig.IS_SELECTED_AVATAR, "false").equals("true")) {
                sharedPreferences = this.getSharedPreferences(SignupOneActivity.class.getName(), Context.MODE_PRIVATE);
                c.setImagePath(sharedPreferences.getString(BuildConfig.USER_AVATAR, ""));
            } else {
//                c.setImagePath(sharedPreferences.getString(BuildConfig.USER_PHOTO, ""));
                //photo from camera or gallery
            }
        }
        sharedPreferences = this.getSharedPreferences(SignupTwoActivity.class.getName(), Context.MODE_PRIVATE);
        c.setUsername(sharedPreferences.getString(BuildConfig.USER_USERNAME, ""));
        c.setPassword(sharedPreferences.getString(BuildConfig.USER_PASSWORD, ""));
        sharedPreferences = this.getSharedPreferences(SignupThreeActivity.class.getName(), Context.MODE_PRIVATE);
        c.setPreguntaSeguridad(sharedPreferences.getString(BuildConfig.USER_QUESTION, ""));
        c.setRespuestaSeguridad(sharedPreferences.getString(BuildConfig.USER_ANSWER1, ""));
        c.setRespuestaSeguridad2(sharedPreferences.getString(BuildConfig.USER_ANSWER2, ""));
        return c;
    }

    private String createId() {
        Calendar c = Calendar.getInstance();
        String userId = "user" + Calendar.YEAR + Calendar.MONTH + Calendar.DAY_OF_MONTH + Calendar.HOUR + Calendar.MINUTE + Calendar.SECOND;
        return userId;
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

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }


    private void checkDataSaved() {
        sharedPreferences = this.getSharedPreferences(SignupThreeActivity.class.getName(), Context.MODE_PRIVATE);
        answer1.setText(sharedPreferences.getString(BuildConfig.USER_ANSWER1, ""));
        answer2.setText(sharedPreferences.getString(BuildConfig.USER_ANSWER2, ""));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        checkDataSaved();
    }

    private QuestionsComponent component() {
        if (component == null) {
            component = DaggerQuestionsComponent.builder()
                    .rootComponent(((App) getApplication()).getComponent())
                    .questionsModule(new QuestionsModule(getApplicationContext()))
                    .mainModule(((App) getApplication()).getMainModule())
                    .build();
        }
        return component;
    }
    private void cargarImagenPerfil(String path) {
        try {
            File f = new File(path);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            image.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            image.setImageResource(R.drawable.user_default_image);
            //e.printStackTrace();
        }
    }
}

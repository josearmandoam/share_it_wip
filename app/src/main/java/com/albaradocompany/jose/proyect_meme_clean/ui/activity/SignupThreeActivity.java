package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.RegistrationResponseImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerUIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIModule;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Question;
import com.albaradocompany.jose.proyect_meme_clean.interactor.QuestionsInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.RegistrationResponseInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.ShowAvatarDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.SignupThreePresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSignupThree;
import com.albaradocompany.jose.proyect_meme_clean.ui.view.ShowSnackBarImp;
import com.squareup.picasso.Picasso;

import java.util.List;

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
    @BindView(R.id.signup_three_lyt)
    RelativeLayout layout;

    @BindDrawable(R.drawable.user_default_image)
    Drawable defaultUserImage;
    @BindString(R.string.default_font)
    String textFont;
    @BindString(R.string.error_answer)
    String answerErrorMessage;
    @BindString(R.string.noInternetAvailable)
    String noInternet;
    @BindString(R.string.error)
    String error;
    @BindString(R.string.account_created)
    String acountCreated;
    @BindString(R.string.error_registration)
    String errorRegistration;
    @BindColor(android.R.color.white)
    int white;
    @BindColor(R.color.color_login)
    int colorLogin;
    @BindDrawable(R.drawable.roundedbutton)
    Drawable round;
    private AbsSignupThree presenter;
    private RegistrationResponseInteractor interactor;
    private int cnt;
    @Inject
    QuestionsInteractor questionsInteractor;
    @Inject
    UserSharedImp userSharedImp;
    private List<Question> listQuestions;
    UIComponent component;
    private ShowSnackBarImp showSnackBar;
    public static Uri uriReceived;
    public static Bitmap bitmapReceived;

    @OnClick(R.id.signup_three_button_back)
    public void onBackpressed(View view) {
        presenter.onBackPressed();
    }

    @OnClick(R.id.signup_three_button_confirm)
    public void onConfirmClicked(View view) {
        if (checkFields()) {
            userSharedImp.saveSignThreeData(question.getText().toString(), answer1.getText().toString(), answer2.getText().toString());
            Login user = userSharedImp.getUser();

            interactor = new RegistrationResponseInteractor(new RegistrationResponseImp(user), new MainThreadImp(), new ThreadExecutor());
            presenter.onConfirmClicked(interactor, user, ((BitmapDrawable) image.getDrawable()).getBitmap());

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
    public void onCleanClicked(View view) {
        presenter.onCleanClicked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component().inject(this);
        initializePrensenter();
        initialize();
    }

    private void initialize() {
        showSnackBar = new ShowSnackBarImp(this);

        uriReceived = null;
        bitmapReceived = null;

        getExtras();
    }

    private void getExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Uri uri = (Uri) bundle.get("uri");
            if (uri != null) {
                uriReceived = uri;
            }
            Bitmap bm = (Bitmap) bundle.get("bitmap");
            if (bm != null) {
                bitmapReceived = bm;
            }
        }
    }

    private void initializePrensenter() {
        presenter = new SignupThreePresenter(this, questionsInteractor);
        presenter.setView(this);
        presenter.setNavigator(this);
        presenter.initialize();

        layout.requestFocus();
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
    public void checkInfoSaved() {
        checkDataSaved();
    }

    @Override
    public void showLoading() {
        pbr.setVisibility(View.VISIBLE);
        bConfirm.setBackgroundColor(white);
    }

    @Override
    public void hideLoading() {
        pbr.setVisibility(View.GONE);
        bConfirm.setBackground(round);
    }

    @Override
    public void loadUserImage() {
        if (userSharedImp.isAvatarTaken()) {
            Picasso.with(this).load(userSharedImp.getUserAvatar()).into(image);
            uriReceived = null;
            bitmapReceived = null;
        } else {
            if (uriReceived != null) {
                image.setImageURI(uriReceived);
            }
            if (bitmapReceived != null) {
                image.setImageBitmap(bitmapReceived);
            }
        }
        if (!userSharedImp.isAvatarTaken() && uriReceived == null && bitmapReceived == null)
            image.setImageDrawable(defaultUserImage);
    }

    @Override
    public void showImage() {
        ShowAvatarDialog showAvatarDialog = new ShowAvatarDialog(this, 3);
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
        showSnackBar.show(acountCreated, Color.GREEN);
    }

    @Override
    public void showErrorRegistration() {
        pbr.setVisibility(View.GONE);
        bConfirm.setVisibility(View.VISIBLE);
        showSnackBar.show(errorRegistration, Color.RED);
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
    }

    private boolean checkFields() {
        if (answer1.getText().toString().isEmpty()) {
            showSnackBar.show(answerErrorMessage, Color.RED);
            return false;
        }
        return true;
    }

    @Override
    public void navigateToLogin() {
        userSharedImp.removeSignInformation();
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
        saveExtras();
    }

    private void saveExtras() {
        Intent intent = new Intent();
        if (uriReceived != null) {
            intent.putExtra("uri", uriReceived);
        }
        if (bitmapReceived != null) {
            intent.putExtra("bitmap", bitmapReceived);
        }
        setResult(RESULT_OK, intent);
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

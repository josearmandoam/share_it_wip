package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.UpdatePasswordImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.UserByEmailImp;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UpdatePasswordInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UserByEmailInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsPassword;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.PasswordPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.view.ShowSnackBarImp;
import com.albaradocompany.jose.proyect_meme_clean.usecase.ShowSnackBar;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class PasswordActivity extends BaseActivty implements AbsPassword.View, AbsPassword.Navigator {
    @BindView(R.id.password_pbr_email)
    ProgressBar email_pbr;
    @BindView(R.id.password_pbr_question)
    ProgressBar question_pbr;
    @BindView(R.id.password_pbr_update)
    ProgressBar update_pbr;
    @BindView(R.id.password_tv_email_send)
    Button send_email;
    @BindView(R.id.password_tv_question_send)
    Button send_question;
    @BindView(R.id.password_tv_update_send)
    Button send_update;
    @BindView(R.id.password_lyt_email)
    RelativeLayout lyt_email;
    @BindView(R.id.password_lyt_question)
    RelativeLayout lyt_question;
    @BindView(R.id.password_lyt_update_password)
    RelativeLayout lyt_update;
    @BindView(R.id.password_et_email)
    EditText email;
    @BindView(R.id.password_et_answer1)
    EditText answ1;
    @BindView(R.id.password_et_answer2)
    EditText answ2;
    @BindView(R.id.password_et_password1)
    EditText password1;
    @BindView(R.id.password_et_password2)
    EditText password2;
    @BindView(R.id.password_tv_question)
    TextView question;

    @BindString(R.string.default_font)
    String default_font;
    @BindString(R.string.noInternetAvailable)
    String noInternet;
    @BindString(R.string.correct_email)
    String correct_email;
    @BindString(R.string.correct_answers)
    String correct_answers;
    @BindString(R.string.failed_asnwers)
    String failed_answers;
    @BindString(R.string.error_password_coincidence)
    String error_passwords;
    @BindString(R.string.password_changed)
    String password_changed;
    @BindString(R.string.password_not_changed)
    String password_not_changed;
    @BindString(R.string.failed_email)
    String failed_email;
    @BindString(R.string.empty_email)
    String empty_email;
    @BindColor(R.color.color_login)
    int color_login;

    UserByEmailInteractor interactor;
    UpdatePasswordInteractor passwordInteractor;
    AbsPassword presenter;
    private Login user;
    ShowSnackBar showSnackBar;

    @OnClick(R.id.password_tv_email_send)
    public void onEmailSendClicked(View view) {
        interactor = new UserByEmailInteractor(new UserByEmailImp(email.getText().toString()),
                new MainThreadImp(), new ThreadExecutor());
        if (!email.getText().toString().isEmpty()) {
            presenter.onEmailSubmitClicked(interactor);
        } else {
            showSnackBar.show(empty_email, Color.RED);
        }
    }

    @OnClick(R.id.password_tv_question_send)
    public void onQuestionSendClicked(View view) {
        presenter.onQuestionsSubmitClicked(user, answ1.getText().toString(), answ2.getText().toString());
    }

    @OnClick(R.id.password_tv_update_send)
    public void onUpdateSendClicked(View view) {
        if (password1.getText().toString().equals(password2.getText().toString())) {
            passwordInteractor = new UpdatePasswordInteractor(new UpdatePasswordImp(user.getIdUser(), password1.getText().toString()), new MainThreadImp(), new ThreadExecutor());
            presenter.onUpdateSubmitClicked(passwordInteractor);
        } else {
            showSnackBar.show(error_passwords, Color.RED);
        }
    }

    @OnClick(R.id.password_iv_back)
    public void onBackClicked(View view) {
        presenter.onBackPressed();
    }

    @OnClick(R.id.password_iv_delete)
    public void onCloseClicked(View view) {
        presenter.onCleanClicked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializePresenter();
        initialize();
    }

    private void initialize() {
        showSnackBar = new ShowSnackBarImp(this);
    }

    private void initializePresenter() {
        presenter = new PasswordPresenter();
        presenter.setView(this);
        presenter.setNavigator(this);
        presenter.initialize();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_password;
    }

    @Override
    protected boolean hideToolbar() {
        return true;
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
    public void showEmailLoading() {
        send_email.setVisibility(View.GONE);
        email_pbr.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmailLoading() {
        send_email.setVisibility(View.VISIBLE);
        email_pbr.setVisibility(View.GONE);
        question.setText(user.getPreguntaSeguridad());
    }

    @Override
    public void showQuestionsLoading() {
        send_question.setVisibility(View.GONE);
        question_pbr.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideQuestionsLoading() {
        send_question.setVisibility(View.VISIBLE);
        question_pbr.setVisibility(View.GONE);
    }

    @Override
    public void showUpdateLoading() {
        update_pbr.setVisibility(View.VISIBLE);
        send_update.setVisibility(View.GONE);
    }

    @Override
    public void hideUpdateLoading() {
        update_pbr.setVisibility(View.GONE);
        send_update.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmailSucces(Login login) {
        showSnackBar.show(correct_email, Color.RED);
        this.user = login;
    }

    @Override
    public void showQuestionsSuccess() {
        showSnackBar.show(correct_answers, Color.GREEN);
    }

    @Override
    public void shoQuestionsFailure() {
        showSnackBar.show(failed_answers, Color.RED);
    }

    @Override
    public void showUpdateSuccess() {
        showSnackBar.show(password_changed, Color.GREEN);
    }

    @Override
    public void showUpdateFailure() {
        showSnackBar.show(password_not_changed, Color.RED);
    }

    @Override
    public void showEmailFailure() {
        showSnackBar.show(failed_email, Color.RED);
    }

    @Override
    public void cleanFields() {
        clean();
    }

    private void clean() {
        email.setText("");
        answ1.setText("");
        answ2.setText("");
        password1.setText("");
        password2.setText("");
    }

    @Override
    public void navigateToQuestions() {
        lyt_email.setVisibility(View.GONE);
        lyt_question.setVisibility(View.VISIBLE);
    }

    @Override
    public void navigateToUpdatePassword() {
        lyt_question.setVisibility(View.GONE);
        lyt_update.setVisibility(View.VISIBLE);
    }

    @Override
    public void navigateToSignin() {
        openSignin(this);
    }

    @Override
    public void navigateToBack() {
        this.finish();
    }

    private void openSignin(Context context) {
        this.finish();
        Intent intent = new Intent(this, LoginActivity.class);
        context.startActivity(intent);
    }
}

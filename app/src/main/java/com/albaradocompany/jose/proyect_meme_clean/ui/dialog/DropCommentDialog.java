package com.albaradocompany.jose.proyect_meme_clean.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ProgressBar;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.UpdateCommentApiImp;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UpdateCommentInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.CommentsActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.DropCommentPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsDropCommentPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.view.ShowSnackBarImp;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jose on 15/05/2017.
 */

public class DropCommentDialog extends AlertDialog implements AbsDropCommentPresenter.Navigator, AbsDropCommentPresenter.View {
    private static final String DELETE = "delete";

    Comment comment;
    Context context;
    AlertDialog dialog;
    AbsDropCommentPresenter presenter;
    ShowSnackBarImp showSnackBarImp;

    @BindView(R.id.drop_comment_pbr)
    ProgressBar progressBar;
    @BindString(R.string.error_drop_comment)
    String error_drop_comment;

    @OnClick(R.id.drop_comment_option1)
    public void onDropCommentClicked(View view) {
        presenter.onDropClicked(getDELCommentInteractor(), comment);
    }

    public DropCommentDialog(Context context, Comment comment) {
        super(context);
        this.context = context;
        this.comment = comment;
        initialize();
    }

    private void initialize() {
        dialog = new AlertDialog.Builder(context)
                .setView(R.layout.dialog_drop_comment)
                .create();
        dialog.show();

        ButterKnife.bind(this, dialog);

        presenter = new DropCommentPresenter(getContext());
        presenter.setNavigator(this);
        presenter.setView(this);

        showSnackBarImp = new ShowSnackBarImp(getOwnerActivity());
    }

    public UpdateCommentInteractor getDELCommentInteractor() {
        return new UpdateCommentInteractor(new UpdateCommentApiImp(null, null,
                comment.getCommentId(), null, null, null, null, null, null, DELETE), new MainThreadImp(), new ThreadExecutor());
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showNoInternetAvailable() {
        showSnackBarImp.show(error_drop_comment, Color.RED);
    }

    @Override
    public void showError(Exception e) {
        showSnackBarImp.show(e.getMessage(), Color.RED);
    }

    @Override
    public void hideDialog() {
        ((CommentsActivity) context).updateCommentsFromOutSite();
        dialog.dismiss();
    }

    @Override
    public void showFailure() {
        showSnackBarImp.show(error_drop_comment, Color.RED);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }
}

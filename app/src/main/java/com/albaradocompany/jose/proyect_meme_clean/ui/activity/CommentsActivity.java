package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD.GetUserBDImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.UserBD;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.CommentsApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.UpdateCommentApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerUIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIModule;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;
import com.albaradocompany.jose.proyect_meme_clean.global.util.DateUtil;
import com.albaradocompany.jose.proyect_meme_clean.global.util.RecyclerHelper;
import com.albaradocompany.jose.proyect_meme_clean.interactor.CommentsInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UpdateCommentInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;
import com.albaradocompany.jose.proyect_meme_clean.ui.adaptor.CommentsRecyclerAdapter;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.DropCommentDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.CommentPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsCommentPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.view.ShowSnackBarImp;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetComments;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class CommentsActivity extends BaseActivty implements AbsCommentPresenter.View, AbsCommentPresenter.Navigator {
    private static final String INSERT = "insert";
    private static final String DELETE = "delete";

    @BindView(R.id.comments_rv_list_comments)
    RecyclerView recyclerView;
    @BindView(R.id.comments_tv_comment)
    EditText comment;
    @BindView(R.id.comments_ibtn_send)
    ImageButton btn_send;
    @BindView(R.id.comments_pbr)
    ProgressBar progressBar;
    @BindView(R.id.comments_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindDrawable(R.drawable.send)
    Drawable sendLight;
    @BindDrawable(R.drawable.send_dark)
    Drawable sendDark;
    @BindString(R.string.commento_couldnt_send)
    String comment_failed;
    @BindString(R.string.error_update_comments)
    String error_update_comments;

    List<Comment> comments;
    CommentsRecyclerAdapter adapter;
    AbsCommentPresenter presenter;
    String imageId;
    UIComponent component;
    Comment commentOnCache;
    ShowSnackBarImp showSnackBarImp;

    UserBD userBD;
    @Inject
    GetUserBDImp getUserBDImp;
    @Inject
    UserSharedImp userSharedImp;

    @OnClick(R.id.comments_ibtn_send)
    public void onSendCommentClicked(View view) {
        presenter.onSendCommentClicked(getINSCommentInteractor(), imageId);
    }

    @OnClick(R.id.comments_ibtn_back)
    public void onBackClicked(View view) {
        presenter.onBackClicked();
    }

    CommentsRecyclerAdapter.onCommentClicked onCommentClicked = new CommentsRecyclerAdapter.onCommentClicked() {
        @Override
        public void onPictureClicked() {

        }

        @Override
        public void onUsernameClicked() {

        }

        @Override
        public void onLongClick(Comment comment) {
            if (comment.getUserId().equals(userSharedImp.getUserID())) {
                presenter.onCommentLongClick(comment);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getPictureComments(getCommentsInteractor(imageId), imageId);
    }

    private void initialize() {
        component().inject(this);

        userBD = getUserBDImp.getUserBD(userSharedImp.getUserID());

        getDataBundle();
        adapter = new CommentsRecyclerAdapter(this, comments, onCommentClicked);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        presenter = new CommentPresenter(this);
        presenter.setView(this);
        presenter.setNavigator(this);

        showSnackBarImp = new ShowSnackBarImp(this);

        initializeSwipeRefresh();
    }

    private void updateRecycler() {
        adapter = null;
        adapter = new CommentsRecyclerAdapter(this, comments, onCommentClicked);
        recyclerView.setAdapter(adapter);
    }

    private void initializeSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getPictureComments(getCommentsInteractor(imageId), imageId);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comments;
    }

    @Override
    protected boolean hideToolbar() {
        return true;
    }

    public void getDataBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            comments = (ArrayList<Comment>) bundle.get("comments");
            imageId = (String) bundle.get("imageId");
        }
    }

    public UpdateCommentInteractor getINSCommentInteractor() {
        commentOnCache = new Comment(imageId, userSharedImp.createCommentID(), userBD.user_name + " " + userBD.user_lastname,
                userBD.user_profile, comment.getText().toString(), userSharedImp.getUserID(), DateUtil.getCurrentDate(), DateUtil.getCurrentTime());
        return new UpdateCommentInteractor(new UpdateCommentApiImp(commentOnCache.getUserId(), commentOnCache.getImageId(),
                commentOnCache.getCommentId(), commentOnCache.getComment(), userBD.user_name,
                userBD.user_lastname, commentOnCache.getProfile(), commentOnCache.getTime(),
                commentOnCache.getDate(), INSERT), new MainThreadImp(), new ThreadExecutor());
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
    public void showLoading() {
        comment.setEnabled(false);
        btn_send.setImageDrawable(sendLight);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        btn_send.setImageDrawable(sendDark);
    }

    @Override
    public void onNoInternetAvailable() {
        showSnackBarImp.show(comment_failed, Color.RED);
    }

    @Override
    public void shoError(Exception e) {
        showSnackBarImp.show(e.getMessage(), Color.RED);
    }

    @Override
    public void showFailure() {
        showSnackBarImp.show(comment_failed, Color.RED);
    }

    @Override
    public void updateComments() {
        comment.setText("");
        comment.setEnabled(true);
        comments.add(commentOnCache);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showDialogDropComment(Comment comment) {
        new DropCommentDialog(this, comment);
    }

    @Override
    public void showNoInternetAvailable() {
        showSnackBarImp.show(error_update_comments, Color.RED);
    }

    @Override
    public void showError(Exception e) {
        showSnackBarImp.show(e.getMessage(), Color.RED);
    }

    @Override
    public void showComments(List<Comment> comments) {
        checkNewComments(comments);
    }

    private void checkNewComments(List<Comment> newComments) {
        if (RecyclerHelper.hasNewComments(comments, newComments)) {
            this.comments = newComments;
            updateRecycler();
        }
    }

    private GetComments getCommentsInteractor(String imageId) {
        return new CommentsInteractor(new CommentsApiImp(imageId), new MainThreadImp(), new ThreadExecutor());
    }

    public void updateCommentsFromOutSite() {
        presenter.getPictureComments(getCommentsInteractor(imageId), imageId);
    }

    @Override
    public void navigateToBack() {
        onBackPressed();
    }
}

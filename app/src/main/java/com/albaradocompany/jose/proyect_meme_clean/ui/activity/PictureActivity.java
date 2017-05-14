package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD.GetUserBDImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.CommentsApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.LikesApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerUIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIModule;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Like;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.interactor.CommentsInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.LikesInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.LikesDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.PicturePresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsPicturePresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.view.ShowSnackBarImp;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetComments;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetLikes;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class PictureActivity extends BaseActivty implements AbsPicturePresenter.View, AbsPicturePresenter.Navigator {

    @BindView(R.id.picture_row_iv_photo)
    ImageView image;
    @BindView(R.id.picture_row_iv_user_profile)
    ImageView profile;
    @BindView(R.id.picture_row_tv_description)
    TextView description;
    @BindView(R.id.picture_row_tv_user_name)
    TextView name;
    @BindView(R.id.picture_row_tv_time)
    TextView time;
    @BindView(R.id.picture_row_tv_likes)
    TextView likes;
    @BindView(R.id.picture_row_tv_comments)
    TextView comments;
    @BindView(R.id.picture_lyt_photo)
    RelativeLayout layoutPhoto;
    @BindView(R.id.picture_pbr)
    ProgressBar progressBar;
    @BindView(R.id.picture_row_ibtn_save)
    ImageButton btnSave;
    @BindView(R.id.picture_row_ibtn_like)
    ImageButton btnLike;

    @BindString(R.string.noInternetAvailable)
    String noInternet;
    @BindDrawable(R.drawable.save_unclicked)
    Drawable saveUnclicked;
    @BindDrawable(R.drawable.save_clicked)
    Drawable saveClicked;
    @BindDrawable(R.drawable.heart_border)
    Drawable heartBorder;
    @BindDrawable(R.drawable.heart_fill)
    Drawable heartFill;


    List<Like> listLikes;
    List<Comment> listComments;
    AbsPicturePresenter presenter;
    LikesInteractor likesInteractor;
    GetComments commentsInteractor;
    ShowSnackBarImp showSnackBarImp;
    UIComponent component;
    @Inject
    GetUserBDImp getUserBD;
    @Inject
    UserSharedImp userSharedImp;

    @OnClick(R.id.picture_row_ibtn_comments)
    public void onCommentClicked(View view) {
        presenter.onCommentsClicked();
    }

    @OnClick(R.id.picture_row_tv_comments)
    public void onTVCommentClicked(View view) {
        presenter.onCommentsClicked();
    }
    @OnClick(R.id.picture_row_ibtn_like)
    public void onLikesClicked(View view) {
        presenter.onLikesClicked();
    }

    @OnClick(R.id.picture_row_tv_likes)
    public void onTVLikesClicked(View view) {
        presenter.onLikesClicked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    private void initialize() {
        component().inject(this);

        showSnackBarImp = new ShowSnackBarImp(this);
        presenter = new PicturePresenter(this);
        presenter.setView(this);
        presenter.setNavigator(this);
        getDataReceived();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_picture;
    }

    @Override
    protected boolean hideToolbar() {
        return true;
    }

    public void getDataReceived() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String imageId = (String) bundle.get("imageId");
            Login user = (Login) bundle.get("user");
            Picture pic = (Picture) bundle.get("image");
            presenter.initializeData(user, pic);
            presenter.getPictureLikes(getLikesInteractor(imageId), imageId);
            presenter.getPictureComments(getCommentsInteractor(imageId), imageId);
        }
    }

    private GetComments getCommentsInteractor(String imageId) {
        commentsInteractor = new CommentsInteractor(new CommentsApiImp(imageId), new MainThreadImp(), new ThreadExecutor());
        return commentsInteractor;
    }

    public GetLikes getLikesInteractor(String imageId) {
        likesInteractor = new LikesInteractor(new LikesApiImp(imageId), new MainThreadImp(), new ThreadExecutor());
        return likesInteractor;
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        layoutPhoto.setVisibility(View.GONE);
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
    public void showLikes(List<Like> likesList) {
        this.listLikes = likesList;
        likes.setText("" + likesList.size());
        if (likesList.size() > 0) {
            checkLiked(likesList);
        } else {
            btnLike.setImageDrawable(heartBorder);
        }
    }

    @Override
    public void showComments(List<Comment> commentsList) {
        this.listComments = commentsList;
        comments.setText("" + commentsList.size());

    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        layoutPhoto.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPicture(String imagePath) {
        Picasso.with(this).load(imagePath).into(image);
    }

    @Override
    public void showUserProfile(String imagePath) {
        Picasso.with(this).load(imagePath).into(profile);
    }

    @Override
    public void showDescription(String desc) {
        description.setText(desc);
    }

    @Override
    public void showUsername(String s) {
        name.setText(s);
    }

    @Override
    public void showTime(String timee) {
        time.setText(timee);
    }

    @Override
    public void showSaved(String imageId) {
        checkSaved(imageId);
    }

    @Override
    public void showLikesDialog() {
        new LikesDialog(this, listLikes);
    }

    private void checkSaved(String imageId) {
        if (getUserBD.isPhotoSaved(imageId)) {
            btnSave.setImageDrawable(saveClicked);
        } else {
            btnSave.setImageDrawable(saveUnclicked);
        }
    }

    private void checkLiked(List<Like> list) {
        boolean like = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUserId().equals(userSharedImp.getUserID())) {
                like = true;
            }
        }
        if (like)
            btnLike.setImageDrawable(heartFill);
        else
            btnLike.setImageDrawable(heartBorder);
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
    public void navigateToComments() {
        openCommentsActivity();
    }

    public void openCommentsActivity() {
        Intent intent = new Intent(this, CommentsActivity.class);
        intent.putExtra("comments", (Serializable) listComments);
        startActivity(intent);
    }
}

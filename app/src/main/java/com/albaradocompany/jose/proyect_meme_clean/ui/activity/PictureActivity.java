package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD.GetUserBDImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.UserBD;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.CommentsApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.LikesApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.UpdateLikesApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.UpdateSavedPictureApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerUIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIModule;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Like;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.global.model.User;
import com.albaradocompany.jose.proyect_meme_clean.global.util.DateUtil;
import com.albaradocompany.jose.proyect_meme_clean.global.util.RecyclerHelper;
import com.albaradocompany.jose.proyect_meme_clean.interactor.CommentsInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.LikesInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UpdateLikesInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UpdateSavedPictureInteractor;
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
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class PictureActivity extends BaseActivty implements AbsPicturePresenter.View, AbsPicturePresenter.Navigator {

    private static final String DELETE = "delete";
    private static final String INSERT = "insert";
    private static final String COMMENTS = "comments";
    private static final String IMAGE_ID = "imageId";
    private static final int COMMENT_ACTION = 0;
    private static final String USER = "user";
    private static final String IMAGE = "image";
    @BindView(R.id.picture_detail_iv_photo)
    ImageView image;
    @BindView(R.id.picture_detail_iv_user_profile)
    ImageView profile;
    @BindView(R.id.picture_detail_tv_description)
    TextView description;
    @BindView(R.id.picture_detail_tv_user_name)
    TextView name;
    @BindView(R.id.picture_detail_tv_time)
    TextView time;
    @BindView(R.id.picture_detail_tv_likes)
    TextView likes;
    @BindView(R.id.picture_detail_tv_comments)
    TextView comments;
    @BindView(R.id.picture_lyt_photo)
    RelativeLayout layoutPhoto;
    @BindView(R.id.picture_pbr)
    ProgressBar progressBar;
    @BindView(R.id.picture_detail_ibtn_save)
    ImageButton btnSave;
    @BindView(R.id.picture_detail_ibtn_like)
    ImageButton btnLike;
    @BindView(R.id.picture_detail_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

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
    @BindString(R.string.likes)
    String likes_title;
    @BindString(R.string.comments)
    String comments_title;

    List<Like> listLikes = new ArrayList<>();
    List<Comment> listComments = new ArrayList<>();
    AbsPicturePresenter presenter;
    LikesInteractor likesInteractor;
    GetComments commentsInteractor;
    ShowSnackBarImp showSnackBarImp;
    UIComponent component;
    String imageId;
    UserBD userBD;
    @Inject
    GetUserBDImp getUserBD;
    @Inject
    UserSharedImp userSharedImp;
    Picture picture;

    @OnClick(R.id.picture_detail_ibtn_comments)
    public void onCommentClicked(View view) {
        presenter.onCommentsClicked();
    }

    @OnClick(R.id.picture_detail_tv_comments)
    public void onTVCommentClicked(View view) {
        presenter.onCommentsClicked();
    }

    @OnClick(R.id.picture_detail_ibtn_like)
    public void onLikesClicked(View view) {
        updateUserLikePictureApi();
    }

    @OnClick(R.id.picture_ibtn_back)
    public void onBackClicked(View view) {
        presenter.onBackClicked();
    }

    @OnClick(R.id.picture_detail_tv_likes)
    public void onTVLikesClicked(View view) {
        presenter.onLikesClicked();
    }

    @OnClick(R.id.picture_detail_ibtn_save)
    public void onSaveClicked(View view) {
        updateUserSavePictureApi();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.resume();
    }

    private void initialize() {
        component().inject(this);

        userBD = getUserBD.getUserBD(userSharedImp.getUserID());

        showSnackBarImp = new ShowSnackBarImp(this);
        presenter = new PicturePresenter(this);
        presenter.setView(this);
        presenter.setNavigator(this);
        getDataReceived();
        initializeSwipeRefresh();

    }

    private void initializeSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.resume();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
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
            imageId = (String) bundle.get(IMAGE_ID);
            User user = (User) bundle.get(USER);
            picture = (Picture) bundle.get(IMAGE);
            presenter.initializeData(user, picture, getLikesInteractor(imageId), getCommentsInteractor(imageId));
            presenter.initialize();
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
        if (RecyclerHelper.hasNewLikes(listLikes, likesList)) {
            listLikes = likesList;
            likes.setText("" + likesList.size() + " " + likes_title);
            if (likesList.size() > 0) {
                checkLiked(likesList);
            } else {
                btnLike.setImageDrawable(heartBorder);
            }
        } else {
            likes.setText("" + listLikes.size() + " " + likes_title);
            if (listLikes.size() > 0) {
                checkLiked(likesList);
            } else {
                btnLike.setImageDrawable(heartBorder);
            }
        }
    }

    @Override
    public void showComments(List<Comment> commentsList) {
        if (RecyclerHelper.hasNewComments(listComments, commentsList)) {
            listComments = commentsList;
            comments.setText("" + commentsList.size() + " " + comments_title);
        } else {
            comments.setText("" + listComments.size() + " " + comments_title);
        }
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        layoutPhoto.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPicture(String imagePath) {
        if (!imagePath.isEmpty())
            Picasso.with(this).load(imagePath).into(image);
    }

    @Override
    public void showUserProfile(String imagePath) {
        if (!imagePath.isEmpty())
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
    public void showTime(String date, String timee) {
        time.setText(DateUtil.timeAgo(date, timee));
    }

    @Override
    public void showSaved(String imageId) {
        checkSaved(imageId);
    }

    @Override
    public void showLikesDialog() {
        new LikesDialog(this, listLikes);
    }

    @Override
    public void showUnLikePicture() {
        btnLike.setImageDrawable(heartBorder);
    }

    @Override
    public void showLikePicture() {
        btnLike.setImageDrawable(heartFill);
    }

    @Override
    public void showPictureNotSaved() {
        btnSave.setImageDrawable(saveUnclicked);
    }

    @Override
    public void showPictureSaved() {
        btnSave.setImageDrawable(saveClicked);
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

    @Override
    public void navigateToBack() {
        onBackPressed();
    }

    public void openCommentsActivity() {
        Intent intent = new Intent(this, CommentsActivity.class);
        intent.putExtra(COMMENTS, (Serializable) listComments);
        intent.putExtra(IMAGE_ID, imageId);
        startActivity(intent);
    }

    private void updateUserLikePictureApi() {
        if (btnLike.getDrawable().equals(heartFill)) {
            presenter.onUserUnLikePhoto(getUpdateDELLikeInteractor(), listLikes);
        } else {
            presenter.onUserLikePhoto(getUpdateINSLikeInteractor(), listLikes);
        }
    }

    private void updateUserSavePictureApi() {
        if (btnSave.getDrawable().equals(saveClicked)) {
            presenter.onDeleteSavedPicture(getUpdateDELSavedPictureInteractor());
        } else {
            presenter.onInsertSavePicture(getUpdateINSSavePictureInteractor());
        }
    }

    public UpdateLikesInteractor getUpdateDELLikeInteractor() {
        return new UpdateLikesInteractor(new UpdateLikesApiImp(userBD.userId, imageId,
                userBD.user_name, userBD.user_lastname, userBD.user_profile, userSharedImp.createLikeID(), DELETE), new MainThreadImp(), new ThreadExecutor());
    }

    public UpdateLikesInteractor getUpdateINSLikeInteractor() {
        return new UpdateLikesInteractor(new UpdateLikesApiImp(userBD.userId, imageId,
                userBD.user_name, userBD.user_lastname, userBD.user_profile, userSharedImp.createLikeID(), INSERT), new MainThreadImp(), new ThreadExecutor());
    }

    public UpdateSavedPictureInteractor getUpdateDELSavedPictureInteractor() {
        return new UpdateSavedPictureInteractor(new UpdateSavedPictureApiImp(userBD.userId, null,
                null, null, imageId, null, DELETE),
                new MainThreadImp(), new ThreadExecutor());
    }

    public UpdateSavedPictureInteractor getUpdateINSSavePictureInteractor() {
        return new UpdateSavedPictureInteractor(new UpdateSavedPictureApiImp(userBD.userId, picture.getImagePath(),
                picture.getDescription(), DateUtil.getCurrentDateFormated(), imageId, DateUtil.getCurrentTimeFormated(), INSERT),
                new MainThreadImp(), new ThreadExecutor());
    }
}

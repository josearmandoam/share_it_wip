package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD.GetUserBDImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.UserBD;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.CommentsApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.FeedApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.LikesApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.PicturesByIdApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.PicturesSavedApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.UpdateLikesApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.UpdateSavedPictureApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Feed;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Like;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Post;
import com.albaradocompany.jose.proyect_meme_clean.interactor.CommentsInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.FeedInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.LikesInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.PicturesByIdInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UpdateLikesInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UpdateSavedPictureInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.MainActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsFeedPresenter;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetComments;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetFeed;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetLikes;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetPicturesById;
import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdateLike;
import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdateSavedPicture;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by jose on 20/05/2017.
 */

public class FeedPresenter extends AbsFeedPresenter {
    private static final String INSERT = "insert";
    private static final String DELETE = "delete";
    private static final int UPDATE = 1;
    private static final int GET = 0;
    Context context;
    String userId;
    List<Post> posts;
    UserBD userBD;

    @Inject
    GetUserBDImp getUserBDImp;
    @Inject
    UserSharedImp userSharedImp;
    private int action;

    public FeedPresenter(Context context, String userId) {
        this.context = context;
        this.userId = userId;
    }

    @Override
    public void initialize() {
        getComponent().inject(this);
        view.showLoading();
        userBD = getUserBDImp.getUserBD(userId);
        checkForUserSavedPictures();
        action = GET;
        posts = new ArrayList<>();
    }

    public void checkForUserSavedPictures() {
        PicturesByIdInteractor getPicturesById = new PicturesByIdInteractor(new PicturesSavedApiImp(userBD.userId), new MainThreadImp(), new ThreadExecutor());
        getPicturesById.getPictures(new GetPicturesById.Listener() {
            @Override
            public void onNoInternetAvailable() {
                view.showNoInternetAvailable();
            }

            @Override
            public void onError(Exception e) {
                view.showError(e);
            }

            @Override
            public void onPicturesReceived(List<Picture> pictures) {
                for (Picture picture : pictures) {
                    getUserBDImp.insertUserSavedPicture(picture);
                }
                userSharedImp.saveUserID(userBD.userId);
            }
        });
    }

    @Override
    public void resume() {
        action = UPDATE;
        if (posts != null) {
            view.showPosts(posts);
        } else {
            posts = new ArrayList<>();
            getFeed(new FeedInteractor(new FeedApiImp(userId), new MainThreadImp(), new ThreadExecutor()));
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void getFeed(FeedInteractor feedInteractor) {
        feedInteractor.getFeed(new GetFeed.Listener() {
            @Override
            public void onNoInternetAvailable() {
                view.showNoInternetAvailable();
            }

            @Override
            public void onError(Exception e) {
                view.showError(e);
            }

            @Override
            public void onFeedReceived(List<Feed> feeds) {
                getPicturesOfFeed(feeds);
            }
        });
    }

    @Override
    public void onCommentsClicked(List<Comment> comments, String imageId, Post post, int adapterPosition) {
        navigator.navigateToComments(comments, imageId, post, adapterPosition);
    }

    @Override
    public void onLikesClicked(List<Like> likes) {
        view.showLikesDialog(likes);
    }

    @Override
    public void onSaveClicked(Picture picture, String userId) {
        getUserBDImp.insertUserSavedPicture(picture);
        UpdateSavedPictureInteractor interactor = getSavePictureInteractor(picture, userId);
        interactor.updateSavedPicture(new UpdateSavedPicture.Listener() {
            @Override
            public void onNoInternetAvailable() {
                view.showNoInternetAvailable();
            }

            @Override
            public void onError(Exception e) {
                view.showError(e);
            }

            @Override
            public void onSuccess() {
                view.showSaveSuccess();
            }

            @Override
            public void onFailure() {
                view.showSaveFailure();
            }
        });
    }

    @Override
    public void onUnSaveClicked(final Picture picture, String userId) {
        UpdateSavedPictureInteractor interactor = getUnSavePictureInteractor(userId);
        interactor.updateSavedPicture(new UpdateSavedPicture.Listener() {
            @Override
            public void onNoInternetAvailable() {
                view.showNoInternetAvailable();
            }

            @Override
            public void onError(Exception e) {
                view.showError(e);
            }

            @Override
            public void onSuccess() {
                view.showUnSaveSuccess();
                getUserBDImp.deleteUserSavedPicture(picture.getImageId());
            }

            @Override
            public void onFailure() {
                view.showUnSaveFailure();
            }
        });
    }

    @Override
    public void onSaveLikeClicked(String imageId) {
        UpdateLikesInteractor interactor = getINSUpdateLikeInteractor(imageId);
        interactor.updateLike(new UpdateLike.Listener() {
            @Override
            public void onNoInternetAvailable() {
                view.showNoInternetAvailable();
            }

            @Override
            public void onError(Exception e) {
                view.showError(e);
            }

            @Override
            public void onSuccess() {
                view.showLikeSuccess();
            }

            @Override
            public void onFailure() {
                view.showLikedFailure();
            }
        });
    }


    @Override
    public void onUnSaveLikeClicked(String userId, String imageId) {
        UpdateLikesInteractor interactor = getDELUpdateLIkesInteractor(userId, imageId);
        interactor.updateLike(new UpdateLike.Listener() {
            @Override
            public void onNoInternetAvailable() {
                view.showNoInternetAvailable();
            }

            @Override
            public void onError(Exception e) {
                view.showError(e);
            }

            @Override
            public void onSuccess() {
                view.showUnLikeSuccess();
            }

            @Override
            public void onFailure() {
                view.showUnLikeFailure();
            }
        });
    }

    @Override
    public void onSearchClickedFAB() {
        navigator.navigateToSearch();
    }

    @Override
    public void onSavePictureClickedFAB() {
        navigator.navigateToSavePictures();
    }

    @Override
    public void onProfileClickedFAB() {
        navigator.navigateToProfile();
    }

    private void getPicturesOfFeed(List<Feed> feeds) {
        for (final Feed feed : feeds) {
            PicturesByIdInteractor interactor = getPicturesInteractor(feed.getxUserId());
            interactor.getPictures(new GetPicturesById.Listener() {

                @Override
                public void onNoInternetAvailable() {
                    view.showNoInternetAvailable();
                }

                @Override
                public void onError(Exception e) {
                    view.showError(e);
                }

                @Override
                public void onPicturesReceived(List<Picture> pictures) {
                    for (int i = 0; i < pictures.size(); i++) {
                        Post c = new Post();
                        c.setPicture(pictures.get(i));
                        c.setFeedId(feed.getFeedId());
                        c.setUserId(feed.getUserId());
                        c.setxUsername(feed.getxUsername());
                        c.setxUserId(feed.getxUserId());
                        c.setxProfile(feed.getxProfile());
                        posts.add(c);
                    }
                    getLikesComments();
                }
            });
        }
    }

    private void getLikesComments() {
        for (final Post post : posts) {
            CommentsInteractor interactorComments = getCommentsInteractor(post.getPicture().getImageId());
            interactorComments.getComments(new GetComments.Listener() {
                @Override
                public void onNoInternetAvailable() {
                    view.showNoInternetAvailable();
                }

                @Override
                public void onError(Exception e) {
                    view.showError(e);
                }

                @Override
                public void onCommentesReceived(List<Comment> comments) {
                    post.setCommentList(comments);
                }
            });
            LikesInteractor interactorLikes = getLikesInteractor(post.getPicture().getImageId());
            interactorLikes.getLikes(new GetLikes.Listener() {
                @Override
                public void onNoInternetAvailable() {
                    view.showNoInternetAvailable();
                }

                @Override
                public void onError(Exception e) {
                    view.showError(e);
                }

                @Override
                public void onLikesReceived(List<Like> likes) {
                    post.setLikeList(likes);
                    if (posts.size() - 1 > 0)
                        if (post.getPicture().getImageId().equals(posts.get(posts.size() - 1).getPicture().getImageId()))
                            postReceived(posts);
                }


            });
        }
    }

    private void postReceived(List<Post> posts) {
        switch (action) {
            case GET:
                view.showPosts(orderList(posts));
                view.hideLoading();
                view.showFloatingButton();
                break;
            case UPDATE:
                view.updatePosts(orderList(posts));
                break;
        }

    }

    private List<Post> orderList(List<Post> posts) {
        Collections.sort(posts, new Comparator<Post>() {
            @Override
            public int compare(Post post, Post t1) {
                if (Date.valueOf(post.getPicture().getDate()).after(Date.valueOf(t1.getPicture().getDate())))
                    return 0;
                else
                    return -1;
            }
        });
        return posts;
    }

    private LikesInteractor getLikesInteractor(String imageId) {
        return new LikesInteractor(new LikesApiImp(imageId), new MainThreadImp(), new ThreadExecutor());
    }

    public PicturesByIdInteractor getPicturesInteractor(String s) {
        return new PicturesByIdInteractor(new PicturesByIdApiImp(s), new MainThreadImp(), new ThreadExecutor());
    }

    public CommentsInteractor getCommentsInteractor(String imageId) {
        return new CommentsInteractor(new CommentsApiImp(imageId), new MainThreadImp(), new ThreadExecutor());
    }

    public UpdateSavedPictureInteractor getSavePictureInteractor(Picture picture, String userId) {
        return new UpdateSavedPictureInteractor(new UpdateSavedPictureApiImp(userId, picture.getImagePath(),
                picture.getDescription(), picture.getDate(), picture.getImageId(), picture.getTime(), INSERT), new MainThreadImp(), new ThreadExecutor());
    }

    private UpdateSavedPictureInteractor getUnSavePictureInteractor(String userId) {
        return new UpdateSavedPictureInteractor(
                new UpdateSavedPictureApiImp(userId, null, null, null, null, null, DELETE),
                new MainThreadImp(), new ThreadExecutor());
    }

    private UpdateLikesInteractor getINSUpdateLikeInteractor(String imageId) {
        return new UpdateLikesInteractor(new UpdateLikesApiImp(userId, imageId,
                userBD.user_name, userBD.user_lastname, userBD.user_profile, userSharedImp.createLikeID(), INSERT),
                new MainThreadImp(), new ThreadExecutor());
    }

    private UpdateLikesInteractor getDELUpdateLIkesInteractor(String userId, String imageId) {
        return new UpdateLikesInteractor(new UpdateLikesApiImp(userId, imageId, null, null, null, null, DELETE),
                new MainThreadImp(), new ThreadExecutor());
    }

    private FeedInteractor getFeedInteractor() {
        return new FeedInteractor(new FeedApiImp(userId), new MainThreadImp(), new ThreadExecutor());
    }

    protected UIComponent getComponent() {
        return ((MainActivity) context).component();
    }
}

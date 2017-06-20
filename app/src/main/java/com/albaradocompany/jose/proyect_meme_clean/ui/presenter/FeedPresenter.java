package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD.GetUserBDImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.UserBD;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.CommentsApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.FeedApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.LikesApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.PicturesByIdApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.UpdateLikesApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Feed;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Like;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Post;
import com.albaradocompany.jose.proyect_meme_clean.global.util.ListUtil;
import com.albaradocompany.jose.proyect_meme_clean.interactor.CommentsInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.FeedInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.LikesInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.PicturesByIdInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UpdateLikesInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.MainActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsFeedPresenter;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetComments;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetFeed;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetLikes;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetPicturesById;
import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdateLike;

import java.util.ArrayList;
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

    public FeedPresenter(Context context, String userId) {
        this.context = context;
        this.userId = userId;
    }

    @Override
    public void initialize() {
        getComponent().inject(this);
        if (posts != null) {
            view.updatePosts(posts);
        } else {
            view.showLoading();
            getFeed(getFeedInteractor());
        }
        userBD = getUserBDImp.getUsers().get(0);
//        checkForUserSavedPictures();
    }
//
//    public void checkForUserSavedPictures() {
//        PicturesByIdInteractor getPicturesById = new PicturesByIdInteractor(new PicturesSavedApiImp(userBD.userId), new MainThreadImp(), new ThreadExecutor());
//        getPicturesById.getPictures(new GetPicturesById.Listener() {
//            @Override
//            public void onNoInternetAvailable() {
//                view.showNoInternetAvailable();
//            }
//
//            @Override
//            public void onError(Exception e) {
//                view.showError(e);
//            }
//
//            @Override
//            public void onPicturesReceived(List<Picture> pictures) {
//                for (Picture picture : pictures) {
//                    getUserBDImp.insertUserSavedPicture(picture);
//                }
////                userSharedImp.saveUserID(userBD.userId);
//            }
//        });
//    }

    @Override
    public void resume() {
//        if (posts != null) {
//            view.updatePosts(posts);
//            getFeed(new FeedInteractor(new FeedApiImp(userId), new MainThreadImp(), new ThreadExecutor()));
//        } else {
//            getFeed(new FeedInteractor(new FeedApiImp(userId), new MainThreadImp(), new ThreadExecutor()));
//        }
//
        getFeed(new FeedInteractor(new FeedApiImp(userId), new MainThreadImp(), new ThreadExecutor()));

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
                view.hideLoading();
                view.showFloatingButton();
                view.showNoInternetAvailable();
            }

            @Override
            public void onError(Exception e) {
                view.hideLoading();
                view.showFloatingButton();
                view.showError(e);
            }

            @Override
            public void onFeedReceived(List<Feed> feeds) {
                view.hideLoading();
                view.hideRefreshLoading();
                if (createPosts(feeds).size() == 0) {
                    view.showFloatingButton();
                    view.showNoFeedAvailable();
                } else {
                    view.showPosts(ListUtil.orderPosts(createPosts(feeds)));
                    view.showFloatingButton();
                }
//                getPicturesOfFeed(feeds);
            }
        });
    }

    private List<Post> createPosts(List<Feed> feeds) {
        List<Post> list = new ArrayList<Post>();
        for (int i = 0; i < feeds.size(); i++) {
            for (int j = 0; j < feeds.get(i).getPictures().size(); j++) {
                list.add(new Post(feeds.get(i).getFeedId(), feeds.get(i).getUserId(),
                        feeds.get(i).getxUserId(), feeds.get(i).getxProfile(), feeds.get(i).getxUsername(),
                        feeds.get(i).getPictures().get(j), feeds.get(i).getPictures().get(j).getLikes(),
                        feeds.get(i).getPictures().get(j).getComments()));
            }
        }
        return list;
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
    }

    @Override
    public void onUnSaveClicked(final Picture picture, String userId) {
        getUserBDImp.deleteUserSavedPicture(picture.getImageId());
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

    @Override
    public void onUserClicked(String userId) {
        if (userId.equals(this.userId))
            navigator.navigateToProfile();
        else
            navigator.openUserProfile(userId);
    }

    private void getPicturesOfFeed(List<Feed> feeds) {
        final List<Post> list = new ArrayList<Post>();
        for (final Feed feed : feeds) {
            PicturesByIdInteractor interactor = getPicturesInteractor(feed.getxUserId());
            interactor.getPictures(new GetPicturesById.Listener() {

                @Override
                public void onNoInternetAvailable() {
//                    view.showNoInternetAvailable();
                }

                @Override
                public void onError(Exception e) {
//                    view.showError(e);
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
                        list.add(c);
                    }
                    getLikesComments(list);
                }
            });
        }
    }

    private void getLikesComments(final List<Post> list) {
        for (final Post post : list) {
            CommentsInteractor interactorComments = getCommentsInteractor(post.getPicture().getImageId());
            interactorComments.getComments(new GetComments.Listener() {
                @Override
                public void onNoInternetAvailable() {
//                    view.showNoInternetAvailable();
                }

                @Override
                public void onError(Exception e) {
//                    view.showError(e);
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
//                    view.showNoInternetAvailable();
                }

                @Override
                public void onError(Exception e) {
//                    view.showError(e);
                }

                @Override
                public void onLikesReceived(List<Like> likes) {
                    post.setLikeList(likes);
                    if (list.size() - 1 > 0)
                        if (post.getPicture().getImageId().equals(list.get(list.size() - 1).getPicture().getImageId()))
                            if (posts != null) {
                                if (list.size() != posts.size()) {
                                    view.showPosts(ListUtil.orderPosts(list));
                                    posts = list;
                                } else {
                                    view.updatePosts(ListUtil.orderPosts(list));
                                }
                            } else {
                                posts = list;
                                view.showPosts(ListUtil.orderPosts(list));
                                view.hideLoading();
                                view.showFloatingButton();
                            }
//                            postReceived(posts);
                }
            });
        }
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

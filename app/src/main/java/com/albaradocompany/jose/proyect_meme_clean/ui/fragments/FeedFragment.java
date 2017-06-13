package com.albaradocompany.jose.proyect_meme_clean.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.UserBD;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Like;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Post;
import com.albaradocompany.jose.proyect_meme_clean.global.util.RecyclerHelper;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.CommentsActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.ProfileActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SavedPicturesActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SearchActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.UserActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.adaptor.FeedRecyclerAdapter;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.LikesDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.FeedPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsFeedPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.view.ShowSnackBarImp;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by jose on 19/05/2017.
 */

public class FeedFragment extends Fragment implements AbsFeedPresenter.View, AbsFeedPresenter.Navigator, RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {
    private static final String USERID = "userId";
    private static final int FIRST_POSITION = 0;
    private static final int SECOND_POSITION = 1;
    private static final int THIRD_POSITION = 2;
    private static final String COMMENTS = "comments";
    private static final String IMAGE_ID = "imageId";
    private static final String POST = "post";
    private static final String POSITION = "position";
    private static final int COMMENT_ACTION = 0;
    private static final String LIST_STATE_KEY = "list_state";

    @BindView(R.id.feed_recyclerview)
    RecyclerView recyclerView;
    @BindString(R.string.noInternetAvailable)
    String noInternet;
    @BindView(R.id.feed_pbr)
    ProgressBar progressBar;
    @BindView(R.id.feed_rfab)
    RapidFloatingActionButton rfaBtn;
    @BindView(R.id.feed_rfal)
    RapidFloatingActionLayout rfaLayout;
    @BindView(R.id.feed_swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.feed_empty_feed)
    RelativeLayout empty_feed;
    @BindView(R.id.feed_lyt_container)
    FrameLayout layout;


    RapidFloatingActionHelper rfaHelper;
    AbsFeedPresenter presenter;
    static String userId;
    ShowSnackBarImp showSnackBar;
    FeedRecyclerAdapter adapter;
    private static UserBD userDB;
    private List<Post> posts;
    private LinearLayoutManager layoutManager;
    private Parcelable mListState;
    private LikesDialog dialog;

    @OnClick({R.id.empty_feed_tv_search, R.id.empty_feed_iv_search})
    public void onSearchClicked(View view) {
        presenter.onSearchClickedFAB();
    }

    FeedRecyclerAdapter.Listener onClickListener = new FeedRecyclerAdapter.Listener() {
        @Override
        public void onCommentsClicked(List<Comment> comments, String imageId, Post post, int adapterPosition) {
            presenter.onCommentsClicked(comments, imageId, post, adapterPosition);
        }

        @Override
        public void onLikesClicked(List<Like> likes) {
            presenter.onLikesClicked(likes);
        }

        @Override
        public void onSaveClicked(Picture picture, String userId) {
            presenter.onSaveClicked(picture, userId);
        }

        @Override
        public void onUnSaveClicked(Picture picture, String userId) {
            presenter.onUnSaveClicked(picture, userId);
        }

        @Override
        public void onSaveLikeClicked(String imageId) {
            presenter.onSaveLikeClicked(imageId);
        }

        @Override
        public void onUnSaveLikeClicked(String userId, String imageId) {
            presenter.onUnSaveLikeClicked(userId, imageId);
        }

        @Override
        public void onUserClicked(String userId) {
            presenter.onUserClicked(userId);
        }
    };

    public FeedFragment() {
    }

    public static FeedFragment newInstance(UserBD userBD) {
        Bundle args = new Bundle();
        FeedFragment fragment = new FeedFragment();
        setUserId(userBD.userId);
        setuserDB(userBD);
        fragment.setArguments(args);
        return fragment;
    }

    public static void setuserDB(UserBD user) {
        userDB = user;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Override
    public void onResume() {
        presenter.resume();
        super.onResume();
    }

    private void initialize() {
        presenter = new FeedPresenter(getContext(), userId);
        presenter.setView(this);
        presenter.setNavigator(this);

        layoutManager = new LinearLayoutManager(getContext());
        showSnackBar = new ShowSnackBarImp(getActivity());

        posts = new ArrayList<>();
    }

    private void initializeSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                parentResume();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);
        initializeSwipeRefresh();
        presenter.initialize();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showNoInternetAvailable() {
        showSnackBar.show(noInternet, BuildConfig.COLOR_RED);
    }

    @Override
    public void showError(Exception e) {
        showSnackBar.show(e.getMessage(), BuildConfig.COLOR_RED);
    }

    @Override
    public void showPosts(List<Post> listpost) {
        if (listpost.size() == 1) {
            empty_feed.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            if (posts.isEmpty()) {
                initializeRecycler(listpost);
                this.posts = listpost;
            } else {
                updatePosts(listpost);
            }
            empty_feed.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void initializeRecycler(List<Post> listpost) {
        adapter = new FeedRecyclerAdapter(getContext(), listpost, onClickListener);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void showLoading() {
        rfaBtn.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        rfaBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLikesDialog(List<Like> likes) {
        dialog = new LikesDialog(likes);
        dialog.show(getActivity().getFragmentManager(), LikesDialog.class.getName());
    }

    @Override
    public void showSaveSuccess() {
//        Toast.makeText(getContext(), "Photo saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSaveFailure() {
//        Toast.makeText(getContext(), "Photo not saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUnSaveSuccess() {
//        Toast.makeText(getContext(), "Photo unsaved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUnSaveFailure() {
//        Toast.makeText(getContext(), "Photo not unsaved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLikeSuccess() {
//        Toast.makeText(getContext(), "Photo liked", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showLikedFailure() {
//        Toast.makeText(getContext(), "Photo not liked", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showUnLikeSuccess() {
//        Toast.makeText(getContext(), "Photo unliked", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showUnLikeFailure() {
//        Toast.makeText(getContext(), "Photo not unliked", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showFloatingButton() {
        configureFloating();
    }

    @Override
    public void updatePosts(List<Post> listpost) {
        if (listpost.size() == 1) {
            empty_feed.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            if (RecyclerHelper.hasNewPosts(listpost, posts)) {
                adapter.setNewPosts(RecyclerHelper.getNewPosts(listpost, posts));
            } else {
                for (int i = 0; i < listpost.size(); i++) {
                    posts.get(i).setCommentList(RecyclerHelper.hasPostNewComments(listpost.get(i).getCommentList(), posts.get(i).getCommentList()));
                    posts.get(i).setLikeList(RecyclerHelper.hasPostNewLikes(listpost.get(i).getLikeList(), posts.get(i).getLikeList()));
                    adapter.updateListAt(i, posts.get(i));
                    adapter.notifyItemChanged(i);
                }
//            adapter.updatePosts(RecyclerHelper.updateLikesAndComments(listpost, posts));
            }
            empty_feed.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showNoFeedAvailable() {
        recyclerView.setVisibility(View.GONE);
        empty_feed.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRefreshLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void navigateToComments(List<Comment> comments, String imageId, Post post, int adapterPosition) {
        openCommentsActivity(comments, imageId, post, adapterPosition);
    }

    @Override
    public void navigateToSearch() {
        openSearch();
    }

    private void openSearch() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToSavePictures() {
        openSavedPictures(getContext());
    }

    @Override
    public void navigateToProfile() {
        openProfileActivity(getContext());
    }

    @Override
    public void openUserProfile(String userId) {
        openUserDetail(getActivity().getApplicationContext(), userId);
    }

    public static void openUserDetail(Context ctx, String userId) {
        Intent intent = new Intent(ctx, UserActivity.class);
        intent.putExtra(USERID, userId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }

    public void openCommentsActivity(List<Comment> comments, String imageId, Post post, int adapterPosition) {
        Intent intent = new Intent(getActivity(), CommentsActivity.class);
        intent.putExtra(COMMENTS, (Serializable) comments);
        intent.putExtra(IMAGE_ID, imageId);
        intent.putExtra(POST, post);
        intent.putExtra(POSITION, adapterPosition);
        startActivityForResult(intent, COMMENT_ACTION);
    }

    public static void setUserId(String id) {
        userId = id;
    }

    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem rfacLabelItem) {
        rfaBtn.setPressed(false);
        rfaHelper.toggleContent();
        switch (position) {
            case FIRST_POSITION:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.onSearchClickedFAB();
                    }
                }, 500);
                break;
            case SECOND_POSITION:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.onSavePictureClickedFAB();
                    }
                }, 500);
                break;
            case THIRD_POSITION:

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.onProfileClickedFAB();
                    }
                }, 500);
                break;
        }
    }

    @Override
    public void onRFACItemIconClick(final int position, RFACLabelItem rfacLabelItem) {
        rfaBtn.setPressed(false);
        rfaHelper.toggleContent();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (position) {
                    case FIRST_POSITION:
                        presenter.onSearchClickedFAB();
                        break;
                    case SECOND_POSITION:
                        presenter.onSavePictureClickedFAB();
                        break;
                    case THIRD_POSITION:
                        presenter.onProfileClickedFAB();
                        break;
                }
            }
        }, 200);

    }

    public static void openProfileActivity(Context ctx) {
        Intent intent = new Intent(ctx, ProfileActivity.class);
        ctx.startActivity(intent);
    }

    public static void openSavedPictures(Context ctx) {
        Intent intent = new Intent(ctx, SavedPicturesActivity.class);
        ctx.startActivity(intent);
    }

    private void configureFloating() {
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(getContext());
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
//                .setLabel("Search")
                        .setResId(R.drawable.search)
                        .setIconNormalColor(0xff9C0000)
                        .setIconPressedColor(0xffB03333)
                        .setLabelColor(0xff9C0000)
                        .setWrapper(3)
        );
        items.add(new RFACLabelItem<Integer>()
//                .setLabel("Saved Photos")
                        .setResId(R.drawable.saved_light)
                        .setIconNormalColor(0xff5CAC77)
                        .setIconPressedColor(0xff007D2A)
                        .setLabelColor(0xff007D2A)
                        .setWrapper(2)
        );
        items.add(new RFACLabelItem<Integer>()
//                .setLabel("Profile")
                        .setResId(R.drawable.user)
                        .setIconNormalColor(0xff00A4A9)
                        .setIconPressedColor(0xff00696C)
                        .setLabelColor(0xff00A4A9)
                        .setWrapper(0)
        );
        rfaContent.setItems(items)
                .setIconShadowRadius(ABTextUtil.dip2px(getContext(), 5))
                .setIconShadowColor(R.color.color_login)
                .setIconShadowDy(ABTextUtil.dip2px(getContext(), 5));
        rfaHelper = new RapidFloatingActionHelper(
                getContext(),
                rfaLayout,
                rfaBtn,
                rfaContent
        ).build();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
            if (requestCode == COMMENT_ACTION) {
                if (data.getExtras() != null) {
                    int position = (int) data.getExtras().get(POSITION);
                    Post post = (Post) data.getExtras().get(POST);
                    adapter.updateListAt(position, post);
                    adapter.notifyItemChanged(position);
                    try {
                        Thread.sleep(28);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
    }

    public void parentResume() {
//        if (mListState != null) {
//            layoutManager.onRestoreInstanceState(mListState);
        if (presenter != null) {
            presenter.resume();
        }
//        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        mListState = layoutManager.onSaveInstanceState();
//        outState.putParcelable(LIST_STATE_KEY, mListState);
//        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if (savedInstanceState != null) {
//            mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
//        }
    }
}

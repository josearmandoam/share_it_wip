package com.albaradocompany.jose.proyect_meme_clean.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.view.View;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Feed;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.NotificationActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.adaptor.FeedSheetRecyclerAdapter;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jose on 03/06/2017.
 */

public class FeedButtonSheedDialogFragment extends BottomSheetDialogFragment {
    private static final String COMPLETENAME = "completeName";
    private static final String M_USER_ID = "mUserId"; // user account id
    private static final String USER_ID = "userId";//notification line user id
    private static final String NAME = "notifcationLineName";
    @BindView(R.id.sheet_recycler)
    RecyclerView recyclerView;
    FeedSheetRecyclerAdapter adapter;
    private String mCompleteName;
    static List<Feed> feed;

    static FeedButtonSheedDialogFragment.Listener listener = new NullListener();

    FeedSheetRecyclerAdapter.Listener onContactClickListener = new FeedSheetRecyclerAdapter.Listener() {
        @Override
        public void onContacClicked(Feed feed) {
            dismiss();
            openNotifications(feed);
            /**/
        }
    };

    private void openNotifications(Feed feed) {
        Intent intent = new Intent(getActivity(), NotificationActivity.class);
        intent.putExtra(NAME, feed.getxUsername());
//        intent.putExtra(LIST, (Serializable) list);
        intent.putExtra(M_USER_ID, feed.getUserId());
        intent.putExtra(COMPLETENAME, mCompleteName);
        intent.putExtra(USER_ID, feed.getxUserId());
        startActivity(intent);
    }

    public static FeedButtonSheedDialogFragment newInstance(List<Feed> feeds, String mCompleteName, Listener listener) {
        Bundle args = new Bundle();
        setFeed(feeds);
        setListener(listener);
        args.putString(COMPLETENAME, mCompleteName);
        FeedButtonSheedDialogFragment fragment = new FeedButtonSheedDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static void setFeed(List<Feed> feeds) {
        feed = feeds;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        if (getArguments() != null) {
            this.mCompleteName = getArguments().getString(COMPLETENAME);
        }
        View contentView = View.inflate(getContext(), R.layout.feed_bottom_sheet, null);
        ButterKnife.bind(this, contentView);
        initialize();
        dialog.setContentView(contentView);
    }

    private void initialize() {
        adapter = new FeedSheetRecyclerAdapter(getActivity(), feed, onContactClickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        SnapHelper snapHelper = new GravitySnapHelper(Gravity.START);
        snapHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        listener.onDialogDissmiss();
        super.onDismiss(dialog);
    }

    public static void setListener(Listener listene) {
        listener = listene;
    }

    public interface Listener {
        void onDialogDissmiss();
    }

    private static class NullListener implements Listener {
        @Override
        public void onDialogDissmiss() {
        }
    }
}

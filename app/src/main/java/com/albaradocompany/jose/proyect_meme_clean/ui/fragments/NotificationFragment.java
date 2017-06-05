package com.albaradocompany.jose.proyect_meme_clean.ui.fragments;

import android.animation.Animator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Feed;
import com.albaradocompany.jose.proyect_meme_clean.global.model.NotificationLine;
import com.albaradocompany.jose.proyect_meme_clean.ui.adaptor.NotificationRecyclerAdapter;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.FeedButtonSheedDialogFragment;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.NotificationActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.NotificationFragmentPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsNotificationFragmentPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jose on 20/05/2017.
 */

public class NotificationFragment extends Fragment implements AbsNotificationFragmentPresenter.View, AbsNotificationFragmentPresenter.Navigator {
    private static final String COMPLETENAME = "completeName";
    private static final String M_USER_ID = "mUserId"; // user account id
    private static final String USER_ID = "userId";//notification line user id
    private static final String NAME = "notifcationLineName";
    @BindView(R.id.notification_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.notification_fbtn_add)
    FloatingActionButton fab;

    AbsNotificationFragmentPresenter presenter;
    NotificationRecyclerAdapter adapter;
    private String mUserId;
    private String mCompleteName;
    private FeedButtonSheedDialogFragment dialog;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            presenter.onNotificationsReceived();
        }
    };


    FeedButtonSheedDialogFragment.Listener onDialogListener = new FeedButtonSheedDialogFragment.Listener() {
        @Override
        public void onDialogDissmiss() {
            presenter.onSheetDialogDismiss();
        }
    };

    @OnClick(R.id.notification_fbtn_add)
    public void onFloatingClicked(View view) {
        presenter.onFloatingClicked();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.onFloatingButtonHidden(mUserId);
            }
        },500);
    }

    NotificationRecyclerAdapter.Listener onNotificationClickListener = new NotificationRecyclerAdapter.Listener() {
        @Override
        public void onNotificationClicked(String name, List<NotificationLine> list, String userId) {
            presenter.onNotificationLineClicked(name, list, userId);
        }
    };

    public NotificationFragment() {

    }

    public static NotificationFragment newInstance(String mUserId, String mCompleteName) {
        Bundle args = new Bundle();
        args.putString(M_USER_ID, mUserId);
        args.putString(COMPLETENAME, mCompleteName);
        NotificationFragment fragment = new NotificationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            this.mUserId = getArguments().getString(M_USER_ID);
            this.mCompleteName = getArguments().getString(COMPLETENAME);
        }
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, view);
        intialize();
        return view;
    }

    private void intialize() {
        adapter = null;
        presenter = new NotificationFragmentPresenter(getContext());
        presenter.setView(this);
        presenter.setNavigator(this);
        presenter.initialize();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void notifyNewNotification(NotificationLine line) {
        if (adapter != null) {
            adapter.addNewNotification(line);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        presenter.resume();
        if (adapter != null)
            adapter.notifyDataSetChanged();
        showFloatingButton();
        getActivity().registerReceiver(mMessageReceiver, new IntentFilter("broadcast"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mMessageReceiver);
    }

    @Override
    public void showNotifications(List<NotificationLine> notificationLines, List<NotificationLine> allNotifications) {
        if (adapter == null) {
            adapter = new NotificationRecyclerAdapter(getActivity().getApplicationContext(), notificationLines, allNotifications, onNotificationClickListener);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
        } else {
            adapter.updateList(notificationLines, allNotifications);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void openNotifications(String name, List<NotificationLine> list, String userId) {
        openNotificationsActivity(name, this.mUserId, mCompleteName, userId);
//        dialog.show(getActivity().getFragmentManager(), NotificationActivity.class.getName());
    }

    private void openNotificationsActivity(String name, String mUserId, String mCompleteName, String userId) {
        Intent intent = new Intent(getActivity(), NotificationActivity.class);
        intent.putExtra(NAME, name);
//        intent.putExtra(LIST, (Serializable) list);
        intent.putExtra(M_USER_ID, mUserId);
        intent.putExtra(COMPLETENAME, mCompleteName);
        intent.putExtra(USER_ID, userId);
        startActivity(intent);
    }

    @Override
    public void hideFloatingButton() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            final Interpolator interpolador = AnimationUtils.loadInterpolator(getActivity(),
                    android.R.interpolator.fast_out_slow_in);
            fab.animate()
                    .scaleX(1)
                    .scaleY(1)
                    .setInterpolator(interpolador)
                    .setDuration(300)
                    .setStartDelay(0)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            fab.animate()
                                    .scaleY(0)
                                    .scaleX(0)
                                    .setInterpolator(interpolador)
                                    .setDuration(300)
                                    .start();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
        }
    }

    @Override
    public void showFloatingButton() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            final Interpolator interpolador = AnimationUtils.loadInterpolator(getActivity(),
                    android.R.interpolator.fast_out_slow_in);
            fab.animate()
                    .scaleX(0)
                    .scaleY(0)
                    .setInterpolator(interpolador)
                    .setDuration(300)
                    .setStartDelay(0)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            fab.animate()
                                    .scaleY(1)
                                    .scaleX(1)
                                    .setInterpolator(interpolador)
                                    .setDuration(300)
                                    .start();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
        }
    }

    @Override
    public void showButtonSheet(List<Feed> list) {
        dialog = FeedButtonSheedDialogFragment.newInstance(list, mCompleteName, onDialogListener);
        dialog.show(getActivity().getSupportFragmentManager(), FeedButtonSheedDialogFragment.class.getName());
    }
}

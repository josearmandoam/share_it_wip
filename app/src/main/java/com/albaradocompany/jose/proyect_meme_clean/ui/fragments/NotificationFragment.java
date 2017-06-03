package com.albaradocompany.jose.proyect_meme_clean.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.model.NotificationLine;
import com.albaradocompany.jose.proyect_meme_clean.ui.adaptor.NotificationRecyclerAdapter;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.NotificationsDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.NotificationPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsNotificationPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jose on 20/05/2017.
 */

public class NotificationFragment extends Fragment implements AbsNotificationPresenter.View, AbsNotificationPresenter.Navigator {
    private static final String USERID = "userId";
    private static final String COMPLETENAME = "completeName";
    @BindView(R.id.notification_recycler)
    RecyclerView recyclerView;

    AbsNotificationPresenter presenter;
    NotificationRecyclerAdapter adapter;
    NotificationsDialog dialog;
    private String userId;
    private String mCompleteName;

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
        args.putString(USERID, mUserId);
        args.putString(COMPLETENAME, mCompleteName);
        NotificationFragment fragment = new NotificationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            this.userId = getArguments().getString(USERID);
            this.mCompleteName = getArguments().getString(COMPLETENAME);
        }
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);
        intialize();
        return view;
    }

    private void intialize() {
        presenter = new NotificationPresenter(getContext());
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

    public void notifyNewNotification(NotificationLine line) {
        if (adapter != null) {
            adapter.addNewNotification(line);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showNotifications(List<NotificationLine> notificationLines, List<NotificationLine> allNotifications) {
        if (adapter == null) {
            adapter = new NotificationRecyclerAdapter(getActivity().getApplicationContext(), notificationLines, allNotifications, onNotificationClickListener);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void showNotificationDialog(String name, List<NotificationLine> list, String userId) {
        dialog = NotificationsDialog.newInstance(name, list, this.userId, mCompleteName, userId);
        dialog.show(getActivity().getFragmentManager(), NotificationsDialog.class.getName());
    }
}

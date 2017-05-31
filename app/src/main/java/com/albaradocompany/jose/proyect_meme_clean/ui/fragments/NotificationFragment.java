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
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.NotificationPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsNotificationPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jose on 20/05/2017.
 */

public class NotificationFragment extends Fragment implements AbsNotificationPresenter.View, AbsNotificationPresenter.Navigator {
    @BindView(R.id.notification_recycler)
    RecyclerView recyclerView;

    AbsNotificationPresenter presenter;
    NotificationRecyclerAdapter adapter;

    public NotificationFragment() {

    }

    public static NotificationFragment newInstance() {

        Bundle args = new Bundle();
        NotificationFragment fragment = new NotificationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, null);
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
        if (adapter!=null){
            adapter.addNewNotification(line);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showNotifications(List<NotificationLine> notificationLines) {
        if (adapter == null) {
            adapter = new NotificationRecyclerAdapter(getActivity().getApplicationContext(), notificationLines);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
        }
    }
}

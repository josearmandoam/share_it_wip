package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD.GetUserBDImp;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerUIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIModule;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.NotificationLine;
import com.albaradocompany.jose.proyect_meme_clean.ui.adaptor.NotificationDialogRecyclerAdapter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.NotificationsPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsNotificationPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.view.ShowSnackBarImp;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jose on 01/06/2017.
 */

public class NotificationActivity extends BaseActivty implements AbsNotificationPresenter.View, AbsNotificationPresenter.Navigator {
    private static final String M_USER_ID = "mUserId"; // user account id
    private static final String USER_ID = "userId";//notification line user id
    private static final String NAME = "notifcationLineName";
    private static final String COMPLETENAME = "completeName";
    @BindView(R.id.notification_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.notification_tv_name)
    TextView tvName;
    @BindView(R.id.notification_et_message)
    EditText message;
    @BindView(R.id.notification_ibtn_send)
    ImageButton send;
    @BindView(R.id.notification_progressbar)
    ProgressBar progressBar;
    @Inject
    GetUserBDImp db;
    private LinearLayoutManager linearLayoutManager;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            presenter.onNotificationsReceived(intent);
        }
    };

    @OnClick(R.id.notification_ibtn_send)
    public void onSendMessageClicked(View view) {
        if (!message.getText().toString().isEmpty())
            if (db.getAllNotifications(userId).size() > 0)
                presenter.onSendMessageClicked(message.getText().toString(), mUserId, mCompleteName, userId);
            else
                presenter.registerNewLine(message.getText().toString(), mUserId, mCompleteName, userId, notifcationLineName);
    }

    @OnClick(R.id.notification_ibtn_back)
    public void onBackClicked(View view) {
        presenter.onBackClicked();
    }

    private UIComponent component;

    AbsNotificationPresenter presenter;
    NotificationDialogRecyclerAdapter adapter;

    private String userId;
    private String mUserId;
    private String notifcationLineName;
    private String mCompleteName;
    private ShowSnackBarImp snackBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component().inject(this);
        initialize();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notifications;
    }

    @Override
    protected boolean hideToolbar() {
        return true;
    }

    private void initialize() {
        if (getArguments() != null) {
            mUserId = getArguments().getString(M_USER_ID);
            userId = getArguments().getString(USER_ID);
            notifcationLineName = getArguments().getString(NAME);
            mCompleteName = getArguments().getString(COMPLETENAME);
//            notifications = (List<NotificationLine>) getArguments().get(LIST);
        }
        tvName.setText(notifcationLineName);
        presenter = new NotificationsPresenter(this);
        presenter.setView(this);
        presenter.setNavigator(this);
        presenter.initialize();

        presenter.initializeRecycler(db.getAllNotifications(userId), mUserId);
        snackBar = new ShowSnackBarImp(this);
    }

    @Override
    public void showNotifications(List<NotificationLine> notifications, String mUserId) {
        if (adapter == null) {
            adapter = new NotificationDialogRecyclerAdapter(this, notifications, mUserId);
//            setNotificationList(notifications);
            linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(linearLayoutManager);
            db.updateNotificationsState(userId);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.clear();
            adapter.addNewNotifications(notifications);
//            setNotificationList(notifications);
            recyclerView.scrollToPosition(notifications.size() - 1);
            db.updateNotificationsState(userId);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void showLoading() {
        send.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        send.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFailure() {
        snackBar.show(getString(R.string.error_ocurred), BuildConfig.COLOR_RED);
    }

    @Override
    public void showError(Exception e) {
        snackBar.show(e.getMessage(), BuildConfig.COLOR_RED);
    }

    @Override
    public void showNoInternetAvailable() {
        snackBar.show(getString(R.string.noInternetAvailable), BuildConfig.COLOR_RED);
    }

    @Override
    public void cleanMessage() {
        message.setText("");
    }

    public Bundle getArguments() {
        return getIntent().getExtras();
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
    public void navigateToBack() {
        onBackPressed();
    }

    @Override
    protected void onResume() {
        showNotifications(db.getAllNotifications(userId), userId);
        registerReceiver(mMessageReceiver, new IntentFilter("broadcast"));
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mMessageReceiver);
    }
}

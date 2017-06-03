package com.albaradocompany.jose.proyect_meme_clean.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.NotificationLine;
import com.albaradocompany.jose.proyect_meme_clean.ui.adaptor.NotificationDialogRecyclerAdapter;
import com.albaradocompany.jose.proyect_meme_clean.ui.fragments.NotificationFragment;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.NotificationsDialogPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsNotificationDialogPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.view.ShowSnackBarImp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jose on 01/06/2017.
 */

public class NotificationsDialog extends DialogFragment implements AbsNotificationDialogPresenter.View, AbsNotificationDialogPresenter.Navigator {
    private static final String M_USER_ID = "mUserId"; // user account id
    private static final String USER_ID = "userId";//notification line user id
    private static final String NAME = "notifcationLineName";
    private static final String COMPLETENAME = "completeName";
    @BindView(R.id.notification_dialog_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.notification_dialog_tv_name)
    TextView tvName;
    @BindView(R.id.notification_dialog_et_message)
    EditText message;
    @BindView(R.id.notification_dialog_ibtn_send)
    ImageButton send;
    @BindView(R.id.notification_dialog_progressbar)
    ProgressBar progressBar;

    @OnClick(R.id.notification_dialog_ibtn_send)
    public void onSendMessageClicked(View view) {
        presenter.onSendMessageClicked(message.getText().toString(), mUserId, mCompleteName, userId);
    }

    private static List<NotificationLine> notifications;

    AbsNotificationDialogPresenter presenter;
    NotificationDialogRecyclerAdapter adapter;

    private String userId;
    private String mUserId;
    private String notifcationLineName;
    private String mCompleteName;
    private ShowSnackBarImp snackBar;

    public static NotificationsDialog newInstance(String name, List<NotificationLine> list, String mUserId, String mCompleteName, String userId) {
        Bundle args = new Bundle();
        setNotificationList(list);
        args.putString(M_USER_ID, mUserId);
        args.putString(NAME, name);
        args.putString(COMPLETENAME, mCompleteName);
        args.putString(USER_ID, userId);
        NotificationsDialog fragment = new NotificationsDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static void setNotificationList(List<NotificationLine> notificationList) {
        notifications = notificationList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null) {
            this.mUserId = getArguments().getString(M_USER_ID);
            this.userId = getArguments().getString(USER_ID);
            this.notifcationLineName = getArguments().getString(NAME);
            this.mCompleteName = getArguments().getString(COMPLETENAME);
        }
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_notifications, null);
        ButterKnife.bind(this, view);
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
        initialize();
        return dialog;
    }

    private void initialize() {
        tvName.setText(notifcationLineName);
        presenter = new NotificationsDialogPresenter(getActivity());
        presenter.setView(this);
        presenter.setNavigator(this);
        presenter.initialize();

        presenter.initializeRecycler(notifications, mUserId);
        snackBar = new ShowSnackBarImp(getActivity());
    }

    @Override
    public void showNotifications(List<NotificationLine> notifications, String mUserId) {
        if (adapter == null) {
            adapter = new NotificationDialogRecyclerAdapter(getActivity(), notifications, mUserId);
//            setNotificationList(notifications);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
        } else {
            adapter.clear();
            adapter.addNewNotifications(notifications);
//            setNotificationList(notifications);
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
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}

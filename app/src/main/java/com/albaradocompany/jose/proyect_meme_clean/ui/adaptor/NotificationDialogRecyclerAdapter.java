package com.albaradocompany.jose.proyect_meme_clean.ui.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.NotificationLine;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jose on 02/06/2017.
 */

public class NotificationDialogRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int SENDER = 0;
    private static final int RECEIVER = 1;
    Context context;
    List<NotificationLine> notifications;
    String userId;

    public NotificationDialogRecyclerAdapter(Context context, List<NotificationLine> notifications, String userId) {
        this.context = context;
        this.notifications = notifications;
        this.userId = userId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case SENDER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_dialog_notification2, parent, false);
                return new SendViewHolder(view);
            case RECEIVER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_dialog_notification, parent, false);
                return new ReceiveViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (notifications != null) {
            if (notifications.get(position).getReceptor().equals(userId))
                return RECEIVER;
            else
                return SENDER;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!notifications.get(position).getReceptor().equals(userId)) {
            ((SendViewHolder) holder).message.setText(notifications.get(position).getMessage());
            Picasso.with(context).load(BuildConfig.BASE_URL_DEFAULT + userId+"_profile").memoryPolicy(MemoryPolicy.NO_CACHE).into(((SendViewHolder) holder).profile);
            ((SendViewHolder) holder).time.setText(notifications.get(position).getTime().substring(0, 5));
        } else {
            ((ReceiveViewHolder) holder).message.setText(notifications.get(position).getMessage());
            Picasso.with(context).load(notifications.get(position).getProfile()).memoryPolicy(MemoryPolicy.NO_CACHE).into(((ReceiveViewHolder) holder).profile);
            ((ReceiveViewHolder) holder).time.setText(notifications.get(position).getTime().substring(0, 5));
        }
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void clear() {
//        notifications.clear();
    }

    public void addNewNotifications(List<NotificationLine> notifications) {
//        this.notifications = notifications;
        for (int i = this.notifications.size(); i < notifications.size(); i++) {
            this.notifications.add(notifications.get(i));
        }
    }

    public static class ReceiveViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.dialog_notification_tv_message)
        TextView message;
        @BindView(R.id.dialog_notification_iv_profile)
        ImageView profile;
        @BindView(R.id.dialog_notification_tv_time)
        TextView time;

        public ReceiveViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class SendViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.dialog_notification2_tv_message)
        TextView message;
        @BindView(R.id.dialog_notification2_iv_profile)
        ImageView profile;
        @BindView(R.id.dialog_notification2_tv_time)
        TextView time;

        public SendViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

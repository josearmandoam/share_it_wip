package com.albaradocompany.jose.proyect_meme_clean.ui.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.NotificationLine;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jose on 31/05/2017.
 */

public class NotificationRecyclerAdapter extends RecyclerView.Adapter<NotificationRecyclerAdapter.NotificationViewHolder> {
    private static final String NOT_SEEN = "not seen";
    Context context;
    List<NotificationLine> lines;
    List<NotificationLine> allNotifications;
    NotificationRecyclerAdapter.Listener listener = new NullListener();

    public NotificationRecyclerAdapter(Context context, List<NotificationLine> lines, List<NotificationLine> allNotifications, Listener listener) {
        this.context = context;
        this.lines = lines;
        this.allNotifications = allNotifications;
        this.listener = listener;
    }

    @Override
    public NotificationRecyclerAdapter.NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationRecyclerAdapter.NotificationViewHolder holder, int position) {
        holder.name.setText(lines.get(position).getTitle());
        holder.message.setText(lines.get(position).getMessage());
        int numNotifications = getNumNotifications(lines.get(position).getUserId(), allNotifications);
        if (numNotifications == 0) {
            holder.buble.setVisibility(View.GONE);
            holder.profile.setBorderColor(BuildConfig.COLOR_GRAY);
        } else {
            holder.buble.setVisibility(View.VISIBLE);
            holder.numberNot.setText("" + numNotifications);
            holder.profile.setBorderColor(BuildConfig.COLOR_GREEN);
        }

        Picasso.with(context).load(lines.get(position).getProfile()).into(holder.profile);
    }

    private int getNumNotifications(String userId, List<NotificationLine> allNotifications) {
        int count = 0;
        for (NotificationLine notification : allNotifications) {
            if (notification.getUserId().equals(userId) && notification.getState().equals(NOT_SEEN))
                count++;
        }
        return count;
    }

    @Override
    public int getItemCount() {
        return lines.size();
    }

    public void addNewNotification(NotificationLine line) {
        lines.add(line);
    }

    public interface Listener {
        void onNotificationClicked(String name, List<NotificationLine> list, String userId);
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.notification_row_ibtn_buble)
        ImageButton buble;
        @BindView(R.id.notification_row_tv_number_notif)
        TextView numberNot;
        @BindView(R.id.notification_row_iv_profile)
        CircleImageView profile;
        @BindView(R.id.notification_row_tv_name)
        TextView name;
        @BindView(R.id.notification_row_tv_message)
        TextView message;

        @OnClick({R.id.notification_row_tv_number_notif, R.id.notification_row_tv_name, R.id.notification_row_lyt_container})
        public void onNotificationClicked(View view) {
            listener.onNotificationClicked(name.getText().toString(),getNotificationsOfUser(lines.get(getAdapterPosition()).getUserId()), lines.get(getAdapterPosition()).getUserId());
        }

        public NotificationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private List<NotificationLine> getNotificationsOfUser(String userId) {
        List<NotificationLine> list = new ArrayList<NotificationLine>();
        for (NotificationLine notification : allNotifications) {
            if (notification.getUserId().equals(userId) || notification.getReceptor().equals(userId))
                list.add(notification);
        }
        return list;
    }

    private class NullListener implements Listener {
        @Override
        public void onNotificationClicked(String name, List<NotificationLine> list, String userId) {

        }
    }
}

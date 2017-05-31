package com.albaradocompany.jose.proyect_meme_clean.ui.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.NotificationLine;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jose on 31/05/2017.
 */

public class NotificationRecyclerAdapter extends RecyclerView.Adapter<NotificationRecyclerAdapter.NotificationViewHolder> {
    private static final String NOT_SEEN = "not seen";
    Context context;
    List<NotificationLine> lines;

    public NotificationRecyclerAdapter(Context context, List<NotificationLine> lines) {
        this.context = context;
        this.lines = lines;
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
        Picasso.with(context).load(lines.get(position).getProfile()).into(holder.profile);
        if (lines.get(position).getState().equals(NOT_SEEN))
            holder.profile.setBorderColor(BuildConfig.COLOR_GREEN);
        else
            holder.profile.setBorderColor(BuildConfig.COLOR_GRAY);
    }

    @Override
    public int getItemCount() {
        return lines.size();
    }

    public void addNewNotification(NotificationLine line) {
        lines.add(line);
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.notification_row_iv_profile)
        CircleImageView profile;
        @BindView(R.id.notification_row_tv_name)
        TextView name;
        @BindView(R.id.notification_row_tv_message)
        TextView message;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

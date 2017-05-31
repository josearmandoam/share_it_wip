package com.albaradocompany.jose.proyect_meme_clean.datasource.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.util.DateUtil;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by jose on 29/05/2017.
 */

public class FcmMessageService extends FirebaseMessagingService {
    private static final String TITLE = "title";
    private static final String MESSAGE = "message";
    private static final String TOKEN = "token";
    private static final String ACTION = "action";
    private static final String TIME = "time";
    private static final String USER_ID = "userId";
    private static final String LINEID = "lineId";
    private static final String PROFILE = "profile";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String header = remoteMessage.getNotification().getTitle();
        String title = header.substring(0, header.indexOf("-"));
        String userId = header.substring(header.indexOf("-") + 1, header.length());
        String body = remoteMessage.getNotification().getBody();
        String time = DateUtil.getCurrentTimeFormated();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(LINEID, "line" + DateUtil.getCurrentDate() + DateUtil.getCurrentTime());
        intent.putExtra(USER_ID, userId);
        intent.putExtra(MESSAGE, body);
        intent.putExtra(ACTION, "notification");
        intent.putExtra(TIME, time);
        intent.putExtra(PROFILE, BuildConfig.BASE_URL_DEFAULT+userId + "_profile");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification.Builder notificationBuilder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_stat_camera)
                .setContentTitle(title)
                .setStyle(new Notification.BigTextStyle().bigText(title))
                .setAutoCancel(true)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setSound(alarmSound);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setColor(getApplicationContext().getResources().getColor(R.color.red));
            notificationBuilder.setSmallIcon(R.drawable.ic_action_action_search);
            notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ic_launcher_round));
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
        super.onMessageReceived(remoteMessage);
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_email : R.drawable.ic_notification;
    }
}

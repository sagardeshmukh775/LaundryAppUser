package com.cleanandcomfort.smtrick.user_laundryapp;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.cleanandcomfort.smtrick.user_laundryapp.Constants.Constants;

public class NotificationHelper {

    public static void displayNotification(Context context, String title, String body) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, Constants.CHANNEL_ID)
                        .setSmallIcon(R.drawable.album1)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from(context);
        mNotificationMgr.notify(1, mBuilder.build());

    }
}

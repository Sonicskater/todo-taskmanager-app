package com.describe.taskmanager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import android.content.Context;

//Replaced by Notification.class
@Deprecated
public class ReminderUI extends AppCompatActivity {
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private int notification_id;
    private RemoteViews remoteViews;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_notification);

        context = this;
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        remoteViews = new RemoteViews(getPackageName(), R.layout.reminder_notification);
        /*
        remoteViews.setImageViewResource(R.id.notify_icon, R.mipmap.ic_launcher);
        remoteViews.setTextViewText(R.id.notify_title, "Text");
        */
        /*
        remoteViews.setProgressBar(R.id.progressBar, 100, 50, true);
        */
        notification_id = (int) System.currentTimeMillis();
        Intent button_intent = new Intent("Button_Pressed");
        button_intent.putExtra("id", notification_id);

        PendingIntent p_button_intent = PendingIntent.getBroadcast(context,123, button_intent,0);
        /*
        remoteViews.setOnClickPendingIntent(R.id.button, p_button_intent);


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent notification_intent = new Intent(context,ReminderUI.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notification_intent,0);

                builder = new NotificationCompat.Builder(context);
                builder.setSmallIcon(R.mipmap.ic_launcher)
                        .setAutoCancel(true)
                        .setCustomBigContentView(remoteViews)
                        .setContentIntent(pendingIntent);
                notificationManager.notify(notification_id, builder.build());

            }
        });
        */




    }



}

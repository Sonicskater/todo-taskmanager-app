package com.describe.taskmanager;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.v4.app.NotificationCompat;



public class NotificationHelper extends ContextWrapper {


    public static final String channel1ID = "channel1ID";
    public static final String channel1Name = "channel 1";
    public static final String channel2ID = "channel2ID";
    public static final String channel2Name = "channel 2";
    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannels();
        }

    }
    @TargetApi(Build.VERSION_CODES.O)
    public void createChannels(){
        NotificationChannel channel1 = new NotificationChannel(channel1ID, channel1Name, NotificationManager.IMPORTANCE_DEFAULT);
        channel1.enableLights(true);
        channel1.enableVibration(true);
        channel1.setLightColor(R.color.colorPrimary);

        getManager().createNotificationChannel(channel1);


        NotificationChannel channel2 = new NotificationChannel(channel2ID, channel2Name, NotificationManager.IMPORTANCE_DEFAULT);
        channel2.enableLights(true);
        channel2.enableVibration(true);
        channel2.setLightColor(R.color.colorPrimary);

        getManager().createNotificationChannel(channel2);

    }

    public NotificationManager getManager(){
        if (mManager == null){
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getChannel1Notification(String title, String message){
        return new NotificationCompat.Builder(getApplicationContext(), channel1ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_one);
    }

    public NotificationCompat.Builder getChannel2Notification(String title, String message){
        return new NotificationCompat.Builder(getApplicationContext(), channel2ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_two);
    }

    public void sendOnChannel1(String title, String message) {
        NotificationCompat.Builder nb = this.getChannel1Notification(title, message);
        this.getManager().notify(1, nb.build());
    }
}

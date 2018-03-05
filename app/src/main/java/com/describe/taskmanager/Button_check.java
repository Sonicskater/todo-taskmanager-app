package com.describe.taskmanager;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


/**
 * Created by solanduressa on 2018-03-05.
 */

public class Button_check extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(intent.getExtras().getInt("id"));
        Toast.makeText(context, "Button Pressed", Toast.LENGTH_SHORT).show();

    }
}

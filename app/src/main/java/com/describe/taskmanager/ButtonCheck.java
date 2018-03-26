package com.describe.taskmanager;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

//I have no idead why this was made, but its not used anywhere so i deprecated it -Devon
@Deprecated
public class ButtonCheck extends BroadcastReceiver {
    @Override
    @Deprecated
    public void onReceive(Context context, Intent intent){
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(intent.getExtras().getInt("id"));
        Toast.makeText(context, "Button Pressed", Toast.LENGTH_SHORT).show();

    }
}

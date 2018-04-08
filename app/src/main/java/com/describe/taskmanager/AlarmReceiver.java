package com.describe.taskmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by bencook on 2018-04-08.
 */

public class AlarmReceiver extends BroadcastReceiver implements FirestoreInterface {


    @Override
    public void onReceive(Context context, Intent intent) {
        FirestoreAgent.getInstance().getAllTasks(this);
    }

    @Override
    public void updateTaskCollection(String collectionName, ArrayList<TaskEvent> collectionContent) {

        Date current = new Date();
        for (TaskEvent task : collectionContent) {
            if (task.getAlarmDate().compareTo(current) < 0 && task.getHasNotified() == false) {
                //TODO: send notification
            }
        }
    }

    @Override
    public void updateCategoryCollection(String collectionName, ArrayList<Category> collectionContent) {

    }
}

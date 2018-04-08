package com.describe.taskmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by bencook on 2018-04-08.
 */

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        TaskNotificationService service = new TaskNotificationService(context);

        Log.d("AlarmReceiver", "looking for tasks to notify on");

        FirestoreAgent.getInstance().getAllTasks(service);
    }


    private class TaskNotificationService implements FirestoreInterface {
        private Context context;

        public TaskNotificationService(Context context) {
            this.context = context;
        }

        @Override
        public void updateTaskCollection(String collectionName, ArrayList<TaskEvent> collectionContent) {

            Date current = new Date();
            for (TaskEvent task : collectionContent) {

                Date eventDate = task.getDate();

                if (eventDate.compareTo(current) < 0 && task.getHasNotified() == false) {

                    String message = String.format("Task: %s, Description: %s", task.getTitle(), task.getDescription());
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                    System.out.println(task.getTitle());
                    System.out.println("getDate: " + task.getDate());
                    System.out.println("getAlarmDate: " + task.getAlarmDate());
                    System.out.println("getCreateTime: " + task.getCreateTime());
                    System.out.println("");



                   // Log.d("AlarmReceiver", message);
                }
            }
        }

        @Override
        public void updateCategoryCollection(String collectionName, ArrayList<Category> collectionContent) {

        }
    }
}

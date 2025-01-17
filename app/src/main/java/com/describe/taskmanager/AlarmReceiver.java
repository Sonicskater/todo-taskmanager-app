package com.describe.taskmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by bencook on 2018-04-08.
 */

public class AlarmReceiver extends BroadcastReceiver {


    //when the AlarmReceiver receives an intent
    @Override
    public void onReceive(Context context, Intent intent) {
        //creates a helper class TaskNotificationService (defined below)
        TaskNotificationService service = new TaskNotificationService(context);

        Log.i("AlarmReceiver", "looking for tasks to notify on");

        //gets an instance of the repository

        if (FirebaseAuth.getInstance().getCurrentUser()!=null&&!FirebaseAuth.getInstance().getCurrentUser().isAnonymous()) {

            FirestoreAgent.getInstance().getAllTasks(service);
        }
    }


    //helper class TaskNotificationService
    //implements the Firestore interface
    private class TaskNotificationService implements FirestoreInterface {
        //context of the activity
        private Context context;
        private NotificationHelper notificationHelper;


        //constructor
        public TaskNotificationService(Context context) {
            this.context = context;
        }


        @Override
        public void updateTaskCollection(String collectionName, ArrayList<TaskEvent> collectionContent) {

            Log.i("AlarmReceiver", "checking tasks...");

            //sets the "now" variable to the current date and time to be compared to
            //the taskEvents
            Date now = new Date();
            //for each task in the repository
            for (TaskEvent task : collectionContent) {

                //get the date
                if (task.hasAlarm()) {
                    long eventMillies = task.getAlarmDate().getTime();
                    long nowAsMillis = now.getTime();
                    long difference = Math.abs(nowAsMillis - eventMillies);

                    //System.out.println("hello");

                    //if the difference between the current date and the date to check is less than 30 seconds
                    //display a notification
                    if (difference <= 30000) {

                        String message = String.format("Task: %s, Description: %s", task.getTitle(), task.getDescription());



                        notificationHelper = new NotificationHelper(context);
                        notificationHelper.createChannels();
                        notificationHelper.sendOnChannel1(task.getTitle(), task.getDescription());


                    /*
                    System.out.println(task.getTitle());
                    System.out.println("getDate: " + task.getDate());
                    System.out.println("getAlarmDate: " + task.getAlarmDate());
                    System.out.println("getCreateTime: " + task.getCreateTime());
                    System.out.println("");
                    */


                        Log.d("AlarmReceiver", message);
                    }
                }
            }
        }

        @Override
        public void updateCategoryCollection(String collectionName, ArrayList<Category> collectionContent) {

        }
    }
}

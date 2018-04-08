package com.describe.taskmanager;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.CalendarContract.Events;

import java.util.Calendar;


public class CalendarAgent {
    private static CalendarAgent instance = null;
    public static CalendarAgent getInstance(){
        if (instance==null){
            instance = new CalendarAgent();
        }
        return instance;
    }
    private CalendarAgent(){

    }
    public void updateEvent(NotifyInterface callingObject,TaskEvent event,Context context){
        if (event.isSynced()&&event.hasAlarm()){
            ContentResolver resolver = context.getContentResolver();
            ContentValues values = new ContentValues();
            Calendar startTime = Calendar.getInstance();
            startTime.setTime(event.getAlarmDate());

            values.put(Events.DTSTART,startTime.getTimeInMillis());
            values.put(Events.DTEND,startTime.getTimeInMillis()+3600000);
            values.put(Events.TITLE,event.getTitle());
            values.put(Events.DESCRIPTION,event.getDescription());
            values.put(Events.CALENDAR_ID, 1L);
            values.put(Events.EVENT_TIMEZONE,Calendar.getInstance().getTimeZone().toString());
            try {
                Uri uri = ContentUris.withAppendedId(Events.CONTENT_URI,event.getCalID());
                resolver.update(uri,values,null,null);
            }catch(SecurityException exception){
                callingObject.NotifyUser("Missing Calendar Permission");
            }



        }
        else{
            callingObject.NotifyUser("");
        }

    }
    void createEvent(NotifyInterface callingObject,TaskEvent event,Context context){
        if (event.isSynced()&&event.hasAlarm()){
            ContentResolver resolver = context.getContentResolver();
            ContentValues values = new ContentValues();
            Calendar startTime = Calendar.getInstance();
            startTime.setTime(event.getAlarmDate());

            values.put(Events.DTSTART,startTime.getTimeInMillis());
            values.put(Events.DTEND,startTime.getTimeInMillis()+3600000);
            values.put(Events.TITLE,event.getTitle());
            values.put(Events.DESCRIPTION,event.getDescription());
            values.put(Events.CALENDAR_ID, 1L);
            values.put(Events.EVENT_TIMEZONE,Calendar.getInstance().getTimeZone().toString());
            try {
                Uri uri = resolver.insert(Events.CONTENT_URI, values);
                if (uri != null) {
                    long eventID = Long.parseLong(uri.getLastPathSegment());
                    event.setCalID(eventID);
                }
            }catch(SecurityException exception){
                callingObject.NotifyUser("Missing Calendar Permission");
            }

        }
        else{
            callingObject.NotifyUser("");
        }
    }
    public void deleteEvent(NotifyInterface callingObject,TaskEvent event,Context context){
        if (event.isSynced()&&event.isSynced()){
            ContentResolver resolver = context.getContentResolver();
            Uri deleteUri = ContentUris.withAppendedId(Events.CONTENT_URI, event.getCalID());
            resolver.delete(deleteUri, null, null);

        }
        else{
            callingObject.NotifyUser("");
        }
    }
}

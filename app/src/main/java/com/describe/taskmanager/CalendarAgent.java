package com.describe.taskmanager;



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
    public void updateEvent(NotifyInterface callingObject,TaskEvent event){
        if (event.isSynced()){

        }
        else{
            callingObject.NotifyUser("Updating Calendar Event Failed");
        }

    }
    public void createEvent(NotifyInterface callingObject,TaskEvent event){
        if (event.isSynced()){

        }
        else{
            callingObject.NotifyUser("Creating Calendar Event Failed");
        }
    }
    public void deleteEvent(NotifyInterface callingObject,TaskEvent event){
        if (event.isSynced()){

        }
        else{
            callingObject.NotifyUser("Deleting Calendar Event Failed");
        }
    }
}

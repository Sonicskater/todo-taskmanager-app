package com.describe.taskmanager;

public interface FSNotificationInterface extends FirestoreInterface {
    //These would have default methods, but if we added them then our app wouldn't support devices older than API 24/Android 7.0,
    //meaning most of our test devices wouldn't work.(All except Devons Phone)
    void firebaseSuccess(String message_title, String message_content);
    void firebaseFailure(String error_code,String message_title,String extra_content);
}

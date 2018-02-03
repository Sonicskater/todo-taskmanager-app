package com.describe.taskmanager;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by devon on 1/31/2018.
 */

public class firebaseAgent {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public void getUserCollection(){
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    public int getUSerID(String file,final UIInterface callingObject,final String fieldName){
        DocumentReference docRef = db.collection("users").document(file);

        final int num = 1234;
        Task document = docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        Log.d(TAG, "DocumentSnapshot data: " + task.getResult().getData());
                        Log.d("test",task.getResult().getData().get("user_object").toString());
                        Log.d("test",task.getResult().getData().get("user_object").getClass().toString());
                        Log.d("test",Integer.toString(num));
                        callingObject.updateField(fieldName,"test_content");
                        user tempUser = document.toObject(com.describe.taskmanager.user.class);
                        int userID = tempUser.userID;
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });



        int userID = 0;





        return userID;
    }
}

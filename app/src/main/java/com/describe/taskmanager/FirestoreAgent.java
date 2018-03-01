package com.describe.taskmanager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.firebase.ui.auth.data.model.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

import static android.content.ContentValues.TAG;




public class FirestoreAgent {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Random randTaskID = new Random();
    private String UserID = "g2x3irLzu1DTJXbymPXw";
    private final com.google.firebase.auth.FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
    public FirestoreAgent(){
        if (User != null){
            UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
    }








    //Code derived from Firstore docs

    //Gets a specified UserDocument and returns it to the requested field

    public void getUserDocument(String user, final UIInterface callingObject, final String fieldName){
        DocumentReference docRef = db.collection("users").document(UserID);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {

                        callingObject.updateObject(fieldName,task.getResult().getData().toString());

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    //Gets a specified CategoryDocument and return it to the requested field
    public void getCategoryDocument(String user, final UIInterface callingObject, final String fieldName){
        DocumentReference docRef = db.collection("users").document(UserID).collection("categories").document("Category");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {

                        callingObject.updateObject(fieldName,task.getResult().getData().toString());

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
    //Gets a specified TaskDocument and return it to the requested field
    public void getTaskDocument(String user, String category, String task, final UIInterface callingObject, final String fieldName){
        DocumentReference docRef = db.collection("users").document(UserID).collection("categories").document(category).collection("tasks").document(task);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    TaskEvent taskObj = task.getResult().toObject(TaskEvent.class);
                    if (taskObj != null) {

                        callingObject.updateObject(fieldName,taskObj);

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
    // Returns the collection of categories from a specified User.
    public void getCategoryCollection(final String user,final UIInterface callingObject){
        db.collection("users").document(UserID).collection("categories").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            ArrayList<Category> docs = new ArrayList<Category>();

                            for (DocumentSnapshot taskDoc : task.getResult()){
                                docs.add(taskDoc.toObject(Category.class));
                            }
                            callingObject.updateCategoryCollection(UserID+"/"+callingObject.toString(),docs);
                        }
                    }
                });

    }
    public void getTaskCollection(final String user,final UIInterface callingObject,final String category){
        db.collection("users").document(UserID).collection("categories").document(category).collection("tasks").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            ArrayList<TaskEvent> docs = new ArrayList<TaskEvent>();

                            for (DocumentSnapshot taskDoc : task.getResult()){
                                docs.add(taskDoc.toObject(TaskEvent.class));
                                Log.d("TASK_ADDED",taskDoc.toObject(TaskEvent.class).getTitle());
                            }
                            callingObject.updateTaskCollection(category+"/"+UserID+"/"+callingObject.toString(),docs);
                        }
                    }
                });

    }
    public void addCategory(final String user, final Category categoryObj, final UIInterface callingObject){
        db.collection("users").document(UserID).collection("categories").document(categoryObj.getCategoryTitle()).set(categoryObj

        ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void aVoid) {
                                       callingObject.firebaseSuccess("Added Category","Category added successfully");
                                   }
                               }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callingObject.firebaseFailure("1x000A","Failed to add Category","failed to add Category to User");
            }
        });
    }
    public void addTask(final String user, final String category, final TaskEvent taskObj, final UIInterface callingObject){

            final int newTaskID = randTaskID.nextInt(99999999);
            db.collection("users").document(UserID).collection("categories").document(category).collection("tasks").document(Integer.toString(newTaskID)).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()){
                                DocumentSnapshot doc = task.getResult();
                                if (doc.exists()) {
                                    addTask(UserID,category,taskObj,callingObject);
                                }
                                else{
                                    db.collection("users").document(UserID).collection("categories").document(category).collection("tasks").document(Integer.toString(newTaskID)).set(taskObj

                                    ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            callingObject.firebaseSuccess("Added task","task added successfully");
                                        }
                                    }
                                    ).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            callingObject.firebaseFailure("1x000A","Failed to add task","failed to add task to User");
                                        }
                                    });
                                }

                            }
                        }
                    });

    }

    public void updateTask(String debug_user, String category, TaskEvent currentEvent, TaskEventView taskEventView) {
        //this is a thing for devon to do, need to fill this in
    }

    public void deleteTask(String debug_user, String category, TaskEvent currentEvent, TaskEventView taskEventView) {
    }
}

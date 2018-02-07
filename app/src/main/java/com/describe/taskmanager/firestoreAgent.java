package com.describe.taskmanager;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by devon on 1/31/2018.
 */

public class firestoreAgent {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //Gets a specified UserDocument and returns it to the requested field
    public void getUserDocument(String file, final UIInterface callingObject, final String fieldName){
        DocumentReference docRef = db.collection("users").document(file);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {

                        callingObject.updateField(fieldName,task.getResult().getData().toString());

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
    public void getCategoryDocument(String file, final UIInterface callingObject, final String fieldName){
        DocumentReference docRef = db.collection("users").document(file).collection("categories").document("category");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {

                        callingObject.updateField(fieldName,task.getResult().getData().toString());

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
    public void getTaskDocument(String file, final UIInterface callingObject, final String fieldName){
        DocumentReference docRef = db.collection("users").document(file).collection("categories").document("category").collection("tasks").document("default_task");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {

                        callingObject.updateField(fieldName,task.getResult().getData().toString());

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
    // Returns the collection of categories from a specified user.
    public void getCategoryCollection(final String user,final UIInterface callingObject){
        db.collection("users").document(user).collection("categories").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            List<String> docs = new ArrayList<String>();

                            for (DocumentSnapshot taskDoc : task.getResult()){
                                docs.add(taskDoc.getData().toString());
                            }
                            callingObject.updateCollection(user.toString()+"/"+callingObject.toString(),docs.toString());
                        }
                    }
                });

    }
    public void getTaskCollection(final String user,final UIInterface callingObject,final String category){
        db.collection("users").document(user).collection("categories").document(category).collection("tasks").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            List<String> docs = new ArrayList<String>();

                            for (DocumentSnapshot taskDoc : task.getResult()){
                                docs.add(taskDoc.getData().toString());
                            }
                            callingObject.updateCollection(category.toString()+"/"+user.toString()+"/"+callingObject.toString(),docs.toString());
                        }
                    }
                });

    }
    public void addCategory(final String user, final category categoryObj, final UIInterface callingObject){
        db.collection("users").document(user).collection("categories").document(categoryObj.getCategoryTitle()).set(categoryObj

        ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void aVoid) {
                                       callingObject.firebaseSuccess("Added category","category added successfully");
                                   }
                               }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callingObject.firebaseFailure("1x000A","Failed to add Category","failed to add Category to user");
            }
        });
    }
    public void addTask(final String user, final String category,final task taskObj,final UIInterface callingObject){
        db.collection("users").document(user).collection("categories").document(category).collection("tasks").document(taskObj.getTaskTitle()).set(taskObj

        ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callingObject.firebaseSuccess("Added task","task added successfully");
            }
                                                                                                                            }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callingObject.firebaseFailure("1x000A","Failed to add task","failed to add task to user");
            }
        });
    }
}

package com.describe.taskmanager;

import android.support.annotation.NonNull;

import android.util.Log;


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

class FirestoreAgent {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth AuthInstance = FirebaseAuth.getInstance();
    private Random randTaskID = new Random();
    private String UserID = "";
    private static FirestoreAgent instance = null;
    //reuse instance of FirestoreAgent instead of creating a new one every time.
    static FirestoreAgent getInstance(){
        if(instance == null){
            instance = new FirestoreAgent();
        }
        return instance;
    }

    // use FirestoreAgent.getInstance() instead of new FirestoreAgent(). WILL BE MADE PRIVATE IN THE FUTURE

    private FirestoreAgent(){

        final com.google.firebase.auth.FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
        if (User != null){
            UserID = User.getUid();
        }
        else{
            AuthInstance.signInAnonymously();
        }
    }


    //Code derived from Firestore docs

    //Gets a specified UserDocument and returns it to the requested field
    private void updateUserID(){
        if (AuthInstance.getCurrentUser()!=null){
            this.UserID=AuthInstance.getCurrentUser().getUid();
        }
    }
    // Returns the collection of categories from a specified User.
    @Deprecated
    void getCategoryCollection(final String user,final UIInterface callingObject){
        //Check if ID is valid (not empty) before getting any data to reduce load on server and for security.
        this.updateUserID();
        if (!this.UserID.equals("")) {
            db.collection("users").document(UserID).collection("categories").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<Category> docs = new ArrayList<>();

                                for (DocumentSnapshot taskDoc : task.getResult()) {
                                    docs.add(taskDoc.toObject(Category.class));
                                }
                                callingObject.updateCategoryCollection(UserID + "/" + callingObject.toString(), docs);
                            }
                        }
                    });
        }

    }
    void getCategoryCollection(final FirestoreInterface callingObject){
        //Check if ID is valid (not empty) before getting any data to reduce load on server and for security.
        this.updateUserID();
        if (!this.UserID.equals("")) {
            db.collection("users").document(UserID).collection("categories").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<Category> docs = new ArrayList<>();

                                for (DocumentSnapshot taskDoc : task.getResult()) {
                                    docs.add(taskDoc.toObject(Category.class));
                                }
                                callingObject.updateCategoryCollection(UserID + "/" + callingObject.toString(), docs);
                            }
                        }
                    });
        }

    }
    @Deprecated
    void getTaskCollection(final String user,final UIInterface callingObject,final String category){
        //Check if ID is valid (not empty) before getting any data to reduce load on server and for security.
        this.updateUserID();
        if (!this.UserID.equals("")) {
            db.collection("users").document(UserID).collection("categories").document(category).collection("tasks").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<TaskEvent> docs = new ArrayList<>();

                                for (DocumentSnapshot taskDoc : task.getResult()) {
                                    docs.add(taskDoc.toObject(TaskEvent.class));
                                    Log.d("TASK_ADDED", taskDoc.toObject(TaskEvent.class).getTitle());
                                }
                                callingObject.updateTaskCollection(category, docs);
                            }
                        }
                    });
        }

    }
    void getTaskCollection(final FirestoreInterface callingObject,final String category){
        //Check if ID is valid (not empty) before getting any data to reduce load on server and for security.
        this.updateUserID();
        if (!this.UserID.equals("")) {
            db.collection("users").document(UserID).collection("categories").document(category).collection("tasks").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<TaskEvent> docs = new ArrayList<>();

                                for (DocumentSnapshot taskDoc : task.getResult()) {
                                    docs.add(taskDoc.toObject(TaskEvent.class));
                                    Log.d("TASK_ADDED", taskDoc.toObject(TaskEvent.class).getTitle());
                                }
                                callingObject.updateTaskCollection(category, docs);
                            }
                        }
                    });
        }

    }
    void addCategory(final String user, final Category categoryObj, final UIInterface callingObject){
        //Check if ID is valid (not empty) before getting any data to reduce load on server and for security.
        this.updateUserID();
        if (!this.UserID.equals("")) {
            db.collection("users").document(UserID).collection("categories").document(categoryObj.getCategoryTitle()).set(categoryObj

            ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                       @Override
                                       public void onSuccess(Void aVoid) {
                                           callingObject.firebaseSuccess("Added Category", "Category added successfully");
                                       }
                                   }
            ).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    callingObject.firebaseFailure("1x000A", "Failed to add Category", "failed to add Category to User");
                }
            });
        }
    }
    void addTask(final String user, final String category, final TaskEvent taskObj, final UIInterface callingObject){
        //Check if ID is valid (not empty) before getting any data to reduce load on server and for security.
        this.updateUserID();
        if (!this.UserID.equals("")) {
            final int newTaskID = randTaskID.nextInt(99999999);
            db.collection("users").document(UserID).collection("categories").document(category).collection("tasks").document(Integer.toString(newTaskID)).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot doc = task.getResult();
                                if (doc.exists()) {
                                    addTask(UserID, category, taskObj, callingObject);
                                } else {
                                    taskObj.setId(newTaskID);
                                    db.collection("users").document(UserID).collection("categories").document(category).collection("tasks").document(Integer.toString(newTaskID)).set(taskObj

                                    ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                               @Override
                                                               public void onSuccess(Void aVoid) {
                                                                   callingObject.firebaseSuccess("Added task", "task added successfully");
                                                               }
                                                           }
                                    ).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            callingObject.firebaseFailure("1x000A", "Failed to add task", "failed to add task to User");
                                        }
                                    });
                                }

                            }
                        }
                    });
        }

    }

    void updateTask(String category, final TaskEvent taskObj, final UIInterface callingObject) {
        db.collection("users").document(UserID).collection("categories").document(category).collection("tasks").document(Integer.toString(taskObj.getId())).set(taskObj

        ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void aVoid) {
                                       callingObject.firebaseSuccess("Updated task", "task updated successfully");
                                   }
                               }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callingObject.firebaseFailure("1x000A", "Failed to update task", "failed to update task " +Integer.toString(taskObj.getId())+ " User");
            }
        });
    }

    void deleteTask(String category, final TaskEvent taskObj, final UIInterface callingObject) {
        db.collection("users").document(UserID).collection("categories").document(category).collection("tasks").document(Integer.toString(taskObj.getId())).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void aVoid) {
                                       callingObject.firebaseSuccess("Deleted task", "task deleted successfully");
                                   }
                               }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callingObject.firebaseFailure("1x000A", "Failed to delete task", "failed to delete task " +Integer.toString(taskObj.getId())+ " User");
            }
        });
    }

    /////////////////// DEPRECATED CODE, KEPT FOR REFERENCE////////////////////////////

    @Deprecated
    void getUserDocument( final UIInterface callingObject, final String fieldName){
        //Check if ID is valid (not empty) before getting any data to reduce load on server and for security.

        this.updateUserID();
        if (!this.UserID.equals("")){
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


    }

    //Gets a specified CategoryDocument and return it to the requested field
    @Deprecated
    void getCategoryDocument( final UIInterface callingObject, final String fieldName){
        //Check if ID is valid (not empty) before getting any data to reduce load on server and for security.

        this.updateUserID();
        if (!this.UserID.equals("")) {
            DocumentReference docRef = db.collection("users").document(UserID).collection("categories").document("Category");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {

                            callingObject.updateObject(fieldName, task.getResult().getData().toString());

                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }
    }
    //Gets a specified TaskDocument and return it to the requested field
    @Deprecated
    void getTaskDocument(String category, String task, final UIInterface callingObject, final String fieldName){
        //Check if ID is valid (not empty) before getting any data to reduce load on server and for security.
        this.updateUserID();
        if (!this.UserID.equals("")) {
            DocumentReference docRef = db.collection("users").document(UserID).collection("categories").document(category).collection("tasks").document(task);

            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        @NonNull
                        TaskEvent taskObj = task.getResult().toObject(TaskEvent.class);

                        callingObject.updateObject(fieldName, taskObj);

                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }
    }
}

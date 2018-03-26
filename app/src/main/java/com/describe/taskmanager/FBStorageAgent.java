package com.describe.taskmanager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class FBStorageAgent {
    private FirebaseStorage firebaseStorageInstance;
    private StorageReference   storageRef;
    private boolean enabled = false;
    private static FBStorageAgent instance;
    public static FBStorageAgent getInstance(){
        if(instance == null){
            instance = new FBStorageAgent();
        }
        return instance;
    }
    private FBStorageAgent(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null){
            if (!currentUser.isAnonymous()){
                enabled = true;
                firebaseStorageInstance = FirebaseStorage.getInstance();
                storageRef = firebaseStorageInstance.getReference();
            }
        }
    }
    public ArrayList<String> getFileList(){
        ArrayList<String> fileList = new ArrayList<String>();
        if (enabled) {




        }
        return fileList;
    }

    public void downloadFile(String filePath){

    }
}

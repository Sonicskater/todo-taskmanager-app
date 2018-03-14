package com.describe.taskmanager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class FBStorageAgent {
    private FirebaseStorage firebaseStorageInstance;
    private StorageReference   storageRef;
    private boolean enabled = false;
    public FBStorageAgent(){
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            if (!FirebaseAuth.getInstance().getCurrentUser().isAnonymous()){
                enabled = true;
                firebaseStorageInstance = FirebaseStorage.getInstance();
                storageRef = firebaseStorageInstance.getReference();
            }
        }
        firebaseStorageInstance = FirebaseStorage.getInstance();
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

package com.describe.taskmanager;

import android.graphics.Bitmap;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Random;

//Currently un-used, meant for future feature
@SuppressWarnings("FieldCanBeLocal")
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
                enabled = true;
                firebaseStorageInstance = FirebaseStorage.getInstance();
                storageRef = firebaseStorageInstance.getReference();

        }
    }
    public ArrayList<String> getFileList(){
        ArrayList<String> fileList = new ArrayList<>();
        if (enabled) {





        }
        return fileList;
    }

    public void downloadFile(String filePath, final ImageInterface callingObject){

        StorageReference islandRef = storageRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()+filePath);

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                callingObject.receiveBitmap(ImageUtils.convertByteArray(bytes));
            }
        });
    }
    public void deleteImage(final TaskEvent event){
        storageRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()+event.getPath()).delete();

    }
    public void uploadImage(final TaskEvent event,Bitmap image){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String path = "/"+Integer.toString((new Random()).nextInt())+".jpg/";
        event.setPath(path);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 40, stream);
        UploadTask upload = storageRef.child(uid+path).putBytes(stream.toByteArray());
        upload.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                event.setDlURL(taskSnapshot.getDownloadUrl());
            }
        });

    }
}

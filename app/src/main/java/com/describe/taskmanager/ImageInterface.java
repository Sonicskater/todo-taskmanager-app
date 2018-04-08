package com.describe.taskmanager;

import android.graphics.Bitmap;


public interface ImageInterface {
    void receiveBitmap(Bitmap image);
    void imageFailure(String path);
}

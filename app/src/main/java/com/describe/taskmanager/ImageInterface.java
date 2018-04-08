package com.describe.taskmanager;

import android.graphics.Bitmap;

/**
 * Created by bencook on 2018-04-08.
 */

interface ImageInterface {
    void receiveBitmap(Bitmap image);
    void imageFailure(String path);
}

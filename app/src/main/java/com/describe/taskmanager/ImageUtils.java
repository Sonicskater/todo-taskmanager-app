package com.describe.taskmanager;

import android.graphics.Bitmap;
import android.graphics.Canvas;

class ImageUtils {

    //Stubbed in in order to get compile to work
    public static Bitmap convertByteArray(byte[] bytes) {

        int w = 35, h = 35;

        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        return Bitmap.createBitmap(w, h, conf); // this creates a MUTABLE bitmap

    }
}

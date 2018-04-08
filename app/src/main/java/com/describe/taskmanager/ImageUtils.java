package com.describe.taskmanager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import java.nio.ByteBuffer;

public class ImageUtils {
    private static ImageUtils instance;
    public static ImageUtils getInstance(){
        if (instance==null){
            instance = new ImageUtils();
        }
        return instance;

    }
    private ImageUtils(){

    }
    static public Bitmap convertByteArray(byte[] byteMap){
        return BitmapFactory.decodeByteArray(byteMap,0,byteMap.length);
    }
    static public Bitmap convertImage(Image image){
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length,null);
    }
    static public Bitmap getImage(String path){
        return BitmapFactory.decodeFile(path);
    }

}

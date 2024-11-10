package com.assignment.freshly.Utils;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class ImageUtils {
    public static byte[] getImageBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // Compress the Bitmap
        return stream.toByteArray();
    }
}

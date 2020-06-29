package com.kl.blueberry.ui.profile;

import android.graphics.Bitmap;

import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.ViewModel;

import java.io.IOException;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;

/**
 * Created by Kaltrina Lubovci on 13,June,2020
 */
public class ProfileViewModel extends ViewModel {
    Bitmap getRotateImage(String photoPath, Bitmap bitmap) {
        ExifInterface ei = null;
        try {
            ei = new ExifInterface(photoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = ei.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
        );

        Bitmap rotatedBitmap = null;
        switch (orientation){
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(bitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(bitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(bitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
                rotatedBitmap = bitmap;
                break;
        }

        return rotatedBitmap;

    }
}

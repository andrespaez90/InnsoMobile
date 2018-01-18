package com.innso.mobile.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.support.graphics.drawable.VectorDrawableCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {

    public static final String IMAGE_JPG = "image/jpeg";
    private static final String REDUCED_PREFIX = "reduced_";
    private static final String STOREKEEPER_ID = "SKID_";
    public static final int MAX_PHOTO_RESOLUTION = 1000;

    public static Drawable getDrawableVector(Context context, int srcImage) {
        return (Drawable) (AndroidVersionUtil.hasLollipop() ? context.getResources().getDrawable(srcImage, context.getTheme()) : VectorDrawableCompat.create(context.getResources(), srcImage, context.getTheme()));
    }

    public static File resizeImage(File file) {
        return resizeImage(file, MAX_PHOTO_RESOLUTION, MAX_PHOTO_RESOLUTION);
    }

    public static File resizeImage(File file, int newWidth, int newHeight) {
        File finalFile = null;

        try {
            Bitmap unscaledBitmap = ScalingUtilities.decodeFile(file.getAbsolutePath(), newWidth, newHeight, ScalingUtilities.ScalingLogic.FIT);
            if (unscaledBitmap.getWidth() <= newWidth && unscaledBitmap.getHeight() <= newHeight) {
                unscaledBitmap.recycle();
            } else {
                Bitmap scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, newWidth, newHeight, ScalingUtilities.ScalingLogic.FIT);
                finalFile = new File(file.getParentFile().getAbsolutePath(), REDUCED_PREFIX + file.getName().trim());
                FileOutputStream fileOutputStream = new FileOutputStream(finalFile);
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 75, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                scaledBitmap.recycle();
            }
        } catch (Throwable var7) {
            var7.printStackTrace();
        }

        if (finalFile == null) {
            finalFile = file;
        } else {
            saveImageOrientation(file, finalFile);
            file.delete();
        }

        return finalFile;
    }

    private static void saveImageOrientation(File file, File finalFile) {
        try {
            String orientation = (new ExifInterface(file.getPath())).getAttribute("Orientation");
            ExifInterface exifNew = new ExifInterface(finalFile.getPath());
            exifNew.setAttribute("Orientation", orientation);
            exifNew.saveAttributes();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

}

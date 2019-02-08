package com.innso.mobile.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.FaceDetector;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.util.List;

import androidx.fragment.app.Fragment;

public class CameraUtil {

    public static void openCamera(Activity context, String pathFolder, int requestCode) {

        Uri uri = FileUtil.generateUriForPicture(context, pathFolder);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        if (!AndroidVersionUtil.hasLollipop()) {
            List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        }
        context.startActivityForResult(intent, requestCode);
    }

    public static void openCamera(Fragment fragment, String pathFolder, int requestCode) {

        Context context = fragment.getContext();

        Uri uri = FileUtil.generateUriForPicture(context, pathFolder);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        if (!AndroidVersionUtil.hasLollipop()) {
            List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        }
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void openGallery(Activity context, int requestCode) {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        context.startActivityForResult(pickPhoto, requestCode);
    }

    public static int detectFace(File file, int maxFaces) {

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

        bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;

        Bitmap image = BitmapFactory.decodeFile(file.getPath(), bitmapOptions);

        image = validateOrientation(image, file.getPath());

        FaceDetector faceDetector = new FaceDetector(image.getWidth(), image.getHeight(), maxFaces);

        FaceDetector.Face[] faces = new FaceDetector.Face[maxFaces];

        return faceDetector.findFaces(image, faces);

    }

    private static Bitmap validateOrientation(Bitmap bitmap, String filePath) {
        ExifInterface exif = null;
        try {
            String currentOrientation = new ExifInterface(filePath).getAttribute(ExifInterface.TAG_ORIENTATION);
            if (currentOrientation.equalsIgnoreCase("6")) {
                return rotate(bitmap, 90);
            } else if (currentOrientation.equalsIgnoreCase("8")) {
                return rotate(bitmap, 270);
            } else if (currentOrientation.equalsIgnoreCase("3")) {
                return rotate(bitmap, 180);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private static Bitmap rotate(Bitmap bitmap, int degree) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix mtx = new Matrix();
        mtx.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }
}

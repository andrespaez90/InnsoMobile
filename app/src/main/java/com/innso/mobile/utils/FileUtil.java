package com.innso.mobile.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import java.io.File;

public class FileUtil {

    private static String appName;
    private static String applicationId;
    private static final String CHAT = "chat";
    public static final String BILL = "bills/";
    public static String pathLastFileGenerated;

    public static void init(String id) {
        applicationId = id;
    }

    public static void generateBasicFolders(String name) {
        appName = name;
        createFolder(Environment.getExternalStorageDirectory() + File.separator + appName);
        createFolder(getPathForChatFolder());
        createFolder(getPathForBillFolder());
        createFolder(getPathForUserFolder());
    }

    private static void createFolder(String path) {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public static Uri generateUriForPicture(Context context, String folderPath) {
        String name = System.currentTimeMillis() + ".jpg";
        File file = new File(folderPath, name);
        pathLastFileGenerated = folderPath + name;
        return FileProvider.getUriForFile(context, applicationId + ".provider", file);
    }

    public static String getPathForChatFolder() {
        return Environment.getExternalStorageDirectory() + File.separator + appName + File.separator + "chat" + File.separator;
    }


    public static String getPathForBillFolder() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + File.separator + appName + File.separator + "bill";
    }

    public static String getPathForUserFolder() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + File.separator + appName + File.separator + "user";
    }

    public static File getLastFileModifiedOnFolder(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles(pathname -> pathname.isFile());
        long lastMod = -9223372036854775808L;
        File choice = null;
        if (files != null) {
            File[] var6 = files;
            int var7 = files.length;

            for (int var8 = 0; var8 < var7; ++var8) {
                File file = var6[var8];
                if (file.lastModified() > lastMod) {
                    choice = file;
                    lastMod = file.lastModified();
                }
            }
        }

        return choice;
    }

    public static File getPhotoToGallery(Context context, Intent data) {
        Uri selectedImage = data.getData();
        String[] filePath = new String[]{"_data"};
        Cursor cursor = context.getContentResolver().query(selectedImage, filePath, (String) null, (String[]) null, (String) null);
        cursor.moveToFirst();
        String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
        return new File(imagePath);
    }

}

package com.innso.mobile.managers;


import android.os.Environment;

import java.io.File;

public class FileManager {

    public static File getLastModifiedFileOnExternalStorageInFolder(String folder) {
        File file = null;
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File folderFile = new File(externalStorageDirectory + "/" + folder);
        if (folderFile.exists() && folderFile.isDirectory()) {
            File[] files = folderFile.listFiles(File::isFile);
            long lastMod = Long.MIN_VALUE;
            if (files != null) {
                for (File fileToCheck : files) {
                    if (fileToCheck.lastModified() > lastMod) {
                        file = fileToCheck;
                        lastMod = fileToCheck.lastModified();
                    }
                }
            }
        }
        return file;
    }


}

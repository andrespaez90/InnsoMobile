package com.innso.mobile.managers;

import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.innso.mobile.utils.DateUtils;
import com.innso.mobile.utils.ImageUploader;
import com.innso.mobile.utils.ImageUtils;

import java.io.File;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FirebaseStorageManager {

    public static Observable<String> attemptToUploadPhoto(String photosPath, File scaledFile) {
        return uploadPictureToFirebase(photosPath, scaledFile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static Observable<String> uploadPictureToFirebase(String folderName, File file) {

        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageReference = storage.getReference();

        String timeStamp = DateUtils.getCurrentDate("yyyyMMdd_HHmmss", Locale.US);

        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType(ImageUtils.IMAGE_JPG)
                .setCustomMetadata("taken_date", timeStamp)
                .build();

        if (file == null) {
            file = FileManager.getLastModifiedFileOnExternalStorageInFolder(folderName);
        }

        Uri imageFile = Uri.fromFile(file);

        String path = folderName + file.getName().trim();

        return ImageUploader.uploadImage(imageFile, storageReference, path, metadata);
    }

}

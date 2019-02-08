package com.innso.mobile.utils;

import android.net.Uri;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

public class ImageUploader implements OnFailureListener, OnSuccessListener<UploadTask.TaskSnapshot>, OnProgressListener<UploadTask.TaskSnapshot> {

    public static Observable<String> uploadImage(final Uri uriFile, final StorageReference storageReference,
                                                 final String path, final StorageMetadata metadata) {

        return Observable.create(subscriber -> {
            ImageUploader uploader = new ImageUploader(subscriber);
            UploadTask uploadTask = storageReference.child(path).putFile(uriFile, metadata);
            uploadTask.addOnFailureListener(uploader);
            uploadTask.addOnSuccessListener(uploader);
            uploadTask.addOnProgressListener(uploader);
        });
    }

    private final ObservableEmitter<? super String> subscriber;

    private ImageUploader(final ObservableEmitter<? super String> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void onFailure(@NonNull Exception exception) {
        if (!subscriber.isDisposed()) {
            subscriber.onError(exception);
        }
    }

    @Override
    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        if (!subscriber.isDisposed()) {
            Uri downloadUrl = taskSnapshot.getDownloadUrl();
            subscriber.onNext(downloadUrl.toString());
            subscriber.onComplete();
        }
    }

    @Override
    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
        Log.e(ImageUploader.class.getName(), "Progress: " + ((taskSnapshot.getBytesTransferred() * 100) / taskSnapshot.getTotalByteCount()));
    }
}
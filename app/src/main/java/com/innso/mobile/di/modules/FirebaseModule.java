package com.innso.mobile.di.modules;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FirebaseModule {

    @Provides
    @Singleton
    FirebaseAuth authManager() {
        return FirebaseAuth.getInstance();
    }


    @Provides
    @Singleton
    FirebaseStorage firebaseStorage() {
        return FirebaseStorage.getInstance();
    }

    @Provides
    @Singleton
    StorageReference firebaseStorageReference(FirebaseStorage firebaseStorage) {
        return firebaseStorage.getReference();
    }
}

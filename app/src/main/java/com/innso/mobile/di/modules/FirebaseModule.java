package com.innso.mobile.di.modules;


import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.innso.mobile.R;
import com.innso.mobile.api.config.ApiConfig;
import com.innso.mobile.providers.ResourceProvider;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FirebaseModule {

    @Provides
    @Singleton
    FirebaseUser userManager(FirebaseAuth firebaseAuth) {
        return firebaseAuth.getCurrentUser();
    }

    @Provides
    @Singleton
    FirebaseAuth authManager() {
        return FirebaseAuth.getInstance();
    }


    @Provides
    @Singleton
    FirebaseStorage firebaseStorage(){
        return FirebaseStorage.getInstance();
    }

    @Provides
    @Singleton
    StorageReference firebaseStorageReference(FirebaseStorage firebaseStorage){
        return firebaseStorage.getReference();
    }


    @Provides
    @Singleton
    @Named("firebaseUserAdmin")
    FirebaseAuth userFirebaseManager(Context context, ApiConfig apiConfig, ResourceProvider resourceProvider){

        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                .setDatabaseUrl(apiConfig.getFirebaseUrl())
                .setApiKey(resourceProvider.getString(R.string.google_api_key))
                .setApplicationId(resourceProvider.getString(R.string.google_app_id)).build();

        FirebaseApp myApp = FirebaseApp.initializeApp(context,firebaseOptions, "firebaseUserAdmin");

        return FirebaseAuth.getInstance(myApp);
    }
}

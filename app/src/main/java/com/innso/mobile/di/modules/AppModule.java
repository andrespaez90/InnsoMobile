package com.innso.mobile.di.modules;


import android.content.Context;

import com.innso.mobile.InnsoApplication;
import com.innso.mobile.managers.preferences.PrefsManager;
import com.innso.mobile.providers.ResourceProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final InnsoApplication app;

    public AppModule(InnsoApplication app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Context appContext() {
        return app;
    }

    @Provides
    @Singleton
    public PrefsManager preferenceManager(Context context) {
        PrefsManager.init(context);
        return PrefsManager.getInstance();
    }

    @Provides
    @Singleton
    public ResourceProvider resourceProvider(Context context) {
        return new ResourceProvider(context);
    }

}

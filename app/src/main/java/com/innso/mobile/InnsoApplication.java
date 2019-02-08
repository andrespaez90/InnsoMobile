package com.innso.mobile;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.innso.mobile.di.components.AppComponent;
import com.innso.mobile.di.components.DaggerAppComponent;
import com.innso.mobile.di.modules.AppModule;
import com.innso.mobile.utils.FileUtil;
import io.fabric.sdk.android.Fabric;


public class InnsoApplication extends Application {

    private AppComponent appComponent;

    private static InnsoApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        instance = this;
        updateDagger();
        initialize();
    }

    public void updateDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    private void initialize() {
        FileUtil.init(BuildConfig.APPLICATION_ID);
        FileUtil.generateBasicFolders(getString(R.string.app_name));
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public static InnsoApplication get() {
        return instance;
    }
}

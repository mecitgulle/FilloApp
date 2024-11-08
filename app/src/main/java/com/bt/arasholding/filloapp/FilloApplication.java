package com.bt.arasholding.filloapp;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.BuildConfig;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.bt.arasholding.filloapp.di.component.ApplicationComponent;
import com.bt.arasholding.filloapp.di.component.DaggerApplicationComponent;
import com.bt.arasholding.filloapp.di.module.ApplicationModule;
import com.bt.arasholding.filloapp.utils.AppLogger;

public class FilloApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
//
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);

        AppLogger.init();

        AndroidNetworking.initialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
        }
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }
}
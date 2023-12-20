package com.bt.arasholding.filloapp.di.component;

import android.app.Application;
import android.content.Context;

import com.bt.arasholding.filloapp.FilloApplication;
import com.bt.arasholding.filloapp.data.DataManager;
import com.bt.arasholding.filloapp.di.ApplicationContext;
import com.bt.arasholding.filloapp.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(FilloApplication app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}
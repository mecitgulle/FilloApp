package com.bt.arasholding.filloapp.di.module;

import android.app.Application;
import android.content.Context;

import com.bt.arasholding.filloapp.BuildConfig;
import com.bt.arasholding.filloapp.data.AppDataManager;
import com.bt.arasholding.filloapp.data.DataManager;
import com.bt.arasholding.filloapp.data.db.AppDbHelper;
import com.bt.arasholding.filloapp.data.db.DbHelper;
import com.bt.arasholding.filloapp.data.network.ApiHeader;
import com.bt.arasholding.filloapp.data.network.ApiHelper;
import com.bt.arasholding.filloapp.data.network.AppApiHelper;
import com.bt.arasholding.filloapp.data.prefs.AppPreferencesHelper;
import com.bt.arasholding.filloapp.data.prefs.PreferencesHelper;
import com.bt.arasholding.filloapp.di.ApiInfo;
import com.bt.arasholding.filloapp.di.ApplicationContext;
import com.bt.arasholding.filloapp.di.PreferenceInfo;
import com.bt.arasholding.filloapp.utils.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    //region Application Modules

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    //endregion Application Modules

    //region Api Modules

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return BuildConfig.API_KEY;
    }

    @Provides
    @Singleton
    ApiHeader.ProtectedApiHeader provideProtectedApiHeader(@ApiInfo String apiKey) {
        return new ApiHeader.ProtectedApiHeader(apiKey,1212L , null);
    }

    //endregion Api Modules

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    //region Database Modules

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    //endregion Database Modules

    //region Preferences Modules

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    //endregion Preferences Modules
}
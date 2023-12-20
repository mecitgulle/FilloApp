package com.bt.arasholding.filloapp.ui.splash;

import com.bt.arasholding.filloapp.di.PerActivity;
import com.bt.arasholding.filloapp.ui.base.MvpPresenter;

@PerActivity
public interface SplashMvpPresenter<V extends SplashMvpView> extends MvpPresenter<V> {
    void decideNextActivity();
}
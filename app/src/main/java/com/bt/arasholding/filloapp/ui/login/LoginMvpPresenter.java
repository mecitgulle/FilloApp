package com.bt.arasholding.filloapp.ui.login;

import com.bt.arasholding.filloapp.ui.base.MvpPresenter;

public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {
    void onServerLoginClick(String userName, String password, String deviceToken, String deviceName);
}
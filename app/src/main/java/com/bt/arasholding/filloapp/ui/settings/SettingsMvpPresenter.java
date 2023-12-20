package com.bt.arasholding.filloapp.ui.settings;

import com.bt.arasholding.filloapp.ui.base.MvpPresenter;

public interface SettingsMvpPresenter<V extends SettingsMvpView> extends MvpPresenter<V> {

    void setSelectedCamera(boolean checked);
    void setSelectedBluetooth(boolean checked);
    void setSelectedLazer(boolean checked);

    void setSelectedBluetoothDeviceName(String bluetoohDevice);

    void onViewPrepared();
}
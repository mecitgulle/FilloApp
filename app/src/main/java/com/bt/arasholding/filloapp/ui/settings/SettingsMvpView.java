package com.bt.arasholding.filloapp.ui.settings;

import com.bt.arasholding.filloapp.ui.base.MvpView;

import java.util.List;

public interface SettingsMvpView extends MvpView {
    void updateSelectedCamera(Boolean selectedBluetooth);
    void updateSelectedBluetooth(Boolean selectedCamera);
    void updateSelectedLazer(Boolean selectedLazer);
    void updateBluetoothSpanner(List<String> dataList);
    void setSelectedBluetoothDevice(String selectedBluetoothDeviceName);
}
package com.bt.arasholding.filloapp.ui.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.bt.arasholding.filloapp.ui.base.MvpView;

import java.io.IOException;

public interface BluetoothFragmentMvpView extends MvpView {
    void updateLabel(String label);
    void openBT() throws IOException;
    void closeBT() throws IOException;
    void beginListenForData();
    void updateButtonText(String text);
    void writeData(String data);

    BluetoothDevice findBT();
    BluetoothSocket getBluetoothSocket();

    void updateCurrentDeviceName(String currentBluetoothPairedDeviceName);

    String getBluetoothDeviceName();
}
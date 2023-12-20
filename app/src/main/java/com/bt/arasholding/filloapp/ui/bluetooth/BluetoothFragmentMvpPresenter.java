package com.bt.arasholding.filloapp.ui.bluetooth;

import com.bt.arasholding.filloapp.ui.base.MvpPresenter;

public interface BluetoothFragmentMvpPresenter<V extends BluetoothFragmentMvpView> extends MvpPresenter<V> {
    void onViewPrepared();
}

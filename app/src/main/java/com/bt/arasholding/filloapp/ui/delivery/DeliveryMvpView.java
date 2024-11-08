package com.bt.arasholding.filloapp.ui.delivery;

import com.bt.arasholding.filloapp.ui.base.MvpView;

public interface DeliveryMvpView extends MvpView {
    void showCameraFragment();
    void showBluetoothFragment();
    void updateToolbarTitle();

    void decideProcessFragment();
    void showTokenExpired();

}
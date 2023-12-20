package com.bt.arasholding.filloapp.ui.delivermultiplecustomerwaybill;

import com.bt.arasholding.filloapp.ui.base.MvpPresenter;

public interface DeliverMultipleCustomerMvpPresenter<V extends DeliverMultipleCustomerMvpView> extends MvpPresenter<V> {
    void getCustomer();

    void setLatitude(String latitude);
    void setLongitude(String longitude);

    String getLatitude();
    String getLongitude();
}

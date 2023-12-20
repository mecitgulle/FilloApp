package com.bt.arasholding.filloapp.ui.shippingoutput;

import com.bt.arasholding.filloapp.di.PerActivity;
import com.bt.arasholding.filloapp.ui.base.MvpPresenter;

@PerActivity
public interface ShippingOutputMvpPresenter<V extends ShippingOutputMvpView> extends MvpPresenter<V> {
    void onShippingTrackingClick(String seferNo);
    void onQbAracCikisClick(String seferNo);
}
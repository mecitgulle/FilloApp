package com.bt.arasholding.filloapp.ui.delivery;

import com.bt.arasholding.filloapp.ui.base.MvpPresenter;

public interface DeliveryMvpPresenter<V extends DeliveryMvpView> extends MvpPresenter<V> {
    void onQueryClick(String trackId);
    void onViewPrepared();
}
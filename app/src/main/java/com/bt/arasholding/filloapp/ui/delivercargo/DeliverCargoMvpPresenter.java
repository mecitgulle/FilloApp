package com.bt.arasholding.filloapp.ui.delivercargo;

import com.bt.arasholding.filloapp.data.network.model.DeliveredCargoRequest;
import com.bt.arasholding.filloapp.di.PerActivity;
import com.bt.arasholding.filloapp.ui.base.MvpPresenter;

@PerActivity
public interface DeliverCargoMvpPresenter<V extends DeliverCargoMvpView> extends MvpPresenter<V> {
    void onViewPrepared(String trackId);
    void onViewPreparedByAtfNo(String atfNo);
    void onViewDeliveryInfoPrepared(String trackId);
    void onDeliveredClick(DeliveredCargoRequest request);
    void multiDeliverApiCall(DeliveredCargoRequest request);

    void setLatitude(String latitude);
    void setLongitude(String longitude);

    void getAtfUndeliverableReason();

    String getLatitude();
    String getLongitude();
}
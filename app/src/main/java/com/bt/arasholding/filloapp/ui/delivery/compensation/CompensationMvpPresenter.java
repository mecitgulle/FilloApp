package com.bt.arasholding.filloapp.ui.delivery.compensation;

import com.bt.arasholding.filloapp.data.network.model.CompensationRequest;
import com.bt.arasholding.filloapp.ui.base.MvpPresenter;
import com.bt.arasholding.filloapp.ui.delivery.multidelivery.MultiDeliveryMvpView;

public interface CompensationMvpPresenter<V extends CompensationMvpView> extends MvpPresenter<V> {
    void getCompensationReason();
    void getBarcodeInformation(String barcode, boolean isBarcode);
    void TazminGirisi(CompensationRequest request,String tazminNo);
}

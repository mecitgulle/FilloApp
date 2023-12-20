package com.bt.arasholding.filloapp.ui.nobarcode;

import com.bt.arasholding.filloapp.data.network.model.DeliveredCargoRequest;
import com.bt.arasholding.filloapp.data.network.model.NoBarcodeSaveRequest;
import com.bt.arasholding.filloapp.ui.base.MvpPresenter;

public interface NoBarcodeMvpPresenter<V extends NoBarcodeMvpView> extends MvpPresenter<V> {
    void showNewNoBarcodeDialog();
    void getAllCustomer();
    void saveNoBarcode(NoBarcodeSaveRequest noBarcodeParam);
    void onViewPrepared();
    void GetNoBarcodeCargo();
}

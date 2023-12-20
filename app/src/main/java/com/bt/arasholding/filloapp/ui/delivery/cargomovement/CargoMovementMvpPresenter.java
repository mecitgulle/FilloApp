package com.bt.arasholding.filloapp.ui.delivery.cargomovement;

import com.bt.arasholding.filloapp.ui.base.MvpPresenter;

public interface CargoMovementMvpPresenter<V extends CargoMovementMvpView> extends MvpPresenter<V> {
    void onViewPrepared(String trackId, boolean isBarcode);
    void onViewPreparedNoBarcode(String trackId,String noBarcodeId);
}

package com.bt.arasholding.filloapp.ui.barcodeprinter;

import com.bt.arasholding.filloapp.ui.base.MvpPresenter;

public interface BarcodePrinterMvpPresenter<V extends BarcodePrinterMvpView> extends MvpPresenter<V> {
    void getBarcodeText(String barcode,String barkodTipi);
    void onViewPrepared();
}

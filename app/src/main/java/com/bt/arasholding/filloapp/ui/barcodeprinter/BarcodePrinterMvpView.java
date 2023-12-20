package com.bt.arasholding.filloapp.ui.barcodeprinter;

import com.bt.arasholding.filloapp.ui.base.MvpView;

public interface BarcodePrinterMvpView extends MvpView {

    void printBarcode(String barcodetext);
    void updateButtonText(String text);
}

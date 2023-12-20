package com.bt.arasholding.filloapp.ui.textbarcode;

import com.bt.arasholding.filloapp.data.model.Barcode;
import com.bt.arasholding.filloapp.ui.base.MvpView;

import java.util.List;

public interface TextBarcodeMvpView extends MvpView {

    void updateToolbarTitle();

    void updateBarcodeList(List<Barcode> barcodeList);

    void updateCount(String yuklenecekParcaAdeti);

    void showErrorMessage(String message);

}
package com.bt.arasholding.filloapp.ui.shipment.lazer;

import com.bt.arasholding.filloapp.data.model.Barcode;
import com.bt.arasholding.filloapp.data.network.model.Sefer;
import com.bt.arasholding.filloapp.ui.base.MvpView;

import java.util.List;

public interface ShipmentLazerMvpView extends MvpView {
    void showShipmentLazer(Sefer sefer);
    void incrementCount();
    void updateBarcodeList(List<Barcode> barcodeList);
    String hatYuklemeRotaKontrolDialog(String barcode, String shipment);
    boolean isAlertDialogShowing();
    void clearBarcodeText();
    void showTokenExpired();
}

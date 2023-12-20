package com.bt.arasholding.filloapp.ui.shipment;

import com.bt.arasholding.filloapp.data.model.Barcode;
import com.bt.arasholding.filloapp.data.network.model.Sefer;
import com.bt.arasholding.filloapp.ui.base.MvpView;

import java.util.List;

public interface ShipmentMvpView extends MvpView {
    void openCargoBarcodeActivity(String islemTipi, Sefer shipment);
    void showCameraScanner();
    void showBluetoothScanner();
    void showShipmentFragment(Sefer sefer);
    void updateBarcodeList(List<Barcode> barcodeList);
    void incrementCount();
//    void vibrate();
    String hatYuklemeRotaKontrolDialog(String barcode, String shipment);

    void updateToolbarTitle();

    void decideProcessFragment();
    boolean isAlertDialogShowing();
}
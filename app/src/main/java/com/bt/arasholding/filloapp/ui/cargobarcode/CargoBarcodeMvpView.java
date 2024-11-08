package com.bt.arasholding.filloapp.ui.cargobarcode;

import com.bt.arasholding.filloapp.ui.base.MvpView;

public interface CargoBarcodeMvpView  extends MvpView {
    void updateSayac();
    void showTokenExpired();
    void showErrorMessage(String message);
    String hatYuklemeRotaKontrolDialog(String barcode, String shipment);
    boolean isAlertDialogShowing();
}
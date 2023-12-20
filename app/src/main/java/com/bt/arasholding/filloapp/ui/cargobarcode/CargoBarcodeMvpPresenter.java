package com.bt.arasholding.filloapp.ui.cargobarcode;

import com.bt.arasholding.filloapp.di.PerActivity;
import com.bt.arasholding.filloapp.ui.base.MvpPresenter;

@PerActivity
public interface CargoBarcodeMvpPresenter<V extends CargoBarcodeMvpView> extends MvpPresenter<V> {
    void yukleme(String barcode);
    void indirme(String barcode);
    void yuklemewithsefer(String barcode,int shipmentId);
    void indirmewithsefer(String barcode,int shipmentId);
    void dagitim(String barcode,int shipmentId);
    void hatyukleme(String barcode, int shipmentId);
    void hatyuklemeDevam(String barcode, String shipment, String cevap);
}
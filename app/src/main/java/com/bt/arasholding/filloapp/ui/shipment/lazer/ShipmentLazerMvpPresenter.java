package com.bt.arasholding.filloapp.ui.shipment.lazer;

import com.bt.arasholding.filloapp.data.network.model.Sefer;
import com.bt.arasholding.filloapp.di.PerActivity;
import com.bt.arasholding.filloapp.ui.base.MvpPresenter;
import com.bt.arasholding.filloapp.ui.shipment.ShipmentMvpView;

@PerActivity
public interface ShipmentLazerMvpPresenter <V extends ShipmentLazerMvpView> extends MvpPresenter<V> {
    void getShipmentInformationByShipmentBarcode(String shipmentBarcode,String islemTipi);
    void onViewPrepared();
    void dagitim(String codeContent);
    void hatyukleme(String codeContent);
    void yukleme(String codeContent);
    void indirme(String codeContent);
    void hatyuklemeDevam(String barcode, String shipment, String cevap);
    void setShipment(Sefer sefer);
    void deleteBarcodes(String islemTipi);
}

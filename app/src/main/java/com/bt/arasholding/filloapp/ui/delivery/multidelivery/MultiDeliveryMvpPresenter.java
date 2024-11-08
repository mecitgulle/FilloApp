package com.bt.arasholding.filloapp.ui.delivery.multidelivery;

import com.bt.arasholding.filloapp.data.network.model.AtfParcelCount;
import com.bt.arasholding.filloapp.data.network.model.DeliverCargoImageUploadRequest;
import com.bt.arasholding.filloapp.data.network.model.DeliveredCargoRequest;
import com.bt.arasholding.filloapp.ui.base.MvpPresenter;

import java.util.List;

public interface MultiDeliveryMvpPresenter<V extends MultiDeliveryMvpView> extends MvpPresenter<V> {
    void getBarcodeInformation(String barcode, boolean isBarcode, int okutmaTipi);
    void getBarcodeInformation2(String barcode, boolean isBarcode, int okutmaTipi);

    void refreshList(int islemTipi,int tip);

    void teslimatKapat2(DeliveredCargoRequest teslimatParam, List<AtfParcelCount> okutulanAtfler);
    void teslimatKapat(DeliveredCargoRequest teslimatParam);

    void deleteAtfList(String barcode, int islemTipi);

    void apiCallCustomerDelivery(DeliveredCargoRequest teslimatParam);

    void imageUpload(DeliverCargoImageUploadRequest imageParam);

    void setAlindiMi(long id, String deger);

    void onViewPrepared();

    void showTeslimatDialog2(List<AtfParcelCount> okutulanAtfler);
    void showTeslimatDialog();

//    void setHasarAciklama(long id, String hasarAciklama);

//    void setHasarPhoto(long id, String foto);

    void getAtfUndeliverableReason();

    void setLatitude(String latitude);

    void setLongitude(String longitude);

    void setButtonText(String choose);

    String getLatitude();

    String getLongitude();
}
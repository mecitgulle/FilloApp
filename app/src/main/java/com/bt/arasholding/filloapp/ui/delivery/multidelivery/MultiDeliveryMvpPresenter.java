package com.bt.arasholding.filloapp.ui.delivery.multidelivery;

import com.bt.arasholding.filloapp.data.network.model.DeliverCargoImageUploadRequest;
import com.bt.arasholding.filloapp.data.network.model.DeliveredCargoRequest;
import com.bt.arasholding.filloapp.ui.base.MvpPresenter;

public interface MultiDeliveryMvpPresenter<V extends MultiDeliveryMvpView> extends MvpPresenter<V> {
    void getBarcodeInformation(String barcode, boolean isBarcode);

    void refreshList(int islemTipi);

    void teslimatKapat(DeliveredCargoRequest teslimatParam);

    void deleteAtfList(String barcode, int islemTipi);

    void apiCallCustomerDelivery(DeliveredCargoRequest teslimatParam);

    void imageUpload(DeliverCargoImageUploadRequest imageParam);

    void setAlindiMi(long id, String deger);

    void onViewPrepared();

    void showTeslimatDialog();

    void setHasarAciklama(long id, String hasarAciklama);

    void setHasarPhoto(long id, String foto);

    void getAtfUndeliverableReason();

    void setLatitude(String latitude);

    void setLongitude(String longitude);

    void setButtonText(String choose);

    String getLatitude();

    String getLongitude();
}
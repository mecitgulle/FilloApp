package com.bt.arasholding.filloapp.ui.delivery.multidelivery;

import com.bt.arasholding.filloapp.data.model.Barcode;
import com.bt.arasholding.filloapp.data.network.model.AtfUndeliverableReasonModel;
import com.bt.arasholding.filloapp.ui.base.MvpView;

import java.util.List;

public interface MultiDeliveryMvpView extends MvpView {
    void sendBarcode(String barcode,boolean isBarcode);
    void deliverMultipleCustomer(String barcode, String arkodu);
    void updateBarcodeList(List<Barcode> barcodeList);
    void deleteAtf(String id, int islemTipi);
    void setAlindiMi(long id ,String deger);
    void setHasarPhoto(long id, String aciklama);
    void dispatchTakePictureIntent(String id);
    void showHasarAciklamaDialog(long id);
    void showAlertDialogChoose();
    void showTeslimatDialog();
    void setHasarAciklama(long id,String aciklama);
    void setSpinner(List<AtfUndeliverableReasonModel> model);
    void setButtonText(String choose);
    void showErrorMessage(String message);
}
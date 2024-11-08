package com.bt.arasholding.filloapp.ui.delivery.multidelivery;

import com.bt.arasholding.filloapp.data.model.Barcode;
import com.bt.arasholding.filloapp.data.network.model.AtfParcelCount;
import com.bt.arasholding.filloapp.data.network.model.AtfUndeliverableReasonModel;
import com.bt.arasholding.filloapp.data.network.model.CargoDetail;
import com.bt.arasholding.filloapp.ui.base.MvpView;

import java.util.List;

public interface MultiDeliveryMvpView extends MvpView {
    void sendBarcode(String barcode,int okutulmusSayi,boolean isBarcode);
    void deliverMultipleCustomer(String barcode, String arkodu);
    void updateBarcodeList(List<Barcode> barcodeList,int okutmaTipi);
    void sonucAdapter(List<Barcode> barcodeList, int tip);
    void deleteAtf(String AtfId,int okutmaTipi);
    void setAlindiMi(long id ,String deger);
//    void setHasarPhoto(long id, String aciklama);
    void dispatchTakePictureIntent(String id);
//    void showHasarAciklamaDialog(long id);
    void showAlertDialogChoose();
    void resetSingletonModel();
    void showTeslimatDialog();
    void showTeslimatDialog2(List<AtfParcelCount> okutulanAtfler);
//    void setHasarAciklama(long id,String aciklama);
    void setSpinner(List<AtfUndeliverableReasonModel> model);
    void setButtonText(String choose);
    void showErrorMessage(String message);
    void updateSayac(int sayac,int sayacTip);
    void updateTotalPieceCount(CargoDetail item);
    void showTokenExpired();
}
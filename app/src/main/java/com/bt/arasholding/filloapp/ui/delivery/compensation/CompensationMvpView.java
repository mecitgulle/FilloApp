package com.bt.arasholding.filloapp.ui.delivery.compensation;

import com.bt.arasholding.filloapp.data.network.model.AtfUndeliverableReasonModel;
import com.bt.arasholding.filloapp.ui.base.MvpView;

import java.util.List;

public interface CompensationMvpView extends MvpView {
    void updateAtfNo(String atfNo);
    void updateAtfID(String atfTarihi);
    void updateToplamParca(String toplamParca);
    void updateVarisSube(String varisSubeAdi);
    void updateCikisSube(String cikisSubeAdi);
    void updateIrsaliye(String irsaliye);
    void updateDesi(String desi);
    void updateOdemeSekli(String odemeSekli);
    void updateAlici(String alici);
    void updateGonderen(String gonderen);
    void updateTarih(String tarih);
    void updateTazminNo(String tazminNo);
    void showTazminDialog();
    void setSpinner(List<AtfUndeliverableReasonModel> model);
    void dispatchTakePictureIntent(String id);
    void sendBarcode(String barcode,boolean isBarcode);
    void redirect();
}

package com.bt.arasholding.filloapp.ui.delivery.cargomovement;

import com.bt.arasholding.filloapp.data.network.model.CargoMovementDetail;
import com.bt.arasholding.filloapp.data.network.model.CargoMovementDetailSummarys;
import com.bt.arasholding.filloapp.ui.base.MvpView;

import java.util.List;

public interface CargoMovementMvpView extends MvpView {
    void updateAtfNo(String atfNo);
    void updateAtfTarihi(String atfTarihi);
    void updateToplamParca(String toplamParca);
    void updateVarisSubeAdi(String varisSubeAdi);
    void searchMovements(String trackId,boolean isBarcode);
    void noBarcodeMovements(String trackId,String noBarcodeId);
    void updateHareketlerList(List<CargoMovementDetail> list);
    void updateSummarysList(List<CargoMovementDetailSummarys> list);
    void updateSubeYukleme(int subeYukleme);
    void updateSubeIndirme(int subeIndirme);
    void updateSubeDagitim(int subeDagitim);
    void updateSubeIndirme2(int indirme);
}

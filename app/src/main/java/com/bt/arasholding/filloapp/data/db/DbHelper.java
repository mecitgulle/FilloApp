package com.bt.arasholding.filloapp.data.db;

import com.bt.arasholding.filloapp.data.model.Barcode;

import java.util.List;

import io.reactivex.Observable;

public interface DbHelper {
    Observable<Boolean> saveBarcode(Barcode barcode);
    Observable<List<Barcode>> getAllBarcodes();
    Observable<List<Barcode>> getBarcodesByIslemTipi(int islemTipi);
    Observable<Boolean> deleteBarcodesByType(int islemTipi);
    Observable<Boolean> updateBarcode(long id,String alindiMi);
    Observable<Boolean> deleteBarcode(String barcode);
    Observable<Boolean> updateBarcodebyBarcodeType(Barcode barcode);
    Barcode getBarcodesByBarcode(String barcode);
    Observable<Boolean> updateBarcodeHasarAciklama(long id,String aciklama);
    Observable<Boolean> updateBarcodePhoto(long id,String foto);

}
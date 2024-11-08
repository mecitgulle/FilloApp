package com.bt.arasholding.filloapp.data.db;

import com.bt.arasholding.filloapp.data.model.Barcode;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface DbHelper {
    Observable<Boolean> saveBarcode(Barcode barcode);

    Observable<List<Barcode>> getAllBarcodes();
//   List<Barcode> getAllBarcodes();
    Observable<List<Barcode>> getBarcodesByIslemTipi(int islemTipi);

//    List<Barcode> getBarcodesByIslemTipi(int islemTipi);

    //    Observable<Boolean> deleteBarcodesByType(int islemTipi);
//    Observable<Boolean> updateBarcode(long id, String alindiMi);
//    Observable<Boolean> deleteBarcode(String barcode);
    Observable<Boolean> updateBarcode(long id, String alindiMi);

//    Observable<Boolean> updateBarcodebyBarcodeType(Barcode barcode);

    Single<Boolean> deleteBarcode(String AtfId);

    Single<Boolean> deleteBarcodesByType(int islemTipi);
}

package com.bt.arasholding.filloapp.data.db;

import android.content.Context;

import com.bt.arasholding.filloapp.data.model.Barcode;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class AppDbHelper implements DbHelper {

    private final BarcodeDao barcodeDao;

    @Inject
    public AppDbHelper(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        barcodeDao = db.barcodeDao();
    }

    @Override
    public Observable<Boolean> saveBarcode(Barcode barcode) {
        return Observable.fromCallable(() -> barcodeDao.saveBarcode(barcode) != -1);
    }

//    @Override
//    public Observable<List<Barcode>> getAllBarcodes() {
//        return barcodeDao.getAllBarcodes();
//    }

//    @Override
//    public List<Barcode> getAllBarcodes() {
//        return barcodeDao.getAllBarcodes();
//    }
    @Override
    public Observable<List<Barcode>> getAllBarcodes() {
        return Observable.fromCallable(() -> barcodeDao.getAllBarcodes());
    }

    //    @Override
//    public Observable<List<Barcode>> getBarcodesByIslemTipi(int islemTipi) {
//        return barcodeDao.getBarcodesByIslemTipi(islemTipi);
//    }
//    @Override
//    public List<Barcode> getBarcodesByIslemTipi(int islemTipi) {
//        return barcodeDao.getBarcodesByIslemTipi(islemTipi);
//    }
    @Override
    public Observable<List<Barcode>> getBarcodesByIslemTipi(int islemTipi) {
        return Observable.fromCallable(() -> barcodeDao.getBarcodesByIslemTipi(islemTipi));
    }


    @Override
    public Observable<Boolean> updateBarcode(long id, String alindiMi) {
        Barcode barcode = new Barcode();
        barcode.setId(id);
        barcode.setEvrakDonusluMu(alindiMi);

        return Observable.fromCallable(() -> {
            int count = barcodeDao.updateBarcode(barcode);
            return count > 0; // Eğer güncellenen satır sayısı 0'dan büyükse, güncelleme başarılıdır
        });
    }

    @Override
    public Single<Boolean> deleteBarcode(String AtfId) {
        return Single.fromCallable(() -> {
            int count = barcodeDao.deleteBarcodeByValue(AtfId);
            return count > 0; // Eğer silinen satır sayısı 0'dan büyükse, bir şeyler silinmiştir
        });
    }

    @Override
    public Single<Boolean> deleteBarcodesByType(int islemTipi) {
        return Single.fromCallable(() -> {
            int count = barcodeDao.deleteBarcodesByType(islemTipi);
            return count > 0;
        });
    }
}

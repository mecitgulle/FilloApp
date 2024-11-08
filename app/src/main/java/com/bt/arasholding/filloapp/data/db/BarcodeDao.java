package com.bt.arasholding.filloapp.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bt.arasholding.filloapp.data.model.Barcode;
import io.reactivex.Observable;

import java.util.List;

@Dao
public interface BarcodeDao {

    @Insert
    long saveBarcode(Barcode barcode);

//    @Query("SELECT * FROM Barcode")
//    Observable<List<Barcode>> getAllBarcodes();

    @Query("SELECT * FROM Barcode")
    List<Barcode> getAllBarcodes();

//    @Query("SELECT * FROM Barcode WHERE islemTipi = :islemTipi")
//    Observable<List<Barcode>> getBarcodesByIslemTipi(int islemTipi);

    @Query("SELECT * FROM Barcode WHERE islemTipi = :islemTipi")
    List<Barcode> getBarcodesByIslemTipi(int islemTipi);


    //    @Query("DELETE FROM Barcode WHERE islemTipi = :islemTipi")
//    Observable<Integer> deleteBarcodesByType(int islemTipi);
    @Query("DELETE FROM Barcode WHERE islemTipi = :islemTipi")
    int deleteBarcodesByType(int islemTipi);

    @Update
    int updateBarcode(Barcode barcode);

    //    @Query("DELETE FROM Barcode WHERE barcode = :barcodeValue")
//    Observable<Integer> deleteBarcodeByValue(String barcodeValue);
    @Query("DELETE FROM Barcode WHERE atfId = :AtfId")
    int deleteBarcodeByValue(String AtfId);

}

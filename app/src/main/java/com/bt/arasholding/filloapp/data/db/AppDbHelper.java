package com.bt.arasholding.filloapp.data.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.bt.arasholding.filloapp.data.model.Barcode;
import com.bt.arasholding.filloapp.di.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;

public class AppDbHelper implements DbHelper {

    private final Database mDb;

    @Inject
    public AppDbHelper(@ApplicationContext Context context) {
        mDb = new Database(context);
    }

    @Override
    public Observable<Boolean> saveBarcode(Barcode barcode) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
//                Long barkod = Long.parseLong(barcode.getBarcode(), 10);
                String query = String.format("INSERT INTO Barcode (barkod,islem_tipi,islem_sonucu,aciklama,atf_no,alici_adi,evrak_donuslu_mu,atf_id) VALUES('%s','%s','%s','%s','%s','%s','%s','%s')",
                        barcode.getBarcode(), barcode.getIslemTipi(), barcode.getIslemSonucu(), barcode.getAciklama(), barcode.getAtf_no(), barcode.getAlici_adi(), barcode.getEvrakDonusluMu(), barcode.getAtfId());
                mDb.getWritableDatabase().execSQL(query);

                return true;
            }
        });
    }

    @Override
    public Observable<List<Barcode>> getAllBarcodes() {
        return Observable.fromCallable(new Callable<List<Barcode>>() {
            @Override
            public List<Barcode> call() throws Exception {

                int count = 0;

                List<Barcode> result = new ArrayList<>();

                SQLiteDatabase db = mDb.getReadableDatabase();
                SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

                String[] sqlSelect = new String[]{"id", "barkod", "islem_tipi", "islem_sonucu", "aciklama", "atf_no", "alici_adi", "evrak_donuslu_mu", "atf_id"};
                String table = "Barcode";

                qb.setTables(table);

                Cursor c = qb.query(db, sqlSelect, null, null, null, null, "id desc");

                if (c.moveToFirst()) {
                    do {
                        Barcode barcode = new Barcode();

                        barcode.setAciklama(c.getString(c.getColumnIndex("aciklama")));
                        barcode.setBarcode(c.getString(c.getColumnIndex("barkod")));
                        barcode.setId(c.getLong(c.getColumnIndex("id")));
                        barcode.setIslemSonucu(c.getString(c.getColumnIndex("islem_sonucu")));
                        barcode.setIslemTipi(c.getInt(c.getColumnIndex("islem_tipi")));
                        barcode.setAtf_no(c.getString(c.getColumnIndex("atf_no")));
                        barcode.setAlici_adi(c.getString(c.getColumnIndex("alici_adi")));
                        barcode.setEvrakDonusluMu(c.getString(c.getColumnIndex("evrak_donuslu_mu")));
                        barcode.setAtf_id(c.getString(c.getColumnIndex("atf_id")));
                        barcode.setAtfId(c.getString(c.getColumnIndex("atf_id")));
                        result.add(barcode);

                    } while (c.moveToNext());
                }

                return result;
            }
        });
    }

    @Override
    public Observable<List<Barcode>> getBarcodesByIslemTipi(int islemTipi) {
        return Observable.fromCallable(new Callable<List<Barcode>>() {
            @Override
            public List<Barcode> call() throws Exception {

                List<Barcode> result = new ArrayList<>();

                SQLiteDatabase db = mDb.getReadableDatabase();
                SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

                String[] sqlSelect = new String[]{"id", "barkod", "islem_tipi", "islem_sonucu", "aciklama", "atf_no", "alici_adi", "evrak_donuslu_mu", "atf_id"};
                String table = "Barcode";

                qb.setTables(table);


                Cursor c = qb.query(db, sqlSelect, "islem_tipi=?", new String[]{String.valueOf(islemTipi)}, null, null, "id desc");

                if (c.moveToFirst()) {
                    do {
                        Barcode barcode = new Barcode();

                        barcode.setAciklama(c.getString(c.getColumnIndex("aciklama")));
                        barcode.setBarcode(c.getString(c.getColumnIndex("barkod")));
                        barcode.setId(c.getLong(c.getColumnIndex("id")));
                        barcode.setIslemSonucu(c.getString(c.getColumnIndex("islem_sonucu")));
                        barcode.setIslemTipi(c.getInt(c.getColumnIndex("islem_tipi")));
                        barcode.setAtf_no(c.getString(c.getColumnIndex("atf_no")));
                        barcode.setAlici_adi(c.getString(c.getColumnIndex("alici_adi")));
                        barcode.setEvrakDonusluMu(c.getString(c.getColumnIndex("evrak_donuslu_mu")));
                        barcode.setAtfId(c.getString(c.getColumnIndex("atf_id")));
                        result.add(barcode);

                    } while (c.moveToNext());
                }

                return result;
            }
        });
    }

    @Override
    public Observable<Boolean> deleteBarcodesByType(int islemTipi) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                String query = String.format("DELETE FROM Barcode WHERE islem_tipi = '%s'", islemTipi);
                mDb.getWritableDatabase().execSQL(query);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> updateBarcode(long id, String alindiMi) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                String query = String.format("UPDATE Barcode SET evrak_donuslu_mu = '%s' WHERE id = '%s'", alindiMi, id);
                mDb.getWritableDatabase().execSQL(query);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> deleteBarcode(String barcode) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                String query = String.format("DELETE FROM Barcode WHERE barkod = '%s'", barcode);
                mDb.getWritableDatabase().execSQL(query);
                return true;
            }
        });
    }


    @Override
    public Observable<Boolean> updateBarcodebyBarcodeType(Barcode barcode) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                String query = String.format("UPDATE Barcode SET  islem_sonucu = '%s', aciklama ='%s', atf_no = '%s' WHERE barkod = '%s'", barcode.getIslemSonucu(), barcode.getAciklama(), barcode.getAtf_no(), barcode.getBarcode());
                mDb.getWritableDatabase().execSQL(query);
                return true;
            }
        });
    }

    @Override
    public Barcode getBarcodesByBarcode(String barcode) {
//        return Observable.fromCallable(new Callable<Barcode>() {
//            @Override
//            public Barcode call() throws Exception {
////                String query = String.format("UPDATE Barcode SET barkod = '%s' WHERE barkod = '%s'", barcode.getBarcode(), barcode.getBarcode());
////                Barcode bcode = mDb.getWritableDatabase().execSQL(query);
////                return true;

        Barcode bcode = new Barcode();

        SQLiteDatabase db = mDb.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = new String[]{"id", "barkod", "islem_tipi", "islem_sonucu", "aciklama", "atf_no", "alici_adi", "evrak_donuslu_mu"};
        String table = "Barcode";

        qb.setTables(table);


        Cursor c = qb.query(db, sqlSelect, "barkod=?", new String[]{String.valueOf(barcode)}, null, null, "id desc");

        if (c.moveToFirst()) {
            do {


                bcode.setAciklama(c.getString(c.getColumnIndex("aciklama")));
                bcode.setBarcode(c.getString(c.getColumnIndex("barkod")));
                bcode.setId(c.getLong(c.getColumnIndex("id")));
                bcode.setIslemSonucu(c.getString(c.getColumnIndex("islem_sonucu")));
                bcode.setIslemTipi(c.getInt(c.getColumnIndex("islem_tipi")));
                bcode.setAtf_no(c.getString(c.getColumnIndex("atf_no")));
                bcode.setAlici_adi(c.getString(c.getColumnIndex("alici_adi")));
                bcode.setEvrakDonusluMu(c.getString(c.getColumnIndex("evrak_donuslu_mu")));

            } while (c.moveToNext());
        }

        return bcode;
    }

    @Override
    public Observable<Boolean> updateBarcodeHasarAciklama(long id, String aciklama) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                String query = String.format("UPDATE Barcode SET hasar_aciklama = '%s' WHERE id = '%s'", aciklama, id);
                mDb.getWritableDatabase().execSQL(query);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> updateBarcodePhoto(long id, String foto) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                String query = String.format("UPDATE Barcode SET resim = '%s' WHERE id = '%s'", foto, id);
                mDb.getWritableDatabase().execSQL(query);
                return true;
            }
        });
    }
//        });
}
//}
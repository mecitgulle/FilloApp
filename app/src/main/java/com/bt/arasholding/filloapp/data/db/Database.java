package com.bt.arasholding.filloapp.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bt.arasholding.filloapp.BuildConfig;
import com.bt.arasholding.filloapp.data.model.Barcode;
import com.bt.arasholding.filloapp.di.ApplicationContext;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Database extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "FilloApp.db";
    private static final String TABLE ="Barcode";
    private static final int DATABASE_VERSION = 43;

    @Inject
    public Database(@ApplicationContext Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static final String DATABASE_CREATE_TEAM = "create table "
            + TABLE + "(" + "id" + " integer primary key autoincrement, "
            + "barkod" + " TEXT, "
            + "atf_no" + " TEXT, "
            + "islem_tipi" + " integer, "
            + "islem_sonucu" + " TEXT, "
            + "aciklama" + " TEXT, "
            + "alici_adi" + " TEXT, "
            + "evrak_donuslu_mu" + " TEXT, "
            + "atf_id" + " TEXT);";

    public void onCreateTable(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_TEAM);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("", "Old Version " + oldVersion + " New Version " + newVersion + " db.getVersion is " + db.getVersion());

        if (oldVersion < newVersion) {

            db.execSQL("DROP TABLE IF EXISTS "+TABLE);
            onCreateTable(db);

        }
        //TODO mevcut barcode tablosunu kaldırarak yeniden oluşturulmasını sağlar.
    }

    private SQLiteDatabase upgradeTo2(SQLiteDatabase db) {

        db.beginTransaction();
        try {
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.w("", "onUpgrade failed");
        } finally {
            db.endTransaction();
        }

        return db;
    }


}
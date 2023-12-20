package com.bt.arasholding.filloapp.ui.textbarcode;

import com.bt.arasholding.filloapp.ui.base.MvpPresenter;

public interface TextBarcodeMvpPresenter<V extends TextBarcodeMvpView> extends MvpPresenter<V>  {
    void onViewPrepared(String islemTipi);
    void yukleme(String barcode);
    void indirme(String barcode);
    void hatYukleme(String barcode);
    void refreshList(int islemTipi);
    void sendToServer(String barcode, String islemTipi);
}
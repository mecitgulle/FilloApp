package com.bt.arasholding.filloapp.ui.lazer;

import com.bt.arasholding.filloapp.ui.base.MvpPresenter;
import com.bt.arasholding.filloapp.ui.cargobarcode.CargoBarcodeMvpView;

public interface LazerMvpPresenter<V extends LazerMvpView> extends MvpPresenter<V> {
    void yukleme(String barcode);
    void indirme(String barcode);
}

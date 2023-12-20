package com.bt.arasholding.filloapp.ui.home;

import com.bt.arasholding.filloapp.data.network.model.Sefer;
import com.bt.arasholding.filloapp.ui.base.MvpPresenter;

public interface HomeMvpPresenter<V extends HomeMvpView> extends MvpPresenter<V> {

    void onNavMenuCreated();

    void onDrawerYuklemeClick();

    void onDrawerIndirmeClick();

    void onDrawerAtfTeslimatClick();

    void onDrawerTopluTeslimatClick();

    void onDrawerAracCikisClick();

    void onDrawerSettingsClick();

    void onDrawerLogoutClick();

    void openCargoBarcodeActivity(String islemTipi);

    void setRememberChecked(boolean isChecked);

    void setSelectedCamera(boolean isSelectedCamera);

    void setSelectedBluetooth(boolean isSelectedBluetooth);

    void setSelectedLazer(boolean isSelectedLazer);

    void openTextBarcodeActivity(String islemTipi);

    void openLazerActivity(String islemTipi);

    void onDrawerCargoMovementClick();

    void onDrawerDagitimClick();

    void onDrawerOptionAboutClick();

    void onDrawerBarcodePrinterClick();

    void onDrawerHatYukleme();

    void onDrawerDevir();

    void onDrawerTopluDevir();

    void onDrawerRota();

    void onDrawerTazmin();

    void onDrawerMusteriTeslimat();

    void onDrawerBarkodsuzKargo();
//    void startJob();

    void setShipment(Sefer sefer);

    void setRead(boolean isRead);

    void showCacheAlertDialog();

    void initJobs();

    void setLatitude(String latitude);
    void setLongitude(String longitude);
    String getLatitude();
    String getLongitude();

    String setGroupId();
}
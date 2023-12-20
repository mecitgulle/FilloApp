package com.bt.arasholding.filloapp.ui.home;

import com.bt.arasholding.filloapp.ui.base.MvpView;

public interface HomeMvpView extends MvpView {
    void closeNavigationDrawer();
    void showLogoutDialog();
    void showDecideDialog(String islemTipi);
    void updateAppVersion();
    void updateUserName(String userName);
    void showAboutFragment();
    void lockDrawer();
    void unlockDrawer();
    void openCargoBarcodeActivity(String islemTipi);
    void openShippingOutputActivity();
    void openNoBarcodeActivity();
    void openSettingsActivity();
    void openLoginActivity();
    void openTextBarcodeActivity(String islemTipi);
    void openDeliveryActivity(String islemTipi);
    void openShipmentActivity(String islemTipi);
    void openLazerActivity(String islemTipi);
    void openShipmentLazerActivity(String islemTipi);
    void openBarcodePrinterActivity();
    void openExpeditionRouteActivity();
    void openDeliverMultipleCustomerActivity(String islemTipi);
    void showCacheDialog();
}
package com.bt.arasholding.filloapp.ui.home;

import com.androidnetworking.error.ANError;
import com.bt.arasholding.filloapp.data.DataManager;
import com.bt.arasholding.filloapp.data.model.Barcode;
import com.bt.arasholding.filloapp.data.network.model.CargoMovementRequest;
import com.bt.arasholding.filloapp.data.network.model.Sefer;
import com.bt.arasholding.filloapp.di.PerActivity;
import com.bt.arasholding.filloapp.ui.base.BasePresenter;
import com.bt.arasholding.filloapp.utils.AppConstants;
import com.bt.arasholding.filloapp.utils.AppLogger;
import com.simplymadeapps.quickperiodicjobscheduler.PeriodicJob;
import com.simplymadeapps.quickperiodicjobscheduler.QuickJobFinishedCallback;
import com.simplymadeapps.quickperiodicjobscheduler.QuickPeriodicJob;
import com.simplymadeapps.quickperiodicjobscheduler.QuickPeriodicJobCollection;
//import com.simplymadeapps.quickperiodicjobscheduler.PeriodicJob;
//import com.simplymadeapps.quickperiodicjobscheduler.QuickJobFinishedCallback;
//import com.simplymadeapps.quickperiodicjobscheduler.QuickPeriodicJob;
//import com.simplymadeapps.quickperiodicjobscheduler.QuickPeriodicJobCollection;

import java.util.List;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import jp.yokomark.boogaloo.BackoffTask;
import jp.yokomark.boogaloo.Boogaloo;

@PerActivity
public class HomePresenter<V extends HomeMvpView> extends BasePresenter<V> implements HomeMvpPresenter<V> {

    private static final String TAG = "HomePresenter";

    long i = 0;
    private Sefer shipment;

    @Override
    public void setShipment(Sefer shipment) {
        this.shipment = shipment;
    }

    @Inject
    public HomePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void onNavMenuCreated() {
        getMvpView().updateUserName(getDataManager().getCurrentUserName() + " - " + getDataManager().getCurrentUserBranchCode());
        getMvpView().updateAppVersion();
    }

    @Override
    public void onDrawerYuklemeClick()
    {
        if (getDataManager().getRememberChecked()) {
            if (getDataManager().getSelectedCamera()) {
                getMvpView().openCargoBarcodeActivity(AppConstants.YUKLEME);
            } else if (getDataManager().getSelectedBluetooth()){
                getMvpView().openTextBarcodeActivity(AppConstants.YUKLEME);
            }else{
                getMvpView().openLazerActivity(AppConstants.YUKLEME);
            }
        } else {
            getMvpView().showDecideDialog(AppConstants.YUKLEME);
        }
    }

    @Override
    public void onDrawerIndirmeClick() {
        if (getDataManager().getRememberChecked()) {
            if (getDataManager().getSelectedCamera()) {
                getMvpView().openCargoBarcodeActivity(AppConstants.INDIRME);
            } else if (getDataManager().getSelectedBluetooth()){
                getMvpView().openTextBarcodeActivity(AppConstants.INDIRME);
            }else{
                getMvpView().openLazerActivity(AppConstants.INDIRME);
            }
        } else {
            getMvpView().showDecideDialog(AppConstants.INDIRME);
        }
    }

    @Override
    public void onDrawerHatYukleme() {

//        if (getDataManager().getRememberChecked()) {
//            if (getDataManager().getSelectedCamera()) {
//                getMvpView().openCargoBarcodeActivity(AppConstants.HAT_YUKLEME);
//            } else {
//                getMvpView().openTextBarcodeActivity(AppConstants.HAT_YUKLEME);
//            }
//        } else {
//            getMvpView().showDecideDialog(AppConstants.HAT_YUKLEME);
//        }
        if (getDataManager().getRememberChecked()) {
            if (getDataManager().getSelectedLazer()){
                getMvpView().openShipmentLazerActivity(AppConstants.HAT_YUKLEME);
            }
            else{
                getMvpView().openShipmentActivity(AppConstants.HAT_YUKLEME);
            }
        } else {
            getMvpView().showDecideDialog(AppConstants.HAT_YUKLEME);
        }
    }

    @Override
    public void onDrawerDevir() {
        if (getDataManager().getRememberChecked()) {
            getMvpView().openDeliveryActivity(AppConstants.ATFDEVIR);
        } else {
            getMvpView().showDecideDialog(AppConstants.ATFDEVIR);
        }
    }

    @Override
    public void onDrawerTopluDevir() {
        if (getDataManager().getRememberChecked()) {
            getMvpView().openCargoBarcodeActivity(AppConstants.TOPLUDEVIR);
        } else {
            getMvpView().showDecideDialog(AppConstants.TOPLUDEVIR);
        }
    }
    @Override
    public void onDrawerRota() {
        getMvpView().openExpeditionRouteActivity();
    }

    @Override
    public void onDrawerTazmin() {
        if (getDataManager().getRememberChecked()) {
            getMvpView().openDeliveryActivity(AppConstants.TAZMIN);
        } else {
            getMvpView().showDecideDialog(AppConstants.TAZMIN);
        }
    }

    @Override
    public void onDrawerMusteriTeslimat() {

        if (getDataManager().getRememberChecked()) {
            getMvpView().openDeliverMultipleCustomerActivity(AppConstants.MUSTERITESLIMAT);
        } else {
            getMvpView().showDecideDialog(AppConstants.MUSTERITESLIMAT);
        }
    }
    @Override
    public void onDrawerBarkodsuzKargo() {
        getMvpView().openNoBarcodeActivity();
    }


    @Override
    public void onDrawerDagitimClick() {
        if (getDataManager().getRememberChecked()) {
            if (getDataManager().getSelectedLazer()){
                getMvpView().openShipmentLazerActivity(AppConstants.DAGITIM);
            }
            else{
                getMvpView().openShipmentActivity(AppConstants.DAGITIM);
            }
        } else {
            getMvpView().showDecideDialog(AppConstants.DAGITIM);
        }
    }

    @Override
    public void onDrawerOptionAboutClick() {
        getMvpView().showAboutFragment();
    }

    @Override
    public void onDrawerBarcodePrinterClick() {
        getMvpView().openBarcodePrinterActivity();
    }

    @Override
    public void onDrawerAtfTeslimatClick() {
        if (getDataManager().getRememberChecked()) {
            getMvpView().openDeliveryActivity(AppConstants.ATFTESLIMAT);
        } else {
            getMvpView().showDecideDialog(AppConstants.ATFTESLIMAT);
        }
    }

    @Override
    public void onDrawerTopluTeslimatClick() {

        if (getDataManager().getRememberChecked()) {
            getMvpView().openCargoBarcodeActivity(AppConstants.TOPLUTESLIMAT);
        } else {
            getMvpView().showDecideDialog(AppConstants.TOPLUTESLIMAT);
        }

    }


    @Override
    public void onDrawerAracCikisClick() {
        getMvpView().openShippingOutputActivity();
    }

    @Override
    public void onDrawerSettingsClick() {
        getMvpView().openSettingsActivity();
    }

    @Override
    public void onDrawerLogoutClick() {
        setUserAsLoggedOut();
        getMvpView().openLoginActivity();
    }

    @Override
    public void openCargoBarcodeActivity(String islemTipi) {
        getMvpView().openCargoBarcodeActivity(islemTipi);
    }

    @Override
    public void setRememberChecked(boolean isChecked) {
        getDataManager().setRememberChecked(isChecked);
    }

    @Override
    public void setRead(boolean isRead) {
        getDataManager().setRead(isRead);
    }

    @Override
    public void showCacheAlertDialog() {
        if (!getDataManager().getRead()) {
            getMvpView().showCacheDialog();
        }
    }

    @Override
    public void setSelectedCamera(boolean isSelectedCamera) {
        getDataManager().setSelectedCamera(isSelectedCamera);
    }

    @Override
    public void setSelectedBluetooth(boolean isSelectedBluetooth) {
        getDataManager().setSelectedBluetooth(isSelectedBluetooth);
    }

    @Override
    public void setSelectedLazer(boolean isSelectedLazer) {
        getDataManager().setSelectedLazer(isSelectedLazer);
    }

    @Override
    public void openTextBarcodeActivity(String islemTipi) {
        getMvpView().openTextBarcodeActivity(islemTipi);
    }

    @Override
    public void openLazerActivity(String islemTipi) {
        getMvpView().openLazerActivity(islemTipi);
    }

    @Override
    public void onDrawerCargoMovementClick() {
        if (getDataManager().getRememberChecked()) {
            getMvpView().openDeliveryActivity(AppConstants.YUKLEMEINDIRMEHARAKET);
        } else {
            getMvpView().showDecideDialog(AppConstants.YUKLEMEINDIRMEHARAKET);
        }

    }


    //    @Override
//    public void startJob() {
//        Boogaloo.setup().incremental().interval(10000).until(10000).execute(new BackoffTask() {
//            @Override
//            protected boolean shouldRetry() {
//
//                countUp();
//                return true;
//            }
//
//            @Override
//            public void run() {
//                AppLogger.d("Job başladı !");
//                getCompositeDisposable().add(getDataManager()
//                        .getAllBarcodes()
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Consumer<List<Barcode>>() {
//                            @Override
//                            public void accept(List<Barcode> barcodeList) throws Exception {
//
//                                for (Barcode barcode : barcodeList)
//                                {
//                                    if (barcode.getIslemTipi() == 1){
//
//                                        yukleme(barcode.getBarcode());
//
//                                    } else if(barcode.getIslemTipi() == 2){
//                                        indirme(barcode.getBarcode());
//
//                                    } else if(barcode.getIslemTipi() == 5){
//                                        hatYukleme(barcode.getBarcode());
//
//                                    }
//                                }
//                            }
//                        }, throwable -> {
//
//                        }));
////                Boogaloo.clear();
////                Toast.makeText(getApplicationContext(), getCount() + ": Hey!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
    @Override
    public void initJobs() {
        int jobId = 1;
        QuickPeriodicJob job = new QuickPeriodicJob(jobId, new PeriodicJob() {
            @Override
            public void execute(QuickJobFinishedCallback callback) {
                SomeJobClass.performJob();
                AppLogger.d("Job yanıyor !");
                getCompositeDisposable().add(getDataManager()
                        .getAllBarcodes()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<Barcode>>() {
                            @Override
                            public void accept(List<Barcode> barcodeList) throws Exception {

                                for (Barcode barcode : barcodeList) {
                                    if (barcode.getIslemTipi() == 1 && barcode.getIslemSonucu().equals("")) {
                                        AppLogger.d("Yükleme Girdi !");
                                        yukleme(barcode.getBarcode());

                                    } else if (barcode.getIslemTipi() == 2 && barcode.getIslemSonucu().equals("")) {
                                        AppLogger.d("İndirme Girdi !");
                                        indirme(barcode.getBarcode());

                                    }
//                                    else if (barcode.getIslemTipi() == 5) {
//                                        hatYukleme(barcode.getBarcode());
//
//                                    }
                                }
                            }
                        }, throwable -> {

                        }));
                // When you have done all your work in the job, call jobFinished to release the resources
                callback.jobFinished();
            }
        });

        QuickPeriodicJobCollection.addJob(job);
    }

    public static class SomeJobClass {
        public static void performJob() {

        }
    }

    public void yukleme(String barcode) {

        CargoMovementRequest request = new CargoMovementRequest();
        request.setAccessToken(getDataManager().getAccessToken());
        request.setBarcode(barcode);
        request.setType(1);
        request.setSefer(getDataManager().getCurrentShipmentCode());

        getCompositeDisposable().add(
                getDataManager().doCargoMovementApiCall(request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                cargoMovementResponse -> {
                                    AppLogger.d(cargoMovementResponse.getMessage().toString());
                                    deleteBarcode(barcode);
//                                    if (cargoMovementResponse.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_FORBIDDEN))) {
//
//                                    } else {
//
//                                    }
                                }, throwable -> {

                                })
        );
    }

    public void indirme(String barcode) {

        CargoMovementRequest request = new CargoMovementRequest();
        request.setAccessToken(getDataManager().getAccessToken());
        request.setBarcode(barcode);
        request.setType(2);
        request.setSefer(getDataManager().getCurrentShipmentCode());

        getCompositeDisposable().add(
                getDataManager().doCargoMovementApiCall(request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                cargoMovementResponse -> {
                                    AppLogger.d(cargoMovementResponse.getMessage().toString());
                                    getDataManager().deleteBarcode(barcode);
//                                    if (cargoMovementResponse.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_FORBIDDEN))) {
//
//                                    } else {
//
//                                    }

                                }, throwable -> {

                                })
        );

    }

    public void hatYukleme(String barcode) {

//        CargoMovementRequest request = new CargoMovementRequest();
//        request.setAccessToken(getDataManager().getAccessToken());
//        request.setBarcode(barcode);
//        request.setType(5);
//        request.setSefer(getDataManager().getCurrentShipmentCode());
//
//        getCompositeDisposable().add(
//                getDataManager().doCargoMovementApiCall(request)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                cargoMovementResponse -> {
//                                    getDataManager().deleteBarcode(barcode);
////                                    if (cargoMovementResponse.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {
////
////                                    } else {
////
////                                    }
//
//                                }, throwable -> {
//                                })
//        );
    }

    public void dagitim(String barcode) {
//        if (!getMvpView().isNetworkConnected()) {
//            getMvpView().showMessage(R.string.connection_error);
//            return;
//        }

        if (shipment == null) {
            getMvpView().showMessage("Sefer bulunamadı !");
            return;
        }

        CargoMovementRequest request = new CargoMovementRequest();
        request.setAccessToken(getDataManager().getAccessToken());
        request.setBarcode(barcode);
        request.setType(11);
        request.setSefer(String.valueOf(shipment.getSEFERID()));

        getCompositeDisposable().add(
                getDataManager().doCargoMovementApiCall(request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                cargoMovementResponse -> {

                                    getDataManager().deleteBarcode(barcode);

                                }, throwable -> {


                                })
        );
    }
    private void deleteBarcode(String barcode) {
        getCompositeDisposable().add(getDataManager()
                .deleteBarcode(barcode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {

                    }
                }, throwable -> {

                }));
    }

    @Override
    public void setLatitude(String latitude) {
        getDataManager().setLatitude(latitude);
    }

    @Override
    public void setLongitude(String longitude) {
        getDataManager().setLongitude(longitude);
    }
    @Override
    public String getLatitude() {
        return getDataManager().getLatitude();
    }

    @Override
    public String getLongitude() {
        return getDataManager().getLongitude();
    }

    @Override
    public String setGroupId() {
        return getDataManager().getGroupId();
    }
}
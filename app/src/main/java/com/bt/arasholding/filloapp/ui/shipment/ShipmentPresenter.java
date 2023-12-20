package com.bt.arasholding.filloapp.ui.shipment;

import com.androidnetworking.error.ANError;
import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.DataManager;
import com.bt.arasholding.filloapp.data.model.Barcode;
import com.bt.arasholding.filloapp.data.network.model.CargoMovementRequest;
import com.bt.arasholding.filloapp.data.network.model.Sefer;
import com.bt.arasholding.filloapp.data.network.model.TrackingRequest;
import com.bt.arasholding.filloapp.di.PerActivity;
import com.bt.arasholding.filloapp.ui.base.BasePresenter;
import com.bt.arasholding.filloapp.utils.AppConstants;

import java.util.List;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@PerActivity
public class ShipmentPresenter<V extends ShipmentMvpView> extends BasePresenter<V> implements ShipmentMvpPresenter<V> {

    private Sefer shipment;

    @Override
    public void setShipment(Sefer shipment) {
        this.shipment = shipment;
    }

    @Inject
    public ShipmentPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getShipmentInformationByShipmentBarcode(String shipmentBarcode, String islemTipi) {

        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            return;
        }

        getMvpView().showLoading();

        TrackingRequest request = new TrackingRequest(getDataManager().getAccessToken());
        request.setSeferBarkodu(shipmentBarcode);

        getCompositeDisposable().add(getDataManager()
                .doTrackingApiCall(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();

                    if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {

                        if (getDataManager().getSelectedCamera()) {
                            if (islemTipi.equals(AppConstants.DAGITIM))
                                getMvpView().openCargoBarcodeActivity(AppConstants.DAGITIM, response.getSefer());
                            else if (islemTipi.equals(AppConstants.HAT_YUKLEME))
                                getMvpView().openCargoBarcodeActivity(AppConstants.HAT_YUKLEME, response.getSefer());
                            else if (islemTipi.equals(AppConstants.YUKLEME))
                                getMvpView().openCargoBarcodeActivity(AppConstants.YUKLEME, response.getSefer());
                            else if (islemTipi.equals(AppConstants.INDIRME))
                                getMvpView().openCargoBarcodeActivity(AppConstants.INDIRME, response.getSefer());

                        } else {
                            getMvpView().showShipmentFragment(response.getSefer());
                        }

                    } else {
                        getMvpView().onError(response.getMessage());
                    }

                }, throwable -> {

                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();

                    // handle the login error here
                    if (throwable instanceof ANError) {
                        ANError anError = (ANError) throwable;
                        handleApiError(anError);
                        getMvpView().vibrate();
                    }
                }));
    }

    @Override
    public void getShipmentInformationByShipmentId(String shipmentId, String islemTipi) {

        getMvpView().showLoading();
        getMvpView().hideKeyboard();

        TrackingRequest request = new TrackingRequest(getDataManager().getAccessToken());
        request.setSeferId(shipmentId);

        getCompositeDisposable().add(getDataManager()
                .doTrackingApiCall(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();

                    if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {

                        if (getDataManager().getSelectedCamera()) {
                            if (islemTipi.equals(AppConstants.HAT_YUKLEME))
                                getMvpView().openCargoBarcodeActivity(AppConstants.HAT_YUKLEME, response.getSefer());
                            else if (islemTipi.equals(AppConstants.DAGITIM))
                                getMvpView().openCargoBarcodeActivity(AppConstants.DAGITIM, response.getSefer());
                            else if (islemTipi.equals(AppConstants.YUKLEME))
                                getMvpView().openCargoBarcodeActivity(AppConstants.YUKLEME, response.getSefer());
                            else if (islemTipi.equals(AppConstants.INDIRME))
                                getMvpView().openCargoBarcodeActivity(AppConstants.INDIRME, response.getSefer());
                        } else {
                            getMvpView().showShipmentFragment(response.getSefer());
                        }

                    } else {
                        getMvpView().onError(response.getMessage());
                    }

                }, throwable -> {

                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();

                    // handle the login error here
                    if (throwable instanceof ANError) {
                        ANError anError = (ANError) throwable;
                        handleApiError(anError);
                    }
                }));
    }

    @Override
    public void onViewPrepared() {

        getMvpView().updateToolbarTitle();
        getMvpView().decideProcessFragment();

        if (getDataManager().getSelectedCamera()) {
            getMvpView().showCameraScanner();
        } else {
            deleteBarcodes();
            getMvpView().showBluetoothScanner();
        }
    }

    @Override
    public void dagitim(String barcode) {
        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            return;
        }

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

                                    if (!isViewAttached()) {
                                        return;
                                    }

                                    Barcode mBarcode = new Barcode();
                                    mBarcode.setBarcode(barcode);
                                    mBarcode.setIslemTipi(11);
                                    mBarcode.setIslemSonucu(cargoMovementResponse.getStatusCode());
                                    mBarcode.setAciklama(cargoMovementResponse.getMessage());
                                    mBarcode.setAtf_no(cargoMovementResponse.getAtfNo());

                                    saveBarcode(mBarcode);

                                    if (cargoMovementResponse.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {
                                        getMvpView().incrementCount();
                                    } else {
                                        getMvpView().showMessage(cargoMovementResponse.getMessage());
                                        getMvpView().vibrate();
                                    }

                                }, throwable -> {

                                    if (!isViewAttached()) {
                                        return;
                                    }

                                    getMvpView().hideLoading();

                                    // handle the login error here
                                    if (throwable instanceof ANError) {
                                        ANError anError = (ANError) throwable;
                                        handleApiError(anError);
                                        getMvpView().vibrate();
                                    }
                                })
        );
    }

    @Override
    public void hatyukleme(String barcode) {

        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            return;
        }

        if (shipment == null) {
            getMvpView().showMessage("Sefer bulunamadı !");
            return;
        }

        if (getMvpView().isAlertDialogShowing()) {
            getMvpView().vibrate();
            return;
        }
        getMvpView().showLoading();

        CargoMovementRequest request = new CargoMovementRequest();
        request.setAccessToken(getDataManager().getAccessToken());
        request.setBarcode(barcode);
        request.setType(5);
        request.setSefer(String.valueOf(shipment.getSEFERID()));
        request.setHatYuklemeRotaCevabı(null);


//        saveBarcode(mBarcode);

        getCompositeDisposable().add(
                getDataManager().doCargoMovementApiCall(request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                cargoMovementResponse -> {

                                    if (!isViewAttached()) {
                                        return;
                                    }


                                    Barcode mBarcode = new Barcode();
                                    mBarcode.setBarcode(barcode);
                                    mBarcode.setIslemTipi(5);
                                    mBarcode.setIslemSonucu(cargoMovementResponse.getStatusCode());
                                    mBarcode.setAciklama(cargoMovementResponse.getMessage());
                                    mBarcode.setAtf_no(cargoMovementResponse.getAtfNo());

                                    saveBarcode(mBarcode);
//                                    Barcode bcode = getDataManager().getBarcodesByBarcode(barcode);
//                                    bcode.setIslemSonucu(cargoMovementResponse.getStatusCode());
//                                    bcode.setAciklama(cargoMovementResponse.getMessage());
//                                    bcode.setAtf_no(cargoMovementResponse.getAtfNo());
//
//                                    updateBarcodebyBarcodeType(bcode);

                                    if (cargoMovementResponse.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {
//                                        getMvpView().updateSayac();
                                        getMvpView().incrementCount();
                                        getMvpView().hideLoading();
                                    }
//                                    else if (cargoMovementResponse.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_NOT_FOUND))) {
//                                        getMvpView().hideLoading();
//                                        if (cargoMovementResponse.getHatYuklemeHataliRota() == true) {
//                                            getMvpView().hideLoading();
//                                            getMvpView().vibrate();
//                                            getMvpView().hatYuklemeRotaKontrolDialog(barcode,String.valueOf(shipment.getSEFERID()));
//
//                                        }
//
//                                    }
                                    else {
                                        if (cargoMovementResponse.getHatYuklemeHataliRota() == true) {
                                            getMvpView().hideLoading();
                                            getMvpView().vibrate();
                                            getMvpView().hatYuklemeRotaKontrolDialog(barcode, String.valueOf(shipment.getSEFERID()));

                                        } else {
                                            getMvpView().showMessage(cargoMovementResponse.getMessage());
                                            getMvpView().vibrate();
                                            getMvpView().hideLoading();
                                        }
                                    }
                                }, throwable -> {

                                    if (!isViewAttached()) {
                                        return;
                                    }

                                    getMvpView().hideLoading();

                                    // handle the login error here
                                    if (throwable instanceof ANError) {
                                        ANError anError = (ANError) throwable;
                                        handleApiError(anError);
                                        // getMvpView().showErrorMessage("İnternet yok uyarısı alıyorsanız okutmaya devam edebilirsiniz. Yükleme, indirme ve hat yükleme için internet olmadığında yapacağınız okutmalar internet erişimi sağlandığında sisteme düşecektir.");
                                        getMvpView().vibrate();
                                    }
                                })

        );

    }

    @Override
    public void yukleme(String barcode) {
        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            return;
        }

        if (shipment == null) {
            getMvpView().showMessage("Sefer bulunamadı !");
            return;
        }

        if (getMvpView().isAlertDialogShowing()) {
            getMvpView().vibrate();
            return;
        }
        getMvpView().showLoading();

        CargoMovementRequest request = new CargoMovementRequest();
        request.setAccessToken(getDataManager().getAccessToken());
        request.setBarcode(barcode);
        request.setType(1);
        request.setSefer(String.valueOf(shipment.getSEFERID()));

//        saveBarcode(mBarcode);

        getCompositeDisposable().add(
                getDataManager().doCargoMovementApiCall(request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                cargoMovementResponse -> {

                                    if (!isViewAttached()) {
                                        return;
                                    }

                                    Barcode mBarcode = new Barcode();
                                    mBarcode.setBarcode(barcode);
                                    mBarcode.setIslemTipi(1);
                                    mBarcode.setIslemSonucu(cargoMovementResponse.getStatusCode());
                                    mBarcode.setAciklama(cargoMovementResponse.getMessage());
                                    mBarcode.setAtf_no(cargoMovementResponse.getAtfNo());

                                    saveBarcode(mBarcode);

                                    getMvpView().hideLoading();

                                    if (cargoMovementResponse.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {
                                        getMvpView().incrementCount();
                                    } else {
                                        getMvpView().showMessage(cargoMovementResponse.getMessage());
                                        getMvpView().vibrate();
                                    }

                                }, throwable -> {

                                    if (!isViewAttached()) {
                                        return;
                                    }

                                    getMvpView().hideLoading();

                                    // handle the login error here
                                    if (throwable instanceof ANError) {
                                        ANError anError = (ANError) throwable;
                                        handleApiError(anError);
                                        getMvpView().vibrate();
                                    }
                                })
        );
    }

    @Override
    public void indirme(String barcode) {
        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            return;
        }

        if (shipment == null) {
            getMvpView().showMessage("Sefer bulunamadı !");
            return;
        }

        if (getMvpView().isAlertDialogShowing()) {
            getMvpView().vibrate();
            return;
        }
        getMvpView().showLoading();

        CargoMovementRequest request = new CargoMovementRequest();
        request.setAccessToken(getDataManager().getAccessToken());
        request.setBarcode(barcode);
        request.setType(2);
        request.setSefer(String.valueOf(shipment.getSEFERID()));

//        saveBarcode(mBarcode);

        getCompositeDisposable().add(
                getDataManager().doCargoMovementApiCall(request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                cargoMovementResponse -> {

                                    if (!isViewAttached()) {
                                        return;
                                    }

                                    Barcode mBarcode = new Barcode();
                                    mBarcode.setBarcode(barcode);
                                    mBarcode.setIslemTipi(2);
                                    mBarcode.setIslemSonucu(cargoMovementResponse.getStatusCode());
                                    mBarcode.setAciklama(cargoMovementResponse.getMessage());
                                    mBarcode.setAtf_no(cargoMovementResponse.getAtfNo());

                                    saveBarcode(mBarcode);

                                    getMvpView().hideLoading();

                                    if (cargoMovementResponse.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {
                                        getMvpView().incrementCount();
                                    } else {
                                        getMvpView().showMessage(cargoMovementResponse.getMessage());
                                        getMvpView().vibrate();
                                    }

                                }, throwable -> {

                                    if (!isViewAttached()) {
                                        return;
                                    }

                                    getMvpView().hideLoading();

                                    // handle the login error here
                                    if (throwable instanceof ANError) {
                                        ANError anError = (ANError) throwable;
                                        handleApiError(anError);
                                        getMvpView().vibrate();
                                    }
                                })
        );
    }

    @Override
    public void hatyuklemeDevam(String barcode, String shipment, String cevap) {

        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            return;
        }

        if (shipment == null) {
            getMvpView().showMessage("Sefer bulunamadı !");
            return;
        }

        getMvpView().showLoading();

        CargoMovementRequest request = new CargoMovementRequest();
        request.setAccessToken(getDataManager().getAccessToken());
        request.setBarcode(barcode);
        request.setType(5);
        request.setSefer(String.valueOf(shipment));
        request.setHatYuklemeRotaCevabı(cevap);


//        saveBarcode(mBarcode);

        getCompositeDisposable().add(
                getDataManager().doCargoMovementApiCall(request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                cargoMovementResponse -> {

                                    if (!isViewAttached()) {
                                        return;
                                    }


                                    Barcode mBarcode = new Barcode();
                                    mBarcode.setBarcode(barcode);
                                    mBarcode.setIslemTipi(5);
                                    mBarcode.setIslemSonucu(cargoMovementResponse.getStatusCode());
                                    mBarcode.setAciklama(cargoMovementResponse.getMessage());
                                    mBarcode.setAtf_no(cargoMovementResponse.getAtfNo());

                                    saveBarcode(mBarcode);
//                                    Barcode bcode = getDataManager().getBarcodesByBarcode(barcode);
//                                    bcode.setIslemSonucu(cargoMovementResponse.getStatusCode());
//                                    bcode.setAciklama(cargoMovementResponse.getMessage());
//                                    bcode.setAtf_no(cargoMovementResponse.getAtfNo());
//
//                                    updateBarcodebyBarcodeType(bcode);

                                    if (cargoMovementResponse.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {
//                                        getMvpView().updateSayac();
                                        getMvpView().incrementCount();
                                        getMvpView().hideLoading();
                                    }
//                                    else if (cargoMovementResponse.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_NOT_FOUND))) {
//                                        getMvpView().hideLoading();
//                                        if (cargoMovementResponse.getHatYuklemeHataliRota() == true) {
//                                            getMvpView().hideLoading();
//                                            String cevap = getMvpView().hatYuklemeRotaKontrolDialog(barcode,String.valueOf(shipment.getSEFERID()));
//
//                                        }
//
//
//                                    }
                                    else {
                                        getMvpView().showMessage(cargoMovementResponse.getMessage());
                                        getMvpView().vibrate();
                                        getMvpView().hideLoading();
                                    }
                                }, throwable -> {

                                    if (!isViewAttached()) {
                                        return;
                                    }

                                    getMvpView().hideLoading();

                                    // handle the login error here
                                    if (throwable instanceof ANError) {
                                        ANError anError = (ANError) throwable;
                                        handleApiError(anError);
                                        // getMvpView().showErrorMessage("İnternet yok uyarısı alıyorsanız okutmaya devam edebilirsiniz. Yükleme, indirme ve hat yükleme için internet olmadığında yapacağınız okutmalar internet erişimi sağlandığında sisteme düşecektir.");
                                        getMvpView().vibrate();
                                    }
                                })

        );

    }// TODO hat yüklemeye shipment ıd eklenecek

    @Override
    public void deleteBarcodes() {
        getCompositeDisposable().add(getDataManager()
                .deleteBarcodesByType(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        refreshList(3);
                    }
                }, throwable -> {

                }));
    }

    private void saveBarcode(Barcode barcode) {
        getCompositeDisposable().add(getDataManager()
                .saveBarcode(barcode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        refreshList(barcode.getIslemTipi());
                    }
                }, throwable -> {

                }));
    }

    public void refreshList(int islemTipi) {
        getCompositeDisposable().add(getDataManager()
                .getBarcodesByIslemTipi(islemTipi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Barcode>>() {
                    @Override
                    public void accept(List<Barcode> barcodeList) throws Exception {
                        getMvpView().updateBarcodeList(barcodeList);
                    }
                }, throwable -> {

                }));
    }

    private void updateBarcodebyBarcodeType(Barcode barcode) {
        getCompositeDisposable().add(getDataManager()
                .updateBarcodebyBarcodeType(barcode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        refreshList(barcode.getIslemTipi());
                    }
                }, throwable -> {

                }));
    }
}
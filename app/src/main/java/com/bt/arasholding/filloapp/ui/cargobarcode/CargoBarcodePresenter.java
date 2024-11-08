package com.bt.arasholding.filloapp.ui.cargobarcode;

import com.androidnetworking.error.ANError;
import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.DataManager;
import com.bt.arasholding.filloapp.data.model.Barcode;
import com.bt.arasholding.filloapp.data.network.model.CargoMovementRequest;
import com.bt.arasholding.filloapp.ui.base.BasePresenter;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CargoBarcodePresenter<V extends CargoBarcodeMvpView> extends BasePresenter<V> implements CargoBarcodeMvpPresenter<V> {

    @Inject
    public CargoBarcodePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void yukleme(String barcode) {

//        if (!getMvpView().isNetworkConnected()) {
//            getMvpView().showMessage(R.string.connection_error);
//            getMvpView().vibrate();
//            return;
//        }

        getMvpView().showLoading();

        CargoMovementRequest request = new CargoMovementRequest();
        request.setAccessToken(getDataManager().getAccessToken());
        request.setBarcode(barcode);
        request.setType(1);
        request.setSefer(getDataManager().getCurrentShipmentCode());
//
        if (!getMvpView().isNetworkConnected()) {
            Barcode mBarcode = new Barcode();
            mBarcode.setBarcode(barcode);
            mBarcode.setIslemTipi(1);

            saveBarcode(mBarcode);
        }

        getCompositeDisposable().add(
                getDataManager().doCargoMovementApiCall(request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                cargoMovementResponse -> {

                                    if (!isViewAttached()) {
                                        return;
                                    }

//                                    Barcode bcode = getDataManager().getBarcodesByBarcode(barcode);
//                                    bcode.setIslemSonucu(cargoMovementResponse.getStatusCode());
//                                    bcode.setAciklama(cargoMovementResponse.getMessage());
//                                    bcode.setAtf_no(cargoMovementResponse.getAtfNo());
//
//                                    updateBarcodebyBarcodeType(bcode);

                                    getMvpView().hideLoading();

                                    if (cargoMovementResponse.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {
                                        getMvpView().updateSayac();
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
                                        getMvpView().updateSayac();
                                        getMvpView().showErrorMessage("İnternet yok uyarısı alıyorsanız okutmaya devam edebilirsiniz. Yükleme, indirme ve hat yükleme için internet olmadığında yapacağınız okutmalar internet erişimi sağlandığında sisteme düşecektir.");
                                        getMvpView().vibrate();
                                    }
                                })
        );
    }

    @Override
    public void indirme(String barcode) {

//        if (!getMvpView().isNetworkConnected()) {
//            getMvpView().showMessage(R.string.connection_error);
//            getMvpView().vibrate();
//            return;
//        }

        getMvpView().showLoading();

        CargoMovementRequest request = new CargoMovementRequest();
        request.setAccessToken(getDataManager().getAccessToken());
        request.setBarcode(barcode);
        request.setType(2);
        request.setSefer(getDataManager().getCurrentShipmentCode());

        if (!getMvpView().isNetworkConnected()) {
            Barcode mBarcode = new Barcode();
            mBarcode.setBarcode(barcode);
            mBarcode.setIslemTipi(2);

            saveBarcode(mBarcode);
        }

        getCompositeDisposable().add(
                getDataManager().doCargoMovementApiCall(request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                cargoMovementResponse -> {

                                    if (!isViewAttached()) {
                                        return;
                                    }
//                                    Barcode bcode = getDataManager().getBarcodesByBarcode(barcode);
//                                    bcode.setIslemSonucu(cargoMovementResponse.getStatusCode());
//                                    bcode.setAciklama(cargoMovementResponse.getMessage());
//                                    bcode.setAtf_no(cargoMovementResponse.getAtfNo());
//
//                                    updateBarcodebyBarcodeType(bcode);

                                    getMvpView().hideLoading();

                                    if (cargoMovementResponse.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {
                                        getMvpView().updateSayac();
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
                                        getMvpView().updateSayac();
                                        getMvpView().showErrorMessage("İnternet yok uyarısı alıyorsanız okutmaya devam edebilirsiniz. Yükleme ve indirme için internet olmadığında yapacağınız okutmalar internet erişimi sağlandığında sisteme düşecektir.");
                                        getMvpView().vibrate();
                                    }
                                })
        );

    }

    @Override
    public void yuklemewithsefer(String barcode, int shipmentId) {
        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            return;
        }

        if (shipmentId == 0) {
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
        request.setSefer(String.valueOf(shipmentId));

//        saveBarcode(mBarcode);
        Gson gson = new Gson();
        String json = gson.toJson(request);

        getCompositeDisposable().add(
                getDataManager().doCargoMovementApiCall(request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                cargoMovementResponse -> {

                                    if (!isViewAttached()) {
                                        return;
                                    }

//                                    Barcode mBarcode = new Barcode();
//                                    mBarcode.setBarcode(barcode);
//                                    mBarcode.setIslemTipi(1);
//                                    mBarcode.setIslemSonucu(cargoMovementResponse.getStatusCode());
//                                    mBarcode.setAciklama(cargoMovementResponse.getMessage());
//                                    mBarcode.setAtf_no(cargoMovementResponse.getAtfNo());
//
//                                    saveBarcode(mBarcode);
                                    getMvpView().hideLoading();
                                    if (cargoMovementResponse.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {
                                        getMvpView().updateSayac();
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
    public void indirmewithsefer(String barcode, int shipmentId) {
        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            return;
        }

        if (shipmentId == 0) {
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
        request.setSefer(String.valueOf(shipmentId));

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
                                        getMvpView().updateSayac();
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
    public void hatyukleme(String barcode, int shipmentId) {

        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            getMvpView().vibrate();
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
        request.setSefer(String.valueOf(shipmentId));

//        Barcode mBarcode = new Barcode();
//        mBarcode.setBarcode(barcode);
//        mBarcode.setIslemTipi(5);

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

//                                    Barcode bcode = getDataManager().getBarcodesByBarcode(barcode);
//                                    bcode.setIslemSonucu(cargoMovementResponse.getStatusCode());
//                                    bcode.setAciklama(cargoMovementResponse.getMessage());
//                                    bcode.setAtf_no(cargoMovementResponse.getAtfNo());
//
//                                    updateBarcodebyBarcodeType(bcode);

                                    getMvpView().hideLoading();

                                    if (cargoMovementResponse.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {
                                        getMvpView().updateSayac();
                                    }
//                                    else if (cargoMovementResponse.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_NOT_FOUND))) {
//                                        getMvpView().hideLoading();
//                                        if (cargoMovementResponse.getHatYuklemeHataliRota() == true) {
//                                            getMvpView().hideLoading();
//                                            getMvpView().vibrate();
//                                            getMvpView().hatYuklemeRotaKontrolDialog(barcode,String.valueOf(shipmentId));
//
//                                        }
//
//                                    }
                                    else {
                                        if (cargoMovementResponse.getHatYuklemeHataliRota() == true) {
                                            getMvpView().hideLoading();
                                            getMvpView().vibrate();
                                            getMvpView().hatYuklemeRotaKontrolDialog(barcode, String.valueOf(shipmentId));

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
//                                        getMvpView().showErrorMessage("İnternet yok uyarısı alıyorsanız okutmaya devam edebilirsiniz. Yükleme, indirme ve hat yükleme için internet olmadığında yapacağınız okutmalar internet erişimi sağlandığında sisteme düşecektir.");
                                        getMvpView().vibrate();
                                    }
                                })
        );

    }// TODO hat yüklemeye shipment ıd eklenecek

    @Override
    public void hatyuklemeDevam(String barcode, String shipment, String cevap) {
        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            getMvpView().vibrate();
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
                                        getMvpView().updateSayac();
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
    }

    @Override
    public void dagitim(String barcode, int shipmentId) {

        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            return;
        }

        getMvpView().showLoading();

        CargoMovementRequest request = new CargoMovementRequest();
        request.setAccessToken(getDataManager().getAccessToken());
        request.setBarcode(barcode);
        request.setType(11);
        request.setSefer(String.valueOf(shipmentId));

        getCompositeDisposable().add(
                getDataManager().doCargoMovementApiCall(request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response -> {

                                    if (!isViewAttached()) {
                                        return;
                                    }

                                    getMvpView().hideLoading();

                                    if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {
                                        getMvpView().updateSayac();
                                    } else {
                                        getMvpView().showMessage(response.getMessage());
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

    private void saveBarcode(Barcode barcode) {
        getCompositeDisposable().add(getDataManager()
                .saveBarcode(barcode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
//                        refreshList(barcode.getIslemTipi());
                    }
                }, throwable -> {

                }));
    }

//    private void updateBarcodebyBarcodeType(Barcode barcode) {
//        getCompositeDisposable().add(getDataManager()
//                .updateBarcodebyBarcodeType(barcode)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Boolean>() {
//                    @Override
//                    public void accept(Boolean aBoolean) throws Exception {
//                        refreshList(barcode.getIslemTipi());
//                    }
//                }, throwable -> {
//
//                }));
//    }

//    public void refreshList(int islemTipi) {
//        getCompositeDisposable().add(getDataManager()
//                .getBarcodesByIslemTipi(islemTipi)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<List<Barcode>>() {
//                    @Override
//                    public void accept(List<Barcode> barcodeList) throws Exception {
////                        getMvpView().updateBarcodeList(barcodeList);
//                    }
//                }, throwable -> {
//
//                }));
//    }

//    public void refreshList(int islemTipi) {
//        getCompositeDisposable().add(getDataManager()
//                .getBarcodesByIslemTipi(islemTipi)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<List<Barcode>>() {
//                    @Override
//                    public void accept(List<Barcode> barcodeList) throws Exception {
//                        getMvpView().updateBarcodeList(barcodeList);
//                    }
//                }, throwable -> {
//
//                }));
//    }
}
package com.bt.arasholding.filloapp.ui.lazer;

import com.androidnetworking.error.ANError;
import com.bt.arasholding.filloapp.data.DataManager;
import com.bt.arasholding.filloapp.data.model.Barcode;
import com.bt.arasholding.filloapp.data.network.model.CargoMovementRequest;
import com.bt.arasholding.filloapp.ui.base.BasePresenter;
import com.bt.arasholding.filloapp.ui.cargobarcode.CargoBarcodeMvpPresenter;
import com.bt.arasholding.filloapp.ui.cargobarcode.CargoBarcodeMvpView;

import java.util.List;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LazerPresenter<V extends LazerMvpView> extends BasePresenter<V> implements LazerMvpPresenter<V> {

    @Inject
    public LazerPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
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
        Barcode mBarcode = new Barcode();
        mBarcode.setBarcode(barcode);
        mBarcode.setIslemTipi(1);

        saveBarcode(mBarcode);

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
                                       // getMvpView().showErrorMessage("İnternet yok uyarısı alıyorsanız okutmaya devam edebilirsiniz. Yükleme, indirme ve hat yükleme için internet olmadığında yapacağınız okutmalar internet erişimi sağlandığında sisteme düşecektir.");
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

        Barcode mBarcode = new Barcode();
        mBarcode.setBarcode(barcode);
        mBarcode.setIslemTipi(2);

        saveBarcode(mBarcode);

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
                                        // getMvpView().showErrorMessage("İnternet yok uyarısı alıyorsanız okutmaya devam edebilirsiniz. Yükleme ve indirme için internet olmadığında yapacağınız okutmalar internet erişimi sağlandığında sisteme düşecektir.");
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
//                        getMvpView().updateBarcodeList(barcodeList);
                    }
                }, throwable -> {

                }));
    }
}

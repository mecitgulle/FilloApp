package com.bt.arasholding.filloapp.ui.textbarcode;

import com.androidnetworking.error.ANError;
import com.bt.arasholding.filloapp.data.DataManager;
import com.bt.arasholding.filloapp.data.model.Barcode;
import com.bt.arasholding.filloapp.data.network.model.CargoMovementRequest;
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
public class TextBarcodePresenter<V extends TextBarcodeMvpView> extends BasePresenter<V> implements TextBarcodeMvpPresenter<V> {

    private static final String TAG = "TextBarcodePresenter";

    @Inject
    public TextBarcodePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void onViewPrepared(String islemTipi) {
        getMvpView().updateToolbarTitle();
//        getMvpView().checkBluetooth();

        if (islemTipi.equals(AppConstants.YUKLEME)) {
            deleteBarcodesByTypes(1);
        } else if (islemTipi.equals(AppConstants.INDIRME)) {
            deleteBarcodesByTypes(2);
        } else if (islemTipi.equals(AppConstants.HAT_YUKLEME)) {
            deleteBarcodesByTypes(5);
        }
    }

    @Override
    public void yukleme(String barcode) {

//        if (!getMvpView().isNetworkConnected()) {
//            getMvpView().showMessage(R.string.connection_error);
//            getMvpView().vibrate();
//            return;
//        }


        CargoMovementRequest request = new CargoMovementRequest();
        request.setAccessToken(getDataManager().getAccessToken());
        request.setBarcode(barcode);
        request.setType(1);
        request.setSefer(getDataManager().getCurrentShipmentCode());
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
                                    Barcode mBarcode = new Barcode();
                                    mBarcode.setBarcode(barcode);
                                    mBarcode.setIslemTipi(1);
                                    mBarcode.setIslemSonucu(cargoMovementResponse.getStatusCode());
                                    mBarcode.setAciklama(cargoMovementResponse.getMessage());
                                    mBarcode.setAtf_no(cargoMovementResponse.getAtfNo());

                                    saveBarcode(mBarcode);

                                    if (cargoMovementResponse.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {
                                        getMvpView().updateCount(cargoMovementResponse.getYuklenecekParcaAdeti());
                                    } else {
                                        getMvpView().showMessage(cargoMovementResponse.getMessage());
                                        getMvpView().vibrate();
                                    }
                                }, throwable -> {

                                    if (!isViewAttached()) {
                                        return;
                                    }

                                    // handle the login error here
                                    if (throwable instanceof ANError) {
                                        ANError anError = (ANError) throwable;
                                        handleApiError(anError);
                                        getMvpView().updateCount("0");
                                        getMvpView().showErrorMessage("İnternet yok uyarısı alıyorsanız okutmaya devam edebilirsiniz. Yükleme ve indirme için internet olmadığında yapacağınız okutmalar internet erişimi sağlandığında sisteme düşecektir.");
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
                                    Barcode mBarcode = new Barcode();
                                    mBarcode.setBarcode(barcode);
                                    mBarcode.setIslemTipi(2);
                                    mBarcode.setIslemSonucu(cargoMovementResponse.getStatusCode());
                                    mBarcode.setAciklama(cargoMovementResponse.getMessage());
                                    mBarcode.setAtf_no(cargoMovementResponse.getAtfNo());

                                    saveBarcode(mBarcode);

                                    if (cargoMovementResponse.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {
                                        getMvpView().updateCount(cargoMovementResponse.getYuklenecekParcaAdeti());
                                    } else {
                                        getMvpView().showMessage(cargoMovementResponse.getMessage());
                                        getMvpView().vibrate();
                                    }

                                }, throwable -> {

                                    if (!isViewAttached()) {
                                        return;
                                    }

                                    // handle the login error here
                                    if (throwable instanceof ANError) {
                                        ANError anError = (ANError) throwable;
                                        handleApiError(anError);
                                        getMvpView().updateCount("0");
                                        getMvpView().showErrorMessage("İnternet yok uyarısı alıyorsanız okutmaya devam edebilirsiniz. Yükleme ve indirme için internet olmadığında yapacağınız okutmalar internet erişimi sağlandığında sisteme düşecektir.");
                                        getMvpView().vibrate();
                                    }
                                })
        );

    }

    @Override
    public void hatYukleme(String barcode) {

//        if (!getMvpView().isNetworkConnected()) {
//            getMvpView().showMessage(R.string.connection_error);
//            return;
//        }

//        CargoMovementRequest request = new CargoMovementRequest();
//        request.setAccessToken(getDataManager().getAccessToken());
//        request.setBarcode(barcode);
//        request.setType(5);
//        request.setSefer(getDataManager().getCurrentShipmentCode());
//
//        Barcode mBarcode = new Barcode();
//        mBarcode.setBarcode(barcode);
//        mBarcode.setIslemTipi(5);
//
//        saveBarcode(mBarcode);
//
//        getCompositeDisposable().add(
//                getDataManager().doCargoMovementApiCall(request)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                cargoMovementResponse -> {
//
//                                    if (!isViewAttached()) {
//                                        return;
//                                    }
//
//                                    Barcode bcode = getDataManager().getBarcodesByBarcode(barcode);
//                                    bcode.setIslemSonucu(cargoMovementResponse.getStatusCode());
//                                    bcode.setAciklama(cargoMovementResponse.getMessage());
//                                    bcode.setAtf_no(cargoMovementResponse.getAtfNo());
//
//                                    updateBarcodebyBarcodeType(bcode);
//
//                                    if (cargoMovementResponse.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {
//                                        getMvpView().updateCount(cargoMovementResponse.getYuklenecekParcaAdeti());
//                                    } else {
//                                        getMvpView().showMessage(cargoMovementResponse.getMessage());
//                                        getMvpView().vibrate();
//                                    }
//
//                                }, throwable -> {
//
//                                    if (!isViewAttached()) {
//                                        return;
//                                    }
//
//                                    getMvpView().hideLoading();
//
//                                    // handle the login error here
//                                    if (throwable instanceof ANError) {
//                                        ANError anError = (ANError) throwable;
//                                        handleApiError(anError);
////                                        getMvpView().showErrorMessage("İnternet yok uyarısı alıyorsanız okutmaya devam edebilirsiniz. Yükleme, indirme ve hat yükleme için internet olmadığında yapacağınız okutmalar internet erişimi sağlandığında sisteme düşecektir.");
//                                        getMvpView().vibrate();
//                                    }
//                                })
//        );
    }
    @Override
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
//public void refreshList(int islemTipi) {
//    try {
//        List<Barcode> barcodeList = getDataManager().getBarcodesByIslemTipi(islemTipi);
//        // UI'yi güncellemek için gerekli işlemleri yap
//        getMvpView().updateBarcodeList(barcodeList);
//    } catch (Exception e) {
//        // Hata durumunu ele al
//    }
//}

    @Override
    public void sendToServer(String barcode, String islemTipi) {
        if (islemTipi.equals(AppConstants.YUKLEME)) {
            yukleme(barcode);
        } else if (islemTipi.equals(AppConstants.INDIRME)) {
            indirme(barcode);
        } else if (islemTipi.equals(AppConstants.HAT_YUKLEME)) {
            hatYukleme(barcode);
        }
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

    private void deleteBarcodesByTypes(int islemTipi) {
        getCompositeDisposable().add(getDataManager()
                .deleteBarcodesByType(islemTipi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        refreshList(islemTipi);
                    }
                }, throwable -> {
                    if (!isViewAttached()) {
                        return;
                    }

                    // handle the login error here
                    if (throwable instanceof ANError) {
                        ANError anError = (ANError) throwable;
                        handleApiError(anError);
                    }
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
}
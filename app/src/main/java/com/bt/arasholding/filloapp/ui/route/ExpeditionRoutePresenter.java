package com.bt.arasholding.filloapp.ui.route;

import com.androidnetworking.error.ANError;
import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.DataManager;
import com.bt.arasholding.filloapp.data.network.model.CargoMovementRequest;
import com.bt.arasholding.filloapp.data.network.model.ExpeditionRouteRequest;
import com.bt.arasholding.filloapp.ui.base.BasePresenter;
import com.bt.arasholding.filloapp.ui.delivery.DeliveryMvpPresenter;
import com.bt.arasholding.filloapp.ui.delivery.DeliveryMvpView;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ExpeditionRoutePresenter<V extends ExpeditionRouteMvpView> extends BasePresenter<V> implements ExpeditionRouteMvpPresenter<V> {

    @Inject
    public ExpeditionRoutePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }


    @Override
    public void onViewPrepared() {
        GetExpeditionRoute();
    }

    @Override
    public void GetExpeditionRoute() {

        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            getMvpView().vibrate();
            return;
        }

        getMvpView().showLoading();
        ExpeditionRouteRequest request = new ExpeditionRouteRequest();
        request.setToken(getDataManager().getAccessToken());
//        request.setKurye("EROL TEKNE");
//        request.setCikisSubeKodu("981");

        getCompositeDisposable().add(
                getDataManager().getExpeditionRoute(request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response -> {

                                    if (!isViewAttached()) {
                                        return;
                                    }
//                                    Barcode bcode = getDataManager().getBarcodesByBarcode(barcode);
//                                    bcode.setIslemSonucu(cargoMovementResponse.getStatusCode());
//                                    bcode.setAciklama(cargoMovementResponse.getMessage());
//                                    bcode.setAtf_no(cargoMovementResponse.getAtfNo());
//
//                                    updateBarcodebyBarcodeType(bcode);
//                                    saveBarcode(mBarcode);

                                    if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {
                                        getMvpView().updateBarcodeList(response.getSeferler());
                                    } else {
                                        getMvpView().showMessage(response.getMessage());
                                        getMvpView().vibrate();
                                    }
                                    getMvpView().hideLoading();
                                }, throwable -> {

                                    if (!isViewAttached()) {
                                        getMvpView().hideLoading();
                                        return;
                                    }

                                    // handle the login error here
                                    if (throwable instanceof ANError) {
                                        ANError anError = (ANError) throwable;
                                        handleApiError(anError);
//                                            getMvpView().showErrorMessage("İnternet yok uyarısı alıyorsanız okutmaya devam edebilirsiniz. Yükleme ve indirme için internet olmadığında yapacağınız okutmalar internet erişimi sağlandığında sisteme düşecektir.");
                                        getMvpView().vibrate();
                                        getMvpView().hideLoading();
                                    }
                                })
        );
    }


}

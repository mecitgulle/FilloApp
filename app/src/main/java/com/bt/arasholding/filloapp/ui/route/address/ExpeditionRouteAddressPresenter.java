package com.bt.arasholding.filloapp.ui.route.address;

import com.androidnetworking.error.ANError;
import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.DataManager;
import com.bt.arasholding.filloapp.data.network.model.ExpeditionRouteAddressRequest;
import com.bt.arasholding.filloapp.data.network.model.ExpeditionRouteRequest;
import com.bt.arasholding.filloapp.ui.base.BasePresenter;
import com.bt.arasholding.filloapp.ui.route.ExpeditionRouteMvpPresenter;
import com.bt.arasholding.filloapp.ui.route.ExpeditionRouteMvpView;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ExpeditionRouteAddressPresenter<V extends ExpeditionRouteAddressMvpView> extends BasePresenter<V> implements ExpeditionRouteAddressMvpPresenter<V> {

    @Inject
    public ExpeditionRouteAddressPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void onViewPrepared(String seferId) {
        GetExpeditionRouteAddress(seferId);
    }

    @Override
    public void GetExpeditionRouteAddress(String seferId) {

        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            getMvpView().vibrate();
            return;
        }
        getMvpView().showLoading();

        ExpeditionRouteAddressRequest request = new ExpeditionRouteAddressRequest();
        request.setToken(getDataManager().getAccessToken());
        request.setSeferId(Long.parseLong(seferId));

        getCompositeDisposable().add(
                getDataManager().getExpeditionRouteAddress(request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response -> {

                                    if (!isViewAttached()) {
                                        getMvpView().hideLoading();
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
                                        getMvpView().updateBarcodeList(response.getAdresler());
                                    } else {
                                        getMvpView().showMessage(response.getMessage());
                                        getMvpView().vibrate();
                                    }
                                    getMvpView().hideLoading();
                                }, throwable -> {

                                    if (!isViewAttached()) {
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
    @Override
    public String getLatitude() {
        return getDataManager().getLatitude();
    }

    @Override
    public String getLongitude() {
        return getDataManager().getLongitude();
    }

}

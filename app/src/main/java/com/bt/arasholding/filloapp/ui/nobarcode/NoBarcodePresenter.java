package com.bt.arasholding.filloapp.ui.nobarcode;

import com.androidnetworking.error.ANError;
import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.DataManager;
import com.bt.arasholding.filloapp.data.model.Barcode;
import com.bt.arasholding.filloapp.data.network.model.AtfModel;
import com.bt.arasholding.filloapp.data.network.model.DeliverCargoImageUploadResponse;
import com.bt.arasholding.filloapp.data.network.model.DeliverMultipleCargoModel;
import com.bt.arasholding.filloapp.data.network.model.DeliveredCargoRequest;
import com.bt.arasholding.filloapp.data.network.model.Deneme;
import com.bt.arasholding.filloapp.data.network.model.ExpeditionRouteRequest;
import com.bt.arasholding.filloapp.data.network.model.NoBarcodeSaveRequest;
import com.bt.arasholding.filloapp.data.network.model.NoBarcodeSaveResponse;
import com.bt.arasholding.filloapp.ui.base.BasePresenter;
import com.bt.arasholding.filloapp.utils.AppConstants;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NoBarcodePresenter<V extends NoBarcodeMvpView> extends BasePresenter<V> implements NoBarcodeMvpPresenter<V> {

    @Inject
    public NoBarcodePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void showNewNoBarcodeDialog() {

    }

    @Override
    public void getAllCustomer() {
        getMvpView().showLoading();
        Deneme deneme = new Deneme();
        deneme.setToken(getDataManager().getAccessToken());

        getCompositeDisposable().add(getDataManager()
                .getAllCustomerList(deneme)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();

                    if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {
                        getMvpView().setSpinner(response.getOptionList());

                    } else {
                        getMvpView().showMessage(response.getMessage());
                    }

                }, throwable ->
                {

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
    public void saveNoBarcode(NoBarcodeSaveRequest noBarcodeParam) {

        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            return;
        }
        noBarcodeParam.setToken(getDataManager().getAccessToken());

        getMvpView().showLoading();

        Gson gson = new Gson();
        String json = gson.toJson(noBarcodeParam);

        getCompositeDisposable().add(getDataManager()
                .doNoBarcodeApiCall(noBarcodeParam)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();

                    if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {

                        if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {

                            getMvpView().showMessage("Barkodsuz Kargo Kaydı Başarılı");

                        } else {
                            getMvpView().showMessage(response.getMessage());
                        }


                    } else {
                        getMvpView().showMessage(response.getMessage());
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
    public void onViewPrepared() {
        GetNoBarcodeCargo();
    }

    @Override
    public void GetNoBarcodeCargo() {

        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            getMvpView().vibrate();
            return;
        }

        getMvpView().showLoading();
        Deneme deneme = new Deneme();
        deneme.setToken(getDataManager().getAccessToken());

        getCompositeDisposable().add(
                getDataManager().getNoBarcodeList(deneme)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response -> {

                                    if (!isViewAttached()) {
                                        return;
                                    }

                                    if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {
                                        getMvpView().updateNoBarcodeList(response.getCargoNoBarcodeList());
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

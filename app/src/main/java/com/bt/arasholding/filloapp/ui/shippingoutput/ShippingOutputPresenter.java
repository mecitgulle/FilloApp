package com.bt.arasholding.filloapp.ui.shippingoutput;

import com.androidnetworking.error.ANError;
import com.bt.arasholding.filloapp.data.DataManager;
import com.bt.arasholding.filloapp.data.network.model.TrackingRequest;
import com.bt.arasholding.filloapp.ui.base.BasePresenter;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ShippingOutputPresenter<V extends ShippingOutputMvpView> extends BasePresenter<V> implements ShippingOutputMvpPresenter<V> {

    @Inject
    public ShippingOutputPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void onShippingTrackingClick(String seferNo) {

        getMvpView().hideKeyboard();
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .doTrackingApiCall(new TrackingRequest(seferNo,getDataManager().getAccessToken()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();

                    if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))){
                        getMvpView().updatePlaka(response.getSefer().getPLAKA());
                        getMvpView().updateVarisSube(response.getSefer().getVARISSUBE());
                    }else{
                        getMvpView().onErrorShippingOutput(response.getMessage());
                    }

                }, throwable -> {

                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();

                    // handle the login error here
                    if (throwable instanceof ANError) {
                        ANError anError = (ANError) throwable;
                        handleApiErrorShippingOutput(anError);
                    }
                }));
    }

    @Override
    public void onQbAracCikisClick(String seferNo) {

        getMvpView().hideKeyboard();
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .doShippingOutputApiCall(new TrackingRequest(seferNo,getDataManager().getAccessToken()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();
                    getMvpView().showMessage(response.getMessage());
                    getMvpView().updatePlaka("");
                    getMvpView().updateVarisSube("");

                }, throwable -> {

                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();

                    // handle the login error here
                    if (throwable instanceof ANError) {
                        ANError anError = (ANError) throwable;
                        handleApiErrorShippingOutput(anError);
                    }
                }));
    }
}
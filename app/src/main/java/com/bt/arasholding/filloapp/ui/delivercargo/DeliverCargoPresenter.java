package com.bt.arasholding.filloapp.ui.delivercargo;

import com.androidnetworking.error.ANError;
import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.DataManager;
import com.bt.arasholding.filloapp.data.network.model.CargoDetail;
import com.bt.arasholding.filloapp.data.network.model.CargoDetailRequest;
import com.bt.arasholding.filloapp.data.network.model.DeliverMultipleInfo;
import com.bt.arasholding.filloapp.data.network.model.DeliveredCargoRequest;
import com.bt.arasholding.filloapp.data.network.model.Deneme;
import com.bt.arasholding.filloapp.ui.base.BasePresenter;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DeliverCargoPresenter<V extends DeliverCargoMvpView> extends BasePresenter<V> implements DeliverCargoMvpPresenter<V> {

    private static final String TAG = "DeliverCargoPresenter";

    @Inject
    public DeliverCargoPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void onViewPrepared(String trackId) {

        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            return;
        }

        getMvpView().showLoading();

        CargoDetailRequest request = new CargoDetailRequest(trackId, getDataManager().getAccessToken());

        request.setTeslimatKapatma(false);

        getCompositeDisposable().add(getDataManager()
                .doCargoDetailApiCall(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();

                    if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {

                        CargoDetail cargoDetail = response.getCargoDetails().get(0);

                        if (cargoDetail != null) {
                            getMvpView().updateCargoCount(cargoDetail.getToplamParca());
                            getMvpView().updateReceivedName(cargoDetail.getAlici());
                            getMvpView().updateTrackId(cargoDetail.getAtfNo());
                            getMvpView().updateAtfId(cargoDetail.getAtfId());
                            getMvpView().updateAtfAdet("1");
                            getMvpView().updateTeslimTarihi(cargoDetail.getTeslimTarihi());
                            getMvpView().updateEvrakDonusluVisibility(cargoDetail.getEvrakDonuslu().equals("EVET"));
                        } else {
                            getMvpView().showMessage(response.getMessage());
                        }
                    } else {
                        getMvpView().showMessage(response.getMessage());
                        getMvpView().finishActivity();
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
    public void onViewPreparedByAtfNo(String atfNo) {

        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            return;
        }

        getMvpView().showLoading();

        CargoDetailRequest request = new CargoDetailRequest(getDataManager().getAccessToken());

        request.setAtfNo(atfNo);
        request.setTeslimatKapatma(true);

        getCompositeDisposable().add(getDataManager()
                .doCargoDetailApiCall(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();

                    if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {

                        CargoDetail cargoDetail = response.getCargoDetails().get(0);

                        if (cargoDetail != null) {
                            getMvpView().updateCargoCount(cargoDetail.getToplamParca());
                            getMvpView().updateReceivedName(cargoDetail.getAlici());
                            getMvpView().updateTrackId(cargoDetail.getAtfNo());
                            getMvpView().updateAtfId(cargoDetail.getAtfId());
                            getMvpView().updateAtfAdet("1");
                            getMvpView().updateTeslimTarihi(cargoDetail.getTeslimTarihi());
                            getMvpView().updateEvrakDonusluVisibility(cargoDetail.getEvrakDonuslu().equals("EVET"));
                        } else {
                            getMvpView().showMessage(response.getMessage());
                        }

                    } else {
                        getMvpView().showMessage(response.getMessage());
                        getMvpView().finishActivity();
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
    public void getAtfUndeliverableReason() {

        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            return;
        }

        getMvpView().showLoading();
        Deneme deneme = new Deneme();
        deneme.setToken(getDataManager().getAccessToken());

        getCompositeDisposable().add(getDataManager()
                .getAtfUndeliverableReason(deneme)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();

                    if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {
                        getMvpView().setSpinner(response.getAtfUndeliverableReasonModels());

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
    public void onViewDeliveryInfoPrepared(String trackId) {

        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            return;
        }

        getMvpView().showLoading();

        CargoDetailRequest request = new CargoDetailRequest(getDataManager().getAccessToken());
        request.setKtfBarkodu(trackId);

        getCompositeDisposable().add(getDataManager()
                .doMultiCargoApiCall(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();

                    if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {

                        if (response.getDeliverCargoInfoList().size() != 0) {
                            DeliverMultipleInfo multipleInfo = response.getDeliverCargoInfoList().get(0);

                            getMvpView().showMessage(response.getMessage());

                            if (multipleInfo != null) {
                                getMvpView().updateReceivedName(multipleInfo.getAlici());
                                getMvpView().updateAtfAdet(multipleInfo.getAtfAdet() + "");
                                getMvpView().updateToplamAdet(multipleInfo.getToplamParca() + "");
                            }
                        }
                        else{
                            getMvpView().showMessage(response.getMessage());
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
    public void onDeliveredClick(DeliveredCargoRequest request) {

        if (!getMvpView().isNetworkConnected()){
            getMvpView().showMessage(R.string.connection_error);
            return;
        }

        getMvpView().showLoading();

        request.setToken(getDataManager().getAccessToken());
        request.setTeslimTipi("NT");


        getCompositeDisposable().add(getDataManager()
                .doDeliveredCargoApiCall(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();

                    if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {

                        getMvpView().showMessage(response.getMessage());
                        getMvpView().finishActivity();

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
    public void multiDeliverApiCall(DeliveredCargoRequest request) {

        if (!getMvpView().isNetworkConnected()){
            getMvpView().showMessage(R.string.connection_error);
            return;
        }

        getMvpView().showLoading();

        request.setToken(getDataManager().getAccessToken());
        request.setTeslimTipi("NT");

        Gson gson = new Gson();
        String json = gson.toJson(request);

        getCompositeDisposable().add(getDataManager()
                .doMultiCargoDeliveredApiCall(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();

                    if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {

                        if (response.getResponseList().size() != 0){
                            getMvpView().showAlertDialog(response);
                        }else{
                            getMvpView().showMessage(response.getMessage());
                            getMvpView().finishActivity();
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
}
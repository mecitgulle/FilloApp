package com.bt.arasholding.filloapp.ui.delivery.compensation;

import com.androidnetworking.error.ANError;
import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.DataManager;
import com.bt.arasholding.filloapp.data.model.Barcode;
import com.bt.arasholding.filloapp.data.network.model.CargoDetail;
import com.bt.arasholding.filloapp.data.network.model.CargoDetailRequest;
import com.bt.arasholding.filloapp.data.network.model.CompensationRequest;
import com.bt.arasholding.filloapp.data.network.model.DeliverMultipleCargoModel;
import com.bt.arasholding.filloapp.data.network.model.Deneme;
import com.bt.arasholding.filloapp.di.PerActivity;
import com.bt.arasholding.filloapp.ui.base.BasePresenter;
import com.bt.arasholding.filloapp.ui.delivery.multidelivery.MultiDeliveryMvpPresenter;
import com.bt.arasholding.filloapp.ui.delivery.multidelivery.MultiDeliveryMvpView;
import com.bt.arasholding.filloapp.utils.AppConstants;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@PerActivity
public class CompensationPresenter<V extends CompensationMvpView> extends BasePresenter<V> implements CompensationMvpPresenter<V> {

    @Inject
    public CompensationPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getBarcodeInformation(String barcode, boolean isBarcode) {

        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            return;
        }

        getMvpView().showLoading();

        CargoDetailRequest request = new CargoDetailRequest(getDataManager().getAccessToken());

//        request.setAtfId(barcode);
        if (barcode.length() == 34)
        {
            request.setAtfId(barcode);
        }
        else if (barcode.length() == 7) {
            request.setAtfNo(barcode);
        }
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

//                            if (cargoDetail.getTeslimTarihi() != null)
//                            {
//                                getMvpView().showMessage("Gönderi teslim edilmiş!");
//                                getMvpView().redirect();
//                            }
//////                            else
//                            if (cargoDetail.getTazminNo() != null) {
//                                getMvpView().showMessage("Gönderinin zaten tazmin girişi yapılmış!");
//                                getMvpView().redirect();
//                            }
//                            else {
                                getMvpView().updateAtfNo(cargoDetail.getAtfNo());
                                getMvpView().updateAtfID(cargoDetail.getAtfId());
                                getMvpView().updateIrsaliye(cargoDetail.getIrsaliyeNo());
                                getMvpView().updateGonderen(cargoDetail.getGonderen());
                                getMvpView().updateAlici(cargoDetail.getAlici());
                                getMvpView().updateCikisSube(cargoDetail.getCikisSube());
                                getMvpView().updateVarisSube(cargoDetail.getVarisSube());
                                getMvpView().updateToplamParca(cargoDetail.getToplamParca());
                                getMvpView().updateDesi(cargoDetail.getDesi());
                                getMvpView().updateOdemeSekli(cargoDetail.getOdemeSekli());
                                getMvpView().updateTarih(cargoDetail.getTarih());
                                getMvpView().updateTazminNo(cargoDetail.getTazminNo());
//                            }

                        } else {
                            getMvpView().updateAtfNo("");
                            getMvpView().updateAtfID("");
                            getMvpView().updateIrsaliye("");
                            getMvpView().updateGonderen("");
                            getMvpView().updateAlici("");
                            getMvpView().updateCikisSube("");
                            getMvpView().updateVarisSube("");
                            getMvpView().updateToplamParca("");
                            getMvpView().updateDesi("");
                            getMvpView().updateOdemeSekli("");
                            getMvpView().updateTarih("");
                            getMvpView().updateTazminNo("");
                            getMvpView().showMessage(response.getMessage());
                        }

                    } else {
                        getMvpView().updateAtfNo("");
                        getMvpView().updateAtfID("");
                        getMvpView().updateIrsaliye("");
                        getMvpView().updateGonderen("");
                        getMvpView().updateAlici("");
                        getMvpView().updateCikisSube("");
                        getMvpView().updateVarisSube("");
                        getMvpView().updateToplamParca("");
                        getMvpView().updateDesi("");
                        getMvpView().updateOdemeSekli("");
                        getMvpView().updateTarih("");
                        getMvpView().updateTazminNo("");
                        getMvpView().showMessage(response.getMessage());
                        getMvpView().vibrate();
                    }

                }, throwable -> {

                    getMvpView().updateAtfNo("");
                    getMvpView().updateAtfID("");
                    getMvpView().updateIrsaliye("");
                    getMvpView().updateGonderen("");
                    getMvpView().updateAlici("");
                    getMvpView().updateCikisSube("");
                    getMvpView().updateVarisSube("");
                    getMvpView().updateToplamParca("");
                    getMvpView().updateDesi("");
                    getMvpView().updateOdemeSekli("");
                    getMvpView().updateTarih("");
                    getMvpView().updateTazminNo("");
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
    public void TazminGirisi(CompensationRequest request, String tazminNo) {
        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            return;
        }
        request.setToken(getDataManager().getAccessToken());
        getMvpView().showLoading();
        Gson gson = new Gson();
        String json = gson.toJson(request);

        if (tazminNo.isEmpty())
        {
            getCompositeDisposable().add(getDataManager()
                    .doCompensationApiCall(request)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {

                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoading();


                        if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {
                            getMvpView().showMessage("İşlem Başarılı !");
                            getMvpView().redirect();

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
        else{
            getCompositeDisposable().add(getDataManager()
                    .doCompensationImageUploadApiCall(request)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {

                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoading();


                        if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {
                            getMvpView().showMessage("İşlem Başarılı !");
                            getMvpView().redirect();

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

    }


    @Override
    public void getCompensationReason() {

        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            return;
        }

        getMvpView().showLoading();
        Deneme deneme = new Deneme();
        deneme.setToken(getDataManager().getAccessToken());

        getCompositeDisposable().add(getDataManager()
                .getCompensationReason(deneme)
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
}

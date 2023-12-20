package com.bt.arasholding.filloapp.ui.delivery.cargomovement;

import com.androidnetworking.error.ANError;
import com.bt.arasholding.filloapp.data.DataManager;
import com.bt.arasholding.filloapp.data.network.model.CargoDetailRequest;
import com.bt.arasholding.filloapp.data.network.model.CargoMovementDetail;
import com.bt.arasholding.filloapp.data.network.model.CargoMovementDetailSummarys;
import com.bt.arasholding.filloapp.data.network.model.NoBarcodeSaveAtfNoRequest;
import com.bt.arasholding.filloapp.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CargoMovementPresenter<V extends CargoMovementMvpView> extends BasePresenter<V> implements CargoMovementMvpPresenter<V> {

    private static final String TAG = "CargoMovementPresenter";

    @Inject
    public CargoMovementPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }
    @Override
    public void onViewPrepared(String trackId,boolean isBarcode) {
        getMvpView().showLoading();

        CargoDetailRequest request = new CargoDetailRequest();

        if (isBarcode){
            request.setAtfId(trackId);
        }else{
            request.setAtfNo(trackId);
        }

        request.setToken(getDataManager().getAccessToken());

        getCompositeDisposable().add(
                getDataManager().doCargoMovementDetailApiCall(request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {

                            if (!isViewAttached()) {
                                return;
                            }

                            if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))){

                                if (response.getCargoMovementDetails().size() != 0){

                                    CargoMovementDetail movementItem = response.getCargoMovementDetails().get(0);
                                    List<CargoMovementDetailSummarys> summaryItem = response.getCargoMovementDetailSummarys();

                                    if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))){

                                        getMvpView().updateAtfNo(movementItem.getAtfno());
                                        getMvpView().updateAtfTarihi(movementItem.getAtftarihi());
                                        getMvpView().updateToplamParca(movementItem.getToplamparca());
                                        getMvpView().updateVarisSubeAdi(movementItem.getVarissubeadi());
                                        getMvpView().updateHareketlerList(response.getCargoMovementDetails());
                                        getMvpView().updateSummarysList(response.getCargoMovementDetailSummarys());
//                                        for (CargoMovementDetailSummarys item : summaryItem) {
//                                            if (item.getIslem().equals("SUBE YUKLEME")){
//                                                getMvpView().updateSubeYukleme(item.getAdet());
//                                            }
//                                            else if(item.getIslem().equals("SUBE INDIRME")){
//                                                getMvpView().updateSubeIndirme(item.getAdet());
//                                            }
//                                            else if(item.getIslem().equals("SUBE DAGITIM")){
//                                                getMvpView().updateSubeDagitim(item.getAdet());
//                                            }
//                                        }

                                    }else{
                                        getMvpView().onError(response.getMessage());
                                    }
                                }
                            }else{
                                getMvpView().onError(response.getMessage());
                                getMvpView().vibrate();
                            }

                            getMvpView().hideLoading();

                        },throwable -> {
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
    public void onViewPreparedNoBarcode(String trackId,String noBarcodeId) {
        getMvpView().showLoading();

        NoBarcodeSaveAtfNoRequest request = new NoBarcodeSaveAtfNoRequest();

//        if (isBarcode){
//            request.setAtfId(trackId);
//        }else{
//            request.setAtfNo(trackId);
//        }
        request.setAtfNo(trackId);

        request.setToken(getDataManager().getAccessToken());

        request.setNoBarcodeId(noBarcodeId);

        getCompositeDisposable().add(
                getDataManager().doNoBarcodeAtfNoApiCall(request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {

                            if (!isViewAttached()) {
                                return;
                            }

                            if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))){
                                    if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))){

                                        getMvpView().showMessage("Barkodsuz Kargo Atf No Kaydı Başarılı");

                                    }else{
                                        getMvpView().onError(response.getMessage());
                                    }

                            }
                            else{
                                getMvpView().showMessage(response.getMessage());
                            }

                            getMvpView().hideLoading();

                        },throwable -> {
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


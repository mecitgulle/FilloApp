package com.bt.arasholding.filloapp.ui.delivery;

import com.androidnetworking.error.ANError;
import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.DataManager;
import com.bt.arasholding.filloapp.data.network.model.CargoDetailRequest;
import com.bt.arasholding.filloapp.di.PerActivity;
import com.bt.arasholding.filloapp.ui.base.BasePresenter;
import com.google.gson.Gson;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@PerActivity
public class DeliveryPresenter<V extends DeliveryMvpView> extends BasePresenter<V> implements DeliveryMvpPresenter<V> {

    private static final String TAG = "DeliveryPresenter";

    @Inject
    public DeliveryPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void onViewPrepared() {
        getMvpView().updateToolbarTitle();
        getMvpView().decideProcessFragment();

        if (getDataManager().getSelectedCamera()){
            getMvpView().showCameraFragment();
        }else{
            getMvpView().showBluetoothFragment();
        }
    }

    @Override
    public void onQueryClick(String trackId) {

        if (trackId == null || trackId.isEmpty()) {
            getMvpView().onError(R.string.empty_data);
            return;
        }

        getMvpView().showLoading();

        CargoDetailRequest request = new CargoDetailRequest(getDataManager().getAccessToken());
        request.setAtfNo(trackId);
        request.setTeslimatKapatma(false);

        Gson gson = new Gson();
        String json = gson.toJson(request);

        getCompositeDisposable().add(getDataManager()
                .doCargoDetailApiCall(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))){

                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoading();

                    }else{
                        getMvpView().hideLoading();
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
                })
        );
    }
}
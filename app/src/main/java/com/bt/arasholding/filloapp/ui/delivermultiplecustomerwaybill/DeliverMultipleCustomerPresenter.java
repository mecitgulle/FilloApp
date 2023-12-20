package com.bt.arasholding.filloapp.ui.delivermultiplecustomerwaybill;

import com.androidnetworking.error.ANError;
import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.DataManager;
import com.bt.arasholding.filloapp.data.network.model.Deneme;
import com.bt.arasholding.filloapp.di.PerActivity;
import com.bt.arasholding.filloapp.ui.base.BasePresenter;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@PerActivity
public class DeliverMultipleCustomerPresenter<V extends DeliverMultipleCustomerMvpView> extends BasePresenter<V> implements DeliverMultipleCustomerMvpPresenter<V> {
    private static final String TAG = "DeliverMultipleCustomerPresenter";

    @Inject
    public DeliverMultipleCustomerPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }
    @Override
    public void getCustomer()
    {
        getMvpView().showLoading();
        Deneme deneme = new Deneme();
        deneme.setToken(getDataManager().getAccessToken());

        getCompositeDisposable().add(getDataManager()
                .getCustomerList(deneme)
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
}

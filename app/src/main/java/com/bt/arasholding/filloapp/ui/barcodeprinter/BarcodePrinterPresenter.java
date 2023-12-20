package com.bt.arasholding.filloapp.ui.barcodeprinter;

import com.androidnetworking.error.ANError;
import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.DataManager;
import com.bt.arasholding.filloapp.data.network.model.CargoBarcodeTextRequest;
import com.bt.arasholding.filloapp.di.PerActivity;
import com.bt.arasholding.filloapp.ui.base.BasePresenter;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@PerActivity
public class BarcodePrinterPresenter<V extends BarcodePrinterMvpView> extends BasePresenter<V> implements BarcodePrinterMvpPresenter<V> {

    @Inject
    public BarcodePrinterPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getBarcodeText(String barcode, String barkodTipi) {

        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            return;
        }

        getMvpView().showLoading();

        CargoBarcodeTextRequest request = new CargoBarcodeTextRequest(getDataManager().getAccessToken());
        request.setBarcodetype(barkodTipi);
        request.setBarkod(barcode);
        request.setMobilprintername(getDataManager().getCurrentBluetoothPairedDeviceName());

        getCompositeDisposable().add(getDataManager()
                .getCargoBarcodeTextApiCall(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();

                    if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {

                        getMvpView().printBarcode(response.getBarcodetext());

                    } else {
                        getMvpView().hideKeyboard();
                        getMvpView().vibrate();
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
                        getMvpView().vibrate();
                        handleApiError(anError);
                    }
                }));
    }

    @Override
    public void onViewPrepared() {

    }
}

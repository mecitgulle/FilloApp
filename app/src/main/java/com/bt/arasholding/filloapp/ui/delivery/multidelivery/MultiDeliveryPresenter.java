package com.bt.arasholding.filloapp.ui.delivery.multidelivery;

import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.DataManager;
import com.bt.arasholding.filloapp.data.model.Barcode;
import com.bt.arasholding.filloapp.data.network.model.AtfModel;
import com.bt.arasholding.filloapp.data.network.model.AtfUndeliverableReasonModel;
import com.bt.arasholding.filloapp.data.network.model.CargoDetail;
import com.bt.arasholding.filloapp.data.network.model.CargoDetailRequest;
import com.bt.arasholding.filloapp.data.network.model.DeliverCargoImageUploadRequest;
import com.bt.arasholding.filloapp.data.network.model.DeliverMultipleCargoModel;
import com.bt.arasholding.filloapp.data.network.model.DeliveredCargoRequest;
import com.bt.arasholding.filloapp.data.network.model.Deneme;
import com.bt.arasholding.filloapp.di.PerActivity;
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

@PerActivity
public class MultiDeliveryPresenter<V extends MultiDeliveryMvpView> extends BasePresenter<V> implements MultiDeliveryMvpPresenter<V> {

    int sayac = 0;

    @Inject
    public MultiDeliveryPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getBarcodeInformation(String barcode, boolean isBarcode)
    {

        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            return;
        }

        getMvpView().showLoading();

        CargoDetailRequest request = new CargoDetailRequest(getDataManager().getAccessToken());

//        request.setAtfId(barcode);
            if (barcode.length() == 34 || barcode.length() == 11) {
                request.setAtfId(barcode);
            } else if (barcode.length() == 7) {
                request.setAtfNo(barcode);
            }
            if (barcode.length() == 17)
            {
                request.setAtfNo(barcode);
            request.setKtfBarkodu(barcode);
            }
        request.setTeslimatKapatma(true);
        Gson gson = new Gson();
        String json = gson.toJson(request);


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

                        List<CargoDetail> cargoDetail = response.getCargoDetails();


                        if (cargoDetail.size() > 0) {
                            for (CargoDetail item : cargoDetail) {
                                Barcode mBarcode = new Barcode();
                                if (isBarcode) {
                                    mBarcode.setBarcode(barcode);
                                } else {
                                    mBarcode.setBarcode(item.getAtfId());
                                }
                                mBarcode.setIslemTipi(AppConstants.MULTIDELIVERY_TYPE);
                                mBarcode.setAtf_no(item.getAtfNo());
                                mBarcode.setAtfId(item.getAtfId());
                                mBarcode.setAlici_adi(item.getAlici());
                                mBarcode.setEvrakDonusluMu(item.getEvrakDonuslu());
                                if(item.getTeslimTarihi() != null)
                                {
                                    mBarcode.setIslemSonucu("Bu ATF Teslim Edilmiş Siliniz");
                                }

                                saveBarcode(mBarcode);

                            }

                        } else {
                            getMvpView().showMessage(response.getMessage());
                        }

                    } else {
                        getMvpView().showMessage(response.getMessage());
                        getMvpView().vibrate();
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
    public void deleteAtfList(String barcode, int islemTipi)
    {
        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            return;
        }

        getMvpView().showLoading();


        deleteBarcode(barcode,islemTipi);

        getMvpView().hideLoading();

    }
    private void deleteBarcode(String barcode, int islemTipi) {
        getCompositeDisposable().add(getDataManager()
                .deleteBarcode(barcode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        refreshList(islemTipi);
                    }
                }, throwable -> {

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

    private void saveBarcode(Barcode barcode) {
        getCompositeDisposable().add(getDataManager()
                .saveBarcode(barcode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        refreshList(barcode.getIslemTipi());
                    }
                }, throwable -> {

                }));
    }

    @Override
    public void refreshList(int islemTipi) {
        getCompositeDisposable().add(getDataManager()
                .getBarcodesByIslemTipi(islemTipi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Barcode>>() {
                    @Override
                    public void accept(List<Barcode> barcodeList) throws Exception {
                        getMvpView().updateBarcodeList(barcodeList);
                    }
                }, throwable -> {

                }));
    }

    @Override
    public void teslimatKapat(DeliveredCargoRequest teslimatParam) {
        getCompositeDisposable().add(getDataManager()
                .getBarcodesByIslemTipi(AppConstants.MULTIDELIVERY_TYPE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Barcode>>() {
                    @Override
                    public void accept(List<Barcode> barcodeList) throws Exception {

                        ArrayList<AtfModel> atfModelList = new ArrayList<>();

                        for (Barcode item : barcodeList) {
                            AtfModel atfModel = new AtfModel();
                            atfModel.setAtfId(item.getAtfId());
                            atfModel.setEvrakDonusAlindiMi(item.getEvrakDonusluMu());
                            atfModel.setAtfNo(item.getAtf_no());
                            atfModel.setAliciAdi(item.getAlici_adi());

                            atfModelList.add(atfModel);
                        }

                        teslimatParam.setAtfModelList(atfModelList);

                        apiCall(teslimatParam);
                    }
                }, throwable -> {

                }));
    }

    @Override
    public void setAlindiMi(long id, String deger) {
        getCompositeDisposable().add(getDataManager()
                .updateBarcode(id, deger)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        getMvpView().showMessage("Değiştirildi");
                    }
                }, throwable -> {

                }));
    }

    @Override
    public void setHasarAciklama(long id, String hasarAciklama) {

        getCompositeDisposable().add(getDataManager()
                .updateBarcodeHasarAciklama(id, hasarAciklama)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        getMvpView().showMessage("Kaydedildi");
                    }
                }, throwable -> {

                }));
    }

    @Override
    public void setHasarPhoto(long id, String foto) {
        getCompositeDisposable().add(getDataManager()
                .updateBarcodePhoto(id, foto)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        getMvpView().showMessage("Kaydedildi");
                    }
                }, throwable -> {

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
    public void setButtonText(String choose) {
        if (choose.equals("ATFDEVIR"))
            getMvpView().setButtonText("Devir Gir");
        else
            getMvpView().setButtonText("Teslimat Kapat");
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
    public void onViewPrepared() {
        getCompositeDisposable().add(getDataManager()
                .deleteBarcodesByType(AppConstants.MULTIDELIVERY_TYPE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        refreshList(AppConstants.MULTIDELIVERY_TYPE);
                    }
                }, throwable -> {

                }));
    }

    @Override
    public void showTeslimatDialog() {

        getCompositeDisposable().add(getDataManager()
                .getAllBarcodes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Barcode>>() {
                    @Override
                    public void accept(List<Barcode> barcodes) throws Exception {

                        if (barcodes.size() != 0) {
                            getMvpView().showTeslimatDialog();
                        } else {
                            getMvpView().showMessage("Önce barkodu okutunuz");
                        }
                    }
                }));
    }

    @Override
    public void imageUpload(DeliverCargoImageUploadRequest imageParam) {

        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            return;
        }

        imageParam.setToken(getDataManager().getAccessToken());

        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .doDeliverCargoImageUploadApiCall(imageParam)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();

                    if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {

                        getMvpView().showMessage("Fotoğraf yüklendi");
                        sayac++;

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

    private void apiCall(DeliveredCargoRequest teslimatParam) {

        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            return;
        }
        if (!teslimatParam.getAtfModelList().get(0).getEvrakDonusAlindiMi().equals("HAYIR")) {
            if (sayac == 0) {
                getMvpView().showMessage("Lütfen önce fotoğraf çekiniz");
                return;
            }
        }
        teslimatParam.setToken(getDataManager().getAccessToken());
        teslimatParam.setTeslimTipi("NT");

        getMvpView().showLoading();

        Gson gson = new Gson();
        String json = gson.toJson(teslimatParam);

        onViewPrepared();

        getCompositeDisposable().add(getDataManager()
                .doMultiDeliveryApiCall(teslimatParam)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();



                    if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {
                        List<DeliverMultipleCargoModel> apiResponseList = response.getResponseList();
                        if (apiResponseList != null) {

                            List<Barcode> barcodeList = new ArrayList<>();

                            for (DeliverMultipleCargoModel item : apiResponseList) {
                                Barcode mBarcode = new Barcode();

                                mBarcode.setAtf_no(item.getAtfNo());
                                mBarcode.setIslemSonucu(item.getMessage());
                                mBarcode.setAlici_adi(item.getAliciAdi());

                                barcodeList.add(mBarcode);
                            }

                            getMvpView().updateBarcodeList(barcodeList);
                            getMvpView().showMessage(response.getMessage());

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
    public void apiCallCustomerDelivery(DeliveredCargoRequest teslimatParam) {

        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showMessage(R.string.connection_error);
            return;
        }
        teslimatParam.setToken(getDataManager().getAccessToken());
        teslimatParam.setTeslimTipi("NT");

        getMvpView().showLoading();

        Gson gson = new Gson();
        String json = gson.toJson(teslimatParam);
        onViewPrepared();


        getCompositeDisposable().add(getDataManager()
                .doCustomerDelivery(teslimatParam)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if (!isViewAttached()) {
                        return;
                    }

                    getMvpView().hideLoading();
                    if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {

                        List<DeliverMultipleCargoModel> apiResponseList = response.getResponseList();

                        if (apiResponseList != null) {

                            List<Barcode> barcodeList = new ArrayList<>();
                            getMvpView().onError(response.getMessage());

                            for (DeliverMultipleCargoModel item : apiResponseList) {
                                Barcode mBarcode = new Barcode();

                                mBarcode.setAtf_no(item.getAtfNo());
                                mBarcode.setIslemSonucu(item.getMessage());
                                mBarcode.setAlici_adi(item.getAliciAdi());

                                barcodeList.add(mBarcode);
                            }

                            getMvpView().updateBarcodeList(barcodeList);

                        }
                        getMvpView().showErrorMessage(response.getMessage());

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

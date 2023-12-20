package com.bt.arasholding.filloapp.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;
import android.widget.Toast;

import com.bt.arasholding.filloapp.data.DataManager;
import com.bt.arasholding.filloapp.data.model.Barcode;
import com.bt.arasholding.filloapp.data.network.model.CargoMovementRequest;

import java.util.List;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BarcodeService extends JobService {
    private final DataManager mDataManager;
    private final CompositeDisposable mCompositeDisposable;

    @Inject
    public BarcodeService(DataManager dataManager,
                          CompositeDisposable compositeDisposable) {
        this.mDataManager = dataManager;
        this.mCompositeDisposable = compositeDisposable;
    }
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("tag","OnStart Job");
//        mCompositeDisposable.add(mDataManager
//                .getAllBarcodes()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<List<Barcode>>() {
//                    @Override
//                    public void accept(List<Barcode> barcodeList) throws Exception {
//
//                        for (Barcode barcode : barcodeList)
//                        {
//                            if (barcode.getIslemTipi() == 1){
//
//                                yukleme(barcode.getBarcode());
//
//                            } else if(barcode.getIslemTipi() == 2){
//                                indirme(barcode.getBarcode());
//
//                            } else if(barcode.getIslemTipi() == 5){
//                                hatYukleme(barcode.getBarcode());
//
//                            }
//                        }
//                    }
//                }, throwable -> {
//
//                }));
//
//        jobFinished(params,false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

//    public void yukleme(String barcode) {
//
//        CargoMovementRequest request = new CargoMovementRequest();
//        request.setAccessToken(mDataManager.getAccessToken());
//        request.setBarcode(barcode);
//        request.setType(1);
//        request.setSefer(mDataManager.getCurrentShipmentCode());
//
//        mCompositeDisposable.add(
//                mDataManager.doCargoMovementApiCall(request)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                cargoMovementResponse -> {
//
//                                    if (cargoMovementResponse.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {
//                                        mDataManager.deleteBarcode(barcode);
//                                    } else {
//
//                                    }
//                                }, throwable -> {
//
//                                })
//        );
//    }
//
//    public void indirme(String barcode) {
//
//        CargoMovementRequest request = new CargoMovementRequest();
//        request.setAccessToken(mDataManager.getAccessToken());
//        request.setBarcode(barcode);
//        request.setType(2);
//        request.setSefer(mDataManager.getCurrentShipmentCode());
//
//        mCompositeDisposable.add(
//                mDataManager.doCargoMovementApiCall(request)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                cargoMovementResponse -> {
//
//                                    if (cargoMovementResponse.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {
//                                        mDataManager.deleteBarcode(barcode);
//                                    } else {
//
//                                    }
//
//                                }, throwable -> {
//
//                                })
//        );
//
//    }
//
//    public void hatYukleme(String barcode) {
//
//        CargoMovementRequest request = new CargoMovementRequest();
//        request.setAccessToken(mDataManager.getAccessToken());
//        request.setBarcode(barcode);
//        request.setType(5);
//        request.setSefer(mDataManager.getCurrentShipmentCode());
//
//        mCompositeDisposable.add(
//                mDataManager.doCargoMovementApiCall(request)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                cargoMovementResponse -> {
//
//                                    if (cargoMovementResponse.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))) {
//                                        mDataManager.deleteBarcode(barcode);
//                                    } else {
//
//                                    }
//
//                                }, throwable -> {
//                                })
//        );
//    }
}

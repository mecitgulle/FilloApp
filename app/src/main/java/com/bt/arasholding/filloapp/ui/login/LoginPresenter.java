package com.bt.arasholding.filloapp.ui.login;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.DataManager;
import com.bt.arasholding.filloapp.data.network.model.LoginRequest;
import com.bt.arasholding.filloapp.data.network.model.LoginResponse;
import com.bt.arasholding.filloapp.ui.base.BasePresenter;
import com.bt.arasholding.filloapp.ui.delivermultiplecustomerwaybill.DeliverMultipleCustomerActivity;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V> implements LoginMvpPresenter<V> {

    private static final String TAG = "LoginPresenter";

    @Inject
    public LoginPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void onServerLoginClick(String userName, String password, String deviceToken, String deviceName) {

        if (userName == null || userName.isEmpty()) {
            getMvpView().onError(R.string.empty_username);
            return;
        }
        if (password == null || password.isEmpty()) {
            getMvpView().onError(R.string.empty_password);
            return;
        }


        getMvpView().showLoading();

        getMvpView().hideKeyboard();
        String versionCode = getMvpView().getVersionCode();

        getCompositeDisposable().add(
                getDataManager()
                .doServerLoginApiCall(new LoginRequest.ServerLoginRequest(userName, password,deviceToken,deviceName,getMvpView().getVersionCode()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse response) throws Exception {

                        if (response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_OK))){

                            getDataManager().updateUserInfo(
                                    response.getAccessToken(),
                                    response.getUserId(),
                                    DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER,
                                    response.getUserName(),
                                    response.getSubeKodu(),
                                    response.getSeferId(),
                                    response.getUserGroupId());
                            if (!isViewAttached()) {
                                return;
                            }
                            getMvpView().hideLoading();
                            getMvpView().openHomeActivity();
                        }
                        else if(response.getStatusCode().equals(String.valueOf(HttpsURLConnection.HTTP_NOT_FOUND)))
                        {
                            if (response.getDownloadUri() != null)
                            {
                                getMvpView().hideLoading();
                                getMvpView().showAlertDialog(response);
                            }
                        }
                        else{
                            getMvpView().hideLoading();
                            getMvpView().onError(response.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoading();

                        // handle the login error here
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }
                }));
    }
}
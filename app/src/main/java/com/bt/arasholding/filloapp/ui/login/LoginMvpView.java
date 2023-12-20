package com.bt.arasholding.filloapp.ui.login;

import com.bt.arasholding.filloapp.data.network.model.LoginResponse;
import com.bt.arasholding.filloapp.ui.base.MvpView;

public interface LoginMvpView extends MvpView {
    void openHomeActivity();
    String getVersionCode();
    void showAlertDialog(LoginResponse response);
}
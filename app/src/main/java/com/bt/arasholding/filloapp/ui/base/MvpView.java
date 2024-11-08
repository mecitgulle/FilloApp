package com.bt.arasholding.filloapp.ui.base;

import android.media.MediaPlayer;
import androidx.annotation.StringRes;

public interface MvpView {

    void showLoading();

    void hideLoading();

    void showLoadingWithMessage(String message);

    void hideLoadingWithMessage();

    void openActivityOnTokenExpire();

    void onError(@StringRes int resId);

    void onError(String message);

    void onErrorShippingOutput(@StringRes int resId);

    void onErrorShippingOutput(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    void vibrate();
    void vibrate2(String Message);
//    MediaPlayer playBeepSound();

    boolean isNetworkConnected();

    void hideKeyboard();
}
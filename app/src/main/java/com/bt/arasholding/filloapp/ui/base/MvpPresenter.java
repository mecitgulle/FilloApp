package com.bt.arasholding.filloapp.ui.base;

import com.androidnetworking.error.ANError;

public interface MvpPresenter<V extends MvpView> {

    void onAttach(V mvpView);

    void onDetach();

    void handleApiError(ANError error);

    void handleApiErrorShippingOutput(ANError error);

    void setUserAsLoggedOut();

}
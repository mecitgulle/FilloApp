package com.bt.arasholding.filloapp.ui.route.address;

import com.bt.arasholding.filloapp.ui.base.MvpPresenter;
import com.bt.arasholding.filloapp.ui.route.ExpeditionRouteMvpView;

public interface ExpeditionRouteAddressMvpPresenter<V extends ExpeditionRouteAddressMvpView> extends MvpPresenter<V> {
    void onViewPrepared(String seferId);
    void GetExpeditionRouteAddress(String seferId);
    String getLatitude();
    String getLongitude();
}

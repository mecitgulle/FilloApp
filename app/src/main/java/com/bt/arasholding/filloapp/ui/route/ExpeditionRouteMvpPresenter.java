package com.bt.arasholding.filloapp.ui.route;

import com.bt.arasholding.filloapp.ui.base.MvpPresenter;
import com.bt.arasholding.filloapp.ui.delivery.DeliveryMvpView;

public interface ExpeditionRouteMvpPresenter<V extends ExpeditionRouteMvpView> extends MvpPresenter<V> {

    void onViewPrepared();
    void GetExpeditionRoute();
}

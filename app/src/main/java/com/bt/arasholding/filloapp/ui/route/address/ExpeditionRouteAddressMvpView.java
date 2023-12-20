package com.bt.arasholding.filloapp.ui.route.address;

import com.bt.arasholding.filloapp.data.network.model.ExpeditionRoute;
import com.bt.arasholding.filloapp.ui.base.MvpView;

import java.util.List;

public interface ExpeditionRouteAddressMvpView extends MvpView {

    void updateBarcodeList(List<String> barcodeList);
}

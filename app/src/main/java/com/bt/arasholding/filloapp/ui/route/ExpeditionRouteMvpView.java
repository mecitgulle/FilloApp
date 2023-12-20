package com.bt.arasholding.filloapp.ui.route;

import com.bt.arasholding.filloapp.data.model.Barcode;
import com.bt.arasholding.filloapp.data.network.model.ExpeditionRoute;
import com.bt.arasholding.filloapp.ui.base.MvpView;

import java.util.List;

public interface ExpeditionRouteMvpView extends MvpView {

    void updateBarcodeList(List<ExpeditionRoute> barcodeList);
}

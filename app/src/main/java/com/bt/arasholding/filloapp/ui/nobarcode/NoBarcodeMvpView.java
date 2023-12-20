package com.bt.arasholding.filloapp.ui.nobarcode;

import com.bt.arasholding.filloapp.data.network.model.CustomerResponseModel;
import com.bt.arasholding.filloapp.data.network.model.ExpeditionRoute;
import com.bt.arasholding.filloapp.data.network.model.NoBarcodeCargo;
import com.bt.arasholding.filloapp.ui.base.MvpView;

import java.util.List;

public interface NoBarcodeMvpView extends MvpView {
    void showNewNoBarcodeDialog();
    void setSpinner(List<CustomerResponseModel> model);
    void updateNoBarcodeList(List<NoBarcodeCargo> CargoNoBarcodeList);
}

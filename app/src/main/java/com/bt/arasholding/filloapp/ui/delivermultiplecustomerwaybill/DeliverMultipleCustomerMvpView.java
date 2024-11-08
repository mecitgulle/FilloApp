package com.bt.arasholding.filloapp.ui.delivermultiplecustomerwaybill;

import com.bt.arasholding.filloapp.data.network.model.CustomerResponseModel;
import com.bt.arasholding.filloapp.ui.base.MvpView;

import java.util.List;

public interface DeliverMultipleCustomerMvpView extends MvpView {
    void setSpinner(List<CustomerResponseModel> model);
    void showTokenExpired();
}

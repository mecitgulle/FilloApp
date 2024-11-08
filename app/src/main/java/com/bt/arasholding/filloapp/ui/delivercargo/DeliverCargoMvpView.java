package com.bt.arasholding.filloapp.ui.delivercargo;

import com.bt.arasholding.filloapp.data.network.model.AtfUndeliverableReasonModel;
import com.bt.arasholding.filloapp.data.network.model.DeliverMultipleCargoResponse;
import com.bt.arasholding.filloapp.ui.base.MvpView;

import java.util.List;

public interface DeliverCargoMvpView extends MvpView {

    void updateTrackId(String trackId);
    void updateReceivedName(String name);
    void updateCargoCount(String count);
    void updateAtfId(String atfId);
    void updateAtfAdet(String atfAdet);
    void updateToplamAdet(String toplamAdet);
    void updateTeslimTarihi(String teslimTarihi);
    void updateEvrakDonusluVisibility(boolean visibility);
    void showAlertDialog(DeliverMultipleCargoResponse response);
    void showAlertDialogChoose();
    void setSpinner(List<AtfUndeliverableReasonModel> model);

    void finishActivity();
    void showTokenExpired();
}
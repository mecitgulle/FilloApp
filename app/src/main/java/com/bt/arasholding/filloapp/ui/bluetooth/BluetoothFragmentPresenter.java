package com.bt.arasholding.filloapp.ui.bluetooth;

import android.widget.Toast;

import com.bt.arasholding.filloapp.data.DataManager;
import com.bt.arasholding.filloapp.di.PerActivity;
import com.bt.arasholding.filloapp.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

@PerActivity
public class BluetoothFragmentPresenter<V extends BluetoothFragmentMvpView> extends BasePresenter<V> implements BluetoothFragmentMvpPresenter<V> {

    @Inject
    public BluetoothFragmentPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void onViewPrepared() {

        if (!isViewAttached())
            return;

        getMvpView().updateCurrentDeviceName(getDataManager().getCurrentBluetoothPairedDeviceName());

        if (getDataManager().getCurrentBluetoothPairedDeviceName() == null) {
           getMvpView().showMessage("Lütfen ayarlardan bluetooth cihazı seçiniz");
           getMvpView().updateLabel("Ayarlardan cihaz seçiniz");
        }
        else {
            getMvpView().updateLabel(getDataManager().getCurrentBluetoothPairedDeviceName());
        }

    }
}

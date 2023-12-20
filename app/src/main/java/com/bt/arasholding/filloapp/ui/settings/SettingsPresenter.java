package com.bt.arasholding.filloapp.ui.settings;

import com.bt.arasholding.filloapp.data.DataManager;
import com.bt.arasholding.filloapp.di.PerActivity;
import com.bt.arasholding.filloapp.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

@PerActivity
public class SettingsPresenter<V extends SettingsMvpView> extends BasePresenter<V> implements SettingsMvpPresenter<V> {

    private static final String TAG= "SettingsPresenter";

    @Inject
    public SettingsPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void setSelectedCamera(boolean checked) {
        getDataManager().setSelectedCamera(checked);
    }

    @Override
    public void setSelectedBluetooth(boolean checked) {
        getDataManager().setSelectedBluetooth(checked);
    }

    @Override
    public void setSelectedLazer(boolean checked) {
        getDataManager().setSelectedLazer(checked);
    }


    @Override
    public void setSelectedBluetoothDeviceName(String bluetoothDevice)
    {
        getDataManager().setCurrentBluetoothPairedDeviceName(bluetoothDevice);
    }

    @Override
    public void onViewPrepared() {
        getMvpView().updateSelectedCamera(getDataManager().getSelectedCamera());
        getMvpView().updateSelectedBluetooth(getDataManager().getSelectedBluetooth());
        getMvpView().updateSelectedLazer(getDataManager().getSelectedLazer());
//        getMvpView().updateSelectedCamera(getDataManager().getSelectedCamera());
        getMvpView().setSelectedBluetoothDevice(getDataManager().getCurrentBluetoothPairedDeviceName());

    }
}
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

        boolean unchecked = false;
        getDataManager().setSelectedBluetooth(unchecked);
        getDataManager().setSelectedCamera(checked);
        getDataManager().setSelectedLazer(unchecked);
    }

    @Override
    public void setSelectedBluetooth(boolean checked) {
        boolean unchecked = false;
        getDataManager().setSelectedBluetooth(checked);
        getDataManager().setSelectedCamera(unchecked);
        getDataManager().setSelectedLazer(unchecked);
    }

    @Override
    public void setSelectedLazer(boolean checked) {
        boolean unchecked = false;
        getDataManager().setSelectedLazer(checked);
        getDataManager().setSelectedCamera(unchecked);
        getDataManager().setSelectedBluetooth(unchecked);
    }


    @Override
    public void setSelectedBluetoothDeviceName(String bluetoothDevice)
    {
        getDataManager().setCurrentBluetoothPairedDeviceName(bluetoothDevice);
    }

    @Override
    public void onViewPrepared(String deviceName) {
        if (deviceName.equals("ZebraTechnologiesTC26"))
        {
            boolean selectedLazer = true;
            getMvpView().updateSelectedLazer(selectedLazer);
        }
        else{
            getMvpView().updateSelectedLazer(getDataManager().getSelectedLazer());
            getMvpView().updateSelectedCamera(getDataManager().getSelectedCamera());
            getMvpView().updateSelectedBluetooth(getDataManager().getSelectedBluetooth());
//        getMvpView().updateSelectedCamera(getDataManager().getSelectedCamera());
            getMvpView().setSelectedBluetoothDevice(getDataManager().getCurrentBluetoothPairedDeviceName());
        }

    }
}
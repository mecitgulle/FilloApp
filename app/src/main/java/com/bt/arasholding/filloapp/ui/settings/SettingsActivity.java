package com.bt.arasholding.filloapp.ui.settings;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.widget.RadioButton;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.ui.base.BaseActivity;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class SettingsActivity extends BaseActivity implements SettingsMvpView {

    @Inject
    SettingsMvpPresenter<SettingsMvpView> mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.radio_button_camera)
    RadioButton radioButtonCamera;
    @BindView(R.id.radio_button_bluetooth)
    RadioButton radioButtonBluetooth;
    @BindView(R.id.radio_button_lazer)
    RadioButton radioButtonLazer;
    @BindView(R.id.spinner_list)
    MaterialSpinner bluetoothSpanner;

    BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void setUp() {

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        updateBluetoothSpanner(getBluetoothDeviceNames());

        String DeviceName = getDeviceName();

        mPresenter.onViewPrepared(DeviceName);
    }
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + "" + model.replace(" ","");
    }
    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString().replace(" ","");
    }

    @OnCheckedChanged({R.id.radio_button_camera})
    void onRadioButtonCameraChecked(boolean checked) {
        mPresenter.setSelectedCamera(checked);
    }

    @OnCheckedChanged({R.id.radio_button_bluetooth})
    void onRadioButtonBluetoothChecked(boolean checked) {
        mPresenter.setSelectedBluetooth(checked);
    }

    @OnCheckedChanged({R.id.radio_button_lazer})
    void onRadioButtonLazerChecked(boolean checked) {
        mPresenter.setSelectedLazer(checked);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void updateSelectedCamera(Boolean selectedCamera) {
        radioButtonCamera.setChecked(selectedCamera);
        radioButtonBluetooth.setChecked(!selectedCamera);
        radioButtonLazer.setChecked(!selectedCamera);
    }

    @Override
    public void updateSelectedBluetooth(Boolean selectedBluetooth) {
        radioButtonCamera.setChecked(!selectedBluetooth);
        radioButtonBluetooth.setChecked(selectedBluetooth);
        radioButtonLazer.setChecked(!selectedBluetooth);
    }

    @Override
    public void updateSelectedLazer(Boolean selectedLazer) {
        radioButtonCamera.setChecked(!selectedLazer);
        radioButtonBluetooth.setChecked(!selectedLazer);
        radioButtonLazer.setChecked(selectedLazer);
    }

    public void updateBluetoothSpanner(List<String> dataList) {
        if (dataList != null && dataList.size() > 0) {
            bluetoothSpanner.setItems(dataList);
            bluetoothSpanner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                    mPresenter.setSelectedBluetoothDeviceName(item);
                }
            });
        } else {
            bluetoothSpanner.setItems("Bluetooth cihazı seçiniz");
        }
    }

    @Override
    public void setSelectedBluetoothDevice(String selectedBluetoothDeviceName) {

        int index = bluetoothSpanner.getItems().indexOf(selectedBluetoothDeviceName);

        if (index >= 0)
            bluetoothSpanner.setSelectedIndex(index);
    }

    private List<String> getBluetoothDeviceNames() {
        List<String> resultlist = new ArrayList<>();

        if (mBluetoothAdapter != null) {
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    resultlist.add(device.getName());
                }
            }
        }

        return resultlist;
    }
}

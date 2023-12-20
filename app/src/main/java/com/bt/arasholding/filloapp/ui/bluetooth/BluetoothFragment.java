package com.bt.arasholding.filloapp.ui.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.di.component.ActivityComponent;
import com.bt.arasholding.filloapp.ui.base.BaseFragment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BluetoothFragment extends BaseFragment implements BluetoothFragmentMvpView {

    public static final String TAG = "BluetoothFragment";
    private static final int REQUEST_ENABLE_BT = 2;

    @Inject
    BluetoothFragmentMvpPresenter<BluetoothFragmentMvpView> mPresenter;

    @BindView(R.id.label)
    TextView label;
    @BindView(R.id.btnConnect)
    Button btnConnect;

    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothServerSocket mmServerSocket;
    BluetoothDevice mmDevice;
    InputStream mmInputStream;
    OutputStream mmOutputStream;

    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;

    public interface BluetoothResult {
        void resultData(String codeContent);
    }

    private String pairedDeviceName;

    public BluetoothFragment() {

    }

    public static BluetoothFragment newInstance() {
        BluetoothFragment fragment = new BluetoothFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bluetooth, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        checkBluetooth();
//        startJob();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPresenter.onViewPrepared();
        new BluetoothConnectionAsyncTask(this).execute();
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

    public void checkBluetooth() {

        if (mBluetoothAdapter == null) {
            showMessage("Bluetooth is not available");
            return;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
    }

    @Override
    public BluetoothDevice findBT() {
        if (mBluetoothAdapter != null) {
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {

                    if (device.getName().equals(pairedDeviceName)) {

                        mmDevice = device;
                        return device;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public BluetoothSocket getBluetoothSocket() {
        return mmSocket;
    }

    @Override
    public void updateCurrentDeviceName(String currentBluetoothPairedDeviceName) {
        this.pairedDeviceName = currentBluetoothPairedDeviceName;
    }

    @Override
    public String getBluetoothDeviceName() {
        return this.pairedDeviceName;
    }

    @Override
    public void openBT() throws IOException {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        mmSocket.connect();
        mmInputStream = mmSocket.getInputStream();
        mmOutputStream = mmSocket.getOutputStream();
    }

    @Override
    public void closeBT() throws IOException {
        stopWorker = true;
        if (mmInputStream != null && mmSocket != null) {
            mmInputStream.close();
            mmOutputStream.close();
            mmSocket.close();
        }
    }

    @Override
    public void beginListenForData() {

        final Handler handler = new Handler();
        final byte delimiter = 13; //This is the ASCII code for a newline character

        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                    try {
                        int bytesAvailable = mmInputStream.available();

                        if (bytesAvailable > 0) {
                            byte[] packetBytes = new byte[bytesAvailable];
                            mmInputStream.read(packetBytes);

                            for (int i = 0; i < bytesAvailable; i++) {
                                byte b = packetBytes[i];

                                if (b == delimiter) { // if new line character
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;

                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {

                                            if (getActivity() != null && getActivity() instanceof BluetoothResult)
                                                ((BluetoothResult) getActivity()).resultData(data);
                                        }
                                    });
                                } else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    } catch (IOException io) {
                        stopWorker = true;
                    }
                }
            }
        }).start();
    }

    @Override
    public void updateButtonText(String text) {
        btnConnect.setText(text);
    }

    @Override
    public void writeData(String data) {
        try {

            if (mmOutputStream != null) {
                if (data != null && !data.isEmpty()) {
                    mmOutputStream.write(data.getBytes());

                    updateLabel("Data Sent");
                }
            } else {
                updateLabel("Aktif bir bağlantı bulunmuyor!");
            }

        } catch (IOException e) {
            updateLabel(e.getMessage());
        }
    }

//    public void startJob() {
//        Boogaloo.setup().incremental().interval(10000).until(10000).execute(new BackoffTask() {
//            @Override
//            protected boolean shouldRetry() {
//
//                countUp();
//                return true;
//            }
//
//            @Override
//            public void run() {
//                String lbl = label.getText().toString();
//                UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
//                AppLogger.d(lbl);
//                String kar = lbl.substring(0,1);
//                if (kar.equals("C"))
//                {
//                    try {
//                        mmSocket.connect();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
////                    if (mmSocket != null && !mmSocket.isConnected()) {
////                        Execute();
////                    }
//                }
//                else{
//                    AppLogger.d("Cihaz bulamadım");
//                }
//            }
//        });
//    }

    public static boolean isBluetoothHeadsetConnected() {
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    return mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()
            && mBluetoothAdapter.getProfileConnectionState(BluetoothHeadset.HEADSET) == BluetoothHeadset.STATE_CONNECTED;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == AppCompatActivity.RESULT_OK) {

                    Intent bluetoothPicker = new Intent("android.bluetooth.devicepicker.action.LAUNCH");
                    startActivity(bluetoothPicker);

                } else {
                    // User did not enable Bluetooth or an error occured
                    showMessage(R.string.bt_not_enabled);
                }
        }
    }

    @Override
    protected void setUp(View view) {

    }

    protected void Execute(){
        new BluetoothConnectionAsyncTask(this).execute();
    }
    @Override
    public void updateLabel(String lbl) {
        label.setText(lbl);
    }

    @Override
    public void onDetach() {

        try {
            closeBT();
        } catch (IOException e) {

        }

        super.onDetach();
    }

    @OnClick(R.id.btnConnect)
    public void onViewClicked() {

        if (mmSocket != null && !mmSocket.isConnected()){
            new BluetoothConnectionAsyncTask(this).execute();
        }else{
            try {
                closeBT();
                updateLabel("Bağlantı kapatıldı");
                updateButtonText("Bağlan");
            } catch (IOException e) {
                updateLabel(e.getMessage());
            }
        }
    }
}
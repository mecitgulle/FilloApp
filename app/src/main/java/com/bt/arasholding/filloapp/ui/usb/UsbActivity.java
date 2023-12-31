package com.bt.arasholding.filloapp.ui.usb;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.ui.base.BaseActivity;
import com.bt.arasholding.filloapp.utils.AppLogger;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UsbActivity extends BaseActivity {

    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

    @BindView(R.id.spinnerdevicename)
    Spinner spDeviceName;
    @BindView(R.id.spinnerinterface)
    Spinner spInterface;
    @BindView(R.id.spinnerendpoint)
    Spinner spEndPoint;
    @BindView(R.id.info)
    TextView textInfo;
    @BindView(R.id.infointerface)
    TextView textInfoInterface;
    @BindView(R.id.infoendpoint)
    TextView textEndPoint;
    @BindView(R.id.txtLabel)
    TextView txtLabel;

    ArrayList<String> listDeviceName;
    ArrayList<UsbDevice> listUsbDevice;
    ArrayAdapter<String> adapterDevice;

    ArrayList<String> listInterface;
    ArrayList<UsbInterface> listUsbInterface;
    ArrayAdapter<String> adapterInterface;

    ArrayList<String> listEndPoint;
    ArrayList<UsbEndpoint> listUsbEndpoint;
    ArrayAdapter<String> adapterEndpoint;

    byte[] readBuffer;
    static int TIMEOUT = 0;
    boolean forceClaim = true;

    InputStream mmInputStream;
    int readBufferPosition;
    volatile boolean stopWorker;

    UsbManager usbManager;
    UsbDevice mDevice;
    PendingIntent permissionIntent;

    OnItemSelectedListener deviceOnItemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            UsbDevice device = listUsbDevice.get(position);

            String i = device.toString() + "\n" +
                    "DeviceID: " + device.getDeviceId() + "\n" +
                    "DeviceName: " + device.getDeviceName() + "\n" +
                    "DeviceClass: " + device.getDeviceClass() + " - "
                    + translateDeviceClass(device.getDeviceClass()) + "\n" +
                    "DeviceSubClass: " + device.getDeviceSubclass() + "\n" +
                    "VendorID: " + device.getVendorId() + "\n" +
                    "ProductID: " + device.getProductId() + "\n" +
                    "InterfaceCount: " + device.getInterfaceCount();
            textInfo.setText(i);

            checkUsbDevicve(device);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    OnItemSelectedListener interfaceOnItemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            UsbInterface selectedUsbIf = listUsbInterface.get(position);

            String sUsbIf = "\n" + selectedUsbIf.toString() + "\n"
                    + "Id: " + selectedUsbIf.getId() + "\n"
                    + "InterfaceClass: " + selectedUsbIf.getInterfaceClass() + "\n"
                    + "InterfaceProtocol: " + selectedUsbIf.getInterfaceProtocol() + "\n"
                    + "InterfaceSubclass: " + selectedUsbIf.getInterfaceSubclass() + "\n"
                    + "EndpointCount: " + selectedUsbIf.getEndpointCount();

            textInfoInterface.setText(sUsbIf);
            checkUsbInterface(selectedUsbIf);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    OnItemSelectedListener endpointOnItemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            UsbEndpoint selectedEndpoint = listUsbEndpoint.get(position);

            String sEndpoint = "\n" + selectedEndpoint.toString() + "\n"
                    + translateEndpointType(selectedEndpoint.getType());

            textEndPoint.setText(sEndpoint);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


//    private final BroadcastReceiver usbReceiver = new BroadcastReceiver() {
//
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (ACTION_USB_PERMISSION.equals(action)) {
//                synchronized (this) {
//                    UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
//
//                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
//                        if (device != null) {
//                            //call method to set up device communication
//                        }
//                    } else {
//
//                    }
//                }
//            }
//        }
//    };

    public static Intent getStartIntent(Context context) {
        return new Intent(context, UsbActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usb);

        setUnBinder(ButterKnife.bind(this));

        setUp();
    }

    @Override
    protected void setUp() {

        permissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
//        registerReceiver(usbReceiver, filter);

        checkDeviceInfo();
    }

    @OnClick(R.id.check)
    public void onViewClicked() {
        beginListenForData();
    }

    private void checkDeviceInfo() {
        listDeviceName = new ArrayList<String>();
        listUsbDevice = new ArrayList<UsbDevice>();

        usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

        HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

        while (deviceIterator.hasNext()) {
            UsbDevice device = deviceIterator.next();
            listDeviceName.add(device.getDeviceName());

            mDevice = device;
            listUsbDevice.add(device);
        }

        if (!usbManager.hasPermission(mDevice)){
            usbManager.requestPermission(mDevice, permissionIntent);
        }

        textInfo.setText("");
        textInfoInterface.setText("");
        textEndPoint.setText("");

        adapterDevice = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listDeviceName);
        adapterDevice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDeviceName.setAdapter(adapterDevice);
        spDeviceName.setOnItemSelectedListener(deviceOnItemSelectedListener);
    }

    private void checkUsbDevicve(UsbDevice d) {
        listInterface = new ArrayList<String>();
        listUsbInterface = new ArrayList<UsbInterface>();

        for (int i = 0; i < d.getInterfaceCount(); i++) {
            UsbInterface usbif = d.getInterface(i);
            listInterface.add(usbif.toString());
            listUsbInterface.add(usbif);
        }

        adapterInterface = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listInterface);
        adapterDevice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spInterface.setAdapter(adapterInterface);
        spInterface.setOnItemSelectedListener(interfaceOnItemSelectedListener);
    }

    public void beginListenForData() {

        final Handler handler = new Handler();
        final byte delimiter = 13; //This is the ASCII code for a newline character

        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];

        UsbInterface intf = listUsbDevice.get(0).getInterface(0);
        UsbEndpoint endpoint = intf.getEndpoint(0);
        UsbDeviceConnection connection = usbManager.openDevice(listUsbDevice.get(0));

        if (connection.claimInterface(intf, forceClaim)) {

            AppLogger.e("claimInterface SUCCESS");

            new Thread(new Runnable() {
                @Override
                public void run() {

                    int result = connection.bulkTransfer(endpoint, readBuffer, readBuffer.length, TIMEOUT);

                    if (result > 0){
                        AppLogger.e("result > 0");
                    }else{
                        AppLogger.e("else");
                    }
                }
            }).start();
        } else {

        }

    }

    private void checkUsbInterface(UsbInterface uif) {
        listEndPoint = new ArrayList<String>();
        listUsbEndpoint = new ArrayList<UsbEndpoint>();

        for (int i = 0; i < uif.getEndpointCount(); i++) {
            UsbEndpoint usbEndpoint = uif.getEndpoint(i);
            listEndPoint.add(usbEndpoint.toString());
            listUsbEndpoint.add(usbEndpoint);
        }

        adapterEndpoint = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listEndPoint);
        adapterEndpoint.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEndPoint.setAdapter(adapterEndpoint);
        spEndPoint.setOnItemSelectedListener(endpointOnItemSelectedListener);
    }

    private String translateEndpointType(int type) {
        switch (type) {
            case UsbConstants.USB_ENDPOINT_XFER_CONTROL:
                return "USB_ENDPOINT_XFER_CONTROL (endpoint zero)";
            case UsbConstants.USB_ENDPOINT_XFER_ISOC:
                return "USB_ENDPOINT_XFER_ISOC (isochronous endpoint)";
            case UsbConstants.USB_ENDPOINT_XFER_BULK:
                return "USB_ENDPOINT_XFER_BULK (bulk endpoint)";
            case UsbConstants.USB_ENDPOINT_XFER_INT:
                return "USB_ENDPOINT_XFER_INT (interrupt endpoint)";
            default:
                return "unknown";
        }
    }

    private String translateDeviceClass(int deviceClass) {
        switch (deviceClass) {
            case UsbConstants.USB_CLASS_APP_SPEC:
                return "Application specific USB class";
            case UsbConstants.USB_CLASS_AUDIO:
                return "USB class for audio devices";
            case UsbConstants.USB_CLASS_CDC_DATA:
                return "USB class for CDC devices (communications device class)";
            case UsbConstants.USB_CLASS_COMM:
                return "USB class for communication devices";
            case UsbConstants.USB_CLASS_CONTENT_SEC:
                return "USB class for content security devices";
            case UsbConstants.USB_CLASS_CSCID:
                return "USB class for content smart card devices";
            case UsbConstants.USB_CLASS_HID:
                return "USB class for human interface devices (for example, mice and keyboards)";
            case UsbConstants.USB_CLASS_HUB:
                return "USB class for USB hubs";
            case UsbConstants.USB_CLASS_MASS_STORAGE:
                return "USB class for mass storage devices";
            case UsbConstants.USB_CLASS_MISC:
                return "USB class for wireless miscellaneous devices";
            case UsbConstants.USB_CLASS_PER_INTERFACE:
                return "USB class indicating that the class is determined on a per-interface basis";
            case UsbConstants.USB_CLASS_PHYSICA:
                return "USB class for physical devices";
            case UsbConstants.USB_CLASS_PRINTER:
                return "USB class for printers";
            case UsbConstants.USB_CLASS_STILL_IMAGE:
                return "USB class for still image devices (digital cameras)";
            case UsbConstants.USB_CLASS_VENDOR_SPEC:
                return "Vendor specific USB class";
            case UsbConstants.USB_CLASS_VIDEO:
                return "USB class for video devices";
            case UsbConstants.USB_CLASS_WIRELESS_CONTROLLER:
                return "USB class for wireless controller devices";
            default:
                return "Unknown USB class!";

        }
    }
}

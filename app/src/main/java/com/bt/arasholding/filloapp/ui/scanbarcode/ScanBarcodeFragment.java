package com.bt.arasholding.filloapp.ui.scanbarcode;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;

import com.bt.arasholding.filloapp.ui.base.BaseFragment;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanBarcodeFragment extends BaseFragment implements ScanBarcodeMvpView {

    public static final String TAG = "ScanBarcodeFragment";

    private static final int REQUEST_PERMISSION_CAMERA = 1;

    private String codeFormat;
    private String codeContent;
    private String noResultErrorMsg = "No content";

    public static ScanBarcodeFragment newInstance(Bundle args) {
        ScanBarcodeFragment fragment = new ScanBarcodeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void setUp(View view) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!hasPermission(Manifest.permission.CAMERA))
            requestPermissionsSafely(new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);

        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
        // use forSupportFragment or forFragment method to use fragments instead of activity
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        assert getArguments() != null;
        integrator.setPrompt(getArguments().getString("prompt"));
        integrator.setOrientationLocked(true);
        integrator.setBeepEnabled(true);
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            //we have a result
            codeContent = scanningResult.getContents();
            codeFormat = scanningResult.getFormatName();
            // send received data
            ((ScanResultReceiver) getActivity()).scanResultData(codeFormat, codeContent);

        } else {
            // send exception
            ((ScanResultReceiver) getActivity()).scanResultData(new NoScanResultException(noResultErrorMsg));
        }
    }

    public interface ScanResultReceiver {
        /**
         * function to receive scanresult
         *
         * @param codeFormat  format of the barcode scanned
         * @param codeContent data of the barcode scanned
         */
        void scanResultData(String codeFormat, String codeContent);

        void scanResultData(NoScanResultException noScanData);
    }

    public class NoScanResultException extends Exception {
        public NoScanResultException() {
        }

        public NoScanResultException(String msg) {
            super(msg);
        }

        public NoScanResultException(Throwable cause) {
            super(cause);
        }

        public NoScanResultException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }
}
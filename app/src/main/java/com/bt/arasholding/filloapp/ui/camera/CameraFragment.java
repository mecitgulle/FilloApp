package com.bt.arasholding.filloapp.ui.camera;

import android.Manifest;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.di.component.ActivityComponent;
import com.bt.arasholding.filloapp.ui.base.BaseFragment;
import com.bt.arasholding.filloapp.utils.BeepManager;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CameraFragment extends BaseFragment
        implements CameraMvpView, ZXingScannerView.ResultHandler {

    public static final String TAG = "CameraFragment";
    private static final int REQUEST_PERMISSION_CAMERA = 2;

    @Inject
    BeepManager beepManager;

    @BindView(R.id.cameraView)
    ZXingScannerView mScannerView;
    @BindView(R.id.txtMessage)
    TextView txtMessage;

    public interface ScanResult {
        void scanResultData(String codeContent);
    }

    public static CameraFragment newInstance(Bundle args) {
        CameraFragment fragment = new CameraFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!hasPermission(Manifest.permission.CAMERA))
            requestPermissionsSafely(new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_barcode, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
        }

        List<BarcodeFormat> barcodeFormats = new ArrayList<>();
        barcodeFormats.add(BarcodeFormat.CODE_128);
        barcodeFormats.add(BarcodeFormat.QR_CODE);
        barcodeFormats.add(BarcodeFormat.DATA_MATRIX);

        mScannerView.setAspectTolerance(0.5f);
        mScannerView.setFormats(barcodeFormats);
        mScannerView.setAutoFocus(true);
        mScannerView.setResultHandler(this);

        setUp(view);

        return view;
    }

    @Override
    protected void setUp(View view) {
        txtMessage.setText(getArguments().getString("prompt"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();

        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();

        mScannerView.stopCamera();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        switch (requestCode) {

            case REQUEST_PERMISSION_CAMERA:
                if (hasPermission(Manifest.permission.CAMERA))
                    mScannerView.startCamera();
                else
                    getActivity().finish();
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    @Override
    public void handleResult(Result rawResult) {
        beepManager.playBeepSoundAndVibrate();
        showMessage(rawResult.getText());

        ((ScanResult) getActivity()).scanResultData(rawResult.getText());

        if (mScannerView != null)
            mScannerView.resumeCameraPreview(this);
    }
}

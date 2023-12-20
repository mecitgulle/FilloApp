package com.bt.arasholding.filloapp.ui.barcodeprinter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.ui.base.BaseActivity;
import com.bt.arasholding.filloapp.ui.bluetooth.BluetoothFragment;
import com.bt.arasholding.filloapp.ui.bluetooth.BluetoothFragmentMvpView;
import com.bt.arasholding.filloapp.ui.scanbarcode.ScanBarcodeFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class BarcodePrinterActivity extends BaseActivity
        implements BarcodePrinterMvpView, ScanBarcodeFragment.ScanResultReceiver {

    @Inject
    BarcodePrinterMvpPresenter<BarcodePrinterMvpView> mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edtBarcode)
    EditText edtBarcode;
    @BindView(R.id.btn_barkod_okut)
    Button btnBarcodeOkut;

    String secilenBarkodTipi = "IRSALIYE";

    public static Intent getStartIntent(Context context) {
        return new Intent(context, BarcodePrinterActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_printer);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();
    }

    @Override
    protected void setUp() {

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(BluetoothFragment.TAG);
        if (fragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frame_bluetooth, BluetoothFragment.newInstance(), BluetoothFragment.TAG)
                    .commit();

            edtBarcode.setText("");
        }

        edtBarcode.requestFocus();
        mPresenter.onViewPrepared();
    }

    @OnEditorAction(R.id.edtBarcode)
    public boolean onEditorAction(TextView view, int keyCode, KeyEvent keyEvent) {

        String textBarcode = view.getText().toString();

        if (textBarcode.isEmpty())
            return false;

        showMessage(textBarcode);

        if (keyEvent != null) {
            // Barcode finger scanner

            if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                edtBarcode.setText("");
                mPresenter.getBarcodeText(textBarcode, secilenBarkodTipi);
            }
        } else {
            // Keyboard

            hideKeyboard();
            mPresenter.getBarcodeText(textBarcode, secilenBarkodTipi);
        }

        return false;
    }

    @Override
    public void onFragmentDetached(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .remove(fragment)
                    .commitNow();
        }
    }

    @Override
    public void onBackPressed() {

        onFragmentDetached(BluetoothFragment.TAG);

        super.onBackPressed();
    }

    @Override
    public void printBarcode(String barcodetext) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(BluetoothFragment.TAG);

        if (fragment instanceof BluetoothFragmentMvpView)
            ((BluetoothFragmentMvpView) fragment).writeData(barcodetext);
    }

    @OnCheckedChanged(R.id.rd1)
    void onIrsaliyeBarkoduSelected(CompoundButton button, boolean checked) {
        this.secilenBarkodTipi = checked ? "IRSALIYE" : "KOLIID";
        updateButtonText(secilenBarkodTipi + " BARKODU OKUT");
    }

    @OnClick(R.id.btn_barkod_okut)
    public void onBtnBarkodOkutClicked() {
        Bundle fragmentArgs = new Bundle();
        fragmentArgs.putString("prompt", secilenBarkodTipi + " BARKODU OKUTUNUZ");

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.scan_fragment, ScanBarcodeFragment.newInstance(fragmentArgs), ScanBarcodeFragment.TAG)
                .commit();
    }

    @Override
    public void scanResultData(String codeFormat, String codeContent) {
        if (codeContent != null) {
            edtBarcode.setText(codeContent);
            mPresenter.getBarcodeText(codeContent, secilenBarkodTipi);
        }
    }

    @Override
    public void scanResultData(ScanBarcodeFragment.NoScanResultException noScanData) {
        showMessage(noScanData.getMessage());
    }

    @Override
    public void updateButtonText(String text) {
        btnBarcodeOkut.setText(text);
    }
}
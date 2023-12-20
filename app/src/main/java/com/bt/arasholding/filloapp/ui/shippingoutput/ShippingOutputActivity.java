package com.bt.arasholding.filloapp.ui.shippingoutput;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.ui.base.BaseActivity;
import com.bt.arasholding.filloapp.ui.scanbarcode.ScanBarcodeFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShippingOutputActivity extends BaseActivity implements
        ShippingOutputMvpView,
        ScanBarcodeFragment.ScanResultReceiver {

    private static final String TAG = "ShippingOutputActivity";

    @Inject
    ShippingOutputMvpPresenter<ShippingOutputMvpView> mPresenter;

    @BindView(R.id.edt_sefer_no)
    EditText edtSeferNo;

    @BindView(R.id.txt_plaka)
    TextView txtPlaka;

    @BindView(R.id.txt_varis_sube)
    TextView txtVarisSube;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shippingoutput);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ShippingOutputActivity.class);
        return intent;
    }

    @Override
    protected void setUp() {

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @OnClick(R.id.btn_tracking_shipping)
    void onServerLoginClick(View v) {
        mPresenter.onShippingTrackingClick(edtSeferNo.getText().toString());
    }

    @OnClick(R.id.qb_arac_cikis)
    void onQbAracCikisClick(View v) {
        mPresenter.onQbAracCikisClick(edtSeferNo.getText().toString());
    }

    @Override
    public void updatePlaka(String plaka) {
        txtPlaka.setText(plaka);
    }

    @Override
    public void updateVarisSube(String varisSube) {
        txtVarisSube.setText(varisSube);
    }

    @OnClick(R.id.btn_barkod_okut)
    public void onBtnBarkodOkutClicked() {
        Bundle fragmentArgs = new Bundle();
        fragmentArgs.putString("prompt", "Araç çıkış barkodu okutunuz");

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.scan_fragment, ScanBarcodeFragment.newInstance(fragmentArgs), ScanBarcodeFragment.TAG)
                .commit();
    }

    @Override
    public void scanResultData(String codeFormat, String codeContent) {
        if (codeContent != null) {
            edtSeferNo.setText(codeContent);
            mPresenter.onShippingTrackingClick(edtSeferNo.getText().toString());
        }
    }

    @Override
    public void scanResultData(ScanBarcodeFragment.NoScanResultException noScanData) {
        showMessage(noScanData.getMessage());
    }
}
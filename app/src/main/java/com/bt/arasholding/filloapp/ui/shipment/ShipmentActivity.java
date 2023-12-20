package com.bt.arasholding.filloapp.ui.shipment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.model.Barcode;
import com.bt.arasholding.filloapp.data.network.model.Sefer;
import com.bt.arasholding.filloapp.ui.base.BaseActivity;
import com.bt.arasholding.filloapp.ui.bluetooth.BluetoothFragment;
import com.bt.arasholding.filloapp.ui.cargobarcode.CargoBarcodeActivity;
import com.bt.arasholding.filloapp.ui.scanbarcode.ScanBarcodeFragment;
import com.bt.arasholding.filloapp.ui.shipment.info.ShipmentInfoFragment;
import com.bt.arasholding.filloapp.ui.shipment.search.SearchShipmentFragment;
import com.bt.arasholding.filloapp.ui.textbarcode.BarcodeAdapter;
import com.bt.arasholding.filloapp.utils.AppConstants;
import com.manojbhadane.QButton;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShipmentActivity extends BaseActivity implements
        ShipmentMvpView,
        ScanBarcodeFragment.ScanResultReceiver,
        BluetoothFragment.BluetoothResult,
        ShipmentInfoFragment.ShipmentFragmentCallback,
        SearchShipmentFragment.CallbackSearchShipment {

    private static final String TAG = "ShipmentActivity";
    String cevap = "";
    AlertDialog mAlertDialog;

    @Inject
    ShipmentMvpPresenter<ShipmentMvpView> mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.frame_shipment_scanner)
    FrameLayout frameScanner;
    @BindView(R.id.btn_barkod_okut)
    QButton btnBarkodOkut;
    @BindView(R.id.txtCount)
    TextView txtCount;
    @BindView(R.id.recyclerBarcodeList)
    RecyclerView recyclerBarcodeList;

    private boolean isShipmentBarcode = true;

    private int currentCount = 0;
    String islemTipi = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ShipmentActivity.class);
        return intent;
    }

    @Override
    protected void setUp() {

        if (getIntent() != null)
            islemTipi = getIntent().getStringExtra("islemTipi");

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recyclerBarcodeList.setLayoutManager(new LinearLayoutManager(this));
        recyclerBarcodeList.setHasFixedSize(true);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_shipment, SearchShipmentFragment.newInstance(), SearchShipmentFragment.TAG)
                .commit();

        mPresenter.onViewPrepared();
    }

    @OnClick({R.id.btn_barkod_okut})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_barkod_okut:

                Bundle fragmentArgs = new Bundle();
                fragmentArgs.putString("prompt", "Sefer barkodu okutunuz");

                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frame_camera, ScanBarcodeFragment.newInstance(fragmentArgs), ScanBarcodeFragment.TAG)
                        .commit();
                break;
        }
    }

    @Override
    public void scanResultData(String codeFormat, String codeContent) {
        if (codeContent != null) {
            mPresenter.getShipmentInformationByShipmentBarcode(codeContent, islemTipi);
        }
    }

    @Override
    public void scanResultData(ScanBarcodeFragment.NoScanResultException noScanData) {
        showMessage(noScanData.getMessage());
    }

    @Override
    public void openCargoBarcodeActivity(String islemTipi, Sefer shipment) {
        Intent intent = CargoBarcodeActivity.getStartIntent(this);
        intent.putExtra("islemTipi", islemTipi);
        intent.putExtra("shipment", shipment);

        startActivity(intent);
    }

    @Override
    public void showCameraScanner() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(BluetoothFragment.TAG);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .remove(fragment)
                    .commitNow();
        } else {
            frameScanner.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showBluetoothScanner() {

        btnBarkodOkut.setVisibility(View.INVISIBLE);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_shipment_scanner, BluetoothFragment.newInstance(), BluetoothFragment.TAG)
                .commit();
    }

    @Override
    public void showShipmentFragment(Sefer sefer) {

        onFragmentDetached(SearchShipmentFragment.TAG);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_shipment, ShipmentInfoFragment.newInstance(sefer), ShipmentInfoFragment.TAG)
                .commit();

        mPresenter.setShipment(sefer);

        isShipmentBarcode = false; // Dağıtım ve hat yükleme okutması durumu için hazır hale getirilir
    }

    @Override
    public void updateBarcodeList(List<Barcode> barcodeList) {
        BarcodeAdapter adapter = new BarcodeAdapter(barcodeList, this);

        adapter.notifyDataSetChanged();
        recyclerBarcodeList.setAdapter(adapter);
    }

    @Override
    public void incrementCount() {
        txtCount.setText(String.format("%s", ++currentCount));
    }

    @Override
    public void updateToolbarTitle() {
        if (islemTipi.equals(AppConstants.HAT_YUKLEME)) {
            toolbar.setTitle("Hat Seferi Okut");
        }
        else if(islemTipi.equals(AppConstants.YUKLEME)){
            toolbar.setTitle("Yükleme Seferi Okut");
        }
        else if(islemTipi.equals(AppConstants.INDIRME)){
            toolbar.setTitle("İndirme Seferi Okut");
        }
        else {
            toolbar.setTitle("Dağıtım Seferi Okut");
        }
    }

    @Override
    public void decideProcessFragment() {

    }

    @Override
    public void resultData(String codeContent) {
        if (codeContent != null) {
            if (isShipmentBarcode) {
                mPresenter.getShipmentInformationByShipmentBarcode(codeContent, islemTipi);
            } else {
                if (islemTipi.equals(AppConstants.DAGITIM))
                    mPresenter.dagitim(codeContent);
                else if (islemTipi.equals(AppConstants.HAT_YUKLEME))
                    mPresenter.hatyukleme(codeContent);
                else if (islemTipi.equals(AppConstants.YUKLEME))
                    mPresenter.yukleme(codeContent);
                else if (islemTipi.equals(AppConstants.INDIRME))
                    mPresenter.indirme(codeContent);
            }
        }
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
        onFragmentDetached(ShipmentInfoFragment.TAG);
        onFragmentDetached(SearchShipmentFragment.TAG);

        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void newShipmentClicked() {
        currentCount = 0;
        txtCount.setText(String.format("%s", currentCount));

        onFragmentDetached(ShipmentInfoFragment.TAG);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_shipment, SearchShipmentFragment.newInstance(), SearchShipmentFragment.TAG)
                .commit();

        isShipmentBarcode = true;

        mPresenter.deleteBarcodes();
    }

    @Override
    public void searchShipment(String shipmentNumber) {
        mPresenter.getShipmentInformationByShipmentId(shipmentNumber, islemTipi);
    }

    @Override
    public String hatYuklemeRotaKontrolDialog(String barcode, String shipment) {
        hideLoading();
//        for (int i = 0; i < 3; i++) {
//
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    playBeepSound();
//
//                }
//
//            }, 1000);
//        }
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ShipmentActivity.this);
        alertDialog.setTitle(Html.fromHtml("<font color='#d34836'>UYARI</font>"));
        alertDialog.setIcon(R.drawable.ic_alert);
        alertDialog.setCancelable(false);
        alertDialog.setMessage("Okutulan ATF 'nin Hattı Uygun Değil! \nDevam edilsin mi ?");
        alertDialog.setPositiveButton("EVET", (dialog, which) -> {
            dialog.dismiss();
            cevap = "EVET";
            mPresenter.hatyuklemeDevam(barcode, shipment, cevap);
        });

        alertDialog.setNegativeButton("HAYIR", (dialog, which) -> {
            dialog.dismiss();
            cevap = "HAYIR";
            mPresenter.hatyuklemeDevam(barcode, shipment, cevap);
        });

        mAlertDialog = alertDialog.create();
        mAlertDialog.show();

        return cevap;
    }

    @Override
    public boolean isAlertDialogShowing() {
        if (mAlertDialog != null) {
            if (mAlertDialog.isShowing()) {
                return true;
            }
        } else {
            return false;
        }

        return false;
    }
}

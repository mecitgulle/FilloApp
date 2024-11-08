package com.bt.arasholding.filloapp.ui.cargobarcode;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.network.model.Sefer;
import com.bt.arasholding.filloapp.ui.base.BaseActivity;
import com.bt.arasholding.filloapp.ui.camera.CameraFragment;
import com.bt.arasholding.filloapp.ui.delivercargo.DeliverCargoActivity;
import com.bt.arasholding.filloapp.ui.shipment.ShipmentActivity;
import com.bt.arasholding.filloapp.ui.shipment.lazer.ShipmentLazerActivity;
import com.bt.arasholding.filloapp.utils.AppConstants;
import com.manojbhadane.QButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CargoBarcodeActivity extends BaseActivity implements
        CargoBarcodeMvpView,
        CameraFragment.ScanResult {

    private String islemTipi;
    private Sefer shipment;
    boolean dialogShown = true;
    String cevap = "";
    AlertDialog mAlertDialog;

    @Inject
    CargoBarcodeMvpPresenter<CargoBarcodeMvpView> mPresenter;

    @BindView(R.id.txtSayac)
    TextView txtSayac;
    @BindView(R.id.btn_sefer_okut)
    QButton btn_sefer_okut;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, CargoBarcodeActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();
    }

    @Override
    protected void setUp() {

        if (getIntent() != null) {
            islemTipi = getIntent().getStringExtra("islemTipi");
            shipment = (Sefer) getIntent().getSerializableExtra("shipment");
        }

        Bundle fragmentArgs = new Bundle();

        if (islemTipi.equals(AppConstants.TOPLUTESLIMAT)) {
            fragmentArgs.putString("prompt", "KTF Barkodu Okutunuz (TOPLU TESLİMAT)");
        } else if (islemTipi.equals(AppConstants.INDIRME)) {
            btn_sefer_okut.setVisibility(View.VISIBLE);
            if (shipment != null)
                fragmentArgs.putString("prompt", String.format("İNDİRME- %s - %s", shipment.getSEFERID(), shipment.getPLAKA()));
            else
                fragmentArgs.putString("prompt", "İNDİRME");
        } else if (islemTipi.equals(AppConstants.YUKLEME)) {
            btn_sefer_okut.setVisibility(View.VISIBLE);
            if (shipment != null)
                fragmentArgs.putString("prompt", String.format("YÜKLEME - %s - %s", shipment.getSEFERID(), shipment.getPLAKA()));
            else
                fragmentArgs.putString("prompt", "YÜKLEME");
        } else if (islemTipi.equals(AppConstants.HAT_YUKLEME)) {
            fragmentArgs.putString("prompt", String.format("HAT YÜKLEME - %s - %s", shipment.getSEFERID(), shipment.getPLAKA()));
        } else if (islemTipi.equals(AppConstants.DAGITIM)) {
            fragmentArgs.putString("prompt", String.format("DAĞITIM - %s - %s", shipment.getSEFERID(), shipment.getPLAKA()));
        } else if (islemTipi.equals(AppConstants.TOPLUDEVIR)) {
            fragmentArgs.putString("prompt", "KTF Barkodu Okutunuz (TOPLU DEVİR)");
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_scanner, CameraFragment.newInstance(fragmentArgs), CameraFragment.TAG)
                .commit();
    }

    @OnClick(R.id.btn_sefer_okut)
    public void onBtnSeferOkutClicked() {
        Intent intent = ShipmentActivity.getStartIntent(this);
        intent.putExtra("islemTipi", islemTipi);
        startActivity(intent);
    }

    @Override
    public void updateSayac() {
        int sayac = Integer.valueOf(txtSayac.getText().toString());

        sayac++;

        txtSayac.setText(sayac + "");
    }

    @Override
    public void scanResultData(String codeContent) {
        decide(codeContent);
    }

    private void decide(String barcode) {

        switch (islemTipi) {
            case AppConstants.YUKLEME:
                if (shipment != null)
                    mPresenter.yuklemewithsefer(barcode, shipment.getSEFERID());
                else
                    mPresenter.yukleme(barcode);
                break;
            case AppConstants.INDIRME:
                if (shipment != null)
                    mPresenter.indirmewithsefer(barcode, shipment.getSEFERID());
                else
                    mPresenter.indirme(barcode);
                break;
            case AppConstants.DAGITIM:
                mPresenter.dagitim(barcode, shipment.getSEFERID());
                break;
            case AppConstants.HAT_YUKLEME:
                mPresenter.hatyukleme(barcode, shipment.getSEFERID());
                break;
            default:
                Intent intent = DeliverCargoActivity.getStartIntent(CargoBarcodeActivity.this);
                intent.putExtra("TrackId", barcode);
                intent.putExtra("islemTipi", islemTipi);

                startActivity(intent);
                break;
        }
    }

    @Override
    public void showErrorMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("UYARI");
        builder.setMessage(message);

        builder.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Tamam butonuna basılınca yapılacaklar

            }
        });

        if (dialogShown) {
            builder.show();
            dialogShown = false;
        }

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
//            }, 1500);
//        }
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CargoBarcodeActivity.this);
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
    @Override
    public void showTokenExpired(){
        BaseActivity.showTokenExpired(CargoBarcodeActivity.this,"Oturum süresi doldu. Tekrar giriş yapınız","UYARI");
    }

}
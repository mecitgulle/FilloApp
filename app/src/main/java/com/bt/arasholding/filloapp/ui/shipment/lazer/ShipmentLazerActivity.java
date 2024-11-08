package com.bt.arasholding.filloapp.ui.shipment.lazer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.model.Barcode;
import com.bt.arasholding.filloapp.data.network.model.Sefer;
import com.bt.arasholding.filloapp.ui.base.BaseActivity;
import com.bt.arasholding.filloapp.ui.lazer.LazerActivity;
import com.bt.arasholding.filloapp.ui.lazer.LazerMvpPresenter;
import com.bt.arasholding.filloapp.ui.lazer.LazerMvpView;
import com.bt.arasholding.filloapp.ui.shipment.ShipmentActivity;
import com.bt.arasholding.filloapp.ui.shipment.ShipmentMvpView;
import com.bt.arasholding.filloapp.ui.shipment.info.ShipmentInfoFragment;
import com.bt.arasholding.filloapp.ui.shipment.search.SearchShipmentFragment;
import com.bt.arasholding.filloapp.ui.textbarcode.BarcodeAdapter;
import com.bt.arasholding.filloapp.utils.AppConstants;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class ShipmentLazerActivity extends BaseActivity implements
        ShipmentLazerMvpView {
    private String islemTipi;

    @Inject
    ShipmentLazerMvpPresenter<ShipmentLazerMvpView> mPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_sefer_no)
    EditText edt_sefer_no;
    @BindView(R.id.txtSayac)
    TextView txtSayac;
    @BindView(R.id.recyclerBarcodeList)
    RecyclerView recyclerBarcodeList;
    @BindView(R.id.edtBarcodeText)
    EditText edtBarcodeText;
    @BindView(R.id.btn_search_shipping)
    Button btn_search_shipping;


    private boolean isShipmentBarcode = true;
    private int currentCount = 0;
    String cevap = "";
    AlertDialog mAlertDialog;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ShipmentLazerActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment_lazer);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        edt_sefer_no.setText("");
        edt_sefer_no.setFocusableInTouchMode(true);
        edt_sefer_no.setFocusable(true);
        edt_sefer_no.requestFocus();

        edt_sefer_no.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edt_sefer_no.getWindowToken(), 0);
            }
        }, 100);

        setUp();


    }

    @Override
    protected void setUp() {

        if (getIntent() != null) {
            islemTipi = getIntent().getStringExtra("islemTipi");
        }
        updateToolbarTitle();

        edtBarcodeText.setText("");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);  // Son eklenen en üstte olacak
        layoutManager.setStackFromEnd(true);   // Liste en sondan başlayarak dizilecek
        recyclerBarcodeList.setLayoutManager(layoutManager);
        recyclerBarcodeList.setHasFixedSize(true);
        mPresenter.deleteBarcodes(islemTipi);
    }

    public void updateToolbarTitle() {
        if (islemTipi.equals(AppConstants.HAT_YUKLEME)) {
            toolbar.setTitle("Hat Seferi Okut");
        } else if (islemTipi.equals(AppConstants.YUKLEME)) {
            toolbar.setTitle("Yükleme Seferi Okut");
        } else if (islemTipi.equals(AppConstants.INDIRME)) {
            toolbar.setTitle("İndirme Seferi Okut");
        } else {
            toolbar.setTitle("Dağıtım Seferi Okut");
        }
    }

    @OnClick(R.id.btn_search_shipping)
    public void onViewClicked() {

        if (!edt_sefer_no.getText().toString().isEmpty()) {
            mPresenter.getShipmentInformationByShipmentBarcode(edt_sefer_no.getText().toString(), islemTipi);
            edtBarcodeText.requestFocus();
            edtBarcodeText.setFocusableInTouchMode(true);
            edtBarcodeText.setFocusable(true);

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtBarcodeText.getWindowToken(), 0);
        }
        mPresenter.deleteBarcodes(islemTipi);
    }

    @Override
    public void updateBarcodeList(List<Barcode> barcodeList) {
        BarcodeAdapter adapter = new BarcodeAdapter(barcodeList, this);

        adapter.notifyDataSetChanged();
        recyclerBarcodeList.setAdapter(adapter);

    }

    @OnEditorAction(R.id.edt_sefer_no)
    public boolean onEditorAction(TextView view, int keyCode, KeyEvent keyEvent) {

        String textSeferBarcode = view.getText().toString();

        if (textSeferBarcode.isEmpty())
            return false;

        showMessage(textSeferBarcode);

        if (keyEvent != null) {
            // Barcode finger scanner

            if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                mPresenter.getShipmentInformationByShipmentBarcode(textSeferBarcode, islemTipi);
                edt_sefer_no.setText("");
                edt_sefer_no.setFocusableInTouchMode(true);
                edt_sefer_no.setFocusable(true);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edt_sefer_no.getWindowToken(), 0);
            }
        } else {
            // Keyboard

            hideKeyboard();
            mPresenter.getShipmentInformationByShipmentBarcode(textSeferBarcode, islemTipi);
        }

        return false;
    }

    @OnEditorAction(R.id.edtBarcodeText)
    public boolean onEditorActionBarcode(TextView view, int keyCode, KeyEvent keyEvent) {

        String textBarcode = view.getText().toString();

        if (textBarcode.isEmpty())
            return false;

        showMessage(textBarcode);
        edtBarcodeText.clearFocus();
        if (keyEvent != null) {
            // Barcode finger scanner

            if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//                edtBarcodeText.clearFocus();
                if (islemTipi.equals(AppConstants.DAGITIM))
                    mPresenter.dagitim(textBarcode);
                else if (islemTipi.equals(AppConstants.HAT_YUKLEME))
                    mPresenter.hatyukleme(textBarcode);
                else if (islemTipi.equals(AppConstants.YUKLEME))
                    mPresenter.yukleme(textBarcode);
                else if (islemTipi.equals(AppConstants.INDIRME))
                    mPresenter.indirme(textBarcode);
            }
        } else {
            // Keyboard

            hideKeyboard();


            if (islemTipi.equals(AppConstants.DAGITIM))
                mPresenter.dagitim(textBarcode);
            else if (islemTipi.equals(AppConstants.HAT_YUKLEME))
                mPresenter.hatyukleme(textBarcode);
            else if (islemTipi.equals(AppConstants.YUKLEME))
                mPresenter.yukleme(textBarcode);
            else if (islemTipi.equals(AppConstants.INDIRME))
                mPresenter.indirme(textBarcode);
        }

        return false;
    }

    @Override
    public void incrementCount() {
        edtBarcodeText.setText("");
        edtBarcodeText.requestFocus();
        edtBarcodeText.setFocusableInTouchMode(true);
        edtBarcodeText.setFocusable(true);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtBarcodeText.getWindowToken(), 0);
        txtSayac.setText(String.format("%s", ++currentCount));
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ShipmentLazerActivity.this);
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
    public void clearBarcodeText() {
        edtBarcodeText.setText("");
        edtBarcodeText.requestFocus();

        edtBarcodeText.setFocusableInTouchMode(true);
        edtBarcodeText.setFocusable(true);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtBarcodeText.getWindowToken(), 0);
    }

    @Override
    public void showShipmentLazer(Sefer sefer) {

        edt_sefer_no.setText(String.format("%s - %s", sefer.getPLAKA(), sefer.getSEFERID()));
        mPresenter.setShipment(sefer);

        isShipmentBarcode = false; // Dağıtım ve hat yükleme okutması durumu için hazır hale getirilir
        edtBarcodeText.requestFocus();
    }

    @Override
    public void showTokenExpired(){
        BaseActivity.showTokenExpired(ShipmentLazerActivity.this,"Oturum süresi doldu. Tekrar giriş yapınız","UYARI");
    }

}
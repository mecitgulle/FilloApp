package com.bt.arasholding.filloapp.ui.lazer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.network.model.Sefer;
import com.bt.arasholding.filloapp.ui.base.BaseActivity;
import com.bt.arasholding.filloapp.ui.camera.CameraFragment;
import com.bt.arasholding.filloapp.ui.cargobarcode.CargoBarcodeActivity;
import com.bt.arasholding.filloapp.ui.cargobarcode.CargoBarcodeMvpPresenter;
import com.bt.arasholding.filloapp.ui.cargobarcode.CargoBarcodeMvpView;
import com.bt.arasholding.filloapp.ui.delivercargo.DeliverCargoActivity;
import com.bt.arasholding.filloapp.ui.shipment.ShipmentActivity;
import com.bt.arasholding.filloapp.ui.shipment.lazer.ShipmentLazerActivity;
import com.bt.arasholding.filloapp.utils.AppConstants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class LazerActivity extends BaseActivity implements LazerMvpView {
    private String islemTipi;

    @Inject
    LazerMvpPresenter<LazerMvpView> mPresenter;
    @BindView(R.id.txtSayac)
    TextView txtSayac;
    @BindView(R.id.edtBarcodeText)
    EditText edtBarcodeText;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LazerActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lazer);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();
    }

    @Override
    protected void setUp() {

        if (getIntent() != null) {
            islemTipi = getIntent().getStringExtra("islemTipi");
        }
        updateToolbarTitle();
        edtBarcodeText.setText("");
        edtBarcodeText.requestFocus();
    }

    @Override
    public void updateSayac() {
        int sayac = Integer.valueOf(txtSayac.getText().toString());

        sayac++;

        txtSayac.setText(sayac + "");
    }

    private void decide(String barcode) {

        switch (islemTipi) {
            case AppConstants.YUKLEME:
                    mPresenter.yukleme(barcode);
                break;
            case AppConstants.INDIRME:
                    mPresenter.indirme(barcode);
                break;
        }
    }

    @OnEditorAction(R.id.edtBarcodeText)
    public boolean onEditorAction(TextView view, int keyCode, KeyEvent keyEvent) {

        String textBarcode = view.getText().toString();

        if (textBarcode.isEmpty())
            return false;

        showMessage(textBarcode);

        if (keyEvent != null) {
            // Barcode finger scanner

         if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                edtBarcodeText.setText("");
                switch (islemTipi) {
                    case AppConstants.YUKLEME:
                        mPresenter.yukleme(textBarcode);
                        break;
                    case AppConstants.INDIRME:
                        mPresenter.indirme(textBarcode);
                        break;
                }
            }
        }
        else {
            // Keyboard

            hideKeyboard();
            switch (islemTipi) {
                case AppConstants.YUKLEME:
                    mPresenter.yukleme(textBarcode);
                    break;
                case AppConstants.INDIRME:
                    mPresenter.indirme(textBarcode);
                    break;
            }
        }

        return false;
    }

    @Override
    public void updateToolbarTitle() {
        if (islemTipi.equals(AppConstants.YUKLEME)) {
            toolbar.setTitle("Yükleme");
        } else {
            toolbar.setTitle("İndirme");
        }
    }

    @OnClick(R.id.btn_sefer_okut)
    public void onBtnSeferOkutClicked() {
        Intent intent = ShipmentLazerActivity.getStartIntent(this);
        intent.putExtra("islemTipi", islemTipi);
        startActivity(intent);
    }

}
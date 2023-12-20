package com.bt.arasholding.filloapp.ui.textbarcode;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.model.Barcode;
import com.bt.arasholding.filloapp.ui.base.BaseActivity;
import com.bt.arasholding.filloapp.ui.bluetooth.BluetoothFragment;
import com.bt.arasholding.filloapp.ui.shipment.ShipmentActivity;
import com.bt.arasholding.filloapp.utils.AppConstants;
import com.manojbhadane.QButton;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TextBarcodeActivity extends BaseActivity implements TextBarcodeMvpView,BluetoothFragment.BluetoothResult {

    @Inject
    TextBarcodeMvpPresenter<TextBarcodeMvpView> mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.txtCount)
    TextView txtCount;
    @BindView(R.id.btn_sefer_okut)
    QButton btn_sefer_okut;

    @BindView(R.id.recyclerBarcodeList)
    RecyclerView recyclerBarcodeList;
    BarcodeAdapter adapter;
    private String islemTipi;

    int currentCount = 0;

    boolean dialogShown = true;

    RecyclerView.LayoutManager mLayoutManager;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, TextBarcodeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_barcode);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void setUp() {

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if (getIntent() != null) {
            islemTipi = getIntent().getStringExtra("islemTipi");
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_textBarcode, BluetoothFragment.newInstance(), BluetoothFragment.TAG)
                .commit();

        mLayoutManager = new LinearLayoutManager(this);
        recyclerBarcodeList.setLayoutManager(mLayoutManager);

        mPresenter.onViewPrepared(islemTipi);
    }

    @Override
    public void updateToolbarTitle() {
        if (islemTipi.equals(AppConstants.TOPLUTESLIMAT)) {
            toolbar.setTitle("KTF Barkodu Okutunuz");
        } else if (islemTipi.equals(AppConstants.INDIRME)) {
            toolbar.setTitle("İndirme");
        } else if (islemTipi.equals(AppConstants.YUKLEME)) {
            toolbar.setTitle("Yükleme");
        } else if (islemTipi.equals(AppConstants.ATFTESLIMAT)) {
            toolbar.setTitle("Atf Barkodu");
        }
    }

    @OnClick(R.id.btn_sefer_okut)
    public void onBtnSeferOkutClicked() {
        Intent intent = ShipmentActivity.getStartIntent(this);
        intent.putExtra("islemTipi", islemTipi);
        startActivity(intent);
    }

    @Override
    public void updateBarcodeList(List<Barcode> barcodeList) {
        adapter = new BarcodeAdapter(barcodeList, this);

        adapter.notifyDataSetChanged();
        recyclerBarcodeList.setAdapter(adapter);
    }

    @Override
    public void updateCount(String yuklenecekParcaAdeti) {
        if (yuklenecekParcaAdeti != null) {
            currentCount++;
            txtCount.setText(currentCount + " / " + yuklenecekParcaAdeti);
        }
    }

    @Override
    public void onBackPressed() {
        onFragmentDetached(BluetoothFragment.TAG);
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
    public void resultData(String codeContent) {
        mPresenter.sendToServer(codeContent,islemTipi);
    }

    @Override
    public void showErrorMessage(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("UYARI");
        builder.setMessage(message);

        builder.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Tamam butonuna basılınca yapılacaklar

            }
        });

        if (dialogShown)
        {
            builder.show();
            dialogShown =false;
        }

    }

}
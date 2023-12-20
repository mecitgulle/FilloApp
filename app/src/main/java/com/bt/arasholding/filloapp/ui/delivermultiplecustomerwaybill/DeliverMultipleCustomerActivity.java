
package com.bt.arasholding.filloapp.ui.delivermultiplecustomerwaybill;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.network.model.AtfUndeliverableReasonModel;
import com.bt.arasholding.filloapp.data.network.model.CustomerResponseModel;
import com.bt.arasholding.filloapp.ui.base.BaseActivity;
import com.bt.arasholding.filloapp.ui.delivercargo.DeliverCargoActivity;
import com.bt.arasholding.filloapp.ui.delivery.DeliveryActivity;
import com.bt.arasholding.filloapp.ui.delivery.DeliveryMvpPresenter;
import com.bt.arasholding.filloapp.ui.delivery.DeliveryMvpView;
import com.bt.arasholding.filloapp.ui.home.HomeActivity;
import com.bt.arasholding.filloapp.ui.scanbarcode.ScanBarcodeFragment;
import com.bt.arasholding.filloapp.utils.AppConstants;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeliverMultipleCustomerActivity extends BaseActivity implements
        DeliverMultipleCustomerMvpView{

    @Inject
    DeliverMultipleCustomerPresenter<DeliverMultipleCustomerMvpView> mPresenter;

    @BindView(R.id.btn_musteri_barkod_okut)
    Button btn_musteri_barkod_okut;

    SpinnerAdapter spinnerAdapter;
    MaterialSpinner spinner_customer;
    String musteriId;
    private String enlem, boylam;
    String koordinat;
    private String islemTipi;
    private static final int PERMISSION_REQUEST_CODE = 101;

    LocationManager konumYoneticisi;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_multiple_customer);

        getActivityComponent().inject(this);
        getIntentValues();
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(this);

        mPresenter.getCustomer();

        spinner_customer = findViewById(R.id.spinner_customer);

        spinner_customer.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                CustomerResponseModel model = (CustomerResponseModel) spinnerAdapter.getItemObj(position);
                musteriId = model.getID();
            }
        });

        setUp();
    }

    private void getIntentValues() {
        if (getIntent() != null) {
            islemTipi = getIntent().getStringExtra("islemTipi");
        }
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, DeliverMultipleCustomerActivity.class);
        return intent;
    }

    @Override
    protected void setUp() {
    }
    @Override
    public void setSpinner(List<CustomerResponseModel> model) {

        spinnerAdapter = new SpinnerAdapter(this, R.layout.spinner_row, model);
        spinner_customer.setAdapter(spinnerAdapter);
        CustomerResponseModel list = (CustomerResponseModel) spinnerAdapter.getItemObj(0);
        musteriId = list.getID();
    }
    @OnClick(R.id.btn_musteri_barkod_okut)
    public void onBtnBarkodOkutClicked() {
        if(musteriId == null)
        {
            Toast.makeText(this, "Müşteri Seçiniz !", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = DeliveryActivity.getStartIntent(this);
            intent.putExtra("islemTipi", islemTipi);
            intent.putExtra("arkodu", musteriId);
            startActivity(intent);
        }
    }
}
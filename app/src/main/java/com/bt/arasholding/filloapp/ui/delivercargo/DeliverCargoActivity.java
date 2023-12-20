package com.bt.arasholding.filloapp.ui.delivercargo;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.network.model.AtfUndeliverableReasonModel;
import com.bt.arasholding.filloapp.data.network.model.DeliverMultipleCargoModel;
import com.bt.arasholding.filloapp.data.network.model.DeliverMultipleCargoResponse;
import com.bt.arasholding.filloapp.data.network.model.DeliveredCargoRequest;
import com.bt.arasholding.filloapp.ui.base.BaseActivity;
import com.bt.arasholding.filloapp.ui.cargobarcode.CargoBarcodeActivity;
import com.bt.arasholding.filloapp.ui.delivery.multidelivery.SpinnerAdapter;
import com.bt.arasholding.filloapp.ui.home.HomeActivity;
import com.bt.arasholding.filloapp.utils.AppConstants;
import com.google.gson.Gson;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class DeliverCargoActivity extends BaseActivity implements DeliverCargoMvpView {
    @Inject
    DeliverCargoMvpPresenter<DeliverCargoMvpView> mPresenter;

    @BindView(R.id.txtCargoNo)
    TextView txtCargoNo;
    @BindView(R.id.txtToplamParca)
    TextView txtToplamParca;
    @BindView(R.id.txtAliciAdi)
    TextView txtAliciAdi;
    @BindView(R.id.txtTeslimAlanAdi)
    EditText txtTeslimAlanAdi;
    @BindView(R.id.txtTeslimAlanSoyadi)
    EditText txtTeslimAlanSoyadi;
    @BindView(R.id.txtKimlikNo)
    EditText txtKimlikNo;
    @BindView(R.id.txtAciklama)
    EditText txtAciklama;
    @BindView(R.id.txtAtfAdet)
    TextView txtAtfAdet;
    @BindView(R.id.txtTeslimTarihi)
    TextView txtTeslimTarihi;
    @BindView(R.id.layout_evrak_donus)
    LinearLayout layoutEvrakDonus;
    @BindView(R.id.txtsecilenTeslimSaati)
    TextView txtsecilenTeslimSaati;
    @BindView(R.id.txtsecilenTeslimTarihi)
    TextView txtsecilenTeslimTarihi;

    @BindView(R.id.edtTeslimTarihi)
    TextView edtTeslimTarihi;
    @BindView(R.id.edtTeslimSaati)
    TextView edtTeslimSaati;
    @BindView(R.id.btnDatePicker)
    ImageButton btnDatePicker;
    @BindView(R.id.btnTimePicker)
    ImageButton btnTimePicker;

    private String atfId;
    private String islemTipi;
    private String trackId;
    private String evrakAlindiMi;
    SpinnerAdapter spinnerAdapter;

    private String choose = "TOPLUTESLIMAT";
    LocationManager konumYoneticisi;
    LocationListener locationListener;
    private String enlem, boylam;
    private static final int PERMISSION_REQUEST_CODE = 101;
    String koordinat;
    String devirSebepId;
    MaterialSpinner spinner_undeliverable;

    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int hour, minute;
    static List<DeliverMultipleCargoModel> response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_cargo);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        getIntentValues();

        LinearLayout lytTeslimAlanAd = findViewById(R.id.lytTeslimAlanAd);
        LinearLayout lytTeslimAlanSoyad = findViewById(R.id.lytTeslimAlanSoyad);
        LinearLayout lytTeslimAlanKimlikNo = findViewById(R.id.lytTeslimAlanKimlikNo);
        LinearLayout lytDevirSebebi = findViewById(R.id.lytDevirSebebi);

        calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(DeliverCargoActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String formattedhourOfDay = "" + hourOfDay;
                        String formattedminute = "" + minute;
                        if(minute < 10){

                            formattedminute = "0" + minute;
                        }
                        if(hourOfDay < 10){

                            formattedhourOfDay  = "0" + hourOfDay ;
                        }
                        edtTeslimSaati.setText(formattedhourOfDay + ":" + formattedminute+":00");

                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });

        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        month = month +1;
        int year = calendar.get(Calendar.YEAR);
        String formattedDayOfMonth = "" + day;
        String formattedMonth = "" + month;

        if(month < 10){

            formattedMonth = "0" + month;
        }
        if(day < 10){

            formattedDayOfMonth  = "0" + day ;
        }

        edtTeslimTarihi.setText(formattedDayOfMonth + "/" + (formattedMonth) + "/" + year);

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(DeliverCargoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String formattedDayOfMonth = "" + dayOfMonth;
                        String formattedMonth = "" + month;
                        if(month < 10){

                            formattedMonth = "0" + month;
                        }
                        if(dayOfMonth < 10){

                            formattedDayOfMonth  = "0" + dayOfMonth ;
                        }
                        edtTeslimTarihi.setText(formattedDayOfMonth + "/" + (formattedMonth) + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        if (islemTipi.equals("TOPLUDEVIR")){
            lytDevirSebebi.setVisibility(View.VISIBLE);
            edtTeslimSaati.setHint("Devir Saati");
            edtTeslimTarihi.setHint("Devir Tarihi");
            txtsecilenTeslimTarihi.setText("Devir Tarihi");
            txtsecilenTeslimSaati.setText("Devir Saati");
            mPresenter.getAtfUndeliverableReason();
        }
        else{
            lytTeslimAlanAd.setVisibility(View.VISIBLE);
            lytTeslimAlanSoyad.setVisibility(View.VISIBLE);
            lytTeslimAlanKimlikNo.setVisibility(View.VISIBLE);
        }

        spinner_undeliverable = findViewById(R.id.spinner_undeliverable);

        spinner_undeliverable.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                AtfUndeliverableReasonModel model = (AtfUndeliverableReasonModel) spinnerAdapter.getItemObj(position);

                devirSebepId = model.getSebepId();

//                Toast.makeText(getContext(), "ID: " + model.getValue() + "\nName: " + model.getText(),
//                        Toast.LENGTH_SHORT).show();
            }

        });

        setUp();
    }
    @Override
    public void setSpinner(List<AtfUndeliverableReasonModel> model) {

        spinnerAdapter = new SpinnerAdapter(this, R.layout.spinner_row, model);
        spinner_undeliverable.setAdapter(spinnerAdapter);
    }


    private void getIntentValues() {
        if (getIntent() != null) {
            islemTipi = getIntent().getStringExtra("islemTipi");
            trackId = getIntent().getStringExtra("TrackId");
        }
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, DeliverCargoActivity.class);
        return intent;
    }

    @Override
    protected void setUp() {
        getCurrentLocation();

        if (islemTipi.equals(AppConstants.TOPLUTESLIMAT) || islemTipi.equals(AppConstants.TOPLUDEVIR)) {
            mPresenter.onViewDeliveryInfoPrepared(trackId);
        } else if (islemTipi.equals(AppConstants.ATFNOILETESLIMAT)) {
            mPresenter.onViewPreparedByAtfNo(trackId);
        } else {
            mPresenter.onViewPrepared(trackId);
        }

    }

    @Override
    public void updateTrackId(String trackId) {
        txtCargoNo.setText(trackId);
    }

    @Override
    public void updateReceivedName(String name) {
        txtAliciAdi.setText(name);
    }

    @Override
    public void updateCargoCount(String count) {
        txtToplamParca.setText(count);
    }

    @Override
    public void updateAtfId(String atfId) {
        this.atfId = atfId;
    }

    @Override
    public void updateAtfAdet(String atfAdet) {
        txtAtfAdet.setText(atfAdet);
    }

    @Override
    public void updateToplamAdet(String toplamParca) {
        txtToplamParca.setText(toplamParca);
    }

    @Override
    public void updateTeslimTarihi(String teslimTarihi) {
        txtTeslimTarihi.setText(teslimTarihi);
    }

    @Override
    public void updateEvrakDonusluVisibility(boolean visibility) {
        layoutEvrakDonus.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @OnClick(R.id.btnDeliver)
    public void onViewClicked() {

        DeliveredCargoRequest request = new DeliveredCargoRequest();
        request.setAciklama(txtAciklama.getText().toString());
        request.setAtfId(atfId);
        request.setTeslimTarihi(edtTeslimTarihi.getText().toString());
        request.setTeslimSaati(edtTeslimSaati.getText().toString());

        if (islemTipi.equals("TOPLUTESLIMAT")) {
            request.setKimlikNo(txtKimlikNo.getText().toString());
            request.setTeslimAlanAd(txtTeslimAlanAdi.getText().toString());
            request.setTeslimAlanSoyad(txtTeslimAlanSoyadi.getText().toString());
            request.setIslemTipiAtf("-1");
        }
        else {
            request.setDevirSebebi(devirSebepId);
            request.setIslemTipiAtf("0");
        }

        request.setEvrakDonusAlindiMi(evrakAlindiMi);
        request.setMapXY(koordinat);

        if (islemTipi.equals(AppConstants.TOPLUTESLIMAT) || islemTipi.equals(AppConstants.TOPLUDEVIR)) {
            request.setKtfBarkodu(trackId);
            mPresenter.multiDeliverApiCall(request);
        } else {
            mPresenter.onDeliveredClick(request);
        }
    }

    @Override
    public void showAlertDialog(DeliverMultipleCargoResponse response) {

        this.response = response.getResponseList();
        LayoutInflater inflater = this.getLayoutInflater();
//        View coklu_teslim_islem_sonuc_layout = inflater.inflate(R.layout.coklu_teslim_islem_sonuc_layout, null);

//        RecyclerView islemSonucList = coklu_teslim_islem_sonuc_layout.findViewById(R.id.islemSonucList);
//
//        DeliveredResponseAdapter adapter = new DeliveredResponseAdapter(response.getResponseList(), this);
//        adapter.notifyDataSetChanged();
//        islemSonucList.setAdapter(adapter);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DeliverCargoActivity.this);
        alertDialog.setTitle("İşlem Sonucu !");
//        alertDialog.setView(coklu_teslim_islem_sonuc_layout);
        alertDialog.setMessage(response.getMessage());
//        alertDialog.setStatusCode(response.getStatusCode());
        alertDialog.setIcon(R.drawable.ic_info_black_24dp);

        alertDialog.setPositiveButton("Detaya Git", (dialog, which) -> {
            dialog.dismiss();
            Intent intent = new Intent(DeliverCargoActivity.this, DeliverCargoDetailActivity.class);
            intent.putExtra("alici",txtAliciAdi.getText().toString());

            startActivity(intent);
        });

        alertDialog.show();
    }

    public static List<DeliverMultipleCargoModel> getResponseList() {
        return response;
    }
    @Override
    public void showAlertDialogChoose() {
        LayoutInflater inflater = this.getLayoutInflater();
        View undeliverable_or_delivery_choose_layout = inflater.inflate(R.layout.undeliverable_or_delivery_choose_layout, null);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DeliverCargoActivity.this);
        alertDialog.setTitle("Lütfen Seçim Yapınız !");
        alertDialog.setView(undeliverable_or_delivery_choose_layout);
        alertDialog.setIcon(R.drawable.ic_info_black_24dp);

        alertDialog.setView(undeliverable_or_delivery_choose_layout);

        alertDialog.setPositiveButton("Tamam", (dialog, which) -> {
            dialog.dismiss();
        });

        alertDialog.show();
    }


    @OnCheckedChanged({R.id.rd_alindi})
    void onRdAlindiChanged(boolean checked) {
        this.evrakAlindiMi = checked ? "ALINDI" : "ALINMADI";
    }

    @OnCheckedChanged({R.id.rd_alinmadi})
    void onRdAlinmadiChanged(boolean checked) {
        this.evrakAlindiMi = !checked ? "ALINDI" : "ALINMADI";
    }

    protected void getCurrentLocation() {
        konumYoneticisi = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean kontrol = konumYoneticisi.isProviderEnabled("gps");
        if (!kontrol) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DeliverCargoActivity.this);
            builder.setTitle("Konum Kullanılsın mı?");
            builder.setMessage("Bu uygulama konum ayarınızı değiştirmek istiyor");
            builder.setCancelable(false);
            builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(DeliverCargoActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mPresenter.setLatitude(location.getLatitude()+"");
                mPresenter.setLongitude(location.getLongitude()+"");
                enlem = mPresenter.getLatitude();
                boylam = mPresenter.getLongitude();
                koordinat = enlem + ";" + boylam;
                Log.e("enlem2",location.getLatitude()+"");
                Log.e("boylam2",location.getLongitude()+"");
                Log.e("enlem", mPresenter.getLatitude());
                Log.e("boylam", mPresenter.getLongitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String provider) {
//                Toast.makeText(getApplicationContext(), "Konum Açık", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, PERMISSION_REQUEST_CODE);
            } else {
                getLocation();
            }

        } else {
            getLocation();
        }

    }

    private void getLocation() {
        if(konumYoneticisi.getAllProviders().contains("network")) {
            konumYoneticisi.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, locationListener);
        }
        else {
            konumYoneticisi.requestLocationUpdates("gps", 5000, 0, locationListener);
        }
    }
}
package com.bt.arasholding.filloapp.ui.nobarcode;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.network.model.BarkodsuzKargoImageList;
import com.bt.arasholding.filloapp.data.network.model.CustomerResponseModel;
import com.bt.arasholding.filloapp.data.network.model.NoBarcodeCargo;
import com.bt.arasholding.filloapp.data.network.model.NoBarcodeSaveRequest;
import com.bt.arasholding.filloapp.data.network.model.TazminTalepImageList;
import com.bt.arasholding.filloapp.ui.base.BaseActivity;
import com.bt.arasholding.filloapp.ui.delivermultiplecustomerwaybill.SpinnerAdapter;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.manojbhadane.QButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoBarcodeActivity extends BaseActivity implements NoBarcodeMvpView {
    @Inject
    NoBarcodeMvpPresenter<NoBarcodeMvpView> mPresenter;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclernoBarcodeList)
    RecyclerView recyclernoBarcodeList;
    NoBarcodeAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;


    @BindView(R.id.btn_yeni_ekle)
    QButton btn_yeni_ekle;

    SpinnerAdapter spinnerAdapter;
    MaterialSpinner spinner_all_customer;
    String arkodu;
    TextView txtBarkodsuzKargo;
    private String filePath;
    String encoded;
    List<BarkodsuzKargoImageList> barkodsuzKargoImageLists = new ArrayList<>();

    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int hour, minute;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_barcode);

        dosyalariSil();

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();
    }

    @Override
    protected void setUp() {
        setSupportActionBar(toolbar);
        mLayoutManager = new LinearLayoutManager(this);
        recyclernoBarcodeList.setLayoutManager(mLayoutManager);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mPresenter.onViewPrepared();
    }

    @OnClick(R.id.btn_yeni_ekle)
    public void onViewClicked() {
        showNewNoBarcodeDialog();
    }

    @Override
    public void showNewNoBarcodeDialog() {

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_nobarcode_save, null);

        mPresenter.getAllCustomer();

        spinner_all_customer = dialogView.findViewById(R.id.spinner_all_customer);

        spinner_all_customer.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                CustomerResponseModel model = (CustomerResponseModel) spinnerAdapter.getItemObj(position);
                arkodu = model.getID();
            }
        });

        calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setView(dialogView);
        alertDialog.setIcon(R.drawable.ic_perm_device_information_black_24dp);

        EditText edtUrunKodu = dialogView.findViewById(R.id.edtUrunKodu);
        EditText edtIcerik = dialogView.findViewById(R.id.edtIcerik);
        EditText edtKoliAdet = dialogView.findViewById(R.id.edtKoliAdet);
        EditText edtPlaka = dialogView.findViewById(R.id.edtPlaka);
        EditText edtNoBarcodeAciklama = dialogView.findViewById(R.id.edtNoBarcodeAciklama);
        TextView edtGelisTarihi = dialogView.findViewById(R.id.edtGelisTarihi);
        ImageButton btnDatePicker = dialogView.findViewById(R.id.btnDatePicker);
        ImageButton btnTimePicker = dialogView.findViewById(R.id.btnTimePicker);
        txtBarkodsuzKargo = dialogView.findViewById(R.id.txtBarkodsuzKargoFoto);

        ImageButton btnBarkodsuzFoto = dialogView.findViewById(R.id.btnBarkodsuzFoto);

        btnBarkodsuzFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        int year = calendar.get(Calendar.YEAR);
        String formattedDayOfMonth = "" + day;
        String formattedMonth = "" + month;

        if (month < 10) {

            formattedMonth = "0" + month;
        }
        if (day < 10) {

            formattedDayOfMonth = "0" + day;
        }

        edtGelisTarihi.setText(formattedDayOfMonth + "/" + (formattedMonth) + "/" + year);

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(NoBarcodeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String formattedDayOfMonth = "" + dayOfMonth;
                        String formattedMonth = "" + month;
                        if (month < 10) {

                            formattedMonth = "0" + month;
                        }
                        if (dayOfMonth < 10) {

                            formattedDayOfMonth = "0" + dayOfMonth;
                        }
                        edtGelisTarihi.setText(formattedDayOfMonth + "/" + (formattedMonth) + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        alertDialog.setPositiveButton("Kaydet", (dialog, which) -> {
            dialog.dismiss();

            NoBarcodeSaveRequest request = new NoBarcodeSaveRequest();

            request.setAciklama(edtNoBarcodeAciklama.getText().toString());
            request.setGelisTarihi(edtGelisTarihi.getText().toString());
            request.setIcerik(edtIcerik.getText().toString());
            if (!edtKoliAdet.getText().toString().isEmpty()){
                request.setKoliAdet(Integer.parseInt(edtKoliAdet.getText().toString()));
            }
            request.setPlaka(edtPlaka.getText().toString());
            request.setUrunKodu(edtUrunKodu.getText().toString());
            request.setMusteriId(arkodu);
            request.setBarkodsuzKargoImageLists(barkodsuzKargoImageLists);

            mPresenter.saveNoBarcode(request);
        });

        alertDialog.setNegativeButton("Kapat", (dialog, which) -> {
            dialog.dismiss();
        });

        alertDialog.show();
    }
    @Override
    public void setSpinner(List<CustomerResponseModel> model) {

        spinnerAdapter = new SpinnerAdapter(this, R.layout.spinner_row, model);
        spinner_all_customer.setAdapter(spinnerAdapter);
        CustomerResponseModel list = (CustomerResponseModel) spinnerAdapter.getItemObj(0);
        arkodu = list.getID();
    }
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, NoBarcodeActivity.class);
        return intent;
    }

    @Override
    public void updateNoBarcodeList(List<NoBarcodeCargo> barcodeList) {
        adapter = new NoBarcodeAdapter(barcodeList, this);

        adapter.notifyDataSetChanged();
        recyclernoBarcodeList.setAdapter(adapter);
    }
    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        this.getPackageName() + ".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                uri2 = photoURI;
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "BarkodsuzKargoFoto";
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = new File(storageDir, imageFileName + ".jpg");
        if (!image.exists()) {
            image.createNewFile();
        }

//        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        filePath = image.getAbsolutePath();

        return image;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            String id = extras.getString("id");
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
            try {
                File file = new File(filePath);
                Bitmap imageBitmap = null;
                Uri uri = Uri.fromFile(file);
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
//                        bitmap = getResizedBitmap(bitmap, 25, 25);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();

                encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                BarkodsuzKargoImageList imageList = new BarkodsuzKargoImageList();
                imageList.setBase64String(encoded);
                imageList.setResimTipi("RESIM");
                imageList.setDosyaAdi("BarkodsuzKargoResim" + ".jpg");
                barkodsuzKargoImageLists.add(imageList);
                txtBarkodsuzKargo.setText("BarkodsuzResim" + ".jpg");
            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }

        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Fotağraf çekmeden çıktınız.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "HATA:Fotağraf çekilemedi.", Toast.LENGTH_SHORT).show();
        }
    }
    private void dosyalariSil() {

        File directory = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File[] files = directory.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                files[i].delete();
            }
        }
    }
}
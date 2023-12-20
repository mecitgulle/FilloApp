package com.bt.arasholding.filloapp.ui.delivery.multidelivery;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.model.Barcode;
import com.bt.arasholding.filloapp.data.network.model.AtfUndeliverableReasonModel;
import com.bt.arasholding.filloapp.data.network.model.DeliverCargoImageUploadRequest;
import com.bt.arasholding.filloapp.data.network.model.DeliveredCargoRequest;
import com.bt.arasholding.filloapp.di.component.ActivityComponent;
import com.bt.arasholding.filloapp.ui.base.BaseFragment;
import com.bt.arasholding.filloapp.ui.delivery.DeliveryActivity;
import com.bt.arasholding.filloapp.ui.home.HomeActivity;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.manojbhadane.QButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static androidx.appcompat.app.AppCompatActivity.RESULT_CANCELED;
import static androidx.appcompat.app.AppCompatActivity.RESULT_OK;

public class MultiDeliveryFragment extends BaseFragment implements MultiDeliveryMvpView {

    public static final String TAG = "MultiDeliveryFragment";

    @Inject
    MultiDeliveryMvpPresenter<MultiDeliveryMvpView> mPresenter;

    @BindView(R.id.recycler_delivery_list)
    RecyclerView recyclerDeliveryList;

    @BindView(R.id.btnTeslimatKapat)
    QButton btnTeslimatKapat;

    RecyclerView.LayoutManager mLayoutManager;
    MultiDeliveryRecyclerListAdapter adapter;
    SpinnerAdapter spinnerAdapter;
    String encoded;
    LocationManager konumYoneticisi;
    LocationListener locationListener;
    private String enlem, boylam;
    private static final int PERMISSION_REQUEST_CODE = 101;
    String koordinat;
    String atf_id;
    String devirSebepId;
    String choose = "ATFTESLIMAT";
    private String filePath;
    String barkod2d;

    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int hour, minute;

    MaterialSpinner spinner_undeliverable;

    public MultiDeliveryFragment() {

    }

    public static MultiDeliveryFragment newInstance() {
        MultiDeliveryFragment fragment = new MultiDeliveryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_multi_delivery, container, false);

        ActivityComponent component = getActivityComponent();

        DeliveryActivity activity = (DeliveryActivity) getActivity();
        choose = activity.getIslemTipi();

        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            setUp(view);
        }


        return view;
    }

    @Override
    protected void setUp(View view) {
        mLayoutManager = new LinearLayoutManager(getBaseActivity());
        recyclerDeliveryList.setLayoutManager(mLayoutManager);
        getCurrentLocation(view);
//        showAlertDialogChoose();
        mPresenter.setButtonText(choose);
        mPresenter.onViewPrepared();
        dosyalariSil();
    }

    @OnClick(R.id.btnTeslimatKapat)
    public void onViewClicked() {
        mPresenter.showTeslimatDialog();
    }

    @OnClick(R.id.btnBarkodOkut)
    public void onbtnBarkodOkutClicked() {
        ((DeliveryActivity) getActivity()).showCameraFragment();
    }

    @Override
    public void sendBarcode(String barcode, boolean isBarcode) {
        mPresenter.getBarcodeInformation(barcode, isBarcode);
    }

    @Override
    public void deliverMultipleCustomer(String barcode, String arkodu) {

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_teslimat_kapat, null);

        calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getBaseActivity());
        alertDialog.setTitle("Teslimatı Kapat");
        alertDialog.setView(dialogView);
        alertDialog.setIcon(R.drawable.ic_perm_device_information_black_24dp);

        String nameDefaultValue = "MUTABAKAT";
        String lastNameDefaultValue = "MOBIL";
        EditText edtTeslimAlanAdi = dialogView.findViewById(R.id.edtTeslimAlanAdi);
        edtTeslimAlanAdi.setText(nameDefaultValue);
        EditText edtTeslimAlanSoyadi = dialogView.findViewById(R.id.edtTeslimAlanSoyadi);
        edtTeslimAlanSoyadi.setText(lastNameDefaultValue);
        EditText edtKimlikNo = dialogView.findViewById(R.id.edtKimlikNo);
        EditText edtAciklama = dialogView.findViewById(R.id.edtAciklama);
        TextView edtTeslimTarihi = dialogView.findViewById(R.id.edtTeslimTarihi);
        TextView edtTeslimSaati = dialogView.findViewById(R.id.edtTeslimSaati);
        TextView txtsecilenTeslimSaati = dialogView.findViewById(R.id.txtsecilenTeslimSaati1);
        TextView txtsecilenTeslimTarihi = dialogView.findViewById(R.id.txtsecilenTeslimTarihi1);
        LinearLayout lytTeslimAlanAd = dialogView.findViewById(R.id.lytTeslimAlanAd);
        LinearLayout lytTeslimAlanSoyad = dialogView.findViewById(R.id.lytTeslimAlanSoyad);
        LinearLayout lytTeslimAlanKimlikNo = dialogView.findViewById(R.id.lytTeslimAlanKimlikNo);
        LinearLayout lytDevirSebebi = dialogView.findViewById(R.id.lytDevirSebebi);
        ImageButton btnDatePicker = dialogView.findViewById(R.id.btnDatePicker);
        ImageButton btnTimePicker = dialogView.findViewById(R.id.btnTimePicker);
        spinner_undeliverable = dialogView.findViewById(R.id.spinner_undeliverable);

        spinner_undeliverable.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                AtfUndeliverableReasonModel model = (AtfUndeliverableReasonModel) spinnerAdapter.getItemObj(position);

                devirSebepId = model.getSebepId();

//                Toast.makeText(getContext(), "ID: " + model.getValue() + "\nName: " + model.getText(),
//                        Toast.LENGTH_SHORT).show();
            }

        });
        lytTeslimAlanAd.setVisibility(View.VISIBLE);
        lytTeslimAlanSoyad.setVisibility(View.VISIBLE);
        lytTeslimAlanKimlikNo.setVisibility(View.VISIBLE);

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

        edtTeslimTarihi.setText(formattedDayOfMonth + "/" + (formattedMonth) + "/" + year);

        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String formattedhourOfDay = "" + hourOfDay;
                        String formattedminute = "" + minute;
                        if (minute < 10) {

                            formattedminute = "0" + minute;
                        }
                        if (hourOfDay < 10) {

                            formattedhourOfDay = "0" + hourOfDay;
                        }
                        edtTeslimSaati.setText(formattedhourOfDay + ":" + formattedminute + ":00");

                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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
                        edtTeslimTarihi.setText(formattedDayOfMonth + "/" + (formattedMonth) + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        alertDialog.setPositiveButton("Kaydet", (dialog, which) -> {
            dialog.dismiss();

            DeliveredCargoRequest request = new DeliveredCargoRequest();

            request.setAciklama(edtAciklama.getText().toString());
            request.setTeslimTarihi(edtTeslimTarihi.getText().toString());
            request.setTeslimSaati(edtTeslimSaati.getText().toString());
            request.setArKodu(arkodu);
            request.setCustomerBarcode(barcode);
            request.setTeslimAlanAd(edtTeslimAlanAdi.getText().toString());
            request.setTeslimAlanSoyad(edtTeslimAlanSoyadi.getText().toString());
            request.setKimlikNo(edtKimlikNo.getText().toString());
            request.setIslemTipiAtf("-1");

            request.setMapXY(koordinat);

            mPresenter.apiCallCustomerDelivery(request);
        });
        alertDialog.setNegativeButton("Kapat", (dialog, which) -> {
            dialog.dismiss();
        });

        alertDialog.show();

        String[] items = barcode.split(";");
        int atfSayisi = items.length;
        //Toast.makeText(getContext(), "Kapatılacak ATF Sayısı: " + String.valueOf(atfSayisi) , Toast.LENGTH_SHORT).show();
        getBaseActivity().showErrorMessage("Kapatılacak ATF Sayısı: " + String.valueOf(atfSayisi));
    }


    @Override
    public void updateBarcodeList(List<Barcode> barcodeList) {
        adapter = new MultiDeliveryRecyclerListAdapter(barcodeList, getBaseActivity(), this);

        adapter.notifyDataSetChanged();
        recyclerDeliveryList.setAdapter(adapter);
    }

    @Override
    public void setAlindiMi(long id, String deger) {
        mPresenter.setAlindiMi(id, deger);
    }

    @Override
    public void setHasarPhoto(long id, String foto) {

    }

    @Override
    public void setHasarAciklama(long id, String aciklama) {
        mPresenter.setHasarAciklama(id, aciklama);
    }

    @Override
    public void setSpinner(List<AtfUndeliverableReasonModel> model) {

        spinnerAdapter = new SpinnerAdapter(getBaseActivity(), R.layout.spinner_row, model);
        spinner_undeliverable.setAdapter(spinnerAdapter);
    }

    @Override
    public void setButtonText(String choose) {
        btnTeslimatKapat.setText(choose);
    }


    @Override
    public void dispatchTakePictureIntent(String id) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("id", id);
        atf_id = id;
        File photoFile = null;
        try {
            photoFile = createImageFile(atf_id);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Uri photoUri = FileProvider.getUriForFile(getBaseActivity(), getActivity().getPackageName() + ".provider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, 100);
    }

    private File createImageFile(String uzanti) throws IOException {

        String fName = uzanti;

        if (!atf_id.isEmpty()) {
            fName = atf_id;
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = fName;
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = new File(storageDir, imageFileName + ".jpg");
        if (!image.exists()) {
            image.createNewFile();
        }

//        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        filePath = image.getAbsolutePath();

        return image;
    }

    @Override
    public void showAlertDialogChoose() {
//        LayoutInflater inflater = this.getLayoutInflater();
//        View undeliverable_or_delivery_choose_layout = inflater.inflate(R.layout.undeliverable_or_delivery_choose_layout, null);
//
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getBaseActivity());
//        alertDialog.setTitle("Lütfen Seçim Yapınız !");
//        alertDialog.setView(undeliverable_or_delivery_choose_layout);
//        alertDialog.setIcon(R.drawable.ic_info_black_24dp);
//
//        alertDialog.setView(undeliverable_or_delivery_choose_layout);
//
//        RadioButton rd_teslimat = (RadioButton) undeliverable_or_delivery_choose_layout.findViewById(R.id.rd_teslimat);
//        RadioButton rd_devir = (RadioButton) undeliverable_or_delivery_choose_layout.findViewById(R.id.rd_devir);
//        RadioGroup radio_group = (RadioGroup) undeliverable_or_delivery_choose_layout.findViewById(R.id.radio_group);
//        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//                View radioButton = radio_group.findViewById(checkedId);
//                int index = radio_group.indexOfChild(radioButton);
//
//                switch (index) {
//                    case 0: // first button
//                        choose = "TESLİMAT";
//                        break;
//                    case 1: // secondbutton
//                        choose = "DEVİR";
//                        break;
//                }
//            }
//        });
//        alertDialog.setPositiveButton("Tamam", (dialog, which) -> {
//            dialog.dismiss();
//        });
//
//        alertDialog.show();
    }

//    @OnCheckedChanged({R.id.rd_teslimat})
//    void onRdTeslimatChanged(boolean checked) {
//        this.choose = checked ? "TESLİMAT" : "DEVİR";
//    }
//
//    @OnCheckedChanged({R.id.rd_devir})
//    void onRdDevirChanged(boolean checked) {
//        this.choose = !checked ? "TESLİMAT" : "DEVİR";
//    }

    @Override
    public void showTeslimatDialog() {

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_teslimat_kapat, null);

        calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getBaseActivity());
        if (choose.equals("ATFDEVIR"))
            alertDialog.setTitle("Devir Gir");
        else if (choose.equals("MUSTERITESLIMAT"))
            alertDialog.setTitle("Teslimatı Kapat");
        else {
            alertDialog.setTitle("Teslimat Kaydet");
        }
        alertDialog.setView(dialogView);
        alertDialog.setIcon(R.drawable.ic_perm_device_information_black_24dp);

        EditText edtTeslimAlanAdi = dialogView.findViewById(R.id.edtTeslimAlanAdi);
        EditText edtTeslimAlanSoyadi = dialogView.findViewById(R.id.edtTeslimAlanSoyadi);
        EditText edtKimlikNo = dialogView.findViewById(R.id.edtKimlikNo);
        EditText edtAciklama = dialogView.findViewById(R.id.edtAciklama);
        TextView edtTeslimTarihi = dialogView.findViewById(R.id.edtTeslimTarihi);
        TextView edtTeslimSaati = dialogView.findViewById(R.id.edtTeslimSaati);
        TextView txtsecilenTeslimSaati = dialogView.findViewById(R.id.txtsecilenTeslimSaati1);
        TextView txtsecilenTeslimTarihi = dialogView.findViewById(R.id.txtsecilenTeslimTarihi1);
        LinearLayout lytTeslimAlanAd = dialogView.findViewById(R.id.lytTeslimAlanAd);
        LinearLayout lytTeslimAlanSoyad = dialogView.findViewById(R.id.lytTeslimAlanSoyad);
        LinearLayout lytTeslimAlanKimlikNo = dialogView.findViewById(R.id.lytTeslimAlanKimlikNo);
        LinearLayout lytDevirSebebi = dialogView.findViewById(R.id.lytDevirSebebi);
        ImageButton btnDatePicker = dialogView.findViewById(R.id.btnDatePicker);
        ImageButton btnTimePicker = dialogView.findViewById(R.id.btnTimePicker);
        spinner_undeliverable = dialogView.findViewById(R.id.spinner_undeliverable);

        spinner_undeliverable.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                AtfUndeliverableReasonModel model = (AtfUndeliverableReasonModel) spinnerAdapter.getItemObj(position);

                devirSebepId = model.getSebepId();

//                Toast.makeText(getContext(), "ID: " + model.getValue() + "\nName: " + model.getText(),
//                        Toast.LENGTH_SHORT).show();
            }

        });

        if (choose.equals("ATFDEVIR")) {
            lytDevirSebebi.setVisibility(View.VISIBLE);
//            spinner_undeliverable.setVisibility(View.VISIBLE);
            edtTeslimTarihi.setHint("Devir Tarihi");
            edtTeslimSaati.setHint("Devir Saati");
            txtsecilenTeslimSaati.setText("Devir Saati");
            txtsecilenTeslimTarihi.setText("Devir Tarihi");
            mPresenter.getAtfUndeliverableReason();

        } else {
            lytTeslimAlanAd.setVisibility(View.VISIBLE);
            lytTeslimAlanSoyad.setVisibility(View.VISIBLE);
            lytTeslimAlanKimlikNo.setVisibility(View.VISIBLE);
        }
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

        edtTeslimTarihi.setText(formattedDayOfMonth + "/" + (formattedMonth) + "/" + year);

        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String formattedhourOfDay = "" + hourOfDay;
                        String formattedminute = "" + minute;
                        if (minute < 10) {

                            formattedminute = "0" + minute;
                        }
                        if (hourOfDay < 10) {

                            formattedhourOfDay = "0" + hourOfDay;
                        }
                        edtTeslimSaati.setText(formattedhourOfDay + ":" + formattedminute + ":00");

                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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
                        edtTeslimTarihi.setText(formattedDayOfMonth + "/" + (formattedMonth) + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        alertDialog.setPositiveButton("Kaydet", (dialog, which) -> {
            dialog.dismiss();

            DeliveredCargoRequest request = new DeliveredCargoRequest();

            request.setAciklama(edtAciklama.getText().toString());
            request.setTeslimTarihi(edtTeslimTarihi.getText().toString());
            request.setTeslimSaati(edtTeslimSaati.getText().toString());
            if (choose.equals("ATFDEVIR")) {
                request.setDevirSebebi(devirSebepId);
                request.setIslemTipiAtf("0");
            } else {
                request.setTeslimAlanAd(edtTeslimAlanAdi.getText().toString());
                request.setTeslimAlanSoyad(edtTeslimAlanSoyadi.getText().toString());
                request.setKimlikNo(edtKimlikNo.getText().toString());
                request.setIslemTipiAtf("-1");
            }
            request.setMapXY(koordinat);

            mPresenter.teslimatKapat(request);
        });

        alertDialog.setNegativeButton("Kapat", (dialog, which) -> {
            dialog.dismiss();
        });

        alertDialog.show();
    }


    private void dosyalariSil() {

        File directory = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File[] files = directory.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                files[i].delete();
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            String id = extras.getString("id");
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
            File file = new File(filePath);
            Bitmap imageBitmap = null;
            Uri uri = Uri.fromFile(file);
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
//                        bitmap = getResizedBitmap(bitmap, 25, 25);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

            DeliverCargoImageUploadRequest imageUploadRequest = new DeliverCargoImageUploadRequest();
            imageUploadRequest.setAtfId(atf_id);
            imageUploadRequest.setBase64String(encoded);
            imageUploadRequest.setTeslimatResimTipi("IRSALIYE DÖNÜŞ");
            imageUploadRequest.setDosyaAdi(atf_id + ".jpg");

            mPresenter.imageUpload(imageUploadRequest);
//            setHasarPhoto(id,encoded);
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getActivity(), "Fotağraf çekmeden çıktınız.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "HATA:Fotağraf çekilemedi.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showHasarAciklamaDialog(long id) {
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_hasar_aciklama, null);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getBaseActivity());
        alertDialog.setTitle("Hasar Açıklaması Gir");
        alertDialog.setView(dialogView);
        alertDialog.setIcon(R.drawable.ic_perm_device_information_black_24dp);

        EditText edtHasarAciklama = dialogView.findViewById(R.id.edtHasarAciklama);

        alertDialog.setPositiveButton("Kaydet", (dialog, which) -> {

            setHasarAciklama(id, edtHasarAciklama.getText().toString());
            dialog.dismiss();
        });

        alertDialog.setNegativeButton("Kapat", (dialog, which) -> {
            dialog.dismiss();
        });

        alertDialog.show();
    }

    protected void getCurrentLocation(View view) {
        konumYoneticisi = (LocationManager) view.getContext().getSystemService(Context.LOCATION_SERVICE);
        boolean kontrol = konumYoneticisi.isProviderEnabled("gps");
        if (!kontrol) {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
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
                    Intent intent = new Intent(view.getContext(), HomeActivity.class);
                    startActivity(intent);
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mPresenter.setLatitude(location.getLatitude() + "");
                mPresenter.setLongitude(location.getLongitude() + "");
                enlem = mPresenter.getLatitude();
                boylam = mPresenter.getLongitude();
                koordinat = enlem + ";" + boylam;
                Log.e("enlem2", location.getLatitude() + "");
                Log.e("boylam2", location.getLongitude() + "");
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
            if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, PERMISSION_REQUEST_CODE);
            } else {
                getLocation();
            }

        } else {
            getLocation();
        }

    }
    @Override
    public void deleteAtf(String barcode, int islemTipi) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseActivity());
        builder.setTitle("UYARI?");
        builder.setMessage("Atf Listeden Kaldırılacak Devam Edilsin mi?");
        builder.setCancelable(false);
        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.deleteAtfList(barcode, islemTipi);
            }
        });
        builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.refreshList(islemTipi);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public void showErrorMessage(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseActivity());
        builder.setTitle("UYARI");
        builder.setMessage(message);

        builder.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Tamam butonuna basılınca yapılacaklar

            }
        });


        builder.show();
    }

    private void getLocation() {
        if (konumYoneticisi.getAllProviders().contains("network")) {
            konumYoneticisi.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, locationListener);
        } else {
            konumYoneticisi.requestLocationUpdates("gps", 5000, 0, locationListener);
        }
    }
}
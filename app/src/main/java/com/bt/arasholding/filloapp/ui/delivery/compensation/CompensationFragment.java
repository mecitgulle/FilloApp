package com.bt.arasholding.filloapp.ui.delivery.compensation;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.network.model.AtfUndeliverableReasonModel;
import com.bt.arasholding.filloapp.data.network.model.CompensationRequest;
import com.bt.arasholding.filloapp.data.network.model.DeliverCargoImageUploadRequest;
import com.bt.arasholding.filloapp.data.network.model.DeliveredCargoRequest;
import com.bt.arasholding.filloapp.data.network.model.TazminTalepImageList;
import com.bt.arasholding.filloapp.di.component.ActivityComponent;
import com.bt.arasholding.filloapp.ui.base.BaseActivity;
import com.bt.arasholding.filloapp.ui.base.BaseFragment;
import com.bt.arasholding.filloapp.ui.camera2.CameraActivity;
import com.bt.arasholding.filloapp.ui.delivery.DeliveryActivity;
import com.bt.arasholding.filloapp.ui.delivery.multidelivery.MultiDeliveryFragment;
import com.bt.arasholding.filloapp.ui.delivery.multidelivery.MultiDeliveryMvpPresenter;
import com.bt.arasholding.filloapp.ui.delivery.multidelivery.MultiDeliveryMvpView;
import com.bt.arasholding.filloapp.ui.delivery.multidelivery.MultiDeliveryRecyclerListAdapter;
import com.bt.arasholding.filloapp.ui.delivery.multidelivery.SpinnerAdapter;
import com.bt.arasholding.filloapp.ui.home.HomeActivity;
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
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static androidx.core.content.ContextCompat.getSystemService;

public class CompensationFragment extends BaseFragment implements CompensationMvpView {

    public static final String TAG = "CompensationFragment";
    private static final int REQUEST_CAMERA_PERMISSION = 200;

    @Inject
    CompensationMvpPresenter<CompensationMvpView> mPresenter;

    String choose = "TAZMİN";
    String resimTipi;
    String resimEncoded;
    String tutanakEncoded;
    List<TazminTalepImageList> tazminTalepImageLists = new ArrayList<>();
    //    @BindView(R.id.recycler_atf_list)
//    RecyclerView recycler_atf_list;
//    @BindView(R.id.txtAtfID)
    TextView txtAtfID;

    @BindView(R.id.txtAtfNO)
    TextView txtAtfNO;

    @BindView(R.id.txtTarihC)
    TextView txtTarihC;

    @BindView(R.id.txtGonderen)
    TextView txtGonderen;

    @BindView(R.id.txtAlici)
    TextView txtAlici;

    @BindView(R.id.txtCikisSube)
    TextView txtCikisSube;

    @BindView(R.id.txtVarisSube)
    TextView txtVarisSube;

    @BindView(R.id.txtIrsaliyeNo)
    TextView txtIrsaliyeNo;

    @BindView(R.id.txtToplamParca)
    TextView txtToplamParca;

    @BindView(R.id.txtDesi)
    TextView txtDesi;

    @BindView(R.id.txtOdemeSekli)
    TextView txtOdemeSekli;

    @BindView(R.id.btnTazminGir)
    QButton btnTazminGir;

    @BindView(R.id.txtTazminNo)
    TextView txtTazminNo;


    TextView txtHasarFoto;
    TextView txtTutanakFotoo;
    RecyclerView.LayoutManager mLayoutManager;
    //    MultiDeliveryRecyclerListAdapter adapter;
    SpinnerAdapter spinnerAdapter;
    private static final int PERMISSION_REQUEST_CODE = 101;
    String atf_id;
    String atfID;
    private String filePath;

    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int hour, minute;

    MaterialSpinner spinner_compensation;
    String tazminSebepId;
    private String currentImagePath;

    public CompensationFragment() {
        // Required empty public constructor
    }

    public static CompensationFragment newInstance() {
        CompensationFragment fragment = new CompensationFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_compensation, container, false);

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
//        recycler_atf_list.setLayoutManager(mLayoutManager);

//        mPresenter.setButtonText(choose);
//        mPresenter.onViewPrepared();
        dosyalariSil();
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
    public void sendBarcode(String barcode, boolean isBarcode) {
        mPresenter.getBarcodeInformation(barcode, isBarcode);
    }

    @Override
    public void redirect() {
        Intent i = new Intent(getContext(), HomeActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    @OnClick(R.id.btnTazminGir)
    public void onbtnTazminGirClicked() {
        if (atf_id != null) {
            if (!atf_id.isEmpty())
                showTazminDialog();
            else
                Toast.makeText(getContext(), "ATFID bulunamadı !", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getContext(), "ATFID bulunamadı !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateAtfNo(String atfNo) {
        txtAtfNO.setText(Html.fromHtml("<font color='#9a67ea'><b> ATFNO: </b></font> " + String.valueOf(atfNo)));
    }

    @Override
    public void updateAtfID(String id) {
        atf_id = id;
        atfID = id;
//        txtAtfID.setText(Html.fromHtml("<font color='#9a67ea'><b> ATFID: </b></font> " + String.valueOf(id)));
    }

    @Override
    public void updateToplamParca(String toplamParca) {
        txtToplamParca.setText(Html.fromHtml("<font color='#9a67ea'><b> TOPLAM PARÇA: </b></font> " + String.valueOf(toplamParca)));
    }

    @Override
    public void updateVarisSube(String varisSubeAdi) {
        txtVarisSube.setText(Html.fromHtml("<font color='#9a67ea'><b> VARIŞ ŞUBE: </b></font> " + String.valueOf(varisSubeAdi)));
    }

    @Override
    public void updateCikisSube(String cikisSubeAdi) {
        txtCikisSube.setText(Html.fromHtml("<font color='#9a67ea'><b> ÇIKIŞ ŞUBE: </b> </font>" + String.valueOf(cikisSubeAdi)));
    }

    @Override
    public void updateIrsaliye(String irsaliye) {
        txtIrsaliyeNo.setText(Html.fromHtml("<font color='#9a67ea'><b> İRSALİYE NO: </b></font> " + String.valueOf(irsaliye)));
    }

    @Override
    public void updateDesi(String desi) {
        txtDesi.setText(Html.fromHtml("<font color='#9a67ea'><b> DESİ: </b></font> " + String.valueOf(desi)));
    }

    @Override
    public void updateOdemeSekli(String odemeSekli) {
        txtOdemeSekli.setText(Html.fromHtml("<font color='#9a67ea'><b> ÖDEME ŞEKLİ: </b></font> " + String.valueOf(odemeSekli)));
    }

    @Override
    public void updateAlici(String alici) {
        txtAlici.setText(Html.fromHtml("<font color='#9a67ea'><b> ALICI: </b></font> " + String.valueOf(alici)));
    }

    @Override
    public void updateGonderen(String gonderen) {
        txtGonderen.setText(Html.fromHtml("<font color='#9a67ea'><b> GÖNDEREN: </b></font> " + String.valueOf(gonderen)));
    }

    @Override
    public void updateTarih(String tarih) {
        txtTarihC.setText(Html.fromHtml("<font color='#9a67ea" +
                "'><b> TARİH: </b></font> " + String.valueOf(tarih)));
    }

    @Override
    public void updateTazminNo(String tazminNo) {
        txtTazminNo.setText(tazminNo);
    }

    @Override
    public void setSpinner(List<AtfUndeliverableReasonModel> model) {

        spinnerAdapter = new SpinnerAdapter(getBaseActivity(), R.layout.spinner_row, model);
        spinner_compensation.setAdapter(spinnerAdapter);
    }

    @Override
    public void showTazminDialog() {

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_tazmingirisi, null);

        calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        final String[] secilenAracTipi = {""};
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getBaseActivity());
        alertDialog.setCancelable(false);
        alertDialog.setTitle("Tazmin Girişi");
        alertDialog.setView(dialogView);
        alertDialog.setIcon(R.drawable.ic_perm_device_information_black_24dp);

        EditText edtHasarYeri = dialogView.findViewById(R.id.edtHasarYeri);
        EditText edtHasarAciklama = dialogView.findViewById(R.id.edtAciklama);
        EditText edtPlaka = dialogView.findViewById(R.id.edtPlaka);
        TextView edtHasarTarihi = dialogView.findViewById(R.id.edtHasarTarihi);
        TextView txtsecilenHasarTarihi1 = dialogView.findViewById(R.id.txtsecilenHasarTarihi1);
        txtHasarFoto = dialogView.findViewById(R.id.txtHasarFoto);
        txtTutanakFotoo = dialogView.findViewById(R.id.txtTutanakFotoo);

        ImageButton btnHasarFoto = dialogView.findViewById(R.id.btnHasarFoto);
        ImageButton btnTutanakFoto = dialogView.findViewById(R.id.btnTutanakFoto);
        ImageButton btnDatePicker = dialogView.findViewById(R.id.btnDatePicker);
        RadioButton rd1 = dialogView.findViewById(R.id.rd1);
        RadioButton rd2 = dialogView.findViewById(R.id.rd2);
        RadioGroup radio_group = dialogView.findViewById(R.id.radio_group);
        spinner_compensation = dialogView.findViewById(R.id.spinner_compensation);
        RelativeLayout lyt = dialogView.findViewById(R.id.trhgroup);
        LinearLayout lytDevirSebebi = dialogView.findViewById(R.id.lytDevirSebebi);
        TextView txthasaryeri = dialogView.findViewById(R.id.txthasaryeri);
        TextView txtaciklama = dialogView.findViewById(R.id.txtaciklama);
        TextView txttazminplaka = dialogView.findViewById(R.id.txttazminplaka);
        TextView txtAracTipi = dialogView.findViewById(R.id.txtAracTipi);


        if (!txtTazminNo.getText().toString().isEmpty()) {
            btnDatePicker.setVisibility(View.INVISIBLE);
            radio_group.setVisibility(View.INVISIBLE);
            edtHasarYeri.setVisibility(View.INVISIBLE);
            edtHasarAciklama.setVisibility(View.INVISIBLE);
            edtPlaka.setVisibility(View.INVISIBLE);
            edtHasarTarihi.setVisibility(View.INVISIBLE);
            spinner_compensation.setVisibility(View.INVISIBLE);
            lyt.setVisibility(View.INVISIBLE);
            lytDevirSebebi.setVisibility(View.INVISIBLE);
            txtsecilenHasarTarihi1.setVisibility(View.INVISIBLE);
            txthasaryeri.setVisibility(View.INVISIBLE);
            txtaciklama.setVisibility(View.INVISIBLE);
            txttazminplaka.setVisibility(View.INVISIBLE);
            txtAracTipi.setVisibility(View.INVISIBLE);
        } else {
            btnDatePicker.setVisibility(View.VISIBLE);
            radio_group.setVisibility(View.VISIBLE);
            edtHasarYeri.setVisibility(View.VISIBLE);
            edtHasarAciklama.setVisibility(View.VISIBLE);
            edtPlaka.setVisibility(View.VISIBLE);
            edtHasarTarihi.setVisibility(View.VISIBLE);
            spinner_compensation.setVisibility(View.VISIBLE);
            lytDevirSebebi.setVisibility(View.VISIBLE);
            lyt.setVisibility(View.VISIBLE);
            txtsecilenHasarTarihi1.setVisibility(View.VISIBLE);
            txthasaryeri.setVisibility(View.VISIBLE);
            txtaciklama.setVisibility(View.VISIBLE);
            txttazminplaka.setVisibility(View.VISIBLE);
            txtAracTipi.setVisibility(View.VISIBLE);
        }

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rd1:
                        secilenAracTipi[0] = "HAT";
                        break;
                    case R.id.rd2:
                        secilenAracTipi[0] = "DAGITIM";
                        break;
                }
            }
        });
//        rd1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                secilenAracTipi[0] = isChecked ? "HAT" : "DAGITIM";
//            }
//        });
//        rd2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                secilenAracTipi[0] = isChecked ? "DAGITIM" : "HAT";
//            }
//        });
        spinner_compensation.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                AtfUndeliverableReasonModel model = (AtfUndeliverableReasonModel) spinnerAdapter.getItemObj(position);

                tazminSebepId = model.getSebepId();

//                Toast.makeText(getContext(), "ID: " + model.getValue() + "\nName: " + model.getText(),
//                        Toast.LENGTH_SHORT).show();
            }

        });
        btnHasarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resimTipi = "RESIM";
//                dispatchTakePictureIntent(atf_id);
                cameraPermission(atf_id);
            }
        });
        btnTutanakFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resimTipi = "TUTANAK";
                cameraPermission(atf_id);
//                dispatchTakePictureIntent(atf_id);
            }
        });
//
//            edtTeslimTarihi.setHint("Devir Tarihi");
//            edtTeslimSaati.setHint("Devir Saati");
//            txtsecilenTeslimSaati.setText("Devir Saati");
//            txtsecilenTeslimTarihi.setText("Devir Tarihi");
        mPresenter.getCompensationReason();


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

        edtHasarTarihi.setText(formattedDayOfMonth + "/" + (formattedMonth) + "/" + year);

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
                        edtHasarTarihi.setText(formattedDayOfMonth + "/" + (formattedMonth) + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        alertDialog.setPositiveButton("Kaydet", (dialog, which) -> {
            dialog.dismiss();
            CompensationRequest request = new CompensationRequest();
            if (txtTazminNo.getText().toString().isEmpty()) {

                request.setAtfID(atfID);
                request.setAciklama(edtHasarAciklama.getText().toString());
                request.setHasarTarihi(edtHasarTarihi.getText().toString());
                if (tazminSebepId != null) {
                    request.setTazminSebepID(Integer.parseInt(tazminSebepId));
                }
                request.setHasarYeri(edtHasarYeri.getText().toString());
                request.setTazminTalepImageList(tazminTalepImageLists);
                request.setPlaka(edtPlaka.getText().toString());
                request.setAracTipi(secilenAracTipi[0]);
            } else {
                request.setAtfID(atfID);
//                request.setAciklama(edtHasarAciklama.getText().toString());
//                request.setHasarTarihi(edtHasarTarihi.getText().toString());
//                request.setTazminSebepID(Integer.parseInt(tazminSebepId));
//                request.setHasarYeri(edtHasarYeri.getText().toString());
                request.setTazminTalepImageList(tazminTalepImageLists);
//                request.setPlaka(edtPlaka.getText().toString());
//                request.setAracTipi(secilenAracTipi[0]);
            }

//            request.setTeslimAlanSoyad(edtTeslimAlanSoyadi.getText().toString());
//            request.setKimlikNo(edtKimlikNo.getText().toString());
//
            if (!secilenAracTipi[0].isEmpty() && txtTazminNo.getText().toString().isEmpty()) {
                mPresenter.TazminGirisi(request, txtTazminNo.getText().toString());


            } else if (secilenAracTipi[0].isEmpty() && !txtTazminNo.getText().toString().isEmpty()) {
                mPresenter.TazminGirisi(request, txtTazminNo.getText().toString());
            } else {
                Toast.makeText(getActivity(), "Araç Tipi Seçiniz !", Toast.LENGTH_SHORT).show();
            }

        });

        alertDialog.setNegativeButton("Kapat", (dialog, which) -> {
            dialog.dismiss();
        });

        alertDialog.show();
    }

    //    @OnCheckedChanged(R.id.rd1)
//    public void onAracTipiSelected(CompoundButton button, boolean checked) {
//        this.secilenAracTipi = checked ? "HAT" : "DAGITIM";
//    }
//    public void cameraPermission(String a) {
//        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
//        } else {
//            dispatchTakePictureIntent(a);
//        }
//    }
    public void cameraPermission(String imagePath) {
        // Context ve Activity'nin null olup olmadığını kontrol et
        if (getContext() == null || getActivity() == null) {
            return;
        }
        currentImagePath = imagePath;
        // Kamera iznini kontrol et
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Eğer izin verilmemişse, kullanıcıdan izin iste
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            // Eğer izin verilmişse, doğrudan fotoğraf çekim işlemini başlat
            dispatchTakePictureIntent(currentImagePath);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Kullanıcı izni verdiyse, dispatchTakePictureIntent'i çağır
                dispatchTakePictureIntent(currentImagePath);  // Fotoğraf çekme işlemini başlat
            } else {
                // Kullanıcı izni reddettiyse, bir uyarı göster
                Toast.makeText(getContext(), "Kamera izni gerekli", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //    @Override
//    public void dispatchTakePictureIntent(String id) {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra("id", id);
//        if (resimTipi.equals("RESIM")) {
//            atf_id = id;
//        } else {
//            atf_id = id + "_2";
//        }
//
//        File photoFile = null;
//        try {
//            photoFile = createImageFile(atf_id);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
//        Uri photoUri = FileProvider.getUriForFile(getBaseActivity(), getActivity().getPackageName() + ".provider", photoFile);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
//        startActivityForResult(intent, 100);
//    }
    @Override
    public void dispatchTakePictureIntent(String id) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile(id); // Dosya oluşturma
                filePath = photoFile.getAbsolutePath(); // Dosya yolunu güncelle
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(getBaseActivity(), getActivity().getPackageName() + ".provider", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, 100);
            }
        } else {
            // Kamera uygulaması yoksa
            Intent manualCameraIntent = new Intent(getActivity(), CameraActivity.class);
            manualCameraIntent.putExtra("id", id);
            startActivityForResult(manualCameraIntent, 100);
        }
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data); // Unutma! Bu satırı eklemelisin
        if (requestCode == 100 && resultCode == RESULT_OK) {
            // Eğer resim kendi CameraActivity'nden geldiyse
            if (data != null && data.hasExtra("image_path")) {
                filePath = data.getStringExtra("image_path"); // image_path'i al ve filePath'ı güncelle

                // Burada filePath kullanarak resmi yükleyin
                try {
                    File file = new File(filePath); // filePath burada tanımlı
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.fromFile(file));

                    // Resmi JPEG formatında sıkıştır ve byte dizisine çevir
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();

                    // Base64'e çevir ve gereksiz karakterleri temizle
                    String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT).replace("\n", "").trim();

                    // Resim tipine göre uygun alanları doldur
                    TazminTalepImageList imageList = new TazminTalepImageList();
                    imageList.setBase64String(encodedImage);
                    imageList.setDosyaAdi(atf_id + ".jpg");

                    if (resimTipi.equals("RESIM")) {
                        imageList.setResimTipi("RESIM");
                        tazminTalepImageLists.add(imageList);
                        txtHasarFoto.setText(atf_id + ".jpg");
                    } else {
                        imageList.setResimTipi("TUTANAK");
                        tazminTalepImageLists.add(imageList);
                        txtTutanakFotoo.setText(atf_id + ".jpg");
                    }
                } catch (IOException e) {
                    Toast.makeText(getActivity(), "Resim yüklenirken hata oluştu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Hata: " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            } else {
                // Eğer varsayılan kamera ile çekim yapılmışsa
                // Burada filePath kullanarak resmi yükleyin (bu durumda filePath daha önce güncellenmiş olmalı)
                try {
                    File file = new File(filePath);
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.fromFile(file));

                    // Resmi JPEG formatında sıkıştır ve byte dizisine çevir
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();

                    // Base64'e çevir ve gereksiz karakterleri temizle
                    String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT).replace("\n", "").trim();

                    // Resim tipine göre uygun alanları doldur
                    TazminTalepImageList imageList = new TazminTalepImageList();
                    imageList.setBase64String(encodedImage);
                    imageList.setDosyaAdi(atf_id + ".jpg");

                    if (resimTipi.equals("RESIM")) {
                        imageList.setResimTipi("RESIM");
                        tazminTalepImageLists.add(imageList);
                        txtHasarFoto.setText(atf_id + ".jpg");
                    } else {
                        imageList.setResimTipi("TUTANAK");
                        tazminTalepImageLists.add(imageList);
                        txtTutanakFotoo.setText(atf_id + ".jpg");
                    }
                } catch (IOException e) {
                    Toast.makeText(getActivity(), "Resim yüklenirken hata oluştu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Hata: " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getActivity(), "Fotoğraf çekmeden çıktınız.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "HATA: Fotoğraf çekilemedi.", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void showTokenExpired() {
        BaseActivity.showTokenExpired(getActivity(), "Oturum süresi doldu. Tekrar giriş yapınız", "UYARI");
    }


}
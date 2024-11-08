package com.bt.arasholding.filloapp.ui.delivery.multidelivery;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.bt.arasholding.filloapp.data.network.manager.BarcodeTracker;
import com.bt.arasholding.filloapp.data.network.manager.DeliveryDataManager;
import com.bt.arasholding.filloapp.data.network.model.AtfParcelCount;
import com.bt.arasholding.filloapp.data.network.model.AtfUndeliverableReasonModel;
import com.bt.arasholding.filloapp.data.network.model.CargoDetail;
import com.bt.arasholding.filloapp.data.network.model.CustomSpinnerItem;
import com.bt.arasholding.filloapp.data.network.model.DeliverCargoImageUploadRequest;
import com.bt.arasholding.filloapp.data.network.model.DeliveredCargoRequest;
import com.bt.arasholding.filloapp.di.component.ActivityComponent;
import com.bt.arasholding.filloapp.ui.base.BaseActivity;
import com.bt.arasholding.filloapp.ui.base.BaseFragment;
import com.bt.arasholding.filloapp.ui.base.MvpPresenter;
import com.bt.arasholding.filloapp.ui.delivery.DeliveryActivity;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static androidx.appcompat.app.AppCompatActivity.RESULT_CANCELED;
import static androidx.appcompat.app.AppCompatActivity.RESULT_OK;
import static java.lang.Integer.parseInt;

public class MultiDeliveryFragment extends BaseFragment implements MultiDeliveryMvpView {

    public static final String TAG = "MultiDeliveryFragment";

    @Inject
    MultiDeliveryMvpPresenter<MultiDeliveryMvpView> mPresenter;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    @BindView(R.id.recycler_delivery_list)
    RecyclerView recyclerDeliveryList;

    @BindView(R.id.txtSayac)
    TextView txtSayac;
    @BindView(R.id.txtToplamOkutulan)
    TextView txtToplamOkutulan;
    //    @BindView(R.id.spinner_teslimat_tip)
//    MaterialSpinner spinner_teslimat_tip;
    @BindView(R.id.btnTeslimatKapat)
    QButton btnTeslimatKapat;
    @BindView(R.id.btnTeslimatKapatCoklu)
    QButton btnTeslimatKapatCoklu;

    @BindView(R.id.btnDevir)
    QButton btnDevir;

    @BindView(R.id.btn_total_pieces)
    QButton btn_total_pieces;

    List<AtfParcelCount> atfParcelCountList = new ArrayList<>();
    int okutmaTipi;

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

    int okutulanBarkodSayisi;

    private String teslimTipiValue = "NT";

    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int hour, minute;

    MaterialSpinner spinner_undeliverable;
    MaterialSpinner spinner_TeslimTipi;

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

        btn_total_pieces.setOnClickListener(v -> showATFPieceDetails());

        return view;
    }

    @Override
    protected void setUp(View view) {
        mLayoutManager = new LinearLayoutManager(getBaseActivity());
        recyclerDeliveryList.setLayoutManager(mLayoutManager);
        getCurrentLocation(view);
        resetSingletonModel();
//        setupSpinner(view);
//        showAlertDialogChoose();
        mPresenter.setButtonText(choose);
        mPresenter.onViewPrepared();
        dosyalariSil();

    }

//    private void setupSpinner(View view) {
//
//        spinner_teslimat_tip = view.findViewById(R.id.spinner_teslimat_tip);
//
//        String[] items = {"Normal Teslimat", "Eksik Teslimat"};
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, items);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spinner_teslimat_tip.setAdapter(adapter);
//
//        spinner_teslimat_tip.setSelectedIndex(0);
//        TeslimTip = "1";
//        spinner_teslimat_tip.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
//            @Override
//            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                if (position == 0) {
//                    TeslimTip = "1";
//                } else {
//                    TeslimTip = "2";
//                }
//            }
//        });
//    }

    @OnClick(R.id.btnTeslimatKapat)
    public void onViewClicked() {
        checkMissingPieces();
    }
    public void checkMissingPieces() {
        Map<String, Integer> totalPiecesMap = BarcodeTracker.getInstance().getTotalPiecesMap();
        Map<String, Set<String>> scannedPiecesMap = BarcodeTracker.getInstance().getScannedPiecesMap();

        List<String> eksikParcaListesi = new ArrayList<>();
        Map<String, List<String>> okutulmayanParcalarMap = new HashMap<>();
        Map<String, Integer> okutulmusParcaSayilariMap = new HashMap<>();
        List<String> okutulanParcalar;

        for (String atfNo : totalPiecesMap.keySet()) {
            int totalPieces = totalPiecesMap.get(atfNo);
            Set<String> scannedPieces = scannedPiecesMap.containsKey(atfNo) ? scannedPiecesMap.get(atfNo) : new HashSet<>();
            okutulanParcalar = BarcodeTracker.getInstance().getBarcodesForATF(atfNo);

            int eksikParcaSayisi = totalPieces - scannedPieces.size();
            if (eksikParcaSayisi > 0) {
                List<String> okutulmayanParcalar = new ArrayList<>();
                for (int i = 1; i <= totalPieces; i++) {
                    String parcaNo = String.valueOf(i);
                    if (!scannedPieces.contains(parcaNo)) {
                        okutulmayanParcalar.add(parcaNo);
                    }
                }
                okutulmayanParcalarMap.put(atfNo, okutulmayanParcalar);
                eksikParcaListesi.add(atfNo);

                int okutulmusParcaSayisi = scannedPieces.size();
                okutulmusParcaSayilariMap.put(atfNo, okutulmusParcaSayisi);

            }
            else if(eksikParcaSayisi == 0)
            {
                atfParcelCountList.add(new AtfParcelCount(atfNo,scannedPieces.size(),"NT",okutulanParcalar));
            }
        }

        Map<String, Integer> toplamParcaSayilariMap = new HashMap<>(totalPiecesMap);

        if (!eksikParcaListesi.isEmpty()) {
            showMissingPiecesDialog(eksikParcaListesi, okutulmayanParcalarMap, okutulmusParcaSayilariMap, toplamParcaSayilariMap);
        } else {
            mPresenter.showTeslimatDialog2(atfParcelCountList);
        }
    }
    private void showMissingPiecesDialog(List<String> eksikParcaListesi, Map<String, List<String>> okutulmayanParcalarMap, Map<String, Integer> okutulmusParcaSayilariMap, Map<String, Integer> toplamParcaSayilariMap) {
        // Özel bir tema kullanarak AlertDialog.Builder oluşturuyoruz
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.FullWidthDialog);

        // LinearLayout oluştur
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 40, 40, 40); // Daha fazla padding ekliyoruz

        // Başlık TextView
        TextView titleTextView = new TextView(getContext());
        titleTextView.setText("Eksik Teslimat");
        titleTextView.setTextSize(20); // Başlık boyutunu artırıyoruz
        titleTextView.setTypeface(null, Typeface.BOLD);
        titleTextView.setTextColor(Color.RED);
        titleTextView.setGravity(Gravity.CENTER); // Ortalıyoruz
        layout.addView(titleTextView);

        // Açıklayıcı yazı TextView
        String message = "Tüm Parçalar Okutulmadı. Eksik Teslimat Yapılacak. Devam Edilsin Mi? Teslim Parça Sayısına Toplam Parça yazılan ATF'lere Normal Teslimat İşlemi uygulanır.";
        TextView messageTextView = new TextView(getContext());
        messageTextView.setText(message);
        messageTextView.setTextSize(12); // Yazı boyutunu artırıyoruz
        messageTextView.setTypeface(null, Typeface.ITALIC); // İtalik yapıyoruz
        messageTextView.setTextColor(Color.GRAY); // Yazı rengini gri yapıyoruz
        layout.addView(messageTextView);

        // Liste öğelerini ekleyin
        List<EditText> editTexts = new ArrayList<>(); // EditText referanslarını saklamak için
        for (String eksikParca : eksikParcaListesi) {
            // Kutu oluştur
            LinearLayout itemLayout = new LinearLayout(getContext());
            itemLayout.setOrientation(LinearLayout.VERTICAL);
            itemLayout.setBackgroundResource(R.drawable.item_background); // Arka plan rengi veya şekli

            // ATF ve eksik parça metnini ayır
            String[] parts = eksikParca.split(" - ");
            String atfNo = parts[0];

            // Yan yana dizilecek LinearLayout
            LinearLayout horizontalLayout = new LinearLayout(getContext());
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            // ATF TextView
            TextView atfTextView = new TextView(getContext());
            atfTextView.setText(atfNo + " - Teslim Parça Sayısı: ");
            atfTextView.setTextSize(16); // Yazı boyutunu artırıyoruz
            atfTextView.setTypeface(null, Typeface.BOLD);
            horizontalLayout.addView(atfTextView);

            // EditText ekliyoruz
            EditText parcaEditText = new EditText(getContext());
            parcaEditText.setTextSize(16); // Daha büyük metin boyutu
            parcaEditText.setPadding(16, 16, 16, 16); // İç padding ayarlıyoruz
            parcaEditText.setBackgroundColor(Color.WHITE); // Arka planı beyaz yapıyoruz
            parcaEditText.setTextColor(Color.BLACK); // Yazı rengini siyah yapıyoruz
            parcaEditText.setLayoutParams(new LinearLayout.LayoutParams(
                    0, // Genişlik: ağırlığa göre ayarla
                    LinearLayout.LayoutParams.WRAP_CONTENT, // Yükseklik
                    1.0f // Ağırlık: EditText genişliğini artırır
            ));
            parcaEditText.setInputType(InputType.TYPE_CLASS_NUMBER); // Sadece sayılar için

            int okutulmusParcaSayisi = okutulmusParcaSayilariMap.containsKey(atfNo) ?
                    okutulmusParcaSayilariMap.get(atfNo) : 0;

            parcaEditText.setText(String.valueOf(okutulmusParcaSayisi)); // Okutulmuş parça sayısı varsayılan değer

            // Horizontal layout'a EditText'i ekliyoruz
            horizontalLayout.addView(parcaEditText);
            editTexts.add(parcaEditText); // EditText referansını kaydediyoruz

            // Horizontal layout'u item layout'a ekliyoruz
            itemLayout.addView(horizontalLayout);

            // Yukarıdan boşluk ekliyoruz
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 30, 0, 0); // Üstten 30dp boşluk
            itemLayout.setLayoutParams(params);

            // Kutuya tıklama olayı
            itemLayout.setOnClickListener(v -> {
                // İkinci dialogu göster (okutulmayan parçalarla)
                List<String> okutulmayanParcalar = okutulmayanParcalarMap.get(atfNo);
                if (okutulmayanParcalar != null) {
                    showUnscannedPiecesDialog(atfNo, okutulmayanParcalar);
                }
            });

            // EditText için TextWatcher ekliyoruz
            parcaEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().isEmpty()) {
                        int girilenSayisi = Integer.parseInt(s.toString());

                        // Toplam parça sayısını almak için atf'nin toplam parça sayısını kullanıyoruz
                        int toplamParcaSayisi = toplamParcaSayilariMap.containsKey(atfNo) ? toplamParcaSayilariMap.get(atfNo) : 0;

                        if (girilenSayisi > toplamParcaSayisi) {
                            // Girilen sayı toplam parça sayısını aşıyorsa, onu toplam parça sayısına ayarla
                            parcaEditText.setText(String.valueOf(toplamParcaSayisi));
                            parcaEditText.setSelection(parcaEditText.getText().length()); // İmleci sonuna getir
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });

            // Kutuya ekliyoruz
            layout.addView(itemLayout);
        }

        builder.setView(layout) // Layout'u diyalogda gösteriyoruz
                .setPositiveButton("Kapat", (dialog, which) -> {
                    atfParcelCountList.clear();
                })
                .setNegativeButton("Teslim Et", (dialog, which) -> {

                     for (int i = 0; i < eksikParcaListesi.size(); i++) {
                        String eksikParca = eksikParcaListesi.get(i);
                        String[] parts = eksikParca.split(" - ");
                        String atfNo = parts[0];

                        EditText editText = editTexts.get(i); // EditText referansını alıyoruz
                        String parcaSayisiStr = editText.getText().toString().trim(); // Kullanıcıdan alınan sayı

                        // Toplam parça sayısını almak için atf'nin toplam parça sayısını kullanıyoruz
                        int toplamParcaSayisi = toplamParcaSayilariMap.containsKey(atfNo) ? toplamParcaSayilariMap.get(atfNo) : 0;
                         List<String> okutulanParcalar = BarcodeTracker.getInstance().getBarcodesForATF(atfNo);

                        if (!parcaSayisiStr.isEmpty()) {
                            int parcaSayisi = Integer.parseInt(parcaSayisiStr);
                            String teslimTipi = (parcaSayisi == toplamParcaSayisi) ? "NT" : "ET"; // Buradaki toplamParcaSayisi kullanılabilir
                            // Teslim tipi NT veya ET
                            AtfParcelCount atfParcelCount = new AtfParcelCount(atfNo, parcaSayisi, teslimTipi,okutulanParcalar);
                            atfParcelCountList.add(atfParcelCount);
                        }
                    }

                    mPresenter.showTeslimatDialog2(atfParcelCountList);


                    // Burada atfParcelCountList'i kullanarak teslimat işlemlerini gerçekleştirin
                })
                .setCancelable(false); // Diyalog dışına tıklanamaz
        AlertDialog dialog = builder.create();
        dialog.show(); // Diyaloğu göster
    }
    private void showUnscannedPiecesDialog(String atfNo, List<String> okutulmayanParcalar) {
        // Okutulmamış parçaları formatlamak için yeni bir liste oluştur
        List<String> formattedPieces = new ArrayList<>();
        for (String parcaNo : okutulmayanParcalar) {
            formattedPieces.add(atfNo + " (" + parcaNo + ")");
        }

        AlertDialog.Builder innerBuilder = new AlertDialog.Builder(getContext());
        innerBuilder.setTitle("Okutulmamış Parçalar")
                .setItems(formattedPieces.toArray(new String[0]), null)
                .setPositiveButton("Kapat", null)
                .show();
    }
    private void showATFPieceDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());  // Fragment'in context'i
        builder.setTitle("ATF Parça Detayları");

        StringBuilder message = new StringBuilder();
        for (Map.Entry<String, Integer> entry : DeliveryDataManager.getInstance().getTotalPiecesMap().entrySet()) {
            message.append(entry.getKey())
                    .append(" - Toplam Parça: ").append(entry.getValue()).append("\n");
        }

        builder.setMessage(message.toString());
        builder.setPositiveButton("Tamam", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    @OnClick(R.id.btnBarkodOkut)
    public void onbtnBarkodOkutClicked() {
        ((DeliveryActivity) getActivity()).showCameraFragment();
    }

    @OnClick(R.id.btnTeslimatKapatCoklu)
    public void onbtnTeslimatKapatCokluClicked() {
        mPresenter.showTeslimatDialog();
    }

    @OnClick(R.id.btnDevir)
    public void onbtnDevirClicked() {
        mPresenter.showTeslimatDialog();
    }


    @Override
    public void sendBarcode(String barcode, int okutulmusSayi, boolean isBarcode) {
        if (barcode.length() == 34)
        {
            if(choose.equals("ATFTESLIMAT"))
            {
                btn_total_pieces.setVisibility(View.VISIBLE);
                btnTeslimatKapat.setVisibility(View.VISIBLE);
                btnDevir.setVisibility(View.GONE);
                btnTeslimatKapatCoklu.setVisibility(View.GONE);
                okutmaTipi = 1;
                mPresenter.getBarcodeInformation2(barcode, isBarcode,okutmaTipi);
            }
            else{
                btnTeslimatKapat.setVisibility(View.GONE);
                btnTeslimatKapatCoklu.setVisibility(View.GONE);
                btnDevir.setVisibility(View.VISIBLE);
                btn_total_pieces.setVisibility(View.INVISIBLE);
                okutmaTipi = 1;
                mPresenter.getBarcodeInformation2(barcode, isBarcode,okutmaTipi);
            }
        }
        else {
            okutmaTipi = 2;
            btnTeslimatKapat.setVisibility(View.GONE);
            btnTeslimatKapatCoklu.setVisibility(View.VISIBLE);
            btn_total_pieces.setVisibility(View.INVISIBLE);
            mPresenter.getBarcodeInformation(barcode, isBarcode,okutmaTipi);
        }
        okutulanBarkodSayisi = okutulmusSayi;
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
        TextView txtMusteriTeslimat = dialogView.findViewById(R.id.txtMusteriTeslimat);
        TextView txtMusteriTeslimatFoto = dialogView.findViewById(R.id.txt_musteriteslimat_foto);
        LinearLayout lytTeslimAlanAd = dialogView.findViewById(R.id.lytTeslimAlanAd);
        LinearLayout lytTeslimAlanSoyad = dialogView.findViewById(R.id.lytTeslimAlanSoyad);
        LinearLayout lytTeslimAlanKimlikNo = dialogView.findViewById(R.id.lytTeslimAlanKimlikNo);
        LinearLayout lytDevirSebebi = dialogView.findViewById(R.id.lytDevirSebebi);
        ImageButton btnDatePicker = dialogView.findViewById(R.id.btnDatePicker);
        ImageButton btnTimePicker = dialogView.findViewById(R.id.btnTimePicker);
        spinner_undeliverable = dialogView.findViewById(R.id.spinner_undeliverable);

        ImageButton btnMusteriTeslimatFoto = dialogView.findViewById(R.id.btnMusteriTeslimatFoto);

//        btnMusteriTeslimatFoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dispatchTakePictureIntent();
//            }
//        });


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
            request.setMapXY(mPresenter.getLatitude() + ";" + mPresenter.getLongitude());

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
    public void updateBarcodeList(List<Barcode> barcodeList, int okutmaTipi) {
        adapter = new MultiDeliveryRecyclerListAdapter(barcodeList, getBaseActivity(), this,okutmaTipi);

        adapter.notifyDataSetChanged();
        recyclerDeliveryList.setAdapter(adapter);
    }
    @Override
    public void sonucAdapter(List<Barcode> barcodeList, int tip) {
        MultiDeliveryDialog dialog = new MultiDeliveryDialog(getActivity(), barcodeList, this,tip);
        dialog.show();
    }
    public void goBack() {
        // Geri gitmek istediğiniz yer
        getActivity().onBackPressed(); // veya başka bir işlem
    }
    @Override
    public void resetSingletonModel() {
        atfParcelCountList.clear();
        BarcodeTracker barcodeTracker = BarcodeTracker.getInstance();
        DeliveryDataManager deliveryDataManager = DeliveryDataManager.getInstance();
        barcodeTracker.reset();
        deliveryDataManager.reset();
    }

    @Override
    public void setAlindiMi(long id, String deger) {
        mPresenter.setAlindiMi(id, deger);
    }

//    @Override
//    public void setHasarPhoto(long id, String foto) {
//
//    }

//    @Override
//    public void setHasarAciklama(long id, String aciklama) {
//        mPresenter.setHasarAciklama(id, aciklama);
//    }

    @Override
    public void setSpinner(List<AtfUndeliverableReasonModel> model) {

        spinnerAdapter = new SpinnerAdapter(getBaseActivity(), R.layout.spinner_row, model);
        spinner_undeliverable.setAdapter(spinnerAdapter);
    }

    @Override
    public void setButtonText(String choose) {
        btnTeslimatKapat.setText(choose);
        btnTeslimatKapatCoklu.setText(choose);
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
    public void showTeslimatDialog2(List<AtfParcelCount> okutulanAtfler) {

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
        EditText edtTeslimAdet = dialogView.findViewById(R.id.edtTeslimAdet);

        TextView edtTeslimTarihi = dialogView.findViewById(R.id.edtTeslimTarihi);
        TextView edtTeslimSaati = dialogView.findViewById(R.id.edtTeslimSaati);
        TextView txtsecilenTeslimSaati = dialogView.findViewById(R.id.txtsecilenTeslimSaati1);
        TextView txtsecilenTeslimTarihi = dialogView.findViewById(R.id.txtsecilenTeslimTarihi1);
        TextView txtTeslimAdet = dialogView.findViewById(R.id.txtTeslimAdet);

        LinearLayout lytTeslimAlanAd = dialogView.findViewById(R.id.lytTeslimAlanAd);
        LinearLayout lytTeslimAlanSoyad = dialogView.findViewById(R.id.lytTeslimAlanSoyad);
        LinearLayout lytTeslimAlanKimlikNo = dialogView.findViewById(R.id.lytTeslimAlanKimlikNo);
        LinearLayout lytDevirSebebi = dialogView.findViewById(R.id.lytDevirSebebi);
        LinearLayout lytTeslimTipi = dialogView.findViewById(R.id.lytTeslimTipi);
        ImageButton btnDatePicker = dialogView.findViewById(R.id.btnDatePicker);
        ImageButton btnTimePicker = dialogView.findViewById(R.id.btnTimePicker);
        spinner_undeliverable = dialogView.findViewById(R.id.spinner_undeliverable);
        spinner_TeslimTipi = dialogView.findViewById(R.id.spinner_TeslimTipi);

//        if (TeslimTipi.equals("2")) {
//            edtTeslimAdet.setVisibility(View.VISIBLE);
//            txtTeslimAdet.setVisibility(View.VISIBLE);
//        }

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
            lytTeslimTipi.setVisibility(View.GONE);
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
            request.setTeslimTipi("NT");
            if (choose.equals("ATFDEVIR")) {
                request.setDevirSebebi(devirSebepId);
                request.setIslemTipiAtf("0");
            } else {
                request.setTeslimAlanAd(edtTeslimAlanAdi.getText().toString());
                request.setTeslimAlanSoyad(edtTeslimAlanSoyadi.getText().toString());
                request.setKimlikNo(edtKimlikNo.getText().toString());
                request.setIslemTipiAtf("-1");
            }
//            if (TeslimTipi.equals("2")) {
//                request.setTeslimadet(edtTeslimAdet.getText().toString());
//            }
            request.setMapXY(mPresenter.getLatitude() + ";" + mPresenter.getLongitude());
            mPresenter.teslimatKapat2(request,okutulanAtfler);
        });

        alertDialog.setNegativeButton("Kapat", (dialog, which) -> {
            atfParcelCountList.clear();
            dialog.dismiss();
        });

        alertDialog.show();
    }

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
        EditText edtTeslimAdet = dialogView.findViewById(R.id.edtTeslimAdet);

        TextView edtTeslimTarihi = dialogView.findViewById(R.id.edtTeslimTarihi);
        TextView edtTeslimSaati = dialogView.findViewById(R.id.edtTeslimSaati);
        TextView txtsecilenTeslimSaati = dialogView.findViewById(R.id.txtsecilenTeslimSaati1);
        TextView txtsecilenTeslimTarihi = dialogView.findViewById(R.id.txtsecilenTeslimTarihi1);
        TextView txtTeslimAdet = dialogView.findViewById(R.id.txtTeslimAdet);

        LinearLayout lytTeslimAlanAd = dialogView.findViewById(R.id.lytTeslimAlanAd);
        LinearLayout lytTeslimAlanSoyad = dialogView.findViewById(R.id.lytTeslimAlanSoyad);
        LinearLayout lytTeslimAlanKimlikNo = dialogView.findViewById(R.id.lytTeslimAlanKimlikNo);
        LinearLayout lytDevirSebebi = dialogView.findViewById(R.id.lytDevirSebebi);
        LinearLayout lytTeslimTipi = dialogView.findViewById(R.id.lytTeslimTipi);
        ImageButton btnDatePicker = dialogView.findViewById(R.id.btnDatePicker);
        ImageButton btnTimePicker = dialogView.findViewById(R.id.btnTimePicker);
        spinner_undeliverable = dialogView.findViewById(R.id.spinner_undeliverable);
        spinner_TeslimTipi = dialogView.findViewById(R.id.spinner_TeslimTipi);

// Spinner için adapter oluşturma
// Spinner için adapter oluşturma
        List<CustomSpinnerItem> teslimTipiList = new ArrayList<>();
        teslimTipiList.add(new CustomSpinnerItem("Normal Teslimat", "NT"));
        teslimTipiList.add(new CustomSpinnerItem("Eksik Teslimat", "ET"));

        ArrayAdapter<CustomSpinnerItem> teslimTipiAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, teslimTipiList);
        teslimTipiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_TeslimTipi.setAdapter(teslimTipiAdapter);

// Spinner adapteri setlendikten sonra istediğimiz öğeyi seçiyoruz
        spinner_TeslimTipi.post(() -> {
            CustomSpinnerItem defaultItem = teslimTipiList.get(0);  // 0 indexindeki "Normal Teslimat" öğesi
            spinner_TeslimTipi.setSelectedIndex(0);  // Eğer MaterialSpinner'da setSelectedIndex varsa kullanın, yoksa post ile işlem yapılabilir
            // Eğer gerekli ise, spinner_TeslimTipi.setText(defaultItem.toString()); eklenebilir.
        });

// Spinner seçimi değiştiğinde yapılacak işlemler
        spinner_TeslimTipi.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                CustomSpinnerItem selectedItem = (CustomSpinnerItem) item;
                teslimTipiValue = selectedItem.getValue();  // Seçilen item'in value'su

                // Normal Teslimat seçilmişse edtTeslimAdet gizlenecek
                if (teslimTipiValue.equals("NT")) {
                    edtTeslimAdet.setVisibility(View.GONE);
                    txtTeslimAdet.setVisibility(View.GONE);
                }
                // Eksik Teslimat seçilmişse edtTeslimAdet gösterilecek
                else if (teslimTipiValue.equals("ET")) {
                    edtTeslimAdet.setVisibility(View.VISIBLE);
                    txtTeslimAdet.setVisibility(View.VISIBLE);
                }
            }
        });




//        if (TeslimTipi.equals("2")) {
//            edtTeslimAdet.setVisibility(View.VISIBLE);
//            txtTeslimAdet.setVisibility(View.VISIBLE);
//        }

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
            lytTeslimTipi.setVisibility(View.GONE);
            mPresenter.getAtfUndeliverableReason();

        } else {
            lytTeslimTipi.setVisibility(View.VISIBLE);
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
            request.setTeslimTipi(teslimTipiValue);
            if (choose.equals("ATFDEVIR")) {
                request.setDevirSebebi(devirSebepId);
                request.setIslemTipiAtf("0");
            } else {
                request.setTeslimAlanAd(edtTeslimAlanAdi.getText().toString());
                request.setTeslimAlanSoyad(edtTeslimAlanSoyadi.getText().toString());
                request.setKimlikNo(edtKimlikNo.getText().toString());
                request.setIslemTipiAtf("-1");
                if (teslimTipiValue.equals("ET")) {
                    request.setTeslimadet(edtTeslimAdet.getText().toString());
                }
            }

            request.setMapXY(mPresenter.getLatitude() + ";" + mPresenter.getLongitude());

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

//    @Override
//    public void showHasarAciklamaDialog(long id) {
//        LayoutInflater inflater = this.getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.dialog_hasar_aciklama, null);
//
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getBaseActivity());
//        alertDialog.setTitle("Hasar Açıklaması Gir");
//        alertDialog.setView(dialogView);
//        alertDialog.setIcon(R.drawable.ic_perm_device_information_black_24dp);
//
//        EditText edtHasarAciklama = dialogView.findViewById(R.id.edtHasarAciklama);
//
//        alertDialog.setPositiveButton("Kaydet", (dialog, which) -> {
//
//            setHasarAciklama(id, edtHasarAciklama.getText().toString());
//            dialog.dismiss();
//        });
//
//        alertDialog.setNegativeButton("Kapat", (dialog, which) -> {
//            dialog.dismiss();
//        });
//
//        alertDialog.show();
//    }

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
    public void deleteAtf(String AtfId,int tip) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseActivity());
        builder.setTitle("UYARI?");
        builder.setMessage("Atf Listeden Kaldırılacak Devam Edilsin mi?");
        builder.setCancelable(false);
        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.deleteAtfList(AtfId,tip);
                SayacAzalt();

            }
        });
        builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.refreshList(12,tip);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public void showErrorMessage(String message) {
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

    @Override
    public void updateSayac(int sayi,int sayacTip) {
        if (sayacTip == 1)
        {
            txtToplamOkutulan.setText("Toplam Okutulan:");
        }
        else if (sayacTip == 2)
        {
            txtToplamOkutulan.setText("Okunan Atf Sayısı:");
        }
        int sayac = Integer.valueOf(txtSayac.getText().toString());

        sayac = sayac + sayi;

        txtSayac.setText(sayac + "");
    }

    public void SayacAzalt() {
        int sayac = Integer.valueOf(txtSayac.getText().toString());

        sayac--;

        txtSayac.setText(sayac + "");
    }

    public void updateTotalPieceCount(CargoDetail item) {
        String atfNo = item.getAtfNo();
//        int totalPieces = item.getToplamParca();
        int totalPieces = parseInt(item.getToplamParca());// API'den gelen toplam parça sayısı

        // ATF zaten eklenmediyse toplam parça sayısını ekleyin
        if (!DeliveryDataManager.getInstance().getTotalPiecesMap().containsKey(atfNo)) {
            DeliveryDataManager.getInstance().getTotalPiecesMap().put(atfNo, totalPieces);

            // Toplam parça sayısını güncelleyin
            updateTotalPiecesUI();
        }
    }

    private void updateTotalPiecesUI() {
        int total = 0;
        for (int count : DeliveryDataManager.getInstance().getTotalPiecesMap().values()) {
            total += count;
        }

        // Toplam parçayı gösteren butonu güncelleyin
        btn_total_pieces.setText("Toplam Parça: " + total);
    }

    private void getLocation() {
        if (konumYoneticisi.getAllProviders().contains("network")) {
            if (ActivityCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            konumYoneticisi.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, locationListener);
        } else {
            if (ActivityCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            konumYoneticisi.requestLocationUpdates("gps", 5000, 0, locationListener);
        }
    }
    @Override
    public void showTokenExpired(){
        BaseActivity.showTokenExpired(getActivity(),"Oturum süresi doldu. Tekrar giriş yapınız","UYARI");
    }
}
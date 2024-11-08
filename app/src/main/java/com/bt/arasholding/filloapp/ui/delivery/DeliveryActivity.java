package com.bt.arasholding.filloapp.ui.delivery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.ui.base.BaseActivity;
import com.bt.arasholding.filloapp.ui.bluetooth.BluetoothFragment;
import com.bt.arasholding.filloapp.ui.camera.CameraFragment;
import com.bt.arasholding.filloapp.ui.cargobarcode.CargoBarcodeActivity;
import com.bt.arasholding.filloapp.ui.delivercargo.DeliverCargoActivity;
import com.bt.arasholding.filloapp.ui.delivermultiplecustomerwaybill.DeliverMultipleCustomerActivity;
import com.bt.arasholding.filloapp.ui.delivery.cargomovement.CargoMovementFragment;
import com.bt.arasholding.filloapp.ui.delivery.cargomovement.CargoMovementMvpView;
import com.bt.arasholding.filloapp.ui.delivery.compensation.CompensationFragment;
import com.bt.arasholding.filloapp.ui.delivery.compensation.CompensationMvpView;
import com.bt.arasholding.filloapp.ui.delivery.multidelivery.MultiDeliveryFragment;
import com.bt.arasholding.filloapp.ui.delivery.multidelivery.MultiDeliveryMvpView;
import com.bt.arasholding.filloapp.utils.AppConstants;

import org.w3c.dom.Text;

import java.util.HashSet;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class DeliveryActivity extends BaseActivity implements
        DeliveryMvpView,
        CameraFragment.ScanResult,
        BluetoothFragment.BluetoothResult {

    @Inject
    DeliveryMvpPresenter<DeliveryMvpView> mPresenter;

    private Handler handler = new Handler();
    @BindView(R.id.edtTrackId)
    EditText edtTrackId;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    String islemTipi = "";
    String arkodu = "";
    String noBarcodeId = "";

    private HashSet<String> scannedBarcodes;

    private boolean isBarcodeProcessing = false;
    private TextWatcher barcodeTextWatcher;

    FragmentManager fragmentManager = getSupportFragmentManager();

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, DeliveryActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        scannedBarcodes = new HashSet<>();

        edtTrackId.requestFocus();

        mPresenter.onAttach(this);
        setUp();
        if (islemTipi.equals(AppConstants.MUSTERITESLIMAT)) {
            RelativeLayout atfNoLayout = (RelativeLayout) findViewById(R.id.atfNo);
            RelativeLayout musteriteslimat = (RelativeLayout) findViewById(R.id.rlt_musteriteslimat);
            TextView MusteriTeslimatTxt = (TextView) findViewById(R.id.txtMusteriTeslimat);
            MusteriTeslimatTxt.setVisibility(View.VISIBLE);
            musteriteslimat.setVisibility(View.VISIBLE);
            atfNoLayout.setVisibility(View.GONE);
        }
        setupBarcodeTextWatcher();
    }
    private void setupBarcodeTextWatcher() {
        barcodeTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Sadece otomatik barkod okuma durumunda işlemi tetikle
                if (isBarcodeProcessing) {
                    return; // Eğer barkod işleniyorsa, çık
                }

                // Eğer girilen karakter sayısı 1 ise, bir süre bekle
                if (s.length() == 1) {
                    // Kullanıcının bir süre yazmayı bitirmesini beklemek için gecikme uygula
                    handler.postDelayed(() -> {
                        // Eğer hala aynı uzunluktaysa ve isBarcodeProcessing false ise
                        if (edtTrackId.getText().toString().length() == 1) {
                            isBarcodeProcessing = true; // Barkod işleniyor olarak ayarla
                            onViewClicked(); // Barkod işlendiğinde onViewClicked çağır
                        }
                    }, 6000); // 200ms gecikme, ihtiyaca göre ayarlayın
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
        edtTrackId.addTextChangedListener(barcodeTextWatcher);
    }
    @Override
    protected void setUp() {

        if (getIntent() != null) {
            islemTipi = getIntent().getStringExtra("islemTipi");
            arkodu = getIntent().getStringExtra("arkodu");
            noBarcodeId = getIntent().getStringExtra("noBarcodeId");
        }

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mPresenter.onViewPrepared();
    }

    @OnEditorAction(R.id.edtTrackId)
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
            onViewClicked();
            return true;
        }
        return false;
    }

    @OnClick(R.id.btnSearch)
    public void onViewClicked() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        onFragmentDetached(CameraFragment.TAG);//TODO yeni eklendi 04.03.2020

        String barcode = edtTrackId.getText().toString();

        if (islemTipi.equals(AppConstants.YUKLEMEINDIRMEHARAKET) || islemTipi.equals(AppConstants.NOBARCODE)) {
            if (islemTipi.equals(AppConstants.YUKLEMEINDIRMEHARAKET)) {
                Fragment fragment = fragmentManager.findFragmentByTag(CargoMovementFragment.TAG);
                if (fragment instanceof CargoMovementMvpView) {
                    ((CargoMovementMvpView) fragment).searchMovements(edtTrackId.getText().toString(), false);
                }
            } else {
                Fragment fragment = fragmentManager.findFragmentByTag(CargoMovementFragment.TAG);
                if (fragment instanceof CargoMovementMvpView) {
                    ((CargoMovementMvpView) fragment).searchMovements(edtTrackId.getText().toString(), false);
                }
            }

        } else if (islemTipi.equals(AppConstants.TAZMIN)) {
            Fragment fragment = fragmentManager.findFragmentByTag(CompensationFragment.TAG);
            if (fragment instanceof CompensationMvpView) {
                ((CompensationMvpView) fragment).sendBarcode(edtTrackId.getText().toString(), false);
            }
        } else {

            if (edtTrackId.getText().toString().length() == 34 || edtTrackId.getText().toString().length() == 11 || edtTrackId.getText().toString().length() == 7 || edtTrackId.getText().toString().length() == 17) {
                onBarcodeScanned(edtTrackId.getText().toString(),false);
            }
            else {
//                Intent intent = new Intent(DeliveryActivity.this, DeliverCargoActivity.class);
//                intent.putExtra("TrackId", edtTrackId.getText().toString());
//                intent.putExtra("islemTipi", AppConstants.TOPLUTESLIMAT);
//
//                startActivity(intent);
                Toast.makeText(DeliveryActivity.this, "HATA: Barkod Hatalı", Toast.LENGTH_SHORT).show();
                vibrate2("Barkod Hatalı");
            }
        }
        resetBarcodeField();
    }
    private void resetBarcodeField() {
        edtTrackId.removeTextChangedListener(barcodeTextWatcher); // TextWatcher geçici olarak çıkarılır
        edtTrackId.setText(""); // Barkod alanını temizle
        handler.postDelayed(() -> {
            edtTrackId.addTextChangedListener(barcodeTextWatcher); // TextWatcher tekrar eklenir
            edtTrackId.requestFocus(); // Odak tekrar EditText'te olsun
            isBarcodeProcessing = false; // Barkod işlem durumu sıfırlanır
        }, 100); // Gecikme süresi Zebra cihazınızın hızına göre ayarlanabilir
    }
    public void onBarcodeScanned(String barcode, boolean isBarcode) {

        // Barkod daha önce okutulmuş mu diye kontrol ediyoruz
        if (scannedBarcodes.contains(barcode)) {
            // Barkod zaten okutulduysa kullanıcıya uyarı göster
//            SpannableString spannableMessage = new SpannableString(barcode + " Bu Barkod Daha Önce Okutuldu !");
//            spannableMessage.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, barcode.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//            new AlertDialog.Builder(this)
//                    .setTitle("Mükerrer Barkod")
//                    .setMessage(spannableMessage)
//                    .setPositiveButton("Tamam", (dialog, which) -> dialog.dismiss())
//                    .show();
            vibrate2("Daha Önce Okutuldu");
        } else {
            // Barkod okutulmadıysa, HashSet'e ekle ve işlemleri devam ettir
            scannedBarcodes.add(barcode);

            Fragment fragment = fragmentManager.findFragmentByTag(MultiDeliveryFragment.TAG);
            if (fragment instanceof MultiDeliveryMvpView) {
                ((MultiDeliveryMvpView) fragment).sendBarcode(edtTrackId.getText().toString(),scannedBarcodes.size(), isBarcode);
            }
        }
    }

    public String getIslemTipi() {
        return islemTipi;
    }

    @Override
    public void scanResultData(String codeContent) {

        onFragmentDetached(CameraFragment.TAG);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (islemTipi.equals(AppConstants.YUKLEMEINDIRMEHARAKET) || islemTipi.equals(AppConstants.NOBARCODE)) {
            if (islemTipi.equals(AppConstants.YUKLEMEINDIRMEHARAKET)) {
                Fragment fragment = fragmentManager.findFragmentByTag(CargoMovementFragment.TAG);
                if (fragment instanceof CargoMovementMvpView) {
                    ((CargoMovementMvpView) fragment).searchMovements(codeContent, true);
                }
            } else {
                Fragment fragment = fragmentManager.findFragmentByTag(CargoMovementFragment.TAG);
                if (fragment instanceof CargoMovementMvpView) {
                    ((CargoMovementMvpView) fragment).searchMovements(codeContent, true);
                }
            }
        } else if (islemTipi.equals(AppConstants.TAZMIN)) {
            Fragment fragment = fragmentManager.findFragmentByTag(CompensationFragment.TAG);
            if (fragment instanceof CompensationMvpView) {
                ((CompensationMvpView) fragment).sendBarcode(codeContent, true);
            }
        } else if (islemTipi.equals(AppConstants.MUSTERITESLIMAT)) {
            Fragment fragment = fragmentManager.findFragmentByTag(MultiDeliveryFragment.TAG);
            if (fragment instanceof MultiDeliveryMvpView) {
                ((MultiDeliveryMvpView) fragment).deliverMultipleCustomer(codeContent, arkodu);
            }
        } else {
            if (codeContent.length() == 34 || codeContent.length() == 11 || codeContent.length() == 7 || codeContent.length() == 17) {
                onBarcodeScanned(codeContent,true);
                edtTrackId.setText("");
                edtTrackId.requestFocus();
            } else {
//                Intent intent = new Intent(DeliveryActivity.this, DeliverCargoActivity.class);
//                intent.putExtra("TrackId", codeContent);
//                intent.putExtra("islemTipi", AppConstants.TOPLUTESLIMAT);
//
//                startActivity(intent);
                edtTrackId.setText("");
                edtTrackId.requestFocus();
                Toast.makeText(DeliveryActivity.this, "HATA: Barkod Hatalı", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void showCameraFragment() {

        Bundle fragmentArgs = new Bundle();
        if (islemTipi.equals(AppConstants.MUSTERITESLIMAT)) {
            fragmentArgs.putString("prompt", "BARKODU OKUTUNUZ");
        } else {
            fragmentArgs.putString("prompt", "BARKODU OKUTUNUZ");
        }
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.barcode_frame, CameraFragment.newInstance(fragmentArgs), CameraFragment.TAG)
                .commit();
//
    }

    @Override
    public void showBluetoothFragment() {
//        edtTrackId.requestFocus();
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.barcode_frame, BluetoothFragment.newInstance(), BluetoothFragment.TAG)
//                .commit();

    }

    @Override
    public void resultData(String codeContent) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (islemTipi.equals(AppConstants.YUKLEMEINDIRMEHARAKET)) {
            Fragment fragment = fragmentManager.findFragmentByTag(CargoMovementFragment.TAG);
            if (fragment instanceof CargoMovementMvpView) {
                ((CargoMovementMvpView) fragment).searchMovements(codeContent, true);
            }
        } else if (islemTipi.equals(AppConstants.TAZMIN)) {
            Fragment fragment = fragmentManager.findFragmentByTag(CompensationFragment.TAG);
            if (fragment instanceof CompensationMvpView) {
                ((CompensationMvpView) fragment).sendBarcode(codeContent, true);
            }
        } else {
            if (codeContent.length() == 34 || codeContent.length() == 11 || codeContent.length() == 7 || codeContent.length() == 17) {
                onBarcodeScanned(codeContent,true);
                edtTrackId.setText("");
                edtTrackId.requestFocus();
            } else {
//                Intent intent = new Intent(DeliveryActivity.this, DeliverCargoActivity.class);
//                intent.putExtra("TrackId", codeContent);
//                intent.putExtra("islemTipi", AppConstants.TOPLUTESLIMAT);
//
//                startActivity(intent);
                edtTrackId.setText("");
                Toast.makeText(DeliveryActivity.this, "HATA: Barkod Hatalı", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .remove(fragment)
                    .commitNow();
        }
    }

    @Override
    public void onBackPressed() {
//        onFragmentDetached(BluetoothFragment.TAG);
        onFragmentDetached(CameraFragment.TAG);
//            getSupportFragmentManager().popBackStackImmediate();
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
    public void updateToolbarTitle() {
        if (islemTipi.equals(AppConstants.YUKLEMEINDIRMEHARAKET)) {
            toolbar.setTitle("Atf Yükleme / İndirme Hareketleri");
        } else if (islemTipi.equals(AppConstants.MUSTERITESLIMAT)) {
            toolbar.setTitle("Müşteri Teslimat");
        } else {
            toolbar.setTitle("ATF/KTF Barkodu Okut veya Atf No ile Sorgula");
        }

    }

    @Override
    public void decideProcessFragment() {
        if (islemTipi.equals(AppConstants.YUKLEMEINDIRMEHARAKET)) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_body, CargoMovementFragment.newInstance(), CargoMovementFragment.TAG)
                    .commit();
        } else if (islemTipi.equals(AppConstants.TAZMIN)) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_body, CompensationFragment.newInstance(), CompensationFragment.TAG)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_body, MultiDeliveryFragment.newInstance(), MultiDeliveryFragment.TAG)
                    .commit();
        }
    }
    @Override
    public void showTokenExpired(){
        BaseActivity.showTokenExpired(DeliveryActivity.this,"Oturum süresi doldu. Tekrar giriş yapınız","UYARI");
    }
}
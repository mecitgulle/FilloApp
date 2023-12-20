package com.bt.arasholding.filloapp.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
//import android.support.design.widget.NavigationView;
import com.bt.arasholding.filloapp.ui.delivercargo.DeliverCargoActivity;
import com.bt.arasholding.filloapp.ui.delivermultiplecustomerwaybill.DeliverMultipleCustomerActivity;
import com.bt.arasholding.filloapp.ui.lazer.LazerActivity;
import com.bt.arasholding.filloapp.ui.nobarcode.NoBarcodeActivity;
import com.bt.arasholding.filloapp.ui.route.ExpeditionRouteActivity;
import com.bt.arasholding.filloapp.ui.shipment.lazer.ShipmentLazerActivity;
import com.google.android.material.navigation.NavigationView;

import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bt.arasholding.filloapp.BuildConfig;
import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.ui.about.AboutFragment;
import com.bt.arasholding.filloapp.ui.barcodeprinter.BarcodePrinterActivity;
import com.bt.arasholding.filloapp.ui.base.BaseActivity;
import com.bt.arasholding.filloapp.ui.cargobarcode.CargoBarcodeActivity;
import com.bt.arasholding.filloapp.ui.delivery.DeliveryActivity;
import com.bt.arasholding.filloapp.ui.login.LoginActivity;
import com.bt.arasholding.filloapp.ui.settings.SettingsActivity;
import com.bt.arasholding.filloapp.ui.shipment.ShipmentActivity;
import com.bt.arasholding.filloapp.ui.shippingoutput.ShippingOutputActivity;
import com.bt.arasholding.filloapp.ui.textbarcode.TextBarcodeActivity;
import com.bt.arasholding.filloapp.utils.AppConstants;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.rey.material.widget.CheckBox;
import com.simplymadeapps.quickperiodicjobscheduler.QuickPeriodicJobScheduler;
//import com.simplymadeapps.quickperiodicjobscheduler.QuickPeriodicJobScheduler;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, HomeMvpView {

    private static final String TAG = "HomeActivity";
    private static int mJobId = 0;
    @Inject
    HomeMvpPresenter<HomeMvpView> mPresenter;
    @BindView(R.id.card_yukleme)
    CardView card_yukleme;//0
    @BindView(R.id.card_indirme)
    CardView card_indirme;//1
    @BindView(R.id.card_atf_teslimat)
    CardView card_atf_teslimat;//4
    @BindView(R.id.card_toplu_teslimat)
    CardView card_toplu_teslimat;//5
    @BindView(R.id.card_shipping_out)
    CardView card_shipping_out;//6
    @BindView(R.id.card_cargo_movement)
    CardView card_cargo_movement;//7
    @BindView(R.id.card_dagitim)
    CardView card_dagitim;//2
    @BindView(R.id.card_barcode_printer)
    CardView card_barcode_printer;//8
    @BindView(R.id.card_hat_yukleme)
    CardView card_hat_yukleme;//3
    @BindView(R.id.card_atf_devir)
    CardView card_atf_devir;//9
    @BindView(R.id.card_toplu_devir)
    CardView card_toplu_devir;//10
    @BindView(R.id.card_Route)
    CardView card_Route;//11
    @BindView(R.id.card_Tazmin)
    CardView card_Tazmin;//12
    @BindView(R.id.card_musteri_teslimat)
    CardView card_musteri_teslimat;//13
    @BindView(R.id.card_barkodsuz_kargo)
    CardView card_barkodsuz_kargo;//14

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    @BindView(R.id.grid)
    GridLayout gridLayout;

    LocationManager konumYoneticisi;
    LocationListener locationListener;

    private String enlem, boylam;
    private static final int PERMISSION_REQUEST_CODE = 101;
    String koordinat;
    TextView txtUserName, txtVersionName;
    public static final int JOB_ID = 101;

    public JobScheduler jobScheduler;
    public JobInfo jobInfo;

    boolean isRead = true;
    boolean isForceUpdate = true;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(HomeActivity.this);

        int oneSecond = 1000;
        QuickPeriodicJobScheduler jobScheduler = new QuickPeriodicJobScheduler(this);
        jobScheduler.start(1, 60 * 1000L); // Run job with jobId=1 every 60 seconds 5*60*1000L(5dk)

        getCurrentLocation();
        setUp();
    }
    @Override
    protected void setUp() {

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);

        setupNavMenu();
        mPresenter.onNavMenuCreated();
//        mPresenter.showCacheAlertDialog();
        mPresenter.initJobs();
        String Id = mPresenter.setGroupId();
        if(Id != null)
        {
            changeCardViewVisibility(Id);
        }
    }

    private void changeCardViewVisibility(String Id) {
        // 4 = ŞUBE KULLANICISI --- 15 = TERMİNAL KULLANICISI --- 6 = AKTARMA KULLANICISI --- 29 = KURYE
        if(Id.equals("29"))
        {
            gridLayout.removeViewAt(5);
            gridLayout.removeViewAt(5);
            gridLayout.removeViewAt(5);
            gridLayout.removeViewAt(5);
            gridLayout.removeViewAt(9);
            gridLayout.removeViewAt(9);
        }
        else{
            gridLayout.removeViewAt(5);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (drawerLayout != null)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
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
                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                    .remove(fragment)
                    .commitNow();
            unlockDrawer();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(AboutFragment.TAG);
        if (fragment == null) {
            super.onBackPressed();
        } else {
            onFragmentDetached(AboutFragment.TAG);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            showLogoutDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        drawerLayout.closeDrawer(GravityCompat.START);

        if (id == R.id.nav_yukleme) {
            mPresenter.onDrawerYuklemeClick();
            return true;
        } else if (id == R.id.nav_indirme) {
            mPresenter.onDrawerIndirmeClick();
            return true;
        } else if (id == R.id.nav_dagitim) {
            mPresenter.onDrawerDagitimClick();
            return true;
        } else if (id == R.id.nav_atf_teslimat) {
            mPresenter.onDrawerAtfTeslimatClick();
            return true;
        } else if (id == R.id.nav_toplu_teslimat) {
            mPresenter.onDrawerTopluTeslimatClick();
            return true;
        } else if (id == R.id.nav_arac_cikis) {
            mPresenter.onDrawerAracCikisClick();
            return true;
        } else if (id == R.id.nav_tools) {
            mPresenter.onDrawerSettingsClick();
            return true;
        } else if (id == R.id.nav_session) {
            showLogoutDialog();
            return true;
        } else if (id == R.id.nav_cargo_movement) {
            mPresenter.onDrawerCargoMovementClick();
            return true;
        } else if (id == R.id.nav_hat_yukleme) {
            mPresenter.onDrawerHatYukleme();
            return true;
        } else if (id == R.id.nav_devir) {
            mPresenter.onDrawerDevir();
            return true;
        } else if (id == R.id.nav_toplu_devir) {
            mPresenter.onDrawerTopluDevir();
            return true;
        } else if (id == R.id.nav_barkod_yazdir) {
            mPresenter.onDrawerBarcodePrinterClick();
            return true;
        } else if (id == R.id.nav_about) {
            mPresenter.onDrawerOptionAboutClick();
            return true;
        }

        return false;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void closeNavigationDrawer() {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(Gravity.START);
        }
    }

    @OnClick({
            R.id.card_yukleme,
            R.id.card_indirme,
            R.id.card_atf_teslimat,
            R.id.card_toplu_teslimat,
            R.id.card_shipping_out,
            R.id.card_cargo_movement,
            R.id.card_dagitim,
            R.id.card_barcode_printer,
            R.id.card_hat_yukleme,
            R.id.card_atf_devir,
            R.id.card_toplu_devir,
            R.id.card_Route,
            R.id.card_Tazmin,
            R.id.card_musteri_teslimat,
            R.id.card_barkodsuz_kargo
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.card_yukleme:
                mPresenter.onDrawerYuklemeClick();
                break;
            case R.id.card_indirme:
                mPresenter.onDrawerIndirmeClick();
                break;
            case R.id.card_dagitim:
                mPresenter.onDrawerDagitimClick();
                break;
            case R.id.card_atf_teslimat:
                mPresenter.onDrawerAtfTeslimatClick();
                break;
            case R.id.card_toplu_teslimat:
                mPresenter.onDrawerTopluTeslimatClick();
                break;
            case R.id.card_shipping_out:
                mPresenter.onDrawerAracCikisClick();
                break;
            case R.id.card_cargo_movement:
                mPresenter.onDrawerCargoMovementClick();
                break;
            case R.id.card_barcode_printer:
                mPresenter.onDrawerBarcodePrinterClick();
                break;
            case R.id.card_hat_yukleme:
                mPresenter.onDrawerHatYukleme();
                break;
            case R.id.card_atf_devir:
                mPresenter.onDrawerDevir();
                break;
            case R.id.card_toplu_devir:
                mPresenter.onDrawerTopluDevir();
                break;
            case R.id.card_Route:
                mPresenter.onDrawerRota();
                break;
            case R.id.card_Tazmin:
                mPresenter.onDrawerTazmin();
                break;
            case R.id.card_musteri_teslimat:
                mPresenter.onDrawerMusteriTeslimat();
                break;
            case R.id.card_barkodsuz_kargo:
                mPresenter.onDrawerBarkodsuzKargo();
                break;
        }
    }

    void setupNavMenu() {
        View headerLayout = mNavigationView.getHeaderView(0);
        txtUserName = headerLayout.findViewById(R.id.txtUserName);
        txtVersionName = headerLayout.findViewById(R.id.txtVersionName);
    }

    @Override
    public void showDecideDialog(String islemTipi) {

        LayoutInflater inflater = this.getLayoutInflater();
        View decide_layout = inflater.inflate(R.layout.dialog_select_device, null);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
        alertDialog.setTitle("Barkod Çekim Yöntemi Seçiniz!");
        alertDialog.setView(decide_layout);
        alertDialog.setIcon(R.drawable.ic_perm_device_information_black_24dp);

        MaterialSpinner spinner = decide_layout.findViewById(R.id.statusSpanner);

        spinner.setItems("Kamera", "Bluetooth","Lazer");

        CheckBox chkRemember = decide_layout.findViewById(R.id.chkRemember);

        chkRemember.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mPresenter.setRememberChecked(isChecked);
        });

        alertDialog.setView(decide_layout);

        alertDialog.setPositiveButton("Evet", (dialog, which) -> {
            dialog.dismiss();

            if (spinner.getSelectedIndex() == 1) {
                mPresenter.setSelectedCamera(false);
                mPresenter.setSelectedBluetooth(true);
                mPresenter.setSelectedLazer(false);
            } else if (spinner.getSelectedIndex() == 2) {
                mPresenter.setSelectedCamera(false);
                mPresenter.setSelectedBluetooth(false);
                mPresenter.setSelectedLazer(true);
            }
            else{
                mPresenter.setSelectedCamera(true);
                mPresenter.setSelectedBluetooth(false);
                mPresenter.setSelectedLazer(false);
            }

            if (islemTipi.equals(AppConstants.DAGITIM)) {
                openShipmentActivity(AppConstants.DAGITIM);
            } else if (islemTipi.equals(AppConstants.HAT_YUKLEME)) {

                openShipmentActivity(AppConstants.HAT_YUKLEME);
            } else if (islemTipi.equals(AppConstants.ATFTESLIMAT)) {
//                mPresenter.onDrawerAtfTeslimatClick();
                openDeliveryActivity(AppConstants.ATFTESLIMAT);
            } else if (islemTipi.equals(AppConstants.YUKLEMEINDIRMEHARAKET)) {

                openDeliveryActivity(AppConstants.YUKLEMEINDIRMEHARAKET);
            } else if (islemTipi.equals(AppConstants.TAZMIN)) {

                openDeliveryActivity(AppConstants.TAZMIN);

            } else if (islemTipi.equals(AppConstants.MUSTERITESLIMAT)) {

                openDeliverMultipleCustomerActivity(AppConstants.MUSTERITESLIMAT);

            }else {
                if (spinner.getSelectedIndex() == 1) {
                    mPresenter.openTextBarcodeActivity(islemTipi);
                }
                else if(spinner.getSelectedIndex() == 2){
                    mPresenter.openLazerActivity(islemTipi);
                }
                else {
                    mPresenter.openCargoBarcodeActivity(islemTipi);
                }
            }
        });

        alertDialog.setNegativeButton("Hayır", (dialog, which) -> {
            dialog.dismiss();
        });

        alertDialog.show();
    }

    @Override
    public void updateAppVersion() {
        String version = getString(R.string.version) + " " + BuildConfig.VERSION_NAME;
        txtVersionName.setText(version);
    }

    @Override
    public void updateUserName(String userName) {
        txtUserName.setText(userName);
    }

    @Override
    public void openCargoBarcodeActivity(String islemTipi) {
        Intent intent = CargoBarcodeActivity.getStartIntent(this);
        intent.putExtra("islemTipi", islemTipi);
        startActivity(intent);
    }

    @Override
    public void openShippingOutputActivity() {
        startActivity(ShippingOutputActivity.getStartIntent(this));
    }
    @Override
    public void openNoBarcodeActivity() {
        startActivity(NoBarcodeActivity.getStartIntent(this));
    }

    @Override
    public void openSettingsActivity() {
        startActivity(SettingsActivity.getStartIntent(this));
    }

    @Override
    public void openLoginActivity() {
        Intent loginIntent = LoginActivity.getStartIntent(this);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
    }

    @Override
    public void openTextBarcodeActivity(String islemTipi) {
        Intent intent = TextBarcodeActivity.getStartIntent(this);
        intent.putExtra("islemTipi", islemTipi);

        startActivity(intent);
    }

    @Override
    public void openLazerActivity(String islemTipi) {
        Intent intent = LazerActivity.getStartIntent(this);
        intent.putExtra("islemTipi", islemTipi);

        startActivity(intent);
    }

    @Override
    public void openShipmentLazerActivity(String islemTipi) {
        Intent intent = ShipmentLazerActivity.getStartIntent(this);
        intent.putExtra("islemTipi", islemTipi);

        startActivity(intent);
    }

    @Override
    public void openDeliveryActivity(String islemTipi) {
        Intent intent = DeliveryActivity.getStartIntent(this);
        intent.putExtra("islemTipi", islemTipi);
        startActivity(intent);
    }

    @Override
    public void openDeliverMultipleCustomerActivity(String islemTipi) {
        Intent intent = DeliverMultipleCustomerActivity.getStartIntent(this);
        intent.putExtra("islemTipi", islemTipi);
        startActivity(intent);
    }
    @Override
    public void openShipmentActivity(String islemTipi) {
        Intent intent = ShipmentActivity.getStartIntent(this);
        intent.putExtra("islemTipi", islemTipi);
        startActivity(intent);
        //startActivity(ShipmentActivity.getStartIntent(this));
    }

    @Override
    public void openBarcodePrinterActivity() {
        Intent intent = BarcodePrinterActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void openExpeditionRouteActivity() {
        Intent intent = ExpeditionRouteActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void showAboutFragment() {
        lockDrawer();
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .add(R.id.cl_root_view, AboutFragment.newInstance(), AboutFragment.TAG)
                .commit();
    }

    @Override
    public void lockDrawer() {
        if (drawerLayout != null)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void unlockDrawer() {
        if (drawerLayout != null)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public void showLogoutDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
        alertDialog.setTitle("Çıkmak istediğinize emin misiniz?");
        alertDialog.setIcon(R.drawable.ic_exit_to_app_black_24dp);

        alertDialog.setPositiveButton("Evet", (dialog, which) -> {
            dialog.dismiss();
            mPresenter.onDrawerLogoutClick();
        });

        alertDialog.setNegativeButton("Hayır", (dialog, which) -> {
            dialog.dismiss();
        });


        alertDialog.show();
    }

    @Override
    public void showCacheDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
        alertDialog.setTitle("Bilgilendirme");
        alertDialog.setMessage("Uygulamayı güncellediğinizde herhangi bir sıkıntı yaşamamak için sadece bir kereliğine telefon ayarlarından; Ayarlar-> Uygulamalarım-> Fillo Lojistik-> Depolama bölümünden uygulamanın önbelleğini ve depolama alanını temizleyiniz.");
        alertDialog.setPositiveButton("Anladım,Bir daha gösterme", (dialog, which) -> {
            dialog.dismiss();
            mPresenter.setRead(isRead);
        });

        alertDialog.setNegativeButton("Kapat", (dialog, which) -> {
            dialog.dismiss();
        });


        alertDialog.show();
    }

    public void showUpdateDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);

        alertDialogBuilder.setTitle(HomeActivity.this.getString(R.string.app_name));
        alertDialogBuilder.setMessage("Uygulamanın yeni sürümü mevcut. Güncellensin mi ?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Şimdi Güncelle", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                HomeActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                dialog.cancel();
            }
        });
        alertDialogBuilder.setNegativeButton("Çık", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isForceUpdate) {
                    finish();
                }
                dialog.dismiss();
            }
        });
        alertDialogBuilder.show();
    }

    protected void getCurrentLocation() {
        konumYoneticisi = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean kontrol = konumYoneticisi.isProviderEnabled("gps");
        if (!kontrol) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
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
                    Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if(konumYoneticisi.getAllProviders().contains("network")) {
            konumYoneticisi.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, locationListener);
        }
        else {
            konumYoneticisi.requestLocationUpdates("gps", 5000, 0, locationListener);
        }

    }
}
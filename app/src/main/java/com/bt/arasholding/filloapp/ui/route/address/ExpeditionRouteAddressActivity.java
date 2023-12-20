package com.bt.arasholding.filloapp.ui.route.address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.network.model.ExpeditionRoute;
import com.bt.arasholding.filloapp.ui.base.BaseActivity;
import com.bt.arasholding.filloapp.ui.route.ExpeditionRouteAdapter;
import com.bt.arasholding.filloapp.ui.route.ExpeditionRouteMvpPresenter;
import com.bt.arasholding.filloapp.ui.route.ExpeditionRouteMvpView;
import com.bt.arasholding.filloapp.ui.route.maps.MapsActivity;
import com.bt.arasholding.filloapp.utils.AppLogger;
import com.manojbhadane.QButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExpeditionRouteAddressActivity extends BaseActivity implements ExpeditionRouteAddressMvpView {
    @Inject
    ExpeditionRouteAddressMvpPresenter<ExpeditionRouteAddressMvpView> mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycleradresList)
    RecyclerView recycleradresList;
    ExpeditionRouteAddressAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    String sefer;

    @BindView(R.id.btnRota)
    QButton btnRota;

    List<String> adresler = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expedition_route_address);

        Intent i = getIntent();
        sefer = i.getStringExtra("tempSeferID");

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);
//        String ll = mPresenter.getLatitude();
//        AppLogger.e("ss"+mPresenter.getLatitude());
//        AppLogger.e(mPresenter.getLongitude());
        setUp();
    }

    @Override
    protected void setUp() {
        setSupportActionBar(toolbar);
        mLayoutManager = new LinearLayoutManager(this);
        recycleradresList.setLayoutManager(mLayoutManager);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mPresenter.onViewPrepared(sefer);
    }

    @Override
    public void updateBarcodeList(List<String> adresList) {
        adresler =adresList;
        adapter = new ExpeditionRouteAddressAdapter(adresList, this);

        adapter.notifyDataSetChanged();
        recycleradresList.setAdapter(adapter);
    }

    @OnClick(R.id.btnRota)
    public void onbtnRotaClicked() {

        if (adresler.size() == 0) {
            Toast.makeText(getApplicationContext(), "Rota oluşturulacak adres bulunamadı", Toast.LENGTH_LONG).show();
        } else if (adresler.size() < 25) {
            Intent intent = new Intent(ExpeditionRouteAddressActivity.this, MapsActivity.class);
            intent.putStringArrayListExtra("adresler", (ArrayList<String>) adresler);
            intent.putExtra("lat", mPresenter.getLatitude());
            intent.putExtra("lon", mPresenter.getLongitude());
//            intent.putExtra("enlem", enlem);
//            intent.putExtra("boylam", boylam);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Adres sayısı çok fazla rota oluşturulamıyor (25' den az olmalı)", Toast.LENGTH_LONG).show();
        }
    }
}
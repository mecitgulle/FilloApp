package com.bt.arasholding.filloapp.ui.route;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.model.Barcode;
import com.bt.arasholding.filloapp.data.network.model.ExpeditionRoute;
import com.bt.arasholding.filloapp.ui.base.BaseActivity;
import com.bt.arasholding.filloapp.ui.textbarcode.BarcodeAdapter;
import com.bt.arasholding.filloapp.ui.textbarcode.TextBarcodeMvpPresenter;
import com.bt.arasholding.filloapp.ui.textbarcode.TextBarcodeMvpView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ExpeditionRouteActivity extends BaseActivity implements ExpeditionRouteMvpView {

    @Inject
    ExpeditionRouteMvpPresenter<ExpeditionRouteMvpView> mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerseferList)
    RecyclerView recyclerseferList;
    ExpeditionRouteAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ExpeditionRouteActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expedition_route);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();

    }

    @Override
    protected void setUp() {
        setSupportActionBar(toolbar);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerseferList.setLayoutManager(mLayoutManager);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mPresenter.onViewPrepared();
    }

    @Override
    public void updateBarcodeList(List<ExpeditionRoute> barcodeList) {
        adapter = new ExpeditionRouteAdapter(barcodeList, this);

        adapter.notifyDataSetChanged();
        recyclerseferList.setAdapter(adapter);
    }
}
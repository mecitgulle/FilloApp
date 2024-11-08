package com.bt.arasholding.filloapp.ui.delivery.cargomovement;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.network.model.CargoMovementDetail;
import com.bt.arasholding.filloapp.data.network.model.CargoMovementDetailSummarys;
import com.bt.arasholding.filloapp.di.component.ActivityComponent;
import com.bt.arasholding.filloapp.ui.base.BaseActivity;
import com.bt.arasholding.filloapp.ui.base.BaseFragment;
import com.bt.arasholding.filloapp.ui.delivermultiplecustomerwaybill.DeliverMultipleCustomerActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CargoMovementFragment extends BaseFragment implements CargoMovementMvpView {

    public static final String TAG = "CargoMovementFragment";

    @Inject
    CargoMovementMvpPresenter<CargoMovementMvpView> mPresenter;

    @BindView(R.id.listMovements)
    RecyclerView listMovements;
    @BindView(R.id.listSummarys)
    RecyclerView listSummarys;
    @BindView(R.id.txtAtfNo)
    TextView txtAtfNo;
    @BindView(R.id.txtAtfTarihi)
    TextView txtAtfTarihi;
    @BindView(R.id.txtToplamParca)
    TextView txtToplamParca;
    @BindView(R.id.txtVarisSubeAdi)
    TextView txtVarisSubeAdi;

    TextView txtSubeYukleme;
    TextView txtSubeIndirme;
    TextView txtSubeDagitim;
    TextView txtIndirme2;
    ImageButton btndetails;

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.LayoutManager mLayoutManager2;

    public static CargoMovementFragment newInstance() {
        CargoMovementFragment fragment = new CargoMovementFragment();
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

        View view = inflater.inflate(R.layout.fragment_cargo_movement, container, false);

//        txtSubeYukleme = (TextView) view.findViewById(R.id.txtSubeYukleme);
//        txtSubeIndirme = (TextView) view.findViewById(R.id.txtSubeIndirme);
//        txtSubeDagitim = (TextView) view.findViewById(R.id.txtSubeDagitim);
//        btndetails = (ImageButton) view.findViewById(R.id.btndetails);
//        txtIndirme2 = (TextView) view.findViewById(R.id.txtIndirme2);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);

            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            setUp(view);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLayoutManager = new LinearLayoutManager(getBaseActivity());
        mLayoutManager2 = new LinearLayoutManager(getBaseActivity(),LinearLayoutManager.HORIZONTAL, false);
        listMovements.setLayoutManager(mLayoutManager);
        listSummarys.setLayoutManager(mLayoutManager2);
    }

    @Override
    protected void setUp(View view) {

    }

    @Override
    public void updateAtfNo(String atfNo) {
        txtAtfNo.setText(atfNo);
    }

    @Override
    public void updateAtfTarihi(String atfTarihi) {
        txtAtfTarihi.setText(atfTarihi);
    }

    @Override
    public void updateToplamParca(String toplamParca) {
        txtToplamParca.setText(toplamParca);
    }

    @Override
    public void updateVarisSubeAdi(String varisSubeAdi) {
        txtVarisSubeAdi.setText(varisSubeAdi);
    }

    @Override
    public void searchMovements(String trackId, boolean isBarcode) {
        mPresenter.onViewPrepared(trackId, isBarcode);
    }
    @Override
    public void noBarcodeMovements(String trackId,String noBarcodeId) {
        mPresenter.onViewPreparedNoBarcode(trackId, noBarcodeId);
    }
    @Override
    public void updateHareketlerList(List<CargoMovementDetail> list) {
        CargoMovementAdapter adapter = new CargoMovementAdapter(list, getBaseActivity());

        adapter.notifyDataSetChanged();
        listMovements.setAdapter(adapter);
    }

    @Override
    public void updateSummarysList(List<CargoMovementDetailSummarys> list) {
        CargoMovementDetailAdapter adapter = new CargoMovementDetailAdapter(list, getBaseActivity());

        adapter.notifyDataSetChanged();
        listSummarys.setAdapter(adapter);
    }

    @Override
    public void updateSubeYukleme(int subeYukleme) {
        txtSubeYukleme.setText(String.valueOf(subeYukleme));
    }

    @Override
    public void updateSubeIndirme(int subeIndirme) {
        txtSubeIndirme.setText(String.valueOf(subeIndirme));
    }

    @Override
    public void updateSubeDagitim(int subeDagitim) {
        txtSubeDagitim.setText(String.valueOf(subeDagitim));
    }
    @Override
    public void updateSubeIndirme2(int indirme) {

        txtIndirme2.setText(String.valueOf(indirme));
    }
    @Override
    public void showTokenExpired(){
        BaseActivity.showTokenExpired(getActivity(),"Oturum süresi doldu. Tekrar giriş yapınız","UYARI");
    }
}

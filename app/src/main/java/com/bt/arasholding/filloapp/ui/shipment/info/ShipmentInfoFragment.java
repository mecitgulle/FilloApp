package com.bt.arasholding.filloapp.ui.shipment.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.network.model.Sefer;
import com.bt.arasholding.filloapp.di.component.ActivityComponent;
import com.bt.arasholding.filloapp.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShipmentInfoFragment extends BaseFragment {

    public static final String TAG = "ShipmentInfoFragment";

    @BindView(R.id.txtShipment)
    TextView txtShipment;

    public interface ShipmentFragmentCallback {
        void newShipmentClicked();
    }

    public static ShipmentInfoFragment newInstance(Sefer sefer) {
        ShipmentInfoFragment fragment = new ShipmentInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable("sefer", sefer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipment, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
        }

        setUp(view);

        return view;
    }

    @Override
    protected void setUp(View view) {
        if (getArguments() != null) {

            Sefer sefer = (Sefer) getArguments().getSerializable("sefer");

            if (sefer != null) {
                txtShipment.setText(String.format("%s - %s", sefer.getPLAKA(), sefer.getSEFERID()));
            }
        }
    }

    @OnClick(R.id.btnNewShipment)
    public void onViewClicked() {

        if (getActivity() != null)
            ((ShipmentFragmentCallback) getActivity()).newShipmentClicked();
    }
}

package com.bt.arasholding.filloapp.ui.shipment.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.di.component.ActivityComponent;
import com.bt.arasholding.filloapp.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchShipmentFragment extends BaseFragment {

    public static final String TAG = "SearchShipmentFragment";

    @BindView(R.id.edt_sefer_no)
    EditText edtSeferNo;

    public interface CallbackSearchShipment {
        void searchShipment(String shipmentNumber);
    }

    public static SearchShipmentFragment newInstance() {
        SearchShipmentFragment fragment = new SearchShipmentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_shipment, container, false);

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

    }

    @OnClick(R.id.btn_search_shipping)
    public void onViewClicked() {
        if (getActivity() != null)
            ((CallbackSearchShipment) getActivity()).searchShipment(edtSeferNo.getText().toString());
    }
}
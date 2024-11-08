package com.bt.arasholding.filloapp.ui.shipment.search;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.di.component.ActivityComponent;
import com.bt.arasholding.filloapp.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

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
        edtSeferNo.requestFocus();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setUp(view);

        return view;
    }

    @OnEditorAction(R.id.edt_sefer_no)
    public boolean onEditorAction(TextView view, int keyCode, KeyEvent keyEvent) {

        String textSeferBarcode = view.getText().toString();

        if (textSeferBarcode.isEmpty())
            return false;

        showMessage(textSeferBarcode);

        if (keyEvent != null) {
            // Barcode finger scanner

            if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                edtSeferNo.setText("");
                ((CallbackSearchShipment) getActivity()).searchShipment(textSeferBarcode);
            }
        } else {
            // Keyboard
            edtSeferNo.setText("");
            hideKeyboard();
            ((CallbackSearchShipment) getActivity()).searchShipment(textSeferBarcode);
        }

        return false;
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
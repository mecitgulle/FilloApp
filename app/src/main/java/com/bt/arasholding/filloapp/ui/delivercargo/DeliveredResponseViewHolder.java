package com.bt.arasholding.filloapp.ui.delivercargo;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.ui.base.BaseViewHolder;

public class DeliveredResponseViewHolder extends BaseViewHolder {

    TextView txtAtfNo;
    TextView txtMesaj;

    public DeliveredResponseViewHolder(@NonNull View itemView) {
        super(itemView);

        txtAtfNo = itemView.findViewById(R.id.txtatfNo);
        txtMesaj = itemView.findViewById(R.id.txtMessage);
    }

    @Override
    protected void clear() {

    }
}
package com.bt.arasholding.filloapp.ui.route.address;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.ui.base.BaseViewHolder;

public class ExpeditionRouteAddressViewHolder extends BaseViewHolder {
    TextView txtAdres;
    TextView imgCount;

    public ExpeditionRouteAddressViewHolder(@NonNull View itemView) {
        super(itemView);
        txtAdres = itemView.findViewById(R.id.txtAddress);
        imgCount = itemView.findViewById(R.id.imgCount);
    }

    @Override
    protected void clear() {

    }
}

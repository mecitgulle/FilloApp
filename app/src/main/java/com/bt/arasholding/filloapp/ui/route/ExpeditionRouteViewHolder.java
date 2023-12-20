package com.bt.arasholding.filloapp.ui.route;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.ui.base.BaseViewHolder;

public class ExpeditionRouteViewHolder extends BaseViewHolder {

    TextView txtSeferId;
    TextView txtSeferPlaka;
    TextView txtTarih;
    TextView txtSubeAdi;
    TextView txtParca;
    TextView txtNoktaSayisi;

    public ExpeditionRouteViewHolder(@NonNull View itemView) {
        super(itemView);

        txtSeferId = itemView.findViewById(R.id.txtSeferId);
        txtSeferPlaka = itemView.findViewById(R.id.txtSeferPlaka);
        txtTarih = itemView.findViewById(R.id.txtTarih);
        txtSubeAdi = itemView.findViewById(R.id.txtSubeAdi);
        txtParca = itemView.findViewById(R.id.txtRotaParca);
        txtNoktaSayisi = itemView.findViewById(R.id.txtNoktaSayisi);
    }
    @Override
    protected void clear() {

    }
}

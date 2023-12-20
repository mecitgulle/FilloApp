package com.bt.arasholding.filloapp.ui.delivery.cargomovement;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.ui.base.BaseViewHolder;

public class CargoMovementViewHolder extends BaseViewHolder {
    TextView txtIslem;
    TextView txtAnlikSubeAdi;
    TextView txtHareketTarihi;
    TextView txtPlaka;
    TextView txtParca;

    public CargoMovementViewHolder(@NonNull View itemView) {
        super(itemView);

        txtIslem = itemView.findViewById(R.id.txtIslem);
        txtAnlikSubeAdi = itemView.findViewById(R.id.txtAnlikSubeAdi);
        txtHareketTarihi = itemView.findViewById(R.id.txtHareketTarihi);
        txtPlaka = itemView.findViewById(R.id.txtPlaka);
        txtParca = itemView.findViewById(R.id.txtParca);
    }

    @Override
    protected void clear() {

    }
}

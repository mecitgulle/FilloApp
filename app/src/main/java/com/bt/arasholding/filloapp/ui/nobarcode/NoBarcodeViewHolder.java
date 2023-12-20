package com.bt.arasholding.filloapp.ui.nobarcode;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.ui.base.BaseViewHolder;

public class NoBarcodeViewHolder extends BaseViewHolder {
    TextView txtUnvan;
    TextView txtSubeAdi;
    TextView txtUrunKodu;
    TextView txtIcerik;
    TextView txtKoliAdet;
    TextView txtPlaka;
    TextView txtTarih;
    TextView txtAciklama;
    TextView txtOlusturan;

    public NoBarcodeViewHolder(@NonNull View itemView) {
        super(itemView);

        txtUnvan = itemView.findViewById(R.id.txtUnvan);
        txtSubeAdi = itemView.findViewById(R.id.txtSubeAdi);
        txtUrunKodu = itemView.findViewById(R.id.txtUrunKodu);
        txtIcerik = itemView.findViewById(R.id.txtIcerik);
        txtKoliAdet = itemView.findViewById(R.id.txtKoliAdet);
        txtPlaka = itemView.findViewById(R.id.txtPlaka);
        txtTarih = itemView.findViewById(R.id.txtTarih);
        txtAciklama = itemView.findViewById(R.id.txtAciklama);
        txtOlusturan = itemView.findViewById(R.id.txtOlusturan);
    }
    @Override
    protected void clear() {

    }
}

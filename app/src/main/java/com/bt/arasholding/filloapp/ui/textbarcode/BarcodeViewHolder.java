package com.bt.arasholding.filloapp.ui.textbarcode;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.ui.base.BaseViewHolder;

public class BarcodeViewHolder extends BaseViewHolder {

    TextView txt_atf_no;
    TextView txtBarkod;
    TextView txtResult;
    ImageView imgStatus;

    public BarcodeViewHolder(@NonNull View itemView) {
        super(itemView);

        txt_atf_no = itemView.findViewById(R.id.txt_atf_no);
        txtResult =  itemView.findViewById(R.id.txtIslemSonucu);
        txtBarkod =  itemView.findViewById(R.id.txtBarkod);
        imgStatus =  itemView.findViewById(R.id.imgStatus);
    }

    @Override
    protected void clear() {

    }
}

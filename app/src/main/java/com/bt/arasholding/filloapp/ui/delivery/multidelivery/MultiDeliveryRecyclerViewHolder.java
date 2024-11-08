package com.bt.arasholding.filloapp.ui.delivery.multidelivery;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.ui.base.BaseViewHolder;

public class MultiDeliveryRecyclerViewHolder extends BaseViewHolder {

    TextView txt_atf_no;
    TextView txt_parca_no;
    TextView txtAlici;
    TextView txtSonuc;
    RadioButton rdAlindi;
    RadioButton rdAlinmadi;
    LinearLayout layout_evrak_donus;
    LinearLayout btnDeleteDelivery;
    LinearLayout btnlayout;
    Button btnPhoto;
    ImageButton btnDeleteAtf;
    Button btnComment;
    TextView txt_dagitim_varmi;

    public MultiDeliveryRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

        txt_atf_no = itemView.findViewById(R.id.txt_atf_no);
        txt_parca_no = itemView.findViewById(R.id.txt_parca_no);
        txtAlici = itemView.findViewById(R.id.txtAliciAdi);
        txtSonuc = itemView.findViewById(R.id.txtSonuc);
        rdAlindi = itemView.findViewById(R.id.rd_alindi);
        rdAlinmadi = itemView.findViewById(R.id.rd_alinmadi);
        layout_evrak_donus = itemView.findViewById(R.id.layout_evrak_donus);
        btnDeleteDelivery = itemView.findViewById(R.id.btnDeleteDelivery);
        btnlayout = itemView.findViewById(R.id.btnlayout);
        btnPhoto = itemView.findViewById(R.id.btn_photo);
        txt_dagitim_varmi = itemView.findViewById(R.id.txt_dagitim_varmi);
        btnDeleteAtf = itemView.findViewById(R.id.btnDeleteAtf);
//        btnComment = itemView.findViewById(R.id.btn_comment);
    }

    @Override
    protected void clear() {

    }
}

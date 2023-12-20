package com.bt.arasholding.filloapp.ui.delivery.multidelivery;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.model.Barcode;

import java.util.ArrayList;
import java.util.List;

public class MultiDeliveryRecyclerListAdapter extends RecyclerView.Adapter<MultiDeliveryRecyclerViewHolder> {

    private List<Barcode> listBarcode = new ArrayList<>();
    private Context context;
    private MultiDeliveryMvpView fragmentView ;
    private ImageButton btnDeleteAtf;

    public MultiDeliveryRecyclerListAdapter(List<Barcode> barcodeList, Context context,MultiDeliveryMvpView fragmentView) {
        this.listBarcode = barcodeList;
        this.context = context;
        this.fragmentView = fragmentView;
    }

    @NonNull
    @Override
    public MultiDeliveryRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.delivery_list_item, viewGroup, false);

        return new MultiDeliveryRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiDeliveryRecyclerViewHolder barcodeViewHolder, int i) {
        barcodeViewHolder.txtAlici.setText(listBarcode.get(i).getAlici_adi());
        barcodeViewHolder.txt_atf_no.setText(listBarcode.get(i).getAtf_no());
        barcodeViewHolder.txtSonuc.setText(listBarcode.get(i).getIslemSonucu());

        barcodeViewHolder.rdAlindi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fragmentView.setAlindiMi(listBarcode.get(i).getId(),isChecked ? "ALINDI" : "ALINMADI");
            }
        });

            barcodeViewHolder.rdAlinmadi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fragmentView.setAlindiMi(listBarcode.get(i).getId(),!isChecked ? "ALINDI" : "ALINMADI");
            }
        });
        barcodeViewHolder.btnDeleteAtf.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                fragmentView.deleteAtf(listBarcode.get(i).getAtfId(), listBarcode.get(i).getIslemTipi() );
            }
        });

        if (listBarcode.get(i).getEvrakDonusluMu() != null){
            if (listBarcode.get(i).getEvrakDonusluMu().equals("EVET")){
                barcodeViewHolder.layout_evrak_donus.setVisibility(View.VISIBLE);
                barcodeViewHolder.btnlayout.setVisibility(View.VISIBLE);
            }else{
                barcodeViewHolder.layout_evrak_donus.setVisibility(View.INVISIBLE);
            }
        }
        if(listBarcode.get(i).getIslemSonucu() != null)
        {
            barcodeViewHolder.txtSonuc.setVisibility(View.VISIBLE);
        }
        else {
            barcodeViewHolder.txtSonuc.setVisibility(View.GONE);
        }
        barcodeViewHolder.btnPhoto.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                fragmentView.dispatchTakePictureIntent(listBarcode.get(i).getAtfId());
            }
        });
//        barcodeViewHolder.btnComment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fragmentView.showHasarAciklamaDialog(listBarcode.get(i).getId());
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listBarcode.size();
    }
}

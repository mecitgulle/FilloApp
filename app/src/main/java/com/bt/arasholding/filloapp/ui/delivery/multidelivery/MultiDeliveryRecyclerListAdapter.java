package com.bt.arasholding.filloapp.ui.delivery.multidelivery;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
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
    private int okutmaTipi;
    private MultiDeliveryMvpView fragmentView ;
    private ImageButton btnDeleteAtf;

    public MultiDeliveryRecyclerListAdapter(List<Barcode> barcodeList, Context context,MultiDeliveryMvpView fragmentView,int okutmaTipi) {
        this.listBarcode = barcodeList;
        this.context = context;
        this.fragmentView = fragmentView;
        this.okutmaTipi = okutmaTipi;
    }

    @NonNull
    @Override
    public MultiDeliveryRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.delivery_list_item, viewGroup, false);

        return new MultiDeliveryRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiDeliveryRecyclerViewHolder barcodeViewHolder, int position) {
        barcodeViewHolder.txtAlici.setText(listBarcode.get(position).getAlici_adi());
        barcodeViewHolder.txt_atf_no.setText(listBarcode.get(position).getAtf_no());
        barcodeViewHolder.txtSonuc.setText(listBarcode.get(position).getIslemSonucu());
        if (okutmaTipi == 1)
        {
            if (listBarcode.get(position).getBarcode() != null){
                String barkod = listBarcode.get(position).getBarcode();
                String pieceNumberWithZeros = barkod.substring(27, 30);
                int pieceNumber = Integer.parseInt(pieceNumberWithZeros);
                String parcaNo = String.valueOf(pieceNumber);

                barcodeViewHolder.btnDeleteDelivery.setVisibility(View.GONE);
                barcodeViewHolder.txt_parca_no.setVisibility(View.VISIBLE);
                barcodeViewHolder.txt_parca_no.setText(Html.fromHtml("<b>Parça No:</font></b> " + String.valueOf(parcaNo)));
            }
            else{
                barcodeViewHolder.txt_parca_no.setVisibility(View.GONE);
            }
        }
        else if (okutmaTipi == 2)
        {
            barcodeViewHolder.btnDeleteDelivery.setVisibility(View.VISIBLE);
            if (listBarcode.get(position).getToplam_parca() != null) {
                barcodeViewHolder.txt_parca_no.setText(Html.fromHtml("<b>Toplam Parça:</font></b> " + String.valueOf(listBarcode.get(position).getToplam_parca())));
            }
            barcodeViewHolder.btnDeleteDelivery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // `deleteAtf` metodunu çağırın
                    fragmentView.deleteAtf(listBarcode.get(position).getAtfId(),okutmaTipi);
                }
            });
        }

        barcodeViewHolder.rdAlindi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fragmentView.setAlindiMi(listBarcode.get(position).getId(),isChecked ? "ALINDI" : "ALINMADI");
            }
        });

            barcodeViewHolder.rdAlinmadi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fragmentView.setAlindiMi(listBarcode.get(position).getId(),!isChecked ? "ALINDI" : "ALINMADI");
            }
        });

        if (listBarcode.get(position).getEvrakDonusluMu() != null){
            if (listBarcode.get(position).getEvrakDonusluMu().equals("EVET")){
                barcodeViewHolder.layout_evrak_donus.setVisibility(View.VISIBLE);
                barcodeViewHolder.btnlayout.setVisibility(View.VISIBLE);
            }else{
                barcodeViewHolder.layout_evrak_donus.setVisibility(View.GONE);
            }
        }
        if(listBarcode.get(position).getIslemSonucu() != null)
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
                fragmentView.dispatchTakePictureIntent(listBarcode.get(position).getAtfId());
            }
        });
        if (listBarcode.get(position).getDagitim_var_mi() == 0 )
        {
            barcodeViewHolder.txt_dagitim_varmi.setText("Dağıtım Okutması Yok!");
            barcodeViewHolder.txt_dagitim_varmi.setTextColor(ContextCompat.getColor(context, R.color.red_dark2));
        }
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

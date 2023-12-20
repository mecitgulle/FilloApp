package com.bt.arasholding.filloapp.ui.nobarcode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.network.model.NoBarcodeCargo;
import com.bt.arasholding.filloapp.ui.delivery.DeliveryActivity;
import com.bt.arasholding.filloapp.ui.route.ExpeditionRouteViewHolder;
import com.bt.arasholding.filloapp.ui.route.address.ExpeditionRouteAddressActivity;
import com.bt.arasholding.filloapp.utils.AppConstants;

import java.util.List;
public class NoBarcodeAdapter extends RecyclerView.Adapter<NoBarcodeViewHolder>{

    private List<NoBarcodeCargo> listResponse;
    private Context context;

    public NoBarcodeAdapter(List<NoBarcodeCargo> listResponse, Context context) {
        this.listResponse = listResponse;
        this.context = context;
    }

    @NonNull
    @Override
    public NoBarcodeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.no_barcode_item,viewGroup,false);

        return new NoBarcodeViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull NoBarcodeViewHolder holder, int i) {
        holder.txtUnvan.setText(Html.fromHtml("<font color='#9c27b0'><b> ÜNVAN: </b>" + String.valueOf(String.valueOf(listResponse.get(i).getUnvan())) + " </font>"));
        holder.txtSubeAdi.setText(Html.fromHtml("<b> ŞUBE ADI: </b> " + String.valueOf(listResponse.get(i).getSubeAdi())));
        holder.txtUrunKodu.setText(Html.fromHtml("<b> ÜRÜN KODU: </b> " + String.valueOf(listResponse.get(i).getUrunKodu())));
        holder.txtIcerik.setText(Html.fromHtml("<b> İÇERİK: </b> " + String.valueOf(listResponse.get(i).getIcerik())));
        holder.txtKoliAdet.setText(Html.fromHtml("<b> KOLİ ADETİ: </b> " + String.valueOf(listResponse.get(i).getKoliAdet())));
        holder.txtPlaka.setText(Html.fromHtml("<b> PLAKA: </b> " + String.valueOf(listResponse.get(i).getPlaka())));
        holder.txtTarih.setText(Html.fromHtml("<b> TARİH: </b> " + String.valueOf(listResponse.get(i).getTarih())));
        holder.txtAciklama.setText(Html.fromHtml("<b> AÇIKLAMA: </b> " + String.valueOf(listResponse.get(i).getAciklama())));
        holder.txtOlusturan.setText(Html.fromHtml("<b> OLUŞTURAN: </b> " + String.valueOf(listResponse.get(i).getCreUser())));

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String noBarcodeId = String.valueOf(listResponse.get(i).getId());
//
//                Intent i=new Intent(context, DeliveryActivity.class);
//                i.putExtra("islemTipi", AppConstants.NOBARCODE);
//                i.putExtra("noBarcodeId", noBarcodeId);
//                context.startActivity(i);
//                ((Activity)context).finish();
//            }
//        });
    }
    @Override
    public int getItemCount() {
        return listResponse.size();
    }

}

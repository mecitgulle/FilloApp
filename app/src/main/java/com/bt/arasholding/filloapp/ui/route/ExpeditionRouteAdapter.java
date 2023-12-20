package com.bt.arasholding.filloapp.ui.route;

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
import com.bt.arasholding.filloapp.data.network.model.ExpeditionRoute;
import com.bt.arasholding.filloapp.ui.route.address.ExpeditionRouteAddressActivity;

import java.util.List;

public class ExpeditionRouteAdapter extends RecyclerView.Adapter<ExpeditionRouteViewHolder>{
    private List<ExpeditionRoute> listResponse;
    private Context context;

    public ExpeditionRouteAdapter(List<ExpeditionRoute> listResponse, Context context) {
        this.listResponse = listResponse;
        this.context = context;
    }

    @NonNull
    @Override
    public ExpeditionRouteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.route_item,viewGroup,false);

        return new ExpeditionRouteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpeditionRouteViewHolder holder, int i) {
        holder.txtSeferId.setText(Html.fromHtml("<font color='#9c27b0'><b> SEFERID: </b>" + String.valueOf(String.valueOf(listResponse.get(i).getSeferId())) + " </font>"));
        holder.txtSeferPlaka.setText(Html.fromHtml("<b> PLAKA: </b> " + String.valueOf(listResponse.get(i).getPlaka())));
        holder.txtTarih.setText(Html.fromHtml("<b> TARİH: </b> " + String.valueOf(listResponse.get(i).getTarih())));
        holder.txtSubeAdi.setText(Html.fromHtml("<b> ŞUBE: </b> " + String.valueOf(listResponse.get(i).getSubeAdi())));
        holder.txtParca.setText(Html.fromHtml("<b> PARÇA: </b> " + String.valueOf(listResponse.get(i).getParca())));
        holder.txtNoktaSayisi.setText(Html.fromHtml("<b> UĞRAMA NOKTA SAYISI: </b> " + String.valueOf(listResponse.get(i).getNoktaSayisi())));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tempSeferID = String.valueOf(listResponse.get(i).getSeferId());

                Intent i=new Intent(context, ExpeditionRouteAddressActivity.class);
                i.putExtra("tempSeferID", tempSeferID);
                context.startActivity(i);
                ((Activity)context).finish();
            }
        });
    }
    @Override
    public int getItemCount() {
        return listResponse.size();
    }
}

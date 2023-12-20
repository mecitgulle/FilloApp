package com.bt.arasholding.filloapp.ui.route.address;

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

import java.util.List;

public class ExpeditionRouteAddressAdapter extends RecyclerView.Adapter<ExpeditionRouteAddressViewHolder> {
    private List<String> listResponse;
    private Context context;

    public ExpeditionRouteAddressAdapter(List<String> listResponse, Context context) {
        this.listResponse = listResponse;
        this.context = context;
    }

    @NonNull
    @Override
    public ExpeditionRouteAddressViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.route_address_item,viewGroup,false);

        return new ExpeditionRouteAddressViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpeditionRouteAddressViewHolder holder, int i) {
        String dd = listResponse.get(i);
        holder.imgCount.setText(String.valueOf(i+1));
        holder.txtAdres.setText(Html.fromHtml("<font color='#7238d3'><b> Uğrama Noktası: </b></font> " + String.valueOf(listResponse.get(i))));
    }

    @Override
    public int getItemCount() {
        return listResponse.size();
    }
}

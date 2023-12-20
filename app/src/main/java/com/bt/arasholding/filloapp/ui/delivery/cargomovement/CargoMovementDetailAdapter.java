package com.bt.arasholding.filloapp.ui.delivery.cargomovement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.network.model.CargoMovementDetailSummarys;

import java.util.ArrayList;
import java.util.List;

public class CargoMovementDetailAdapter extends RecyclerView.Adapter<CargoMovementDetailViewHolder>  {

    private List<CargoMovementDetailSummarys> listMovement = new ArrayList<>();
    private Context context;

    public CargoMovementDetailAdapter(List<CargoMovementDetailSummarys> listMovement,Context context){
        this.listMovement = listMovement;
        this.context = context;
    }

    @NonNull
    @Override
    public CargoMovementDetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.movement_item_detail, viewGroup, false);

        return new CargoMovementDetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CargoMovementDetailViewHolder viewHolder, int i) {
        viewHolder.txtBaslik.setText(listMovement.get(i).getIslem());
        viewHolder.txtMovement.setText(String.valueOf(listMovement.get(i).getAdet()));
        viewHolder.txtMovementBranch.setText(listMovement.get(i).getSube());
    }

    @Override
    public int getItemCount() {
        return listMovement.size();
    }
}

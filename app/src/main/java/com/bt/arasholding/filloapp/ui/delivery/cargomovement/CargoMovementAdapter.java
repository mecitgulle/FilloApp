package com.bt.arasholding.filloapp.ui.delivery.cargomovement;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.network.model.CargoMovementDetail;

import java.util.ArrayList;
import java.util.List;

public class CargoMovementAdapter extends RecyclerView.Adapter<CargoMovementViewHolder> {

    private List<CargoMovementDetail> listMovement = new ArrayList<>();
    private Context context;

    public CargoMovementAdapter(List<CargoMovementDetail> listMovement,Context context){
        this.listMovement = listMovement;
        this.context = context;
    }

    @NonNull
    @Override
    public CargoMovementViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {//view holderın başlatılması için method
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.movement_item, viewGroup, false);

        return new CargoMovementViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CargoMovementViewHolder viewHolder, int i) {//verileri göstermek için method

        viewHolder.txtIslem.setText(listMovement.get(i).getIslem());
        viewHolder.txtAnlikSubeAdi.setText(listMovement.get(i).getAnliksubeadi());
        viewHolder.txtHareketTarihi.setText(listMovement.get(i).getHarekettarihi());
        viewHolder.txtPlaka.setText(listMovement.get(i).getPlaka());
        viewHolder.txtParca.setText(listMovement.get(i).getKacinciparca() + ". Parça");
    }

    @Override
    public int getItemCount() {
        return listMovement.size();
    }
}

package com.bt.arasholding.filloapp.ui.delivercargo;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.network.model.DeliverMultipleCargoModel;
import java.util.List;

public class DeliveredResponseAdapter extends RecyclerView.Adapter<DeliveredResponseViewHolder> {

    private List<DeliverMultipleCargoModel> listResponse;
    private Context context;

    public DeliveredResponseAdapter(List<DeliverMultipleCargoModel> listResponse,Context context) {
        this.listResponse = listResponse;
        this.context = context;
    }

    @NonNull
    @Override
    public DeliveredResponseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.islem_sonuc_item,viewGroup,false);

        return new DeliveredResponseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveredResponseViewHolder deliveredResponseViewHolder, int i) {
        deliveredResponseViewHolder.txtAtfNo.setText(listResponse.get(i).getAtfNo());
        deliveredResponseViewHolder.txtMesaj.setText(listResponse.get(i).getMessage());
    }

    @Override
    public int getItemCount() {
        return listResponse.size();
    }
}

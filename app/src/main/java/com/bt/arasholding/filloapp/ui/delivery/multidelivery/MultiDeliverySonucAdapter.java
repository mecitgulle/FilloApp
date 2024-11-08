package com.bt.arasholding.filloapp.ui.delivery.multidelivery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.model.Barcode;

import java.util.List;

public class MultiDeliverySonucAdapter extends RecyclerView.Adapter<MultiDeliverySonucAdapter.ViewHolder> {

    private List<Barcode> deliveryResults;
    private OnItemClickListener onItemClickListener;

    // Constructor
    public MultiDeliverySonucAdapter(List<Barcode> deliveryResults) {
        this.deliveryResults = deliveryResults;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater ile item layout'unu bağlıyoruz
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teslimat_sonuc_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Modelden verileri alıp holder'a set ediyoruz
        Barcode result = deliveryResults.get(position);
        holder.txtAtfNo.setText(result.getAtf_no()); // Müşteri adı gibi
        holder.txtSonuc.setText(result.getIslemSonucu()); // Sonuç gibi verileri dolduruyoruz

        // İtem tıklama işlemi
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(result);
            }
        });
    }

    @Override
    public int getItemCount() {
        return deliveryResults.size();
    }

    // ViewHolder Sınıfı
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtAtfNo, txtSonuc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // XML'deki TextView'leri bağla
            txtAtfNo = itemView.findViewById(R.id.txt_atf_no);
            txtSonuc = itemView.findViewById(R.id.txt_sonuc);
        }
    }

    // Tıklama işlevi için interface
    public interface OnItemClickListener {
        void onItemClick(Barcode deliveryResult);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // Veriyi güncellemek için metot
    public void updateData(List<Barcode> newResults) {
        deliveryResults.clear();
        deliveryResults.addAll(newResults);
        notifyDataSetChanged();
    }
}

package com.bt.arasholding.filloapp.ui.textbarcode;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.model.Barcode;

import java.util.ArrayList;
import java.util.List;

public class BarcodeAdapter extends RecyclerView.Adapter<BarcodeViewHolder> {

    private List<Barcode> listBarcode = new ArrayList<>();
    private Context context;

    public BarcodeAdapter(List<Barcode> barcodeList, Context context) {
        this.listBarcode = barcodeList;
        this.context = context;
    }

    @NonNull
    @Override
    public BarcodeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.barcode_item, viewGroup, false);

        return new BarcodeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BarcodeViewHolder barcodeViewHolder, int i) {
        barcodeViewHolder.txtResult.setText(listBarcode.get(i).getAciklama());
        barcodeViewHolder.txtBarkod.setText(listBarcode.get(i).getBarcode());
        barcodeViewHolder.txt_atf_no.setText(listBarcode.get(i).getAtf_no());

        if (listBarcode.get(i).getIslemSonucu().equals("200")){
            barcodeViewHolder.imgStatus.setImageResource(R.drawable.ic_checked);
        }else{
            barcodeViewHolder.imgStatus.setImageResource(R.drawable.ic_error);
        }
    }

    @Override
    public int getItemCount() {
        return listBarcode.size();
    }
}

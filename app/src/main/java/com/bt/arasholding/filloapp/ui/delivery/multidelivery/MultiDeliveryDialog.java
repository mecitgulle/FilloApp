package com.bt.arasholding.filloapp.ui.delivery.multidelivery;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.model.Barcode;

import java.util.List;

public class MultiDeliveryDialog extends Dialog {

    private RecyclerView recyclerView;
    private ImageButton btnClose;
    private List<Barcode> barcodeList;
    private MultiDeliveryFragment fragment;
    private TextView dialog_title;
    private int tip;// Dialoga iletilecek veri

    public MultiDeliveryDialog(@NonNull Context context, List<Barcode> barcodeList, MultiDeliveryFragment fragment, int tip) {
        super(context);
        this.barcodeList = barcodeList;
        this.fragment = fragment;
        this.tip = tip;// Fragment referansını al
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_teslimat_sonuc); // R sınıfını burada doğru şekilde kullanıyorsunuz

        recyclerView = findViewById(R.id.dialog_recycler_view);
        btnClose = findViewById(R.id.btn_close_dialog);
        dialog_title = findViewById(R.id.dialog_title);

        if (tip == 1)
        {
            dialog_title.setText("Teslimat Sonuçları");
        }
        else{
            dialog_title.setText("Devir Sonuçları");
        }


        // RecyclerView'ı ayarlama
        MultiDeliverySonucAdapter adapter = new MultiDeliverySonucAdapter(barcodeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        btnClose.setOnClickListener(v -> {
            dismiss();
            if (fragment != null) {
                fragment.goBack(); // Fragment'in geri gitmesini sağlayacak metot
            }
        });
    }
}

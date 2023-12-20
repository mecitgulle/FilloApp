package com.bt.arasholding.filloapp.ui.delivercargo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.data.network.model.DeliverMultipleCargoModel;
import com.bt.arasholding.filloapp.ui.shippingoutput.ShippingOutputActivity;

import java.util.List;
public class DeliverCargoDetailActivity extends AppCompatActivity {
    List<DeliverMultipleCargoModel> response;
    TextView txtAlici;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coklu_teslim_islem_sonuc_layout);

        txtAlici= findViewById(R.id.txtAlici);

        response =  DeliverCargoActivity.getResponseList();

        Intent intent = getIntent();
        String alici = intent.getStringExtra("alici");

        RecyclerView recyclerView = findViewById(R.id.islemSonucList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        DeliveredResponseAdapter adapter = new DeliveredResponseAdapter(response, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        txtAlici.setText(alici);
    }
}
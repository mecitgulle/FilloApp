package com.bt.arasholding.filloapp.ui.delivery.cargomovement;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.ui.base.BaseViewHolder;

public class CargoMovementDetailViewHolder extends BaseViewHolder {
    TextView txtBaslik;
    TextView txtMovement;
    TextView txtMovementBranch;

    public CargoMovementDetailViewHolder(@NonNull View itemView) {
        super(itemView);

        txtBaslik = itemView.findViewById(R.id.txtBaslik);
        txtMovement = itemView.findViewById(R.id.txtMovement);
        txtMovementBranch = itemView.findViewById(R.id.txtMovementBranch);
    }

    @Override
    protected void clear() {

    }
}

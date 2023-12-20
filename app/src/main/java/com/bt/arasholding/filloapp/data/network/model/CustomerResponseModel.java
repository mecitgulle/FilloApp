package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerResponseModel {
    @Expose
    @SerializedName("ID")
    private String ID;
    @Expose
    @SerializedName("DESCTRIPTION")
    private String DESCTRIPTION;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDESCTRIPTION() {
        return DESCTRIPTION;
    }

    public void setDESCTRIPTION(String DESCTRIPTION) {
        this.DESCTRIPTION = DESCTRIPTION;
    }
}

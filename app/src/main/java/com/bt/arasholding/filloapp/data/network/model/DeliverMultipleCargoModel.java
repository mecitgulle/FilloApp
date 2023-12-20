package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliverMultipleCargoModel {

    @SerializedName("ATFNO")
    @Expose
    private String atfNo;
    @SerializedName("ALICI")
    @Expose
    private String aliciAdi;
    @SerializedName("Message")
    @Expose
    private String message;

    public String getAtfNo() {
        return atfNo;
    }

    public void setAtfNo(String atfNo) {
        this.atfNo = atfNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAliciAdi() {
        return aliciAdi;
    }

    public void setAliciAdi(String aliciAdi) {
        this.aliciAdi = aliciAdi;
    }
}

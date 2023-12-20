package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoBarcodeSaveAtfNoRequest {
    @SerializedName("TOKEN")
    @Expose
    private String token;

    @SerializedName("NOBARCODEID")
    @Expose
    private String noBarcodeId;

    @SerializedName("ATFNO")
    @Expose
    private String atfNo;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNoBarcodeId() {
        return noBarcodeId;
    }

    public void setNoBarcodeId(String noBarcodeId) {
        this.noBarcodeId = noBarcodeId;
    }

    public String getAtfNo() {
        return atfNo;
    }

    public void setAtfNo(String atfNo) {
        this.atfNo = atfNo;
    }
}

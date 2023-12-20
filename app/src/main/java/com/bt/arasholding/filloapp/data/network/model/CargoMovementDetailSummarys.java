package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CargoMovementDetailSummarys {

    @Expose
    @SerializedName("ISLEM")
    private String islem;
    @Expose
    @SerializedName("SUBE")
    private String sube;
    @Expose
    @SerializedName("ADET")
    private int adet;

    public String getIslem() {
        return islem;
    }

    public void setIslem(String islem) {
        this.islem = islem;
    }

    public int getAdet() {
        return adet;
    }

    public void setAdet(int adet) {
        this.adet = adet;
    }

    public String getSube() {
        return sube;
    }

    public void setSube(String sube) {
        this.sube = sube;
    }
}

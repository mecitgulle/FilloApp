package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliverMultipleInfo {

    @SerializedName("ATFADET")
    @Expose
    private int atfAdet;
    @SerializedName("ALICI")
    @Expose
    private String alici;
    @SerializedName("TOPLAMPARCA")
    @Expose
    private int toplamParca;

    public int getAtfAdet() {
        return atfAdet;
    }

    public void setAtfAdet(int atfAdet) {
        this.atfAdet = atfAdet;
    }

    public String getAlici() {
        return alici;
    }

    public void setAlici(String alici) {
        this.alici = alici;
    }

    public int getToplamParca() {
        return toplamParca;
    }

    public void setToplamParca(int toplamParca) {
        this.toplamParca = toplamParca;
    }
}

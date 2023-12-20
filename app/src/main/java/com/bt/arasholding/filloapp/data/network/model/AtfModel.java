package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AtfModel implements Serializable {

    @SerializedName("ATFID")
    @Expose
    private String atfId;

    @SerializedName("EVRAKDONUSALINDIMI")
    @Expose
    private String evrakDonusAlindiMi;

    @SerializedName("ATFNO")
    @Expose
    private String atfNo;

    @SerializedName("ALICI")
    @Expose
    private String aliciAdi;

    public String getAtfId() {
        return atfId;
    }

    public void setAtfId(String atfId) {
        this.atfId = atfId;
    }

    public String getEvrakDonusAlindiMi() {
        return evrakDonusAlindiMi;
    }

    public void setEvrakDonusAlindiMi(String evrakDonusAlindiMi) {
        this.evrakDonusAlindiMi = evrakDonusAlindiMi;
    }

    public String getAtfNo() {
        return atfNo;
    }

    public void setAtfNo(String atfNo) {
        this.atfNo = atfNo;
    }

    public String getAliciAdi() {
        return aliciAdi;
    }

    public void setAliciAdi(String aliciAdi) {
        this.aliciAdi = aliciAdi;
    }
}

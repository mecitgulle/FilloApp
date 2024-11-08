package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

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

    @SerializedName("TESLIMEDILENADET")
    @Expose
    private String teslimEdilenAdet;
    @SerializedName("TESLIMTIPI")
    @Expose
    private String teslimTipi;

    @SerializedName("OKUTULAN_BARKOD_LIST")
    @Expose
    private List<String> okutulan_barkod_list;

    public List<String> getOkutulan_barkod_list() {
        return okutulan_barkod_list;
    }

    public void setOkutulan_barkod_list(List<String> okutulan_barkod_list) {
        this.okutulan_barkod_list = okutulan_barkod_list;
    }

    public String getTeslimEdilenAdet() {
        return teslimEdilenAdet;
    }

    public void setTeslimEdilenAdet(String teslimEdilenAdet) {
        this.teslimEdilenAdet = teslimEdilenAdet;
    }

    public String getTeslimTipi() {
        return teslimTipi;
    }

    public void setTeslimTipi(String teslimTipi) {
        this.teslimTipi = teslimTipi;
    }

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

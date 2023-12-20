package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpeditionRoute {

    @SerializedName("SEFERID")
    @Expose
    private long SeferId;

    @SerializedName("PLAKA")
    @Expose
    private String plaka;

    @SerializedName("TARIH")
    @Expose
    private String tarih;

    @SerializedName("SUBEADI")
    @Expose
    private String subeAdi;

    @SerializedName("PARCA")
    @Expose
    private int parca;

    @SerializedName("NOKTASAYISI")
    @Expose
    private int noktaSayisi;

    public long getSeferId() {
        return SeferId;
    }

    public void setSeferId(long seferId) {
        SeferId = seferId;
    }

    public String getPlaka() {
        return plaka;
    }

    public void setPlaka(String plaka) {
        this.plaka = plaka;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getSubeAdi() {
        return subeAdi;
    }

    public void setSubeAdi(String subeAdi) {
        this.subeAdi = subeAdi;
    }

    public int getParca() {
        return parca;
    }

    public void setParca(int parca) {
        this.parca = parca;
    }

    public int getNoktaSayisi() {
        return noktaSayisi;
    }

    public void setNoktaSayisi(int noktaSayisi) {
        this.noktaSayisi = noktaSayisi;
    }
}

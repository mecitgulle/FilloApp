package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExpeditionRouteAddressResponse {

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

    @SerializedName("KURYEADI")
    @Expose
    private String kuryeAdi;

    @SerializedName("ADRESLER")
    @Expose
    private List<String> adresler;

    @SerializedName("StatusCode")
    @Expose
    private String statusCode;
    @SerializedName("Message")
    @Expose
    private String message;

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

    public String getKuryeAdi() {
        return kuryeAdi;
    }

    public void setKuryeAdi(String kuryeAdi) {
        this.kuryeAdi = kuryeAdi;
    }

    public List<String> getAdresler() {
        return adresler;
    }

    public void setAdresler(List<String> adresler) {
        this.adresler = adresler;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NoBarcodeSaveRequest{
    @SerializedName("TOKEN")
    @Expose
    private String token;

    @SerializedName("ARKODU")
    @Expose
    private String arkodu;

    @SerializedName("URUNKODU")
    @Expose
    private String urunKodu;

    @SerializedName("ICERIK")
    @Expose
    private String icerik;

    @SerializedName("ACIKLAMA")
    @Expose
    private String aciklama;

    @SerializedName("KOLIADET")
    @Expose
    private int koliAdet;

    @SerializedName("PLAKA")
    @Expose
    private String plaka;

    @SerializedName("GELISTARIHI")
    @Expose
    private String gelisTarihi;

    @SerializedName("BarkodsuzKargoImageList")
    @Expose
    private List<BarkodsuzKargoImageList> barkodsuzKargoImageLists;

    public List<BarkodsuzKargoImageList> getBarkodsuzKargoImageLists() {
        return barkodsuzKargoImageLists;
    }

    public void setBarkodsuzKargoImageLists(List<BarkodsuzKargoImageList> barkodsuzKargoImageLists) {
        this.barkodsuzKargoImageLists = barkodsuzKargoImageLists;
    }

    public String getArkodu() {
        return arkodu;
    }

    public void setArkodu(String arkodu) {
        this.arkodu = arkodu;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMusteriId() {
        return arkodu;
    }

    public void setMusteriId(String arkodu) {
        this.arkodu = arkodu;
    }

    public String getUrunKodu() {
        return urunKodu;
    }

    public void setUrunKodu(String urunKodu) {
        this.urunKodu = urunKodu;
    }

    public String getIcerik() {
        return icerik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public int getKoliAdet() {
        return koliAdet;
    }

    public void setKoliAdet(int koliAdet) {
        this.koliAdet = koliAdet;
    }

    public String getPlaka() {
        return plaka;
    }

    public void setPlaka(String plaka) {
        this.plaka = plaka;
    }

    public String getGelisTarihi() {
        return gelisTarihi;
    }

    public void setGelisTarihi(String gelisTarihi) {
        this.gelisTarihi = gelisTarihi;
    }
}

package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoBarcodeCargo {
    @SerializedName("ID")
    @Expose
    private long id;

    @SerializedName("UNVAN")
    @Expose
    private String unvan;

    @SerializedName("SUBEADI")
    @Expose
    private String subeAdi;

    @SerializedName("ARKODU")
    @Expose
    private String arKodu;

    @SerializedName("URUNKODU")
    @Expose
    private String urunKodu;

    @SerializedName("ICERIK")
    @Expose
    private String icerik;

    @SerializedName("KOLIADET")
    @Expose
    private int koliAdet;

    @SerializedName("PLAKA")
    @Expose
    private String plaka;

    @SerializedName("TARIH")
    @Expose
    private String tarih;

    @SerializedName("ACIKLAMA")
    @Expose
    private String aciklama;

    @SerializedName("CREUSER")
    @Expose
    private String creUser;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUnvan() {
        return unvan;
    }

    public void setUnvan(String unvan) {
        this.unvan = unvan;
    }

    public String getSubeAdi() {
        return subeAdi;
    }

    public void setSubeAdi(String subeAdi) {
        this.subeAdi = subeAdi;
    }

    public String getArKodu() {
        return arKodu;
    }

    public void setArKodu(String arKodu) {
        this.arKodu = arKodu;
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

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getCreUser() {
        return creUser;
    }

    public void setCreUser(String creUser) {
        this.creUser = creUser;
    }
}

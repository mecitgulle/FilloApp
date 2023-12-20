package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CargoMovementDetail {

    @Expose
    @SerializedName("VARISSUBEADI")
    private String varissubeadi;
    @Expose
    @SerializedName("DESI")
    private String desi;
    @Expose
    @SerializedName("TOPLAMPARCA")
    private String toplamparca;
    @Expose
    @SerializedName("KACINCIPARCA")
    private String kacinciparca;
    @Expose
    @SerializedName("PLAKA")
    private String plaka;
    @Expose
    @SerializedName("HAREKETTARIHI")
    private String harekettarihi;
    @Expose
    @SerializedName("ANLIKSUBEADI")
    private String anliksubeadi;
    @Expose
    @SerializedName("ISLEM")
    private String islem;
    @Expose
    @SerializedName("ATFTARIHI")
    private String atftarihi;
    @Expose
    @SerializedName("ATFNO")
    private String atfno;
    @Expose
    @SerializedName("ATFID")
    private String atfid;

    public String getVarissubeadi() {
        return varissubeadi;
    }

    public void setVarissubeadi(String varissubeadi) {
        this.varissubeadi = varissubeadi;
    }

    public String getDesi() {
        return desi;
    }

    public void setDesi(String desi) {
        this.desi = desi;
    }

    public String getToplamparca() {
        return toplamparca;
    }

    public void setToplamparca(String toplamparca) {
        this.toplamparca = toplamparca;
    }

    public String getKacinciparca() {
        return kacinciparca;
    }

    public void setKacinciparca(String kacinciparca) {
        this.kacinciparca = kacinciparca;
    }

    public String getPlaka() {
        return plaka;
    }

    public void setPlaka(String plaka) {
        this.plaka = plaka;
    }

    public String getHarekettarihi() {
        return harekettarihi;
    }

    public void setHarekettarihi(String harekettarihi) {
        this.harekettarihi = harekettarihi;
    }

    public String getAnliksubeadi() {
        return anliksubeadi;
    }

    public void setAnliksubeadi(String anliksubeadi) {
        this.anliksubeadi = anliksubeadi;
    }

    public String getIslem() {
        return islem;
    }

    public void setIslem(String islem) {
        this.islem = islem;
    }

    public String getAtftarihi() {
        return atftarihi;
    }

    public void setAtftarihi(String atftarihi) {
        this.atftarihi = atftarihi;
    }

    public String getAtfno() {
        return atfno;
    }

    public void setAtfno(String atfno) {
        this.atfno = atfno;
    }

    public String getAtfid() {
        return atfid;
    }

    public void setAtfid(String atfid) {
        this.atfid = atfid;
    }
}

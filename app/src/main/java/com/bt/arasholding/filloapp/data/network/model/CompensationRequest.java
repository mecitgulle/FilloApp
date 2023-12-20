package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CompensationRequest {
    @SerializedName("TOKEN")
    @Expose
    private String token;

    @SerializedName("ATFID")
    @Expose
    private String atfID;

    @SerializedName("HASARYERI")
    @Expose
    private String hasarYeri;

    @SerializedName("HASARTARIHI")
    @Expose
    private String hasarTarihi;

    @SerializedName("ACIKLAMA")
    @Expose
    private String aciklama;

    @SerializedName("PLAKA")
    @Expose
    private String plaka;

    @SerializedName("ARACTIPI ")
    @Expose
    private String aracTipi ;

    @SerializedName("TAZMINSEBEPID")
    @Expose
    private int tazminSebepID;

    @SerializedName("TazminTalepImageList")
    @Expose
    private List<TazminTalepImageList> tazminTalepImageList;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAtfID() {
        return atfID;
    }

    public void setAtfID(String atfID) {
        this.atfID = atfID;
    }

    public String getHasarYeri() {
        return hasarYeri;
    }

    public void setHasarYeri(String hasarYeri) {
        this.hasarYeri = hasarYeri;
    }

    public String getHasarTarihi() {
        return hasarTarihi;
    }

    public void setHasarTarihi(String hasarTarihi) {
        this.hasarTarihi = hasarTarihi;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public int getTazminSebepID() {
        return tazminSebepID;
    }

    public void setTazminSebepID(int tazminSebepID) {
        this.tazminSebepID = tazminSebepID;
    }

    public List<TazminTalepImageList> getTazminTalepImageList() {
        return tazminTalepImageList;
    }

    public void setTazminTalepImageList(List<TazminTalepImageList> tazminTalepImageList) {
        this.tazminTalepImageList = tazminTalepImageList;
    }

    public String getPlaka() {
        return plaka;
    }

    public void setPlaka(String plaka) {
        this.plaka = plaka;
    }

    public String getAracTipi() {
        return aracTipi;
    }

    public void setAracTipi(String aracTipi) {
        this.aracTipi = aracTipi;
    }
}

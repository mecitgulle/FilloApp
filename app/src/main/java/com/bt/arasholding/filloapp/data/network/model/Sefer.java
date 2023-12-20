package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sefer implements Serializable {

    @SerializedName("PLAKA")
    @Expose
    private String pLAKA;
    @SerializedName("SEFERID")
    @Expose
    private Integer sEFERID;
    @SerializedName("TTINO")
    @Expose
    private String tTINO;
    @SerializedName("VARISSUBE")
    @Expose
    private String vARISSUBE;
    @SerializedName("TARIH")
    @Expose
    private String tARIH;

    public String getPLAKA() {
        return pLAKA;
    }

    public void setPLAKA(String pLAKA) {
        this.pLAKA = pLAKA;
    }

    public Integer getSEFERID() {
        return sEFERID;
    }

    public void setSEFERID(Integer sEFERID) {
        this.sEFERID = sEFERID;
    }

    public String getTTINO() {
        return tTINO;
    }

    public void setTTINO(String tTINO) {
        this.tTINO = tTINO;
    }

    public String getVARISSUBE() {
        return vARISSUBE;
    }

    public void setVARISSUBE(String vARISSUBE) {
        this.vARISSUBE = vARISSUBE;
    }

    public String getTARIH() {
        return tARIH;
    }

    public void setTARIH(String tARIH) {
        this.tARIH = tARIH;
    }
}

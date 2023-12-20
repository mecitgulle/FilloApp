package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CargoDetailRequest {

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("atfId")
    @Expose
    private String atfId;

    @SerializedName("KFTBARKODU")
    @Expose
    private String ktfBarkodu;

    @SerializedName("ATFNO")
    @Expose
    private String atfNo;
    @SerializedName("ISTESLIMATKAPATMA")
    @Expose
    private boolean isTeslimatKapatma;

    public CargoDetailRequest() {
    }

    public CargoDetailRequest(String token) {
        this.token = token;
    }

    public CargoDetailRequest(String atfId, String token) {
        this.token = token;
        this.atfId = atfId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAtfId() {
        return atfId;
    }

    public void setAtfId(String atfId) {
        this.atfId = atfId;
    }

    public String getKtfBarkodu() {
        return ktfBarkodu;
    }

    public void setKtfBarkodu(String ktfBarkodu) {
        this.ktfBarkodu = ktfBarkodu;
    }

    public String getAtfNo() {
        return atfNo;
    }

    public void setAtfNo(String atfNo) {
        this.atfNo = atfNo;
    }

    public boolean isTeslimatKapatma() {
        return isTeslimatKapatma;
    }

    public void setTeslimatKapatma(boolean teslimatKapatma) {
        isTeslimatKapatma = teslimatKapatma;
    }
}
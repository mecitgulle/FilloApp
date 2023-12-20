package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrackingRequest {

    @SerializedName("seferId")
    @Expose
    private String seferId;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("subekodu")
    @Expose
    private String subeKodu;

    @SerializedName("SEFERBARKODU")
    @Expose
    private String seferBarkodu;

    public TrackingRequest(String token){
        this.token = token;
    }

    public TrackingRequest(String seferId, String token, String subeKodu) {
        this.seferId = seferId;
        this.token = token;
        this.subeKodu = subeKodu;
    }

    public TrackingRequest(String seferId, String token) {
        this.seferId = seferId;
        this.token = token;
    }

    public String getSubeKodu() {
        return subeKodu;
    }

    public void setSubeKodu(String subeKodu) {
        this.subeKodu = subeKodu;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSeferId() {
        return seferId;
    }

    public void setSeferId(String seferId) {
        this.seferId = seferId;
    }

    public String getSeferBarkodu() {
        return seferBarkodu;
    }

    public void setSeferBarkodu(String seferBarkodu) {
        this.seferBarkodu = seferBarkodu;
    }
}
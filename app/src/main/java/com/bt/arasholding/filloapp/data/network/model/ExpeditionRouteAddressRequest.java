package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpeditionRouteAddressRequest {
    @SerializedName("TOKEN")
    @Expose
    private String token;

    @SerializedName("SEFERID")
    @Expose
    private long SeferId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getSeferId() {
        return SeferId;
    }

    public void setSeferId(long seferId) {
        SeferId = seferId;
    }
}

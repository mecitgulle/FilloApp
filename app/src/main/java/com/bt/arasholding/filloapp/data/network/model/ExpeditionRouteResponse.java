package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExpeditionRouteResponse {
    @SerializedName("Seferler")
    @Expose
    private List<ExpeditionRoute> seferler;

    @SerializedName("StatusCode")
    @Expose
    private String statusCode;
    @SerializedName("Message")
    @Expose
    private String message;

    public List<ExpeditionRoute> getSeferler() {
        return seferler;
    }

    public void setSeferler(List<ExpeditionRoute> seferler) {
        this.seferler = seferler;
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

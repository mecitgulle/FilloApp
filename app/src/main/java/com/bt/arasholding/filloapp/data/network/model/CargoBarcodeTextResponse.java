package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CargoBarcodeTextResponse {

    @Expose
    @SerializedName("Message")
    private String message;
    @Expose
    @SerializedName("StatusCode")
    private String statusCode;
    @Expose
    @SerializedName("BarcodeText")
    private String barcodetext;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getBarcodetext() {
        return barcodetext;
    }

    public void setBarcodetext(String barcodetext) {
        this.barcodetext = barcodetext;
    }
}

package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NoBarcodeCargoResponse {

    @SerializedName("CargoNoBarcodeList")
    @Expose
    private List<NoBarcodeCargo> CargoNoBarcodeList;

    @SerializedName("StatusCode")
    @Expose
    private String statusCode;
    @SerializedName("Message")
    @Expose
    private String message;

    public List<NoBarcodeCargo> getCargoNoBarcodeList() {
        return CargoNoBarcodeList;
    }

    public void setCargoNoBarcodeList(List<NoBarcodeCargo> cargoNoBarcodeList) {
        CargoNoBarcodeList = cargoNoBarcodeList;
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

package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CargoMovementResponse {

    @SerializedName("StatusCode")
    @Expose
    private String statusCode;
    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("ATFNO")
    @Expose
    private String atfNo;

    @SerializedName("PARCANO")
    @Expose
    private String parcaNo;

    @SerializedName("YUKLENECEKPARCAADEDI")
    @Expose
    private String yuklenecekParcaAdeti;

    @SerializedName("HatYuklemeHataliRota")
    @Expose
    private Boolean HatYuklemeHataliRota;

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

    public String getAtfNo() {
        return atfNo;
    }

    public void setAtfNo(String atfNo) {
        this.atfNo = atfNo;
    }

    public String getParcaNo() {
        return parcaNo;
    }

    public void setParcaNo(String parcaNo) {
        this.parcaNo = parcaNo;
    }

    public Boolean getHatYuklemeHataliRota() {
        return HatYuklemeHataliRota;
    }

    public void setHatYuklemeHataliRota(Boolean hatYuklemeHataliRota) {
        HatYuklemeHataliRota = hatYuklemeHataliRota;
    }

    public String getYuklenecekParcaAdeti() {
        return yuklenecekParcaAdeti;
    }

    public void setYuklenecekParcaAdeti(String yuklenecekParcaAdeti) {
        this.yuklenecekParcaAdeti = yuklenecekParcaAdeti;
    }
}
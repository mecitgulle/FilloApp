package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CargoMovementRequest {

    @SerializedName("BARKOD")
    @Expose
    private String barcode;
    @SerializedName("TYPEID")
    @Expose
    private int type;
    @SerializedName("SEFERID")
    @Expose
    private String sefer;
    @SerializedName("HATYUKLEMEROTACEVAP")
    @Expose
    private String hatYuklemeRotaCevabı;
    @SerializedName("TOKEN")
    @Expose
    private String accessToken;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSefer() {
        return sefer;
    }

    public void setSefer(String sefer) {
        this.sefer = sefer;
    }

    public String getHatYuklemeRotaCevabı() {
        return hatYuklemeRotaCevabı;
    }

    public void setHatYuklemeRotaCevabı(String hatYuklemeRotaCevabı) {
        this.hatYuklemeRotaCevabı = hatYuklemeRotaCevabı;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
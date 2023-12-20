package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BarkodsuzKargoImageList {
    @SerializedName("RESIMTIPI")
    @Expose
    private String resimTipi;

    @SerializedName("DOSYAADI")
    @Expose
    private String dosyaAdi;

    @SerializedName("BASE64STRING")
    @Expose
    private String base64String;

    public String getResimTipi() {
        return resimTipi;
    }

    public void setResimTipi(String resimTipi) {
        this.resimTipi = resimTipi;
    }

    public String getDosyaAdi() {
        return dosyaAdi;
    }

    public void setDosyaAdi(String dosyaAdi) {
        this.dosyaAdi = dosyaAdi;
    }

    public String getBase64String() {
        return base64String;
    }

    public void setBase64String(String base64String) {
        this.base64String = base64String;
    }
}

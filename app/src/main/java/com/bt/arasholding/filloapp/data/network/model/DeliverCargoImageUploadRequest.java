package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliverCargoImageUploadRequest {
    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("ATFID")
    @Expose
    private String atfId;

    @SerializedName("DOSYAADI")
    @Expose
    private String dosyaAdi;

    @SerializedName("TESLIMATRESIMTIPI")
    @Expose
    private String teslimatResimTipi;

    @SerializedName("BASE64STRING")
    @Expose
    private String base64String;

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

    public String getDosyaAdi() {
        return dosyaAdi;
    }

    public void setDosyaAdi(String dosyaAdi) {
        this.dosyaAdi = dosyaAdi;
    }

    public String getTeslimatResimTipi() {
        return teslimatResimTipi;
    }

    public void setTeslimatResimTipi(String teslimatResimTipi) {
        this.teslimatResimTipi = teslimatResimTipi;
    }

    public String getBase64String() {
        return base64String;
    }

    public void setBase64String(String base64String) {
        this.base64String = base64String;
    }
}

package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CargoBarcodeTextRequest {

    @Expose
    @SerializedName("userid")
    private String userid;
    @Expose
    @SerializedName("barcodetype")
    private String barcodetype;
    @Expose
    @SerializedName("token")
    private String token;
    @Expose
    @SerializedName("kullanici")
    private String kullanici;
    @Expose
    @SerializedName("subekodu")
    private String subekodu;
    @Expose
    @SerializedName("mobilprintername")
    private String mobilprintername;
    @Expose
    @SerializedName("parcano")
    private int parcano;
    @Expose
    @SerializedName("barkod")
    private String barkod;

    public CargoBarcodeTextRequest(String token) {
        this.token = token;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getBarcodetype() {
        return barcodetype;
    }

    public void setBarcodetype(String barcodetype) {
        this.barcodetype = barcodetype;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getKullanici() {
        return kullanici;
    }

    public void setKullanici(String kullanici) {
        this.kullanici = kullanici;
    }

    public String getSubekodu() {
        return subekodu;
    }

    public void setSubekodu(String subekodu) {
        this.subekodu = subekodu;
    }

    public String getMobilprintername() {
        return mobilprintername;
    }

    public void setMobilprintername(String mobilprintername) {
        this.mobilprintername = mobilprintername;
    }

    public int getParcano() {
        return parcano;
    }

    public void setParcano(int parcano) {
        this.parcano = parcano;
    }

    public String getBarkod() {
        return barkod;
    }

    public void setBarkod(String barkod) {
        this.barkod = barkod;
    }
}

package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpeditionRouteRequest {

    @SerializedName("KURYEADI")
    @Expose
    private String kurye;

    @SerializedName("TOKEN")
    @Expose
    private String token;

    @SerializedName("CIKISSUBEKODU")
    @Expose
    private String cikisSubeKodu;

    public String getKurye() {
        return kurye;
    }

    public void setKurye(String kurye) {
        this.kurye = kurye;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCikisSubeKodu() {
        return cikisSubeKodu;
    }

    public void setCikisSubeKodu(String cikisSubeKodu) {
        this.cikisSubeKodu = cikisSubeKodu;
    }
}

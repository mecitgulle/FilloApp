package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CargoDetail {

    @SerializedName("ATFNO")
    @Expose
    private String atfNo;
    @SerializedName("TARIH")
    @Expose
    private String tarih;
    @SerializedName("GONDEREN")
    @Expose
    private String gonderen;
    @SerializedName("ALICI")
    @Expose
    private String alici;
    @SerializedName("CIKISSUBE")
    @Expose
    private String cikisSube;
    @SerializedName("VARISSUBE")
    @Expose
    private String varisSube;
    @SerializedName("TOPLAMPARCA")
    @Expose
    private String toplamParca;
    @SerializedName("DESI")
    @Expose
    private String desi;
    @SerializedName("ODEMESEKLI")
    @Expose
    private String odemeSekli;
    @SerializedName("IRSALIYENO")
    @Expose
    private String irsaliyeNo;
    @SerializedName("TESLIMTARIHI")
    @Expose
    private String teslimTarihi;
    @SerializedName("ONGORULENTESLIMTARIHI")
    @Expose
    private String ongorulenTeslimTarihi;
    @SerializedName("ATFID")
    @Expose
    private String atfId;

    @SerializedName("EVRAKDONUSLU")
    @Expose
    private String evrakDonuslu;

    @SerializedName("TAZMINNO")
    @Expose
    private String TazminNo ;


    public String getAtfNo() {
        return atfNo;
    }

    public void setAtfNo(String atfNo) {
        this.atfNo = atfNo;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getGonderen() {
        return gonderen;
    }

    public void setGonderen(String gonderen) {
        this.gonderen = gonderen;
    }

    public String getAlici() {
        return alici;
    }

    public void setAlici(String alici) {
        this.alici = alici;
    }

    public String getCikisSube() {
        return cikisSube;
    }

    public void setCikisSube(String cikisSube) {
        this.cikisSube = cikisSube;
    }

    public String getVarisSube() {
        return varisSube;
    }

    public void setVarisSube(String varisSube) {
        this.varisSube = varisSube;
    }

    public String getToplamParca() {
        return toplamParca;
    }

    public void setToplamParca(String toplamParca) {
        this.toplamParca = toplamParca;
    }

    public String getDesi() {
        return desi;
    }

    public void setDesi(String desi) {
        this.desi = desi;
    }

    public String getOdemeSekli() {
        return odemeSekli;
    }

    public void setOdemeSekli(String odemeSekli) {
        this.odemeSekli = odemeSekli;
    }

    public String getIrsaliyeNo() {
        return irsaliyeNo;
    }

    public void setIrsaliyeNo(String irsaliyeNo) {
        this.irsaliyeNo = irsaliyeNo;
    }

    public String getTeslimTarihi() {
        return teslimTarihi;
    }

    public void setTeslimTarihi(String teslimTarihi) {
        this.teslimTarihi = teslimTarihi;
    }

    public String getOngorulenTeslimTarihi() {
        return ongorulenTeslimTarihi;
    }

    public void setOngorulenTeslimTarihi(String ongorulenTeslimTarihi) {
        this.ongorulenTeslimTarihi = ongorulenTeslimTarihi;
    }

    public String getAtfId() {
        return atfId;
    }

    public void setAtfId(String atfId) {
        this.atfId = atfId;
    }

    public String getEvrakDonuslu() {
        return evrakDonuslu;
    }

    public void setEvrakDonuslu(String evrakDonuslu) {
        this.evrakDonuslu = evrakDonuslu;
    }

    public String getTazminNo() {
        return TazminNo;
    }

    public void setTazminNo(String tazminNo) {
        TazminNo = tazminNo;
    }
}

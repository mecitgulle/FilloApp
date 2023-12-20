package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DeliveredCargoRequest implements Serializable {

    @SerializedName("TOKEN")
    @Expose
    private String token;

    @SerializedName("ATFID")
    @Expose
    private String atfId;

    @SerializedName("TESLIMALANAD")
    @Expose
    private String teslimAlanAd;

    @SerializedName("TESLIMALANSOYAD")
    @Expose
    private String teslimAlanSoyad;

    @SerializedName("ACIKLAMA")
    @Expose
    private String aciklama;

    @SerializedName("KIMLIKNO")
    @Expose
    private String kimlikNo;
    @SerializedName("TESLIMTIPI")
    @Expose
    private String teslimTipi;
    @SerializedName("KTFBARKODU")
    @Expose
    private String ktfBarkodu;

    @SerializedName("EVRAKDONUSALINDIMI")
    @Expose
    private String evrakDonusAlindiMi;

    @SerializedName("MAPXY")
    @Expose
    private String mapXY;

    @SerializedName("TESLIMTARIHI")
    @Expose
    private String teslimTarihi;

    @SerializedName("TESLIMSAATI")
    @Expose
    private String teslimSaati;

    @SerializedName("DEVIRSEBEBI")
    @Expose
    private String devirSebebi;

    @SerializedName("ISLEMTIPI")
    @Expose
    private String islemTipiAtf;

    @SerializedName("ARKODU")
    @Expose
    private String arKodu;

    @SerializedName("CUSTOMERBARCODE")
    @Expose
    private String customerBarcode;


    @SerializedName("AtfModelList")
    @Expose
    private List<AtfModel> atfModelList;

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

    public String getTeslimAlanAd() {
        return teslimAlanAd;
    }

    public void setTeslimAlanAd(String teslimAlanAd) {
        this.teslimAlanAd = teslimAlanAd;
    }

    public String getTeslimAlanSoyad() {
        return teslimAlanSoyad;
    }

    public void setTeslimAlanSoyad(String teslimAlanSoyad) {
        this.teslimAlanSoyad = teslimAlanSoyad;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getKimlikNo() {
        return kimlikNo;
    }

    public void setKimlikNo(String kimlikNo) {
        this.kimlikNo = kimlikNo;
    }

    public String getTeslimTipi() {
        return teslimTipi;
    }

    public void setTeslimTipi(String teslimTipi) {
        this.teslimTipi = teslimTipi;
    }

    public String getKtfBarkodu() {
        return ktfBarkodu;
    }

    public void setKtfBarkodu(String ktfBarkodu) {
        this.ktfBarkodu = ktfBarkodu;
    }

    public String getEvrakDonusAlindiMi() {
        return evrakDonusAlindiMi;
    }

    public void setEvrakDonusAlindiMi(String evrakDonusAlindiMi) {
        this.evrakDonusAlindiMi = evrakDonusAlindiMi;
    }

    public String getMapXY() {
        return mapXY;
    }

    public void setMapXY(String mapXY) {
        this.mapXY = mapXY;
    }

    public String getTeslimTarihi() {
        return teslimTarihi;
    }

    public void setTeslimTarihi(String teslimTarihi) {
        this.teslimTarihi = teslimTarihi;
    }

    public String getTeslimSaati() {
        return teslimSaati;
    }

    public void setTeslimSaati(String teslimSaati) {
        this.teslimSaati = teslimSaati;
    }

    public String getDevirSebebi() {
        return devirSebebi;
    }

    public void setDevirSebebi(String devirSebebi) {
        this.devirSebebi = devirSebebi;
    }

    public String getIslemTipiAtf() {
        return islemTipiAtf;
    }

    public void setIslemTipiAtf(String islemTipiAtf) {
        this.islemTipiAtf = islemTipiAtf;
    }

    public void setAtfModelList(List<AtfModel> resultsList) {
        this.atfModelList = resultsList;
    }

    public List<AtfModel> getAtfModelList() {
        return atfModelList;
    }

    public String getArKodu() {
        return arKodu;
    }

    public void setArKodu(String arKodu) {
        this.arKodu = arKodu;
    }

    public String getCustomerBarcode() {
        return customerBarcode;
    }

    public void setCustomerBarcode(String customerBarcode) {
        this.customerBarcode = customerBarcode;
    }
//    @SerializedName("AtfModelList")
//    @Expose
//    private JsonElement results; // This has been Changed.
//
//    private List<AtfModel> resultsList = null; // This has been added newly and cannot be initialized by gson.
//
//    public JsonElement getResults() {
//        return results;
//    }
//
//    public List<AtfModel> getResultsList() {
//        List<AtfModel> resultList = new ArrayList<>(); // Initializing here just to cover the null pointer exception
//        Gson gson = new Gson();
//        if (getResults() instanceof JsonObject) {
//            resultList.add(gson.fromJson(getResults(), AtfModel.class));
//        } else if (getResults() instanceof JsonArray) {
//            Type founderListType = new TypeToken<ArrayList<AtfModel>>() {
//            }.getType();
//            resultList = gson.fromJson(getResults(), founderListType);
//        }
//        return resultList; // This is the actual list which i need and will work well with my code.
//    }

}

package com.bt.arasholding.filloapp.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Barcode")
public class Barcode {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String atfId;
    private String barcode;
    private int islemTipi;
    private String islemSonucu;
    private String aciklama;
    private String atf_no;
    private String alici_adi;
    private String evrakDonusluMu;
    private String atf_id;
    private String toplam_parca;
    private double dagitim_var_mi;

    public String getToplam_parca() {
        return toplam_parca;
    }

    public void setToplam_parca(String toplam_parca) {
        this.toplam_parca = toplam_parca;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAtfId() {
        return atfId;
    }

    public void setAtfId(String atfId) {
        this.atfId = atfId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getIslemTipi() {
        return islemTipi;
    }

    public void setIslemTipi(int islemTipi) {
        this.islemTipi = islemTipi;
    }

    public String getIslemSonucu() {
        return islemSonucu;
    }

    public void setIslemSonucu(String islemSonucu) {
        this.islemSonucu = islemSonucu;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getAtf_no() {
        return atf_no;
    }

    public void setAtf_no(String atf_no) {
        this.atf_no = atf_no;
    }

    public String getAlici_adi() {
        return alici_adi;
    }

    public void setAlici_adi(String alici_adi) {
        this.alici_adi = alici_adi;
    }

    public String getEvrakDonusluMu() {
        return evrakDonusluMu;
    }

    public void setEvrakDonusluMu(String evrakDonusluMu) {
        this.evrakDonusluMu = evrakDonusluMu;
    }

    public String getAtf_id() {
        return atf_id;
    }

    public void setAtf_id(String atf_id) {
        this.atf_id = atf_id;
    }

    public double getDagitim_var_mi() {
        return dagitim_var_mi;
    }

    public void setDagitim_var_mi(double dagitim_var_mi) {
        this.dagitim_var_mi = dagitim_var_mi;
    }
// Getter ve Setter metodları burada yer alacak
}

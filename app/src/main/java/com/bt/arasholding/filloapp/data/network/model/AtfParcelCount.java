package com.bt.arasholding.filloapp.data.network.model;

import java.util.List;

public class AtfParcelCount {
    private String atfNo;
    private int okutulmusParcaSayisi;
    private String teslimTip;
    private List<String> okutulanParcalar;
    public AtfParcelCount(String atfNo, int okutulmusParcaSayisi, String teslimTip, List<String> okutulanParcalar) {
        this.atfNo = atfNo;
        this.okutulmusParcaSayisi = okutulmusParcaSayisi;
        this.teslimTip = teslimTip;
        this.okutulanParcalar = okutulanParcalar;// Teslim tipi atandı
    }

    public String getAtfNo() {
        return atfNo;
    }

    public int getOkutulmusParcaSayisi() {
        return okutulmusParcaSayisi;
    }

    public String getTeslimTip() {
        return teslimTip;
    }

    public List<String> getOkutulanParcalar() {
        return okutulanParcalar;
    }

}

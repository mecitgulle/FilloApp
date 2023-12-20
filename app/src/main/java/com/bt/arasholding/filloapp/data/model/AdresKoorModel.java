package com.bt.arasholding.filloapp.data.model;

import com.google.android.gms.maps.model.LatLng;

public class AdresKoorModel {
    private LatLng point;
    private String adres;

    public LatLng getPoint() {
        return point;
    }

    public void setPoint(LatLng point) {
        this.point = point;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }
}

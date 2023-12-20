package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public abstract class DtoExample {

    @Expose
    @SerializedName("Message")
    private String message;
    @Expose
    @SerializedName("StatusCode")
    private String statuscode;
    @Expose
    @SerializedName("Sefer")
    private SeferEntity sefer;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public SeferEntity getSefer() {
        return sefer;
    }

    public void setSefer(SeferEntity sefer) {
        this.sefer = sefer;
    }

    public static class SeferEntity {
        @Expose
        @SerializedName("TARIH")
        private String tarih;
        @Expose
        @SerializedName("VARISSUBE")
        private String varissube;
        @Expose
        @SerializedName("TTINO")
        private String ttino;
        @Expose
        @SerializedName("SEFERID")
        private int seferid;
        @Expose
        @SerializedName("PLAKA")
        private String plaka;

        public String getTarih() {
            return tarih;
        }

        public void setTarih(String tarih) {
            this.tarih = tarih;
        }

        public String getVarissube() {
            return varissube;
        }

        public void setVarissube(String varissube) {
            this.varissube = varissube;
        }

        public String getTtino() {
            return ttino;
        }

        public void setTtino(String ttino) {
            this.ttino = ttino;
        }

        public int getSeferid() {
            return seferid;
        }

        public void setSeferid(int seferid) {
            this.seferid = seferid;
        }

        public String getPlaka() {
            return plaka;
        }

        public void setPlaka(String plaka) {
            this.plaka = plaka;
        }
    }
}

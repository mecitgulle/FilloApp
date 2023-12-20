package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AtfUndeliverableReasonModel {

    @Expose
    @SerializedName("SEBEPID")
    private String SebepId;
    @Expose
    @SerializedName("SEBEPTANIMI")
    private String SebepTanimi;

    public String getSebepId() {
        return SebepId;
    }

    public void setSebepId(String sebepId) {
        SebepId = sebepId;
    }

    public String getSebepTanimi() {
        return SebepTanimi;
    }

    public void setSebepTanimi(String sebepTanimi) {
        SebepTanimi = sebepTanimi;
    }
}

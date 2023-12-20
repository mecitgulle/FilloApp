package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AtfUndeliverableReasonResponseModel {

    @SerializedName("AtfUndeliverableReasonList")
    @Expose
    private List<AtfUndeliverableReasonModel> atfUndeliverableReasonModels;
    @SerializedName("StatusCode")
    @Expose
    private String statusCode;
    @SerializedName("Message")
    @Expose
    private String message;

    public List<AtfUndeliverableReasonModel> getAtfUndeliverableReasonModels() {
        return atfUndeliverableReasonModels;
    }

    public void setAtfUndeliverableReasonModels(List<AtfUndeliverableReasonModel> atfUndeliverableReasonModels) {
        this.atfUndeliverableReasonModels = atfUndeliverableReasonModels;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

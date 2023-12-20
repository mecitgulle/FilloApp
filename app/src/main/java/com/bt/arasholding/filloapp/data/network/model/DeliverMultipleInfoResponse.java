package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeliverMultipleInfoResponse {

    @SerializedName("StatusCode")
    @Expose
    private String statusCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("DeliverCargoInfoList")
    @Expose
    private List<DeliverMultipleInfo> deliverCargoInfoList;

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

    public List<DeliverMultipleInfo> getDeliverCargoInfoList() {
        return deliverCargoInfoList;
    }

    public void setDeliverCargoInfoList(List<DeliverMultipleInfo> deliverCargoInfoList) {
        this.deliverCargoInfoList = deliverCargoInfoList;
    }
}

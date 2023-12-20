package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DeliverMultipleCargoResponse implements Serializable {

    @SerializedName("StatusCode")
    @Expose
    private String statusCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("ResponseList")
    @Expose
    private List<DeliverMultipleCargoModel> responseList;

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

    public List<DeliverMultipleCargoModel> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<DeliverMultipleCargoModel> responseList) {
        this.responseList = responseList;
    }
}
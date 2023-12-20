package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerListResponseModel {
    @SerializedName("OptionList")
    @Expose
    private List<CustomerResponseModel> optionList;
    @SerializedName("StatusCode")
    @Expose
    private String statusCode;

    public List<CustomerResponseModel> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<CustomerResponseModel> optionList) {
        this.optionList = optionList;
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

    @SerializedName("Message")
    @Expose
    private String message;
}

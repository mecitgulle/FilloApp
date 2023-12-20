package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IslemSonucu {

    @SerializedName("IsSuccessed")
    @Expose
    private String isSuccessed;
    @SerializedName("Message")
    @Expose
    private String message;

    public String getIsSuccessed() {
        return isSuccessed;
    }

    public void setIsSuccessed(String isSuccessed) {
        this.isSuccessed = isSuccessed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

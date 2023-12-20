package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CargoMovementDetailResponse {

    @SerializedName("StatusCode")
    @Expose
    private String statusCode;
    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("CargoActionsDetails")
    @Expose
    private List<CargoMovementDetail> cargoMovementDetails ;

//    @SerializedName("CargoActionsDetailSummarys")
    @SerializedName("CargoActionsDetailBranchSummarys")
    @Expose
    private List<CargoMovementDetailSummarys> cargoMovementDetailSummarys ;

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

    public List<CargoMovementDetail> getCargoMovementDetails() {
        return cargoMovementDetails;
    }

    public void setCargoMovementDetails(List<CargoMovementDetail> cargoMovementDetails) {
        this.cargoMovementDetails = cargoMovementDetails;
    }

    public List<CargoMovementDetailSummarys> getCargoMovementDetailSummarys() {
        return cargoMovementDetailSummarys;
    }

    public void setCargoMovementDetailSummarys(List<CargoMovementDetailSummarys> cargoMovementDetailSummarys) {
        this.cargoMovementDetailSummarys = cargoMovementDetailSummarys;
    }
}

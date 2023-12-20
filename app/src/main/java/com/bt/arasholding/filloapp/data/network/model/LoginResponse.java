package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @Expose
    @SerializedName("StatusCode")
    private String statusCode;

    @Expose
    @SerializedName("user_id")
    private Long userId;

    @Expose
    @SerializedName("AccessToken")
    private String accessToken;

    @Expose
    @SerializedName("USERNAME")
    private String userName;

    @Expose
    @SerializedName("Message")
    private String message;

    @Expose
    @SerializedName("SUBEKODU")
    private String subeKodu;

    @Expose
    @SerializedName("SEFERID")
    private String seferId;

    @Expose
    @SerializedName("DOWNLOADURI")
    private String downloadUri;

    @Expose
    @SerializedName("GROUPNAME")
    private String groupName;

    @Expose
    @SerializedName("USERGROUPID")
    private String userGroupId;

    public String getSubeKodu() {
        return subeKodu;
    }

    public void setSubeKodu(String subeKodu) {
        this.subeKodu = subeKodu;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSeferId() {
        return seferId;
    }

    public void setSeferId(String seferId) {
        this.seferId = seferId;
    }

    public String getDownloadUri() { return downloadUri; }

    public void setDownloadUri(String downloadUri) { this.downloadUri = downloadUri; }

    public String getUserGroupId() {
        return userGroupId;
    }

    public String getGroupName() { return groupName; }
}

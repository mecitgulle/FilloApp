package com.bt.arasholding.filloapp.data.network.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    public static class ServerLoginRequest {

        @Expose
        @SerializedName("Username")
        private String userName;

        @Expose
        @SerializedName("Password")
        private String password;

        @Expose
        @SerializedName("DeviceToken")
        private String deviceToken;

        @Expose
        @SerializedName("DeviceName")
        private String deviceName;

        @Expose
        @SerializedName("VersionCode")
        private String VersionCode;


        public ServerLoginRequest(String userName, String password,String deviceToken,String deviceName, String versionCode) {
            this.userName = userName;
            this.password = password;
            this.deviceToken = deviceToken;
            this.VersionCode = versionCode;
            this.deviceName = deviceName;

            Gson gson = new Gson();
            String json = gson.toJson(this);
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getDeviceToken() {
            return deviceToken;
        }

        public void setDeviceToken(String deviceToken) {
            this.deviceToken = deviceToken;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceToken) {
            this.deviceToken = deviceName;
        }

        public String getVersionCode() {
            return VersionCode;
        }

        public void setVersionCode(String versionCode) {
            VersionCode = versionCode;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;

            ServerLoginRequest that = (ServerLoginRequest) object;

            if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
            return password != null ? password.equals(that.password) : that.password == null;

        }

        @Override
        public int hashCode() {
            int result = userName != null ? userName.hashCode() : 0;
            result = 31 * result + (password != null ? password.hashCode() : 0);
            return result;
        }
    }
}

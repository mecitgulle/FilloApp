package com.bt.arasholding.filloapp.data.prefs;

import com.bt.arasholding.filloapp.data.DataManager;

public interface PreferencesHelper {

    int getCurrentUserLoggedInMode();

    void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode);

    Long getCurrentUserId();

    void setCurrentUserId(Long userId);

    String getCurrentUserName();

    void setCurrentUserName(String userName);

    String getAccessToken();

    void setAccessToken(String accessToken);

    String getCurrentUserBranchCode();

    void setCurrentUserBranchCode(String branchCode);

    String getCurrentShipmentCode();

    void setCurrentShipmentCode(String shipmentCode);

    Boolean getRememberChecked();

    void setRememberChecked(Boolean checked);

    Boolean getRead();

    void setRead(Boolean isRead);

    Boolean getSelectedCamera();

    Boolean getSelectedBluetooth();

    Boolean getSelectedLazer();

    void setSelectedCamera(Boolean selectedCamera);

    void setSelectedBluetooth(Boolean selectedBluetooth);

    void setSelectedLazer(Boolean selectedLazer);

    String getCurrentBluetoothPairedDeviceName();

    void setCurrentBluetoothPairedDeviceName(String pairedDeviceName);

    void setLatitude(String latitude);

    String getLatitude();

    void setLongitude(String longitude);

    String getLongitude();

    void setGroupId(String groupId);

    String getGroupId();
}
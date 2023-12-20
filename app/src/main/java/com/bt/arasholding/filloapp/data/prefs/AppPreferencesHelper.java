package com.bt.arasholding.filloapp.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.bt.arasholding.filloapp.data.DataManager;
import com.bt.arasholding.filloapp.di.ApplicationContext;
import com.bt.arasholding.filloapp.di.PreferenceInfo;
import com.bt.arasholding.filloapp.utils.AppConstants;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE";
    private static final String PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID";
    private static final String PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME";
    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";
    private static final String PREF_KEY_CURRENT_BRANCH_CODE = "PREF_KEY_CURRENT_BRANCH_CODE";
    private static final String PREF_KEY_CURRENT_SHIPMENT_CODE = "PREF_KEY_CURRENT_SHIPMENT_CODE";
    private static final String PREF_KEY_REMEMBER_CHECKED = "PREF_KEY_REMEMBER_CHECKED";
    private static final String PREF_KEY_READ = "PREF_KEY_READ";
    private static final String PREF_KEY_SELECTED_CAMERA = "PREF_KEY_SELECTED_CAMERA";
    private static final String PREF_KEY_SELECTED_BLUETOOTH = "PREF_KEY_SELECTED_BLUETOOTH";
    private static final String PREF_KEY_SELECTED_LAZER = "PREF_KEY_SELECTED_LAZER";
    private static final String PREF_KEY_SELECTED_BLUETOOTH_DEVICE = "PREF_KEY_SELECTED_BLUETOOTH_DEVICE";
    private static final String PREF_KEY_LATITUDE = "PREF_KEY_LATITUDE";
    private static final String PREF_KEY_LONGITUDE = "PREF_KEY_LONGITUDE";
    private static final String PREF_KEY_GROUPID = "PREF_KEY_GROUPID";

    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(@ApplicationContext Context context,
                                @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPrefs.getInt(PREF_KEY_USER_LOGGED_IN_MODE,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType());
    }

    @Override
    public void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode) {
        mPrefs.edit().putInt(PREF_KEY_USER_LOGGED_IN_MODE, mode.getType()).apply();
    }

    @Override
    public Long getCurrentUserId() {
        long userId = mPrefs.getLong(PREF_KEY_CURRENT_USER_ID, AppConstants.NULL_INDEX);
        return userId == AppConstants.NULL_INDEX ? null : userId;
    }

    @Override
    public void setCurrentUserId(Long userId) {
        long id = userId == null ? AppConstants.NULL_INDEX : userId;
        mPrefs.edit().putLong(PREF_KEY_CURRENT_USER_ID, id).apply();
    }

    @Override
    public String getCurrentUserName() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_NAME, null);
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_NAME, userName).apply();
    }

    @Override
    public String getAccessToken() {
        return mPrefs.getString(PREF_KEY_ACCESS_TOKEN, null);
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPrefs.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken).apply();
    }

    @Override
    public String getCurrentUserBranchCode() {
        return mPrefs.getString(PREF_KEY_CURRENT_BRANCH_CODE, null);
    }

    @Override
    public void setCurrentUserBranchCode(String branchCode) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_BRANCH_CODE, branchCode).apply();
    }

    @Override
    public String getCurrentShipmentCode() {
        return mPrefs.getString(PREF_KEY_CURRENT_SHIPMENT_CODE, null);
    }

    @Override
    public void setCurrentShipmentCode(String shipmentCode) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_SHIPMENT_CODE, shipmentCode).apply();
    }

    @Override
    public Boolean getRememberChecked() {
        return mPrefs.getBoolean(PREF_KEY_REMEMBER_CHECKED, false);
    }

    @Override
    public void setRememberChecked(Boolean checked) {
        mPrefs.edit().putBoolean(PREF_KEY_REMEMBER_CHECKED, checked).apply();
    }

    @Override
    public Boolean getRead() {
        return mPrefs.getBoolean(PREF_KEY_READ, false);
    }

    @Override
    public void setRead(Boolean isRead) {
        mPrefs.edit().putBoolean(PREF_KEY_READ, isRead).apply();

    }

    @Override
    public Boolean getSelectedCamera() {
        return mPrefs.getBoolean(PREF_KEY_SELECTED_CAMERA, false);
    }

    @Override
    public Boolean getSelectedBluetooth() {
        return mPrefs.getBoolean(PREF_KEY_SELECTED_BLUETOOTH, false);
    }

    @Override
    public Boolean getSelectedLazer() {
        return mPrefs.getBoolean(PREF_KEY_SELECTED_LAZER, false);
    }

    @Override
    public void setSelectedCamera(Boolean selectedCamera) {
        mPrefs.edit().putBoolean(PREF_KEY_SELECTED_CAMERA, selectedCamera).apply();
    }
    @Override
    public void setSelectedBluetooth(Boolean selectedluetooth) {
        mPrefs.edit().putBoolean(PREF_KEY_SELECTED_BLUETOOTH, selectedluetooth).apply();
    }
    @Override
    public void setSelectedLazer(Boolean selectedLazer) {
        mPrefs.edit().putBoolean(PREF_KEY_SELECTED_LAZER, selectedLazer).apply();
    }

    @Override
    public String getCurrentBluetoothPairedDeviceName() {
        return mPrefs.getString(PREF_KEY_SELECTED_BLUETOOTH_DEVICE, null);
    }

    @Override
    public void setCurrentBluetoothPairedDeviceName(String pairedDeviceName) {
        mPrefs.edit().putString(PREF_KEY_SELECTED_BLUETOOTH_DEVICE, pairedDeviceName).apply();
    }

    @Override
    public void setLatitude(String latitude) {
        mPrefs.edit().putString(PREF_KEY_LATITUDE, latitude).apply();
    }

    @Override
    public String getLatitude() {
        return mPrefs.getString(PREF_KEY_LATITUDE, null);
    }

    @Override
    public void setLongitude(String longitude) {
        mPrefs.edit().putString(PREF_KEY_LONGITUDE, longitude).apply();
    }

    @Override
    public String getLongitude() {
        return mPrefs.getString(PREF_KEY_LONGITUDE, null);
    }

    @Override
    public void setGroupId(String groupId) {
        mPrefs.edit().putString(PREF_KEY_GROUPID, groupId).apply();
    }

    @Override
    public String getGroupId() {
        return mPrefs.getString(PREF_KEY_GROUPID, null);
    }
}

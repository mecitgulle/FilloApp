package com.bt.arasholding.filloapp.data;

import com.bt.arasholding.filloapp.data.db.DbHelper;
import com.bt.arasholding.filloapp.data.network.ApiHelper;
import com.bt.arasholding.filloapp.data.prefs.PreferencesHelper;

public interface DataManager extends DbHelper, ApiHelper , PreferencesHelper {

    void setUserAsLoggedOut();

    void updateUserInfo(
            String accessToken,
            Long userId,
            LoggedInMode loggedInMode,
            String userName,
            String subeKodu,
            String shipmentCode,
            String groupId
    );

    enum LoggedInMode {

        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_SERVER(1);

        private final int mType;

        LoggedInMode(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }
}
package com.bt.arasholding.filloapp.data;

import android.content.Context;

import com.bt.arasholding.filloapp.data.db.DbHelper;
import com.bt.arasholding.filloapp.data.model.Barcode;
import com.bt.arasholding.filloapp.data.network.ApiHeader;
import com.bt.arasholding.filloapp.data.network.ApiHelper;
import com.bt.arasholding.filloapp.data.network.model.AtfUndeliverableReasonModel;
import com.bt.arasholding.filloapp.data.network.model.AtfUndeliverableReasonResponseModel;
import com.bt.arasholding.filloapp.data.network.model.CargoBarcodeTextRequest;
import com.bt.arasholding.filloapp.data.network.model.CargoBarcodeTextResponse;
import com.bt.arasholding.filloapp.data.network.model.CargoDetailRequest;
import com.bt.arasholding.filloapp.data.network.model.CargoDetailResponse;
import com.bt.arasholding.filloapp.data.network.model.CargoMovementDetailResponse;
import com.bt.arasholding.filloapp.data.network.model.CargoMovementRequest;
import com.bt.arasholding.filloapp.data.network.model.CargoMovementResponse;
import com.bt.arasholding.filloapp.data.network.model.CompensationRequest;
import com.bt.arasholding.filloapp.data.network.model.CustomerListResponseModel;
import com.bt.arasholding.filloapp.data.network.model.CustomerResponseModel;
import com.bt.arasholding.filloapp.data.network.model.DeliverCargoImageUploadRequest;
import com.bt.arasholding.filloapp.data.network.model.DeliverCargoImageUploadResponse;
import com.bt.arasholding.filloapp.data.network.model.DeliverMultipleCargoResponse;
import com.bt.arasholding.filloapp.data.network.model.DeliverMultipleInfoResponse;
import com.bt.arasholding.filloapp.data.network.model.DeliveredCargoRequest;
import com.bt.arasholding.filloapp.data.network.model.DeliveredCargoResponse;
import com.bt.arasholding.filloapp.data.network.model.Deneme;
import com.bt.arasholding.filloapp.data.network.model.ExpeditionRouteAddressRequest;
import com.bt.arasholding.filloapp.data.network.model.ExpeditionRouteAddressResponse;
import com.bt.arasholding.filloapp.data.network.model.ExpeditionRouteRequest;
import com.bt.arasholding.filloapp.data.network.model.ExpeditionRouteResponse;
import com.bt.arasholding.filloapp.data.network.model.LoginRequest;
import com.bt.arasholding.filloapp.data.network.model.LoginResponse;
import com.bt.arasholding.filloapp.data.network.model.NoBarcodeCargoResponse;
import com.bt.arasholding.filloapp.data.network.model.NoBarcodeSaveAtfNoRequest;
import com.bt.arasholding.filloapp.data.network.model.NoBarcodeSaveAtfNoResponse;
import com.bt.arasholding.filloapp.data.network.model.NoBarcodeSaveRequest;
import com.bt.arasholding.filloapp.data.network.model.NoBarcodeSaveResponse;
import com.bt.arasholding.filloapp.data.network.model.TrackingRequest;
import com.bt.arasholding.filloapp.data.network.model.TrackingResponse;
import com.bt.arasholding.filloapp.data.prefs.PreferencesHelper;
import com.bt.arasholding.filloapp.di.ApplicationContext;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";

    private final Context mContext;
    private final ApiHelper mApiHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final DbHelper mDbHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context mContext, DbHelper dbHelper, ApiHelper mApiHelper, PreferencesHelper preferencesHelper) {
        this.mContext = mContext;
        this.mApiHelper = mApiHelper;
        this.mPreferencesHelper = preferencesHelper;
        this.mDbHelper = dbHelper;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }

    @Override
    public Single<TrackingResponse> doTrackingApiCall(TrackingRequest request) {
        return mApiHelper.doTrackingApiCall(request);
    }

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request) {
        return mApiHelper.doServerLoginApiCall(request);
    }

    @Override
    public Single<TrackingResponse> doShippingOutputApiCall(TrackingRequest request) {
        return mApiHelper.doShippingOutputApiCall(request);
    }

    @Override
    public Single<CargoDetailResponse> doCargoDetailApiCall(CargoDetailRequest request) {
        return mApiHelper.doCargoDetailApiCall(request);
    }

    @Override
    public Single<DeliverMultipleInfoResponse> doMultiCargoApiCall(CargoDetailRequest request) {
        return mApiHelper.doMultiCargoApiCall(request);
    }

    @Override
    public Single<DeliverMultipleCargoResponse> doMultiCargoDeliveredApiCall(DeliveredCargoRequest request) {
        return mApiHelper.doMultiCargoDeliveredApiCall(request);
    }

    @Override
    public Single<DeliveredCargoResponse> doDeliveredCargoApiCall(DeliveredCargoRequest request) {
        return mApiHelper.doDeliveredCargoApiCall(request);
    }

    @Override
    public Single<DeliverCargoImageUploadResponse> doDeliverCargoImageUploadApiCall(DeliverCargoImageUploadRequest request) {
        return mApiHelper.doDeliverCargoImageUploadApiCall(request);
    }

    @Override
    public Single<CargoMovementResponse> doCargoMovementApiCall(CargoMovementRequest request) {
        return mApiHelper.doCargoMovementApiCall(request);
    }

    @Override
    public Single<CargoMovementDetailResponse> doCargoMovementDetailApiCall(CargoDetailRequest cargoDetailRequest) {
        return mApiHelper.doCargoMovementDetailApiCall(cargoDetailRequest);
    }

    @Override
    public Single<NoBarcodeSaveAtfNoResponse> doNoBarcodeAtfNoApiCall(NoBarcodeSaveAtfNoRequest noBarcodeSaveAtfNoRequest) {
        return mApiHelper.doNoBarcodeAtfNoApiCall(noBarcodeSaveAtfNoRequest);
    }

    @Override
    public Single<DeliverMultipleCargoResponse> doMultiDeliveryApiCall(DeliveredCargoRequest teslimatKapatRequest) {
        return mApiHelper.doMultiDeliveryApiCall(teslimatKapatRequest);
    }
    @Override
    public Single<DeliverMultipleCargoResponse> doCustomerDelivery(DeliveredCargoRequest teslimatKapatRequest) {
        return mApiHelper.doCustomerDelivery(teslimatKapatRequest);
    }


    @Override
    public Single<DeliverCargoImageUploadResponse> doCompensationApiCall(CompensationRequest request) {
        return mApiHelper.doCompensationApiCall(request);
    }

    @Override
    public Single<DeliverCargoImageUploadResponse> doCompensationImageUploadApiCall(CompensationRequest request) {
        return mApiHelper.doCompensationImageUploadApiCall(request);
    }

    @Override
    public Single<NoBarcodeSaveResponse> doNoBarcodeApiCall(NoBarcodeSaveRequest request) {
        return mApiHelper.doNoBarcodeApiCall(request);
    }

    @Override
    public Single<CargoBarcodeTextResponse> getCargoBarcodeTextApiCall(CargoBarcodeTextRequest barcodeTextRequest) {
        return mApiHelper.getCargoBarcodeTextApiCall(barcodeTextRequest);
    }

    @Override
    public Single<AtfUndeliverableReasonResponseModel> getAtfUndeliverableReason(Deneme deneme) {
        return mApiHelper.getAtfUndeliverableReason(deneme);
    }

    @Override
    public Single<AtfUndeliverableReasonResponseModel> getCompensationReason(Deneme deneme) {
        return mApiHelper.getCompensationReason(deneme);
    }
    @Override
    public Single<CustomerListResponseModel> getCustomerList(Deneme deneme) {
        return mApiHelper.getCustomerList(deneme);
    }
    @Override
    public Single<CustomerListResponseModel> getAllCustomerList(Deneme deneme) {
        return mApiHelper.getAllCustomerList(deneme);
    }
    @Override
    public Single<NoBarcodeCargoResponse> getNoBarcodeList(Deneme deneme) {
        return mApiHelper.getNoBarcodeList(deneme);
    }

    @Override
    public Single<ExpeditionRouteResponse> getExpeditionRoute(ExpeditionRouteRequest model) {
        return mApiHelper.getExpeditionRoute(model);
    }

    @Override
    public Single<ExpeditionRouteAddressResponse> getExpeditionRouteAddress(ExpeditionRouteAddressRequest model) {
        return mApiHelper.getExpeditionRouteAddress(model);
    }

    @Override
    public void setUserAsLoggedOut() {
        mPreferencesHelper.setCurrentUserLoggedInMode(DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT);
    }

    @Override
    public void updateUserInfo(String accessToken, Long userId, LoggedInMode loggedInMode, String userName,String subeKodu,String shipmentCode,String  groupId) {
        setAccessToken(accessToken);
        setCurrentUserId(userId);
        setCurrentUserLoggedInMode(loggedInMode);
        setCurrentUserName(userName);
        setCurrentUserBranchCode(subeKodu);
        setCurrentShipmentCode(shipmentCode);
        setGroupId(groupId);
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public Long getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(Long userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public String getCurrentUserName() {
        return mPreferencesHelper.getCurrentUserName();
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPreferencesHelper.setCurrentUserName(userName);
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
    }

    @Override
    public String getCurrentUserBranchCode() {
        return mPreferencesHelper.getCurrentUserBranchCode();
    }

    @Override
    public void setCurrentUserBranchCode(String branchCode) {
        mPreferencesHelper.setCurrentUserBranchCode(branchCode);
    }

    @Override
    public String getCurrentShipmentCode() {
        return mPreferencesHelper.getCurrentShipmentCode();
    }

    @Override
    public void setCurrentShipmentCode(String shipmentCode) {
        mPreferencesHelper.setCurrentShipmentCode(shipmentCode);
    }


    @Override
    public Boolean getRememberChecked() {
        return mPreferencesHelper.getRememberChecked();
    }

    @Override
    public void setRememberChecked(Boolean checked) {
        mPreferencesHelper.setRememberChecked(checked);
    }

    @Override
    public Boolean getRead() {
        return mPreferencesHelper.getRead();
    }

    @Override
    public void setRead(Boolean isRead) {
        mPreferencesHelper.setRead(isRead);
    }

    @Override
    public Boolean getSelectedCamera() {
        return mPreferencesHelper.getSelectedCamera();
    }

    @Override
    public Boolean getSelectedBluetooth()  {
        return mPreferencesHelper.getSelectedBluetooth();
    }

    @Override
    public Boolean getSelectedLazer() {
        return mPreferencesHelper.getSelectedLazer();
    }

    @Override
    public void setSelectedCamera(Boolean selectedCamera) {
        mPreferencesHelper.setSelectedCamera(selectedCamera);
    }

    @Override
    public void setSelectedBluetooth(Boolean selectedBluetooth) {
        mPreferencesHelper.setSelectedBluetooth(selectedBluetooth);
    }

    @Override
    public void setSelectedLazer(Boolean selectedLazer) {
        mPreferencesHelper.setSelectedLazer(selectedLazer);
    }

    @Override
    public String getCurrentBluetoothPairedDeviceName() {
        return mPreferencesHelper.getCurrentBluetoothPairedDeviceName();
    }

    @Override
    public void setCurrentBluetoothPairedDeviceName(String pairedDeviceName) {
        mPreferencesHelper.setCurrentBluetoothPairedDeviceName(pairedDeviceName);
    }

    @Override
    public void setLatitude(String latitude) {
        mPreferencesHelper.setLatitude(latitude);
    }

    @Override
    public String getLatitude() {
        return mPreferencesHelper.getLatitude();
    }

    @Override
    public void setLongitude(String longitude) {
        mPreferencesHelper.setLongitude(longitude);
    }

    @Override
    public String getLongitude() {
        return mPreferencesHelper.getLongitude();
    }
    @Override
    public String getGroupId() {
        return mPreferencesHelper.getGroupId();
    }

    @Override
    public void setGroupId(String groupId) {
        mPreferencesHelper.setGroupId(groupId);
    }

    @Override
    public Observable<Boolean> saveBarcode(Barcode barcode) {
        return mDbHelper.saveBarcode(barcode);
    }

    @Override
    public Observable<List<Barcode>> getAllBarcodes() {
        return mDbHelper.getAllBarcodes();
    }

    @Override
    public Observable<List<Barcode>> getBarcodesByIslemTipi(int islemTipi) {
        return mDbHelper.getBarcodesByIslemTipi(islemTipi);
    }

    @Override
    public Observable<Boolean> deleteBarcodesByType(int islemTipi) {
        return mDbHelper.deleteBarcodesByType(islemTipi);
    }

    @Override
    public Observable<Boolean> updateBarcode(long id, String alindiMi) {
        return mDbHelper.updateBarcode(id,alindiMi);
    }

    @Override
    public Observable<Boolean> deleteBarcode(String barcode) {
        return mDbHelper.deleteBarcode(barcode);
    }


    @Override
    public Observable<Boolean> updateBarcodebyBarcodeType(Barcode barcode) {
        return mDbHelper.updateBarcodebyBarcodeType(barcode);
    }

    @Override
    public Barcode getBarcodesByBarcode(String barcode) {
        return mDbHelper.getBarcodesByBarcode(barcode);
    }

    @Override
    public Observable<Boolean> updateBarcodeHasarAciklama(long id, String aciklama) {
        return mDbHelper.updateBarcodeHasarAciklama(id,aciklama);
    }

    @Override
    public Observable<Boolean> updateBarcodePhoto(long id, String foto) {
        return mDbHelper.updateBarcodePhoto(id,foto);
    }


//    @Override
//    public Observable<List<Barcode>> getBarcodesByBarcode(String barcode) {
//        return null;
//    }

}
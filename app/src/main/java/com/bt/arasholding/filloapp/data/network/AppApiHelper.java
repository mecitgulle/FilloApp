package com.bt.arasholding.filloapp.data.network;

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
import com.bt.arasholding.filloapp.data.network.model.NoBarcodeCargo;
import com.bt.arasholding.filloapp.data.network.model.NoBarcodeCargoResponse;
import com.bt.arasholding.filloapp.data.network.model.NoBarcodeSaveAtfNoRequest;
import com.bt.arasholding.filloapp.data.network.model.NoBarcodeSaveAtfNoResponse;
import com.bt.arasholding.filloapp.data.network.model.NoBarcodeSaveRequest;
import com.bt.arasholding.filloapp.data.network.model.NoBarcodeSaveResponse;
import com.bt.arasholding.filloapp.data.network.model.TrackingRequest;
import com.bt.arasholding.filloapp.data.network.model.TrackingResponse;
import com.bt.arasholding.filloapp.utils.GsonUtils;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class AppApiHelper implements ApiHelper {

    private ApiHeader mApiHeader;

    @Inject
    public AppApiHelper(ApiHeader apiHeader) {
        mApiHeader = apiHeader;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

    @Override
    public Single<TrackingResponse> doTrackingApiCall(TrackingRequest request) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_TRACKING)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter(request)
                .build()
                .getObjectSingle(TrackingResponse.class);
    }

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_LOGIN)
                .addHeaders("Content-Type", "application/json")
                .addBodyParameter(request)
                .build()
                .getObjectSingle(LoginResponse.class);
    }

    @Override
    public Single<TrackingResponse> doShippingOutputApiCall(TrackingRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SHIPPING_OUTPUT)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(TrackingResponse.class);
    }

    @Override
    public Single<CargoDetailResponse> doCargoDetailApiCall(CargoDetailRequest request) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_CARGO_DETAIL)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter(request)
                .build()
                .getObjectSingle(CargoDetailResponse.class);
    }

    @Override
    public Single<DeliverMultipleInfoResponse> doMultiCargoApiCall(CargoDetailRequest request) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_MULTICARGO_DETAIL)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter(request)
                .build()
                .getObjectSingle(DeliverMultipleInfoResponse.class);
    }

    @Override
    public Single<DeliverMultipleCargoResponse> doMultiCargoDeliveredApiCall(DeliveredCargoRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_MULTICARGO_DELIVERED)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(DeliverMultipleCargoResponse.class);
    }

    @Override
    public Single<DeliveredCargoResponse> doDeliveredCargoApiCall(DeliveredCargoRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_DELIVER_CARGO)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(DeliveredCargoResponse.class);
    }

    @Override
    public Single<DeliverCargoImageUploadResponse> doDeliverCargoImageUploadApiCall(DeliverCargoImageUploadRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_DELIVER_CARGO_IMAGE_UPLOAD)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addJSONObjectBody(GsonUtils.toJsonObject(request))
                .build()
                .getObjectSingle(DeliverCargoImageUploadResponse.class);
    }


    @Override
    public Single<CargoMovementResponse> doCargoMovementApiCall(CargoMovementRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CARGO_MOVEMENT)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(CargoMovementResponse.class);
    }

    @Override
    public Single<CargoMovementDetailResponse> doCargoMovementDetailApiCall(CargoDetailRequest request) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_CARGO_MOVEMENT_DETAIL)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter(request)
                .build()
                .getObjectSingle(CargoMovementDetailResponse.class);
    }

    @Override
    public Single<NoBarcodeSaveAtfNoResponse> doNoBarcodeAtfNoApiCall(NoBarcodeSaveAtfNoRequest request) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_NO_BARCODE_ATFNO_ADD)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter(request)
                .build()
                .getObjectSingle(NoBarcodeSaveAtfNoResponse.class);
    }

    @Override
    public Single<DeliverMultipleCargoResponse> doMultiDeliveryApiCall(DeliveredCargoRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_MULTI_DELIVER_CARGO)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addJSONObjectBody(GsonUtils.toJsonObject(request))
                .build()
                .getObjectSingle(DeliverMultipleCargoResponse.class);
    }
    @Override
    public Single<DeliverMultipleCargoResponse> doCustomerDelivery(DeliveredCargoRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CUSTOMER_DELIVER_CARGO)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addJSONObjectBody(GsonUtils.toJsonObject(request))
                .build()
                .getObjectSingle(DeliverMultipleCargoResponse.class);
    }

    @Override
    public Single<DeliverCargoImageUploadResponse> doCompensationApiCall(CompensationRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_COMPENSATION_CREATE)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addJSONObjectBody(GsonUtils.toJsonObject(request))
                .build()
                .getObjectSingle(DeliverCargoImageUploadResponse.class);
    }
    @Override
    public Single<NoBarcodeSaveResponse> doNoBarcodeApiCall(NoBarcodeSaveRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_NO_BARCODE_ADD)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addJSONObjectBody(GsonUtils.toJsonObject(request))
                .build()
                .getObjectSingle(NoBarcodeSaveResponse.class);
    }

    @Override
    public Single<DeliverCargoImageUploadResponse> doCompensationImageUploadApiCall(CompensationRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_COMPENSATION_IMAGE_UPLOAD)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addJSONObjectBody(GsonUtils.toJsonObject(request))
                .build()
                .getObjectSingle(DeliverCargoImageUploadResponse.class);
    }

    @Override
    public Single<CargoBarcodeTextResponse> getCargoBarcodeTextApiCall(CargoBarcodeTextRequest barcodeTextRequest) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_CARGO_BARCODE_TEXT)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter(barcodeTextRequest)
                .build()
                .getObjectSingle(CargoBarcodeTextResponse.class);
    }


    @Override
    public Single<AtfUndeliverableReasonResponseModel> getAtfUndeliverableReason(Deneme deneme) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_UNDELIVERABLE_REASON)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter(deneme)
                .build()
                .getObjectSingle(AtfUndeliverableReasonResponseModel.class);
    }


    @Override
    public Single<AtfUndeliverableReasonResponseModel> getCompensationReason(Deneme deneme) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_COMPENSATION_REASON)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter(deneme)
                .build()
                .getObjectSingle(AtfUndeliverableReasonResponseModel.class);
    }

    @Override
    public Single<CustomerListResponseModel> getCustomerList(Deneme deneme) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_CUSTOMER_LIST)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter(deneme)
                .build()
                .getObjectSingle(CustomerListResponseModel.class);
    }
    @Override
    public Single<CustomerListResponseModel> getAllCustomerList(Deneme deneme) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_ALL_CUSTOMER_LIST)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter(deneme)
                .build()
                .getObjectSingle(CustomerListResponseModel.class);
    }

    @Override
    public Single<NoBarcodeCargoResponse> getNoBarcodeList(Deneme deneme) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_NO_BARCODE_CARGO_LIST)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter(deneme)
                .build()
                .getObjectSingle(NoBarcodeCargoResponse.class);
    }

    @Override
    public Single<ExpeditionRouteResponse> getExpeditionRoute(ExpeditionRouteRequest model) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_EXPEDITION_ROUTE)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter(model)
                .build()
                .getObjectSingle(ExpeditionRouteResponse.class);
    }

    @Override
    public Single<ExpeditionRouteAddressResponse> getExpeditionRouteAddress(ExpeditionRouteAddressRequest model) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_EXPEDITION_ROUTEADDRESS)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter(model)
                .build()
                .getObjectSingle(ExpeditionRouteAddressResponse.class);
    }
}
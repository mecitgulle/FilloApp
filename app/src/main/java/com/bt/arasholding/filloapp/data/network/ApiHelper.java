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
import com.bt.arasholding.filloapp.data.network.model.NoBarcodeCargo;
import com.bt.arasholding.filloapp.data.network.model.NoBarcodeCargoResponse;
import com.bt.arasholding.filloapp.data.network.model.NoBarcodeSaveAtfNoRequest;
import com.bt.arasholding.filloapp.data.network.model.NoBarcodeSaveAtfNoResponse;
import com.bt.arasholding.filloapp.data.network.model.NoBarcodeSaveRequest;
import com.bt.arasholding.filloapp.data.network.model.NoBarcodeSaveResponse;
import com.bt.arasholding.filloapp.data.network.model.TrackingRequest;
import com.bt.arasholding.filloapp.data.network.model.TrackingResponse;

import java.util.List;

import io.reactivex.Single;

public interface ApiHelper {

    ApiHeader getApiHeader();

    Single<TrackingResponse> doTrackingApiCall(TrackingRequest request);

    Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request);

    Single<TrackingResponse> doShippingOutputApiCall(TrackingRequest request);

    Single<CargoDetailResponse> doCargoDetailApiCall(CargoDetailRequest request);

    Single<DeliverMultipleInfoResponse> doMultiCargoApiCall(CargoDetailRequest request);

    Single<DeliverMultipleCargoResponse> doMultiCargoDeliveredApiCall(DeliveredCargoRequest request);

    Single<DeliveredCargoResponse> doDeliveredCargoApiCall(DeliveredCargoRequest request);

    Single<DeliverCargoImageUploadResponse> doDeliverCargoImageUploadApiCall(DeliverCargoImageUploadRequest request);

    Single<CargoMovementResponse> doCargoMovementApiCall(CargoMovementRequest request);

    Single<CargoMovementDetailResponse> doCargoMovementDetailApiCall(CargoDetailRequest cargoDetailRequest);

    Single<NoBarcodeSaveAtfNoResponse> doNoBarcodeAtfNoApiCall(NoBarcodeSaveAtfNoRequest noBarcodeSaveAtfNoRequest);

    Single<DeliverMultipleCargoResponse> doMultiDeliveryApiCall(DeliveredCargoRequest teslimatKapatRequest);

    Single<DeliverCargoImageUploadResponse> doCompensationApiCall(CompensationRequest request);

    Single<DeliverCargoImageUploadResponse> doCompensationImageUploadApiCall(CompensationRequest request);

    Single<NoBarcodeSaveResponse> doNoBarcodeApiCall(NoBarcodeSaveRequest request);

    Single<CargoBarcodeTextResponse> getCargoBarcodeTextApiCall(CargoBarcodeTextRequest barcodeTextRequest);

    Single<AtfUndeliverableReasonResponseModel> getAtfUndeliverableReason(Deneme deneme);

    Single<AtfUndeliverableReasonResponseModel> getCompensationReason(Deneme deneme);

    Single<CustomerListResponseModel> getCustomerList(Deneme deneme);

    Single<CustomerListResponseModel> getAllCustomerList(Deneme deneme);

    Single<ExpeditionRouteResponse> getExpeditionRoute(ExpeditionRouteRequest model);

    Single<NoBarcodeCargoResponse> getNoBarcodeList(Deneme deneme);

    Single<ExpeditionRouteAddressResponse> getExpeditionRouteAddress(ExpeditionRouteAddressRequest model);

    Single<DeliverMultipleCargoResponse> doCustomerDelivery (DeliveredCargoRequest teslimatKapatRequest);

}